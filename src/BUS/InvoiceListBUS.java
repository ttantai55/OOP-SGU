package BUS;

import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import DAO.ProductListDAO;
import DAO.InvoiceListDAO;
import DAO.PromotionListDAO;
import DAO.WarrantyListDAO;
import DAO.AccountDAO;
import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;

import DTO.Customer;
import DTO.ProductsDTO;
import DTO.Employee;
import DTO.PromotionDTO;
import DTO.WarrantyDTO;
import DTO.PaymentDTO;
import DTO.Cash;
import DTO.Credit;
import DTO.Transfer;
import DTO.Installment;
import java.text.SimpleDateFormat;
import DAO.InvoiceItemListDAO;
import DTO.CartItemDTO;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class InvoiceListBUS {
    private final String filePath = "src/data/InvoiceItem.txt";
    private final String FILE_PRODUCT = "src/data/product.txt";
    Scanner sc = new Scanner(System.in);

    private  InvoiceListDAO invDAO ;
    private  InvoiceItemListDAO invItemDAO;
    private  CustomerDAO customerDAO ;
    private  EmployeeDAO employeeDAO ;
    private  ProductListDAO productsDAO;
    private  PromotionListDAO promotionDAO;
    private  WarrantyListDAO warrantyDAO;
    private  AccountDAO accountDAO;

    public InvoiceListBUS(){
        this.invDAO = new InvoiceListDAO();
        this.customerDAO = new CustomerDAO();
        this.employeeDAO = new EmployeeDAO();
        this.productsDAO = new ProductListDAO();
        this.invItemDAO = new InvoiceItemListDAO();
        this.promotionDAO = new PromotionListDAO();
        this.warrantyDAO = new WarrantyListDAO();
        this.accountDAO = new AccountDAO();
        
        
        invDAO.readFile(filePath);
        invItemDAO.readFile("src/data/InvoiceItem.txt");
        customerDAO.readFile("src/data/Customer.txt");
        accountDAO.readFile("src/data/accounts.txt");
        productsDAO.readFile("src/data/product.txt");
        employeeDAO.readFile("src/data/Employee.txt");
    }

    private static final String FILE_INVOICE = "src/data/Invoice.txt";
    private static final String FILE_INVOICE_ITEM = "src/data/InvoiceItem.txt";

    // ==================== LOAD / SAVE ====================

    public void loadFile() {
        invDAO.readFile(FILE_INVOICE);
        invItemDAO.readFile(FILE_INVOICE_ITEM);

        // Liên kết các chi tiết hóa đơn vào hóa đơn cha
        // + Resolve stub Customer/Employee thành đối tượng thật từ DAO
        InvoiceDTO[] allInvoices = invDAO.getAll();
        for (InvoiceDTO inv : allInvoices) {
            if (inv != null) {
                // Liên kết items
                InvoiceItemDTO[] items = invItemDAO.findByInvoiceId(inv.getInvoiceId());
                inv.setInvoiceItemList(items);

                // Resolve Customer thật từ CustomerDAO (thay stub chỉ có ID)
                Customer realCustomer = customerDAO.findById(inv.getCustomerId());
                if (realCustomer != null) {
                    inv.setCustomer(realCustomer);
                }

                // Resolve Employee thật từ EmployeeDAO (thay stub chỉ có ID)
                Employee realEmployee = employeeDAO.findById(inv.getEmployeeId());
                if (realEmployee != null) {
                    inv.setEmployee(realEmployee);
                }
            }
        }
    }

    public void saveFile() {
        invDAO.writeFile(FILE_INVOICE);
        invItemDAO.writeFile(FILE_INVOICE_ITEM);
        productsDAO.writeFile(FILE_PRODUCT);
    }

    // *nhập hóa đơn*
    // mã hóa đơn
    // mã khách
    // tên khách
    // mã nhân viên
    // tên nhân viên
    // ngày tạo
    // trạng thái hóa đơn
    // nhập số lượng mặt hàng:
    // - mặt hàng thứ n
    // + mã IMEI sản phẩm n
    // + giá bán sản phẩm n
    // + số lượng sản phẩm n
    // + khuyến mãi của sản phẩm n
    // + mã bảo hành của sản phẩm n
    // + tổng tiền của sản phẩm n
    // Tổng tiền tất cả (n1 + n2 + ... +nn)
    // phương thức thanh toán

    public void inputInvoice() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("      TAO HOA DON BAN HANG TAI QUAY");
        System.out.println("=".repeat(50));

        InvoiceDTO invoice = new InvoiceDTO();

        // 1. Tự động sinh mã Hóa đơn (Nhân viên không cần gõ tay)
        String newInvoiceId = "HD" + System.currentTimeMillis();
        System.out.println("-> Ma hoa don: " + newInvoiceId);
        invoice.setInvoiceId(newInvoiceId);

        // 2. Nhập Khách hàng (Hỗ trợ nhập khách vãng lai)
        Customer cus = null;
        while (cus == null) {
            System.out.print("Nhap Ma Khach Hang (VD: KH001) hoac phim '0' neu la khach vang lai: ");
            String cusId = sc.nextLine().trim();
            
            if (cusId.equals("0")) {
                cus = new Customer("KH000", 0, "Moi", new Date());
                cus.setFullName("Khach Vang Lai");
                break; // Thoát vòng lặp, tạo khách ảo thành công
            }
            
            cus = customerDAO.findById(cusId);
            if (cus == null) {
                System.out.println("[Loi] Khong tim thay khach hang trong he thong. Vui long nhap lai!");
            }
        }
        invoice.setCustomer(cus);

        // 3. Nhập Nhân viên
        Employee emp = null;
        while (emp == null) {
            System.out.print("Nhap Ma Nhan Vien thuc hien (VD: NV01): ");
            String empId = sc.nextLine().trim();
            emp = employeeDAO.findById(empId);

            if (emp == null) {
                System.out.println("[Loi] Khong tim thay nhan vien. Vui long nhap lai!");
            }
        }
        invoice.setEmployee(emp);
        invoice.setCreatedDate(new Date());

        // 4. Quét mã sản phẩm liên tục đưa vào hóa đơn
        InvoiceItemDTO[] details = new InvoiceItemDTO[0];
        int soThuTu = 1;
        while (true) {
            System.out.println("\n--- Mat hang thu: " + soThuTu + " ---");
            InvoiceItemDTO detail = new InvoiceItemDTO();
            detail.setInvoiceId(newInvoiceId);

            System.out.print("  Nhap Ma SP (Hoac '0' de ket thuc quet): ");
            String productId = sc.nextLine().trim();
            
            if (productId.equals("0")) {
                break;
            }

            ProductsDTO product = productsDAO.findById(productId);
            if (product == null) {
                System.out.println("  [Loi] Khong tim thay san pham!");
                continue;
            }
            detail.setProduct(product);

            System.out.print("  So luong: ");
            int quantity;
            try {
                quantity = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("  [Loi] So luong phai la so!");
                continue;
            }

            if (quantity <= 0) {
                System.out.println("  [Loi] So luong khong hop le!");
                continue;
            }
            detail.setQuantity(quantity);

            // Bảo hành tự động
            String warrantyId = newInvoiceId + "-WAR-" + product.getProductID();
            Date startDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, product.getWarrantyPeriod());
            WarrantyDTO warranty = new WarrantyDTO(warrantyId, newInvoiceId, product, startDate, cal.getTime(), true);
            warrantyDAO.add(warranty);
            detail.setWarranty(warranty);

            // Khuyến mãi
            System.out.print("  Co ap dung ma khuyen mai? (y/n): ");
            String promoChoice = sc.nextLine().trim().toLowerCase();
            if (promoChoice.equals("y")) {
                System.out.print("  Nhap ma khuyen mai: ");
                PromotionDTO promotion = promotionDAO.findById(sc.nextLine().trim());
                detail.setPromotion(promotion);
                if (promotion == null) System.out.println("  [Loi] Ma KM khong hop le, bo qua giam gia.");
            } else {
                detail.setPromotion(null);
            }

            // Đẩy vào mảng
            details = Arrays.copyOf(details, details.length + 1);
            details[details.length - 1] = detail;
            soThuTu++;
        }

        if (details.length == 0) {
            System.out.println("[Thong bao] Chua co san pham nao duoc them. Huy tao hoa don.");
            return;
        }
        invoice.setInvoiceItemList(details);

        // 5. Tổng kết và Thanh toán
        System.out.println("\n" + "-".repeat(50));
        double total = InvoiceListDAO.calculateTotalPrice(invoice);
        System.out.printf("---> TONG TIEN CAN THANH TOAN: %,.0f VND <---\n", total);

        PaymentDTO payment = paymentMethod(newInvoiceId, total);

        if (payment != null) {
            invoice.setPayment(payment);
            invDAO.add(invoice);

            for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
                invItemDAO.add(item);
                // GHI CHÚ: Gọi hàm trừ số lượng tồn kho (deductStock) ở đây nếu bạn đã code
            }
            System.out.println("\n[THANH CONG] Da luu hoa don " + newInvoiceId + " vao he thong!");
            saveFile(); // Lưu cứng xuống ổ đĩa
        } else {
            System.out.println("\n[Loi] Thanh toan that bai. Hoa don bi huy.");
        }
    }
            

    public void printInvoice(String invoiceId) {
        InvoiceDTO invoice = invDAO.findById(invoiceId);
        if (invoice == null) {
            System.out.println("Không tìm thấy hóa đơn: " + invoiceId + ".");
            return;
        }

        InvoiceItemDTO[] items = invItemDAO.findByInvoiceId(invoiceId);

        System.out.println("\n" + "=".repeat(115));
        System.out.println("            HÓA ĐƠN BÁN HÀNG             ");
        System.out.println(" Mã HD: " + invoice.getInvoiceId());
        System.out.println(" ID Khách hàng: " + invoice.getCustomerId());
        System.out.println(" Tên Khách hàng: " + invoice.getCustomerName());
        System.out.println(" ID Nhân viên: " + invoice.getEmployeeId());
        System.out.println(" Tên Nhân viên: " + invoice.getEmployeeName());
        System.out.println(" Ngày tạo: " + new SimpleDateFormat("dd/MM/yyyy").format(invoice.getCreatedDate()));
        System.out.println(" Trạng thái hóa đơn: " + invoice.isStatus());
        System.out.println("-".repeat(115));
        System.out.printf("%-4s | %-10s | %-20s | %-4s | %-13s | %-8s | %-15s | %-15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Don Gia", "KM(%)", "Bao Hanh", "Thanh Tien");
        System.out.println("-".repeat(115));

        double tongCong = 0;
        for (int i = 0; i < items.length; i++) {
            InvoiceItemDTO item = items[i];
            if (item == null)
                continue;

            double thanhTien = InvoiceItemListDAO.calculateSubTotal(item);

            String kmInfo;
            if (item.getPromotion() != null) {
                kmInfo = (int) item.getPromotion().getDiscountPercent() + "%";
            } else {
                kmInfo = "N/A";
            }

            String thoiGianBH = item.getProduct().getWarrantyPeriod() + "thang";
            System.out.printf("%-4d | %-10s | %-20s | %-4d | %-13.0f | %-8s | %-15s | %,15.0f VND%n",
                    (i + 1),
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    kmInfo,
                    thoiGianBH,
                    thanhTien);

            tongCong += thanhTien;
        }

        System.out.println("-".repeat(115));
        System.out.printf("%-80s TỔNG CỘNG: %,15.0f VNĐ%n", "", tongCong);

        PaymentDTO pay = invoice.getPayment();
        if (pay != null) {
            System.out.println(" THÔNG TIN THANH TOÁN:");
            // công dụng của instanceof là:
            // kiểm tra xem đối tượng pay thực tế thuộc lớp con nào (Cash, Credit, ...) để
            // ép kiểu (casting) về lớp đó
            // và truy cập các hàm getter riêng biệt (như getBank, getCashReceived).
            if (pay instanceof Cash) {
                Cash c = (Cash) pay;
                System.out.println("  - Hình thức: Tiền mặt");
                System.out.printf("  - Tiền khách đưa: %,.0f VNĐ%n", c.getCashReceived());
                System.out.printf("  - Tiền trả lại:   %,.0f VNĐ%n", (c.getCashReceived() - tongCong));

            } else if (pay instanceof Credit) {
                Credit cr = (Credit) pay;
                System.out.println("  - Hình thức: Thẻ tín dụng");
                System.out.println("  - Ngân hàng: " + cr.getBank());
                System.out.println(
                        "  - Số thẻ:    ****" + cr.getNumberId().substring(Math.max(0, cr.getNumberId().length() - 4))); // In
                                                                                                                         // 4
                                                                                                                         // số
                                                                                                                         // cuối
                                                                                                                         // bảo
                                                                                                                         // mật
                System.out.println("  - Chủ thẻ:   " + cr.getNameOnCard());

            } else if (pay instanceof Transfer) {
                Transfer t = (Transfer) pay;
                System.out.println("  - Hình thức: Chuyển khoản");
                System.out.println("  - Ngân hàng: " + t.getBank());
                System.out.println("  - Số TK:     " + t.getAccountNumber());
                System.out.println("  - Chủ TK:    " + t.getAccountName());

            } else if (pay instanceof Installment) {
                Installment ins = (Installment) pay;
                System.out.println("  - Hình thức: Trả góp");
                System.out.println("  - Công ty TC: " + ins.getFinanceCompanyName());
                System.out.println("  - Kỳ hạn:     " + ins.getInstallmentMonths() + " tháng");
                System.out.printf("  - Trả trước:  %,.0f VNĐ%n", ins.getDownPayment());
                System.out.printf("  - Góp mỗi tháng: %,.0f VNĐ%n",
                        (tongCong - ins.getDownPayment()) / ins.getInstallmentMonths());
            }
        } else {
            System.out.println("  - Hình thức: Chưa thanh toán");
        }

        System.out.println("=".repeat(115) + "\n");

    }

    // Xem hoa don theo ten khach hang
    public void printInvoicesByCustomer(String username) {
        

        InvoiceDTO[] results = invDAO.findByName(username);// tac dung cua findByName co the dung de tim username

        if (results == null || results.length == 0) {
            System.out.println("[Thong bao] Khong tim thay hoa don nao cua khach hang: " + username);
            return;
        }

        System.out.println("\n--- TIM THAY " + results.length + " HOA DON ---");
        for (InvoiceDTO inv : results) {
            if (inv != null) {
                printInvoice(inv.getInvoiceId());
            }
        }
    }

    // Xem danh sach tat ca hoa don ban hang
    public void printAllInvoices() {
        invDAO.displayAll();
    }

    public void cancelInvoice() {
        System.out.print("Nhap ma hoa don can huy: ");
        String id = sc.nextLine();
        invDAO.remove(id);
    }

    // =======Ham thanh toan=======

    public void checkoutFromCart(String username, CartItemDTO[] myCart, CartBUS cartBUS) {
        // Kiem tra

        if (myCart == null || myCart.length == 0) {
            System.out.println("Gio hang cua ban dang trong, moi quay lai mua hang");
            return;
        }
        // Vi khi xuat hoa don can lay thong tin chi tiet nen tao 1 doi tuong de lay
        // thong tin
        
        
        
        
        // 1. Tìm tài khoản bằng username để lấy mã Khách Hàng (ownerId)
        DTO.Account acc = accountDAO.findByUsername(username);
        if (acc == null) {
            System.out.println("Khong tim thay tai khoan cua ban trong he thong!");
            return;
        }

        // 2. Dùng ownerId (VD: KH004) để tìm chính xác thông tin Khách Hàng
        Customer cus = customerDAO.findById(acc.getOwnerId());
        if (cus == null) {
            System.out.println("Khong tim thay du lieu khach hang (Ma KH: " + acc.getOwnerId() + ")");
            return;
        }
      

        // ==== Khoi tao hoa don====
        String newInvoiceId = "HD" + System.currentTimeMillis();// ham nay tu tao ma hoa don ngay tai thoi diem bam tao
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setInvoiceId(newInvoiceId);
        invoice.setCreatedDate(new Date());
        invoice.setCustomer(cus);

        // Tao 1 ham lay thong tin nhan vien dang ban hang

        Employee emp = employeeDAO.findById("NV01"); // tam thoi de nhu vay
        invoice.setEmployee(null);

        // Lay du lieu tu gio hang mang sang Chi tiet Hoa don
        myCart = cartBUS.getMyCartItems();
        InvoiceItemDTO[] invoiceDetail = new InvoiceItemDTO[myCart.length];

        for (int i = 0; i < invoiceDetail.length; i++) {
            CartItemDTO cartItem = myCart[i];

            InvoiceItemDTO item = new InvoiceItemDTO();
            item.setInvoiceId(newInvoiceId);
            item.setQuantity(cartItem.getQuantity());

            ProductsDTO product = productsDAO.findById(cartItem.getProductID());
            item.setProduct(product);

            item.setPromotion(null); // de tam
            item.setWarranty(null); // de tam

            invoiceDetail[i] = item;

        }
        invoice.setInvoiceItemList(invoiceDetail);

        // ===Tinh tien===
        double total = InvoiceListDAO.calculateTotalPrice(invoice);
        System.out.printf("\n---> TONG CAN THANH TOAN: %,.0f VND <---\n", total);

        PaymentDTO payment = paymentMethod(newInvoiceId, total);

        if (payment != null) {
            // Luu hoa don
            invoice.setPayment(payment);
            invDAO.add(invoice); // Hinh nhu file nay ch dc save
            

            for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
                invItemDAO.add(item);
                
                productsDAO.deductStock(item.getProductId(), item.getQuantity()); 
            }
            saveFile();
            
 

            // Don dep gio hang sau khi thanh toan xong
            cartBUS.clearMyCart();
            System.out.println("Thanh toan thanh cong!");
        } else {
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
        choice = Integer.parseInt(sc.nextLine());

        String paymentId = invoiceID + "-Pay";
        Date paymentDate = new Date();

        switch (choice) {
            case 1:
                //Thao tac thanh toan tuong trung
                System.out.println("So tien phai tra:");
                double cashReceived = Double.parseDouble(sc.nextLine());
                if (cashReceived < total) {
                    System.out.println("Loi: Thanh toan that bai!");
                    return null;
                }
                return new Cash(paymentId, paymentDate, cashReceived);
            case 2:
                    System.out.println("Chuc nang dang phat trien!");
                    return null;
                
            case 3:
                    System.out.println("Chuc nang dang phat trien!");
                    return null;
            case 4:
                    System.out.println("Chuc nang dang phat trien!");
                    return null;
            default:
                System.out.println("Da huy thanh toan!");
                return null;
        }
    }
    
    // thống kê hóa đơn bán được trong 1 ngày.
    public void getInvoiceStatisticsByDate() {
        System.out.print("Nhap ngay can thong ke (dd/MM/yyyy): ");
        // Dùng trim() để xóa khoảng trắng thừa lỡ người dùng bấm nhầm
        String i = sc.nextLine().trim(); 

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        InvoiceDTO[] allInvoices = invDAO.getAll();

        if (allInvoices == null || allInvoices.length == 0) {
            System.out.println("Danh sach trong!");
            return;
        }

        double tongDoanhThuNgay = 0;
        int count = 0; // đếm có bao nhiêu hóa đơn

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                BAO CAO DOANH THU NGAY: " + i);
        System.out.println("-".repeat(80));
        System.out.printf("%-15s | %-15s | %-15s | %-20s%n", 
                          "Ma Hoa Don", "Ma Khach Hang", "Ma NV", "Tong Tien (VNĐ)");
        System.out.println("-".repeat(80));

        for (InvoiceDTO inv : allInvoices) {
            // Kiểm tra thêm điều kiện isStatus() để loại bỏ các hóa đơn đã Cancelled
            if (inv != null && inv.getCreatedDate() != null && inv.isStatus() == true) {
                
                String formattedDate = sdf.format(inv.getCreatedDate());

                if (formattedDate.equals(i)) { 
                  
                    double invoiceTotal = InvoiceListDAO.calculateTotalPrice(inv);
                    System.out.printf("%-15s | %-15s | %-15s | %,20.0f%n",
                                      inv.getInvoiceId(), 
                                      inv.getCustomerId(), 
                                      inv.getEmployeeId(), 
                                      invoiceTotal);

                    tongDoanhThuNgay += invoiceTotal;
                    count++;
                }
            }
        }

        System.out.println("-".repeat(80));
        if (count == 0) {
            System.out.println("Khong co hoa don HOP LE nao duoc tim thay cho ngay " + i);
        } else {
            System.out.printf("TONG SO HOA DON: %-10d | TONG DOANH THU NGAY: %,.0f VNĐ%n", 
                              count, tongDoanhThuNgay);
        }
        System.out.println("=".repeat(80) + "\n");
    }
    
    
}