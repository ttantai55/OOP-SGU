package BUS;

import DAO.CustomerDAO;
import DAO.InvoiceItemListDAO;
import DAO.InvoiceListDAO;
import DAO.PromotionListDAO;
import DAO.WarrantyListDAO;
import DTO.CartItemDTO;
import DTO.Cash;
import DTO.Credit;
import DTO.Customer;
import DTO.Employee;
import DTO.Installment;
import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;
import DTO.PaymentDTO;
import DTO.ProductsDTO;
import DTO.PromotionDTO;
import DTO.Transfer;
import DTO.WarrantyDTO;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class InvoiceListBUS {
    private static final String FILE_INVOICE      = "data/Invoice.txt";
    private static final String FILE_INVOICE_ITEM = "data/InvoiceItem.txt";

    static Scanner sc = new Scanner(System.in);

    private final InvoiceListDAO     invDAO;
    private final InvoiceItemListDAO invItemDAO;
    private final CustomerDAO        customerDAO;
    private final CustomerService    customerService;
    private final EmployeeService    employeeService;
    private final ProductListBUS     productBUS;
    private final PromotionListDAO   promotionDAO;
    private final WarrantyListDAO    warrantyDAO;

    public InvoiceListBUS() {
        invDAO          = new InvoiceListDAO();
        invItemDAO      = new InvoiceItemListDAO();
        customerDAO     = new CustomerDAO();
        customerService = new CustomerService();
        employeeService = new EmployeeService();
        productBUS      = new ProductListBUS();
        promotionDAO    = new PromotionListDAO();
        warrantyDAO     = new WarrantyListDAO();
        
        invDAO.readFile(FILE_INVOICE);
        invItemDAO.readFile(FILE_INVOICE_ITEM);
        
        customerService.loadFromFile();
        employeeService.loadFromFile();
    }


    // ========= LOAD / SAVE =========

    public void loadFile() {
        invDAO.readFile(FILE_INVOICE);
        invItemDAO.readFile(FILE_INVOICE_ITEM);
        System.out.println("Da tai du lieu thanh cong tu file: " + FILE_INVOICE + " va " + FILE_INVOICE_ITEM);
    }

    public void saveFile() {
        invDAO.writeFile(FILE_INVOICE);
        invItemDAO.writeFile(FILE_INVOICE_ITEM);
        System.out.println("Da luu du lieu vao file: " + FILE_INVOICE + " va " + FILE_INVOICE_ITEM);
    }


    // ========= TIM KIEM =========

    public InvoiceDTO findById(String id) { //tìm hóa đơn theo id
        return invDAO.findById(id);
    }


    // ========= THEM HOA DON =========

    public void inputInvoice() {
        String id;
        while (true) {
            System.out.print("Nhap ma hoa don: ");
            id = sc.nextLine();
            if (invDAO.findById(id) != null) {
                System.out.println("Ma hoa don da ton tai! Vui long nhap ma khac.");
            } else {
                break;
            }
        }

        Customer cus = selectCustomer();
        Employee emp = selectEmployee();

        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setInvoiceId(id);
        invoice.setCustomer(cus);
        invoice.setEmployee(emp);
        invoice.setCreatedDate(new Date());
        invoice.setInvoiceItemList(collectItems(id));

        double total       = InvoiceListDAO.calculateTotalPrice(invoice);
        PaymentDTO payment = paymentMethod(id, total);

        if (payment != null) {
            invoice.setPayment(payment);
            commitInvoice(invoice);
            System.out.println("Da them hoa don " + id + " thanh cong!");
        } else {
            System.out.println("Thanh toan that bai. Hoa don khong duoc luu.");
        }
    }
// thêm khách hàng vào hóa đơn
  private Customer selectCustomer() {
        Customer cus = null;
        while (cus == null) {
            System.out.print("Nhap ma khach hang: ");
            cus = customerService.findById(sc.nextLine());
            if (cus == null) System.out.println("Loi: Khong tim thay khach hang. Vui long nhap lai!");
        }
        return cus;
    }

    private Employee selectEmployee() {
        Employee emp = null;
        while (emp == null) {
            System.out.print("Nhap ma nhan vien: ");
            emp = employeeService.findById(sc.nextLine());
            if (emp == null) System.out.println("Loi: Khong tim thay nhan vien. Vui long nhap lai!");
        }
        return emp;
    }
// tạo số lượng sản phẩm trong 1 hóa đơn
    private InvoiceItemDTO[] collectItems(String invoiceId) {
        InvoiceItemDTO[] items = new InvoiceItemDTO[0];
        String themTiep = "y";
        int soThuTu = 1;
        while (themTiep.equals("y")) {
            System.out.println("Mat hang thu: " + soThuTu);
            InvoiceItemDTO item = buildSingleItem(invoiceId);
            if (item == null) continue;

            items = Arrays.copyOf(items, items.length + 1);
            items[items.length - 1] = item;
            soThuTu++;

            System.out.print("Them mat hang tiep theo? (y/n): ");
            themTiep = sc.nextLine().toLowerCase();
        }
        return items;
    }
// tạo 1 sản phẩm trong hóa đơn
    private InvoiceItemDTO buildSingleItem(String invoiceId) {
        System.out.print("  Ma san pham: ");
        ProductsDTO product = productBUS.getProductByID(sc.nextLine());
        if (product == null) {
            System.out.println("  Khong tim thay san pham! Vui long nhap lai.");
            return null;
        }

        System.out.print("  So luong: ");
        int quantity;
        try {
            quantity = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("  Loi: Vui long nhap so nguyen!");
            return null;
        }
        if (quantity <= 0) {
            System.out.println("  So luong khong hop le! Vui long nhap lai.");
            return null;
        }

        InvoiceItemDTO item = new InvoiceItemDTO();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setWarranty(createWarranty(invoiceId, product));

        System.out.print("  Co ap dung khuyen mai? (y/n): ");
        if (sc.nextLine().toLowerCase().equals("y")) {
            System.out.print("  Nhap ma khuyen mai: ");
            PromotionDTO promo = promotionDAO.findById(sc.nextLine());
            if (promo != null && promo.isStatus()) {
                item.setPromotion(promo);
            } else {
                System.out.println("  Khuyen mai khong hop le hoac da bi huy, bo qua.");
                item.setPromotion(null);
            }
        } else {
            item.setPromotion(null);
        }

        return item;
    }

    private WarrantyDTO createWarranty(String invoiceId, ProductsDTO product) {
        String warrantyId = invoiceId + "-WAR-" + product.getProductID();
        Date startDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, product.getWarrantyPeriod());
        return new WarrantyDTO(warrantyId, invoiceId, product, startDate, cal.getTime(), true);
    }

    private void commitInvoice(InvoiceDTO invoice) {
        invDAO.add(invoice);
        for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
            if (item != null) {
                item.setInvoiceId(invoice.getInvoiceId());
                if (item.getWarranty() != null) warrantyDAO.add(item.getWarranty());
                invItemDAO.add(item);
            }
        }
        saveFile();
    }


    // ========= HUY HOA DON =========

    public void cancelInvoice() {
        System.out.print("Nhap ma hoa don can huy: ");
        String id = sc.nextLine();
        invDAO.remove(id);
        saveFile();
    }


    // ========= HIEN THI =========

    public void displayAll() {
        invDAO.displayAll();
    }

    public void printInvoice(String invoiceId) {
        InvoiceDTO invoice = invDAO.findById(invoiceId);
        if (invoice == null) {
            System.out.println("Khong tim thay hoa don: " + invoiceId + ".");
            return;
        }
        InvoiceItemDTO[] items = invItemDAO.findByInvoiceId(invoiceId);

        printInvoiceHeader(invoice);
        double tongCong = printInvoiceItems(items);
        printPaymentInfo(invoice.getPayment(), tongCong);
        System.out.println("=".repeat(115) + "\n");
    }

    private void printInvoiceHeader(InvoiceDTO invoice) {
        System.out.println("\n" + "=".repeat(115));
        System.out.println("            HOA DON BAN HANG             ");
        System.out.println(" Ma HD: "              + invoice.getInvoiceId());
        System.out.println(" ID Khach hang: "      + invoice.getCustomerId());
        System.out.println(" Ten Khach hang: "     + invoice.getCustomerName());
        System.out.println(" ID Nhan vien: "       + invoice.getEmployeeId());
        System.out.println(" Ten Nhan vien: "      + invoice.getEmployeeName());
        System.out.println(" Ngay tao: "           + new SimpleDateFormat("dd/MM/yyyy").format(invoice.getCreatedDate()));
        System.out.println(" Trang thai hoa don: " + invoice.isStatus());
        System.out.println("-".repeat(115));
        System.out.printf("%-4s | %-10s | %-20s | %-4s | %-13s | %-8s | %-15s | %-15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Don Gia", "KM(%)", "Bao Hanh", "Thanh Tien");
        System.out.println("-".repeat(115));
    }

    private double printInvoiceItems(InvoiceItemDTO[] items) {
        double tongCong = 0;
        for (int i = 0; i < items.length; i++) {
            InvoiceItemDTO item = items[i];
            if (item == null) continue;
            double thanhTien  = InvoiceItemListDAO.calculateSubTotal(item);
            String kmInfo     = item.getPromotion() != null ? (int) item.getPromotion().getDiscountPercent() + "%" : "N/A";
            String thoiGianBH = item.getProduct().getWarrantyPeriod() + "thang";
            System.out.printf("%-4d | %-10s | %-20s | %-4d | %-13.0f | %-8s | %-15s | %,15.0f VND%n",
                    (i + 1), item.getProductId(), item.getProductName(),
                    item.getQuantity(), item.getUnitPrice(), kmInfo, thoiGianBH, thanhTien);
            tongCong += thanhTien;
        }
        System.out.println("-".repeat(115));
        System.out.printf("%-80s TONG CONG: %,15.0f VND%n", "", tongCong);
        return tongCong;
    }

    private void printPaymentInfo(PaymentDTO pay, double tongCong) {
        if (pay == null) {
            System.out.println("  - Hinh thuc: Chua thanh toan");
            return;
        }
        System.out.println(" THONG TIN THANH TOAN:");
        if (pay instanceof Cash) {
            Cash c = (Cash) pay;
            System.out.println("  - Hinh thuc: Tien mat");
            System.out.printf("  - Tien khach dua: %,.0f VND%n", c.getCashReceived());
            System.out.printf("  - Tien tra lai:   %,.0f VND%n", c.getCashReceived() - tongCong);
        } else if (pay instanceof Credit) {
            Credit cr = (Credit) pay;
            System.out.println("  - Hinh thuc: The tin dung");
            System.out.println("  - Ngan hang: " + cr.getBank());
            System.out.println("  - So the:    ****" + cr.getNumberId().substring(Math.max(0, cr.getNumberId().length() - 4)));
            System.out.println("  - Chu the:   " + cr.getNameOnCard());
        } else if (pay instanceof Transfer) {
            Transfer t = (Transfer) pay;
            System.out.println("  - Hinh thuc: Chuyen khoan");
            System.out.println("  - Ngan hang: " + t.getBank());
            System.out.println("  - So TK:     " + t.getAccountNumber());
            System.out.println("  - Chu TK:    " + t.getAccountName());
        } else if (pay instanceof Installment) {
            Installment ins = (Installment) pay;
            System.out.println("  - Hinh thuc: Tra gop");
            System.out.println("  - Cong ty TC: " + ins.getFinanceCompanyName());
            System.out.println("  - Ky han:     " + ins.getInstallmentMonths() + " thang");
            System.out.printf("  - Tra truoc:  %,.0f VND%n", ins.getDownPayment());
            System.out.printf("  - Gop moi thang: %,.0f VND%n", (tongCong - ins.getDownPayment()) / ins.getInstallmentMonths());
        }
    }


