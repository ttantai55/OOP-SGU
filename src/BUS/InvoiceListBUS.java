package BUS;

import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import DAO.ProductListDAO;
import DAO.InvoiceListDAO;
import DAO.PromotionListDAO;
import DAO.WarrantyListDAO;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class InvoiceListBUS {
    Scanner sc = new Scanner(System.in);

    private InvoiceListDAO invDAO = new InvoiceListDAO();
    private InvoiceItemListDAO invItemDAO = new InvoiceItemListDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private ProductListDAO productsDAO = new ProductListDAO();
    private PromotionListDAO promotionDAO = new PromotionListDAO();
    private WarrantyListDAO warrantyDAO = new WarrantyListDAO();



    // *nhập hóa đơn*
    // mã hóa đơn
    // mã khách
    // tên khách
    // mã nhân viên
    // tên nhân viên
    // ngày tạo
    // trạng thái hóa đơn
    // nhập số lượng mặt hàng:
    //  - mặt hàng thứ n
    //      + mã IMEI sản phẩm n
    //      + giá bán sản phẩm n
    //      + số lượng sản phẩm n
    //      + khuyến mãi của sản phẩm n
    //      + mã bảo hành của sản phẩm n
    //      + tổng tiền của sản phẩm n
    // Tổng tiền tất cả (n1 + n2 + ... +nn)
    // phương thức thanh toán 

public void inputInvoice() {
    InvoiceDTO invoice = new InvoiceDTO();

    System.out.print("Nhập mã hóa đơn: ");
    invoice.setInvoiceId(sc.nextLine());

    Customer cus = null;
while (cus == null) {
    System.out.print("Nhập mã khách hàng: ");
    String cusId = sc.nextLine();
    cus = customerDAO.findById(cusId);
    
    if (cus == null) {
        System.out.println("Lỗi: Không tìm thấy khách hàng này. Vui lòng nhập lại!");
    }
}
invoice.setCustomer(cus);


Employee emp = null;
while (emp == null) {
    System.out.print("Nhập mã nhân viên: ");
    String empId = sc.nextLine();
    emp = employeeDAO.findById(empId);
    
    if (emp == null) {
        System.out.println("Lỗi: Không tìm thấy nhân viên. Vui lòng nhập lại!");
    }
}
invoice.setEmployee(emp);

    invoice.setCreatedDate(new Date());

    InvoiceItemDTO[] details = new InvoiceItemDTO[0];
    String themTiep = "y";
    int soThuTu = 1;
    while (themTiep.equals("y")) {
        System.out.println("Mặt hàng thứ: " + soThuTu);
        InvoiceItemDTO detail = new InvoiceItemDTO();

        System.out.print("  Mã IMEI: ");
        ProductsDTO product = productsDAO.findByIMEI(sc.nextLine());
        if (product == null) {
            System.out.println("  Không tìm thấy sản phẩm! Vui lòng nhập lại.");
            continue;
        }
        detail.setProduct(product);

        System.out.print("  Số lượng: ");
        int quantity = Integer.parseInt(sc.nextLine());
        if (quantity <= 0) {
            System.out.println("  Số lượng không hợp lệ! Vui lòng nhập lại.");
            continue;
        }
        detail.setQuantity(quantity);

        String warrantyId = invoice.getInvoiceId() + "-WAR-" + product.getProductID();
        Date startDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, product.getWarrantyPeriod());
        Date endDate = cal.getTime();
        WarrantyDTO warranty = new WarrantyDTO(warrantyId, invoice.getInvoiceId(), product, startDate, endDate, true);
        warrantyDAO.add(warranty);
        detail.setWarranty(warranty);

        System.out.print("  Có áp dụng khuyến mãi? (y/n): ");
        String promoChoice = sc.nextLine().toLowerCase();
        if (promoChoice.equals("y")) {
            System.out.print("  Nhập mã khuyến mãi: ");
            PromotionDTO promotion = promotionDAO.findById(sc.nextLine());
            if (promotion != null) {
                detail.setPromotion(promotion);
            } else {
                System.out.println("  Không tìm thấy khuyến mãi, bỏ qua.");
                detail.setPromotion(null);
            }
        } else {
            detail.setPromotion(null);
        }

        details = Arrays.copyOf(details, details.length + 1);
        details[details.length - 1] = detail;
        soThuTu++;

        System.out.print("Thêm mặt hàng tiếp theo? (y/n): ");
        themTiep = sc.nextLine().toLowerCase();
    }
    invoice.setInvoiceItemList(details);


    System.out.println("--------------------------------------------");
    System.out.println("Chọn phương thức thanh toán:");
    System.out.println("  1. Tiền mặt (Cash)");
    System.out.println("  2. Thẻ tín dụng (Credit)");
    System.out.println("  3. Chuyển khoản (Transfer)");
    System.out.println("  4. Trả góp (Installment)");
    System.out.print("Lựa chọn (1-4): ");
    int choice = Integer.parseInt(sc.nextLine());

    String paymentId = invoice.getInvoiceId() + "-PAY";
    Date paymentDate = new Date();
    PaymentDTO payment = null;

    if (choice == 1) {
        // Cần tính tổng tiền trước khi hỏi tiền khách đưa
        double total = InvoiceListDAO.calculateTotalPrice(invoice); 
        System.out.printf("Tổng cần thanh toán: %,.0f VND%n", total);
        System.out.print("Số tiền khách đưa: ");
        double cashReceived = Double.parseDouble(sc.nextLine());

        if (cashReceived < total) {
            System.out.println("Số tiền khách đưa không đủ!");
            return;
        }
        payment = new Cash(paymentId, paymentDate, cashReceived);

    } else if (choice == 2) {
        System.out.print("Số thẻ: ");
        String numberId = sc.nextLine();
        System.out.print("Tên chủ thẻ: ");
        String nameOnCard = sc.nextLine();
        System.out.print("Ngân hàng: ");
        String bank = sc.nextLine();
        payment = new Credit(paymentId, paymentDate, numberId, nameOnCard, bank);

    } else if (choice == 3) {
        System.out.print("Số tài khoản: ");
        String accountNumber = sc.nextLine();
        System.out.print("Tên chủ tài khoản: ");
        String accountName = sc.nextLine();
        System.out.print("Ngân hàng: ");
        String bank = sc.nextLine();
        payment = new Transfer(paymentId, paymentDate, accountNumber, accountName, bank);

    } else if (choice == 4) {
        System.out.print("Công ty tài chính: ");
        String company = sc.nextLine();
        System.out.print("Mã hợp đồng: ");
        String contractNum = sc.nextLine();
        System.out.print("Số tháng trả góp: ");
        int months = Integer.parseInt(sc.nextLine());
        System.out.print("Tiền trả trước: ");
        double downPayment = Double.parseDouble(sc.nextLine());
        payment = new Installment(paymentId, paymentDate, company, contractNum, months, downPayment);

    } else {
        System.out.println("Lựa chọn không hợp lệ!");
    }

  
    if (payment != null) {
        invoice.setPayment(payment);
        invDAO.add(invoice);

        if (invoice.getInvoiceItemList() != null) {
            for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
                if (item != null) {
                    item.setInvoiceId(invoice.getInvoiceId());
                    invItemDAO.add(item);
                }
            }
        }
        System.out.println("Đã thêm hóa đơn " + invoice.getInvoiceId() + " thành công!");
    } else {
        System.out.println("Thanh toán thất bại. Hóa đơn không được lưu.");
    }
}

public void printInvoice(String invoiceId) {
    InvoiceDTO invoice = invDAO.findById(invoiceId);
    if ( invoice == null ){
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
                "STT", "Mã SP", "Tên SP", "SL", "Đơn Giá", "KM(%)", "Mã BH", "Thành Tiền");
        System.out.println("-".repeat(115));

        double tongCong = 0;
        for (int i = 0; i < items.length; i++) {
            InvoiceItemDTO item = items[i];
            if (item == null) continue;

            double thanhTien = InvoiceItemListDAO.calculateSubTotal(item);

            String kmInfo;
            if (item.getPromotion() != null) {
                kmInfo = (int) item.getPromotion().getDiscountPercent() + "%";
            } else {
                kmInfo = "N/A";
            }

            System.out.printf("%-4d | %-10s | %-20s | %-4d | %-13.0f | %-8s | %-15s | %,15.0f VNĐ%n",
                    (i + 1),
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    kmInfo,
                    item.getWarrantyId(),
                    thanhTien);

            tongCong += thanhTien;
        }

        System.out.println("-".repeat(115));
        System.out.printf("%-80s TỔNG CỘNG: %,15.0f VNĐ%n", "", tongCong);
       
        PaymentDTO pay = invoice.getPayment();
if (pay != null) {
    System.out.println(" THÔNG TIN THANH TOÁN:");
    // công dụng của instanceof là:
    // kiểm tra xem đối tượng pay thực tế thuộc lớp con nào (Cash, Credit, ...) để ép kiểu (casting) về lớp đó 
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
        System.out.println("  - Số thẻ:    ****" + cr.getNumberId().substring(Math.max(0, cr.getNumberId().length() - 4))); // In 4 số cuối bảo mật
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
        System.out.printf("  - Góp mỗi tháng: %,.0f VNĐ%n", (tongCong - ins.getDownPayment()) / ins.getInstallmentMonths());
    }
} else {
    System.out.println("  - Hình thức: Chưa thanh toán");
}

System.out.println("=".repeat(115) + "\n");

    }

    public void cancelInvoice() {
        System.out.print("Nhập mã hóa đơn cần hủy: ");
        String id = sc.nextLine();
        invDAO.remove(id);
    }
}