// In tất cả hóa đơn mà 1 khách hàng nào đó từng mua (nhập Id)

    public void printInvoicesByCustomer() {
        System.out.print("Nhap ma khach hang: ");
        String customerId = sc.nextLine();

        InvoiceDTO[] results = invDAO.findByCustomerId(customerId);
        if (results == null || results.length == 0) {
            System.out.println("Khong co hoa don nao cua khach hang: " + customerId);
            return;
        }
        printInvoiceTable("DANH SACH HOA DON - KHACH HANG: " + customerId, results);
    }

// nhập tên khách hàng để in tất cả hóa đơn mà khách hàng đó từng mua

    public void printInvoicesByCustomerName() {
        System.out.print("Nhap ten khach hang can tra cuu: ");
        String customerName = sc.nextLine();

        InvoiceDTO[] results = invDAO.findByName(customerName);
        if (results == null || results.length == 0) {
            System.out.println("Khong co hoa don nao voi ten khach hang chua: " + customerName);
            return;
        }
        printInvoiceTable("DANH SACH HOA DON - TEN KHACH HANG: " + customerName, results);
    }



    private void printInvoiceTable(String title, InvoiceDTO[] results) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(85));
        System.out.println(title);
        System.out.println("-".repeat(85));
        System.out.printf("%-10s | %-15s | %-15s | %-12s | %-10s | %-15s%n",
                "Ma HD", "Ma KH", "Ten KH", "Ngay Tao", "Trang Thai", "Tong Tien");
        System.out.println("-".repeat(85));
        for (InvoiceDTO inv : results) {
            if (inv != null) {
                String trangThai = inv.isStatus() ? "Hoat dong" : "Da huy";
                System.out.printf("%-10s | %-15s | %-15s | %-12s | %-10s | %,15.0f VND%n",
                        inv.getInvoiceId(), inv.getCustomerId(), inv.getCustomerName(),
                        sdf.format(inv.getCreatedDate()), trangThai,
                        InvoiceListDAO.calculateTotalPrice(inv));
            }
        }
        System.out.println("=".repeat(85) + "\n");
    }


    // =======Ham thanh toan=======

    public void checkoutFromCart(String username, CartItemDTO[] myCart, CartBUS cartBUS) {
        // Kiem tra

        if (myCart == null || myCart.length == 0) {
            System.out.println("Gio hang cua ban dang trong, moi quay lai mua hang");
            return;
        }
        // Vi khi xuat hoa don can lay thong tin chi tiet nen tao 1 doi tuong de lay thong tin
        Customer cus = customerDAO.findByUsername(username);
        if (cus == null) {
            System.out.println("Khong tim thay du lieu khach hang");
            return;
        }

        //==== Khoi tao hoa don====
        String newInvoiceId = "HD" + System.currentTimeMillis();//ham nay tu tao ma hoa don ngay tai thoi diem bam tao
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setInvoiceId(newInvoiceId);
        invoice.setCreatedDate(new Date());
        invoice.setCustomer(cus);


        //Tao 1 ham lay thong tin nhan vien dang ban hang

        Employee emp = employeeService.findById("NV01"); //  tam thoi de nhu vay
        invoice.setEmployee(emp);

        // Lay du lieu tu gio hang mang sang Chi tiet Hoa don
        myCart = cartBUS.getMyCartItems();
        InvoiceItemDTO[] invoiceDetail = new InvoiceItemDTO[myCart.length];

        for (int i = 0; i < invoiceDetail.length; i++) {
            CartItemDTO cartItem = myCart[i];

            InvoiceItemDTO item = new InvoiceItemDTO();
            item.setInvoiceId(newInvoiceId);
            item.setQuantity(cartItem.getQuantity());

            ProductsDTO product = productBUS.getProductByID(cartItem.getProductID());
            item.setProduct(product);

            item.setPromotion(null); // de tam
            item.setWarranty(null); // de tam

            invoiceDetail[i] = item;

        }
        invoice.setInvoiceItemList(invoiceDetail);

        //===Tinh tien===
        double total = InvoiceListDAO.calculateTotalPrice(invoice);
        System.out.printf("\n---> TONG CAN THANH TOAN: %,.0f VND <---\n", total);

        PaymentDTO payment = paymentMethod(newInvoiceId, total);

        if (payment != null) {
            // Luu hoa don
            invoice.setPayment(payment);
            invDAO.add(invoice);

            for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
                invItemDAO.add(item);
                // productBus.deductStock(item.getProductId(), item.getQuantity()); <-- CODE TRỪ KHO ĐỂ Ở ĐÂY
            }

            //Don dep gio hang sau khi thanh toan xong
            cartBUS.clearMyCart();
            saveFile();
            System.out.println("Thanh toan thanh cong!");
        }
        else {
            System.out.println("Thanh toan that bai! Vui long thu lai sau");
        }
    }

    public PaymentDTO paymentMethod(String invoiceID, double total) {
        System.out.println("Chon phuong thuc thanh toan:");
        System.out.println(" 1. Tien mat (Cash)");
        System.out.println(" 2. The tin dung (Credit)");
        System.out.println(" 3. Chuyen khoan (Transfer)");
        System.out.println(" 4. Tra gop (Installment)");
        System.out.print("-> Lua chon (1-4, hoac phim khac de Huy): ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Da huy thanh toan!");
            return null;
        }

        String paymentId = invoiceID + "-Pay";
        Date paymentDate = new Date();

        switch (choice) {
            case 1:
                System.out.printf("Tong can thanh toan: %,.0f VND%n", total);
                System.out.print("So tien khach dua: ");
                double cashReceived = Double.parseDouble(sc.nextLine());
                if (cashReceived < total) {
                    System.out.println("Loi: Thanh toan that bai!");
                    return null;
                }
                return new Cash(paymentId, paymentDate, cashReceived);
            case 2:
                System.out.println("Ngan hang: ");
                String bankCredit = sc.nextLine();
                System.out.println("So the: ");
                String numId = sc.nextLine();
                System.out.println("Ten chu the: ");
                String nameCard = sc.nextLine();
                return new Credit(paymentId, paymentDate, numId, nameCard, bankCredit);
            case 3:
                System.out.print("Ngan hang: ");
                String bankTrans = sc.nextLine();
                System.out.print("So tai khoan: ");
                String accNum = sc.nextLine();
                System.out.print("Ten chu tai khoan: ");
                String accName = sc.nextLine();
                return new Transfer(paymentId, paymentDate, accNum, accName, bankTrans);
            case 4:
                System.out.print("Cong ty tai chinh: ");
                String company = sc.nextLine();
                System.out.print("So thang tra gop: ");
                int months = Integer.parseInt(sc.nextLine());
                System.out.print("Tien tra truoc: ");
                double downPayment = Double.parseDouble(sc.nextLine());
                return new Installment(paymentId, paymentDate, company, "HD-GOP", months, downPayment);
            default:
                System.out.println("Da huy thanh toan!");
                return null;
        }
    }
}
