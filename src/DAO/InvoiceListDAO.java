package DAO;

import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class InvoiceListDAO implements IRepository<InvoiceDTO> {

    private static InvoiceDTO[] invoiceList = new InvoiceDTO[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public InvoiceListDAO() {
    }

    @Override
    // thêm hóa đơn bằng cách đặt biến tạm, tạo mảng mới, copy mảng cũ qua mảng mới và thêm hóa đơn mới vào cuối mảng mới
    public void add(InvoiceDTO invoice) {// tạo 1 mảng có độ dài = mảng cũ + 1
        invoiceList = Arrays.copyOf(invoiceList, invoiceList.length + 1); // copy mảng cũ sang mảng mới(mảng mới thừa 1 phần tử)
        invoiceList[invoiceList.length - 1] = invoice; // hóa đơn cũ nằm ở cuối mảng mới
    }

     @Override
    // sử dụng xóa mềm (sort delete)
    public void remove(String invoiceId) { 
    for (InvoiceDTO inv : invoiceList) { // duyệt qua mảng hóa đơn. Tương tự For nhưng ko cần biết index
    if (inv != null && inv.getInvoiceId().equals(invoiceId)){
        inv.setStatus(false); // dò được id hóa đơn thì đặt là false
        System.out.println("Da xoa hoa don thanh cong: " + invoiceId + ".");
         return;
    }
        System.out.println("Khong tim thay hoa don de xoa!");
}  
}      

    @Override
    // tương tự cách code như remove
    public void update(InvoiceDTO updatedInvoice) {
        for (int i = 0; i < invoiceList.length; i++) {
            if (invoiceList[i] != null && invoiceList[i].getInvoiceId().equals(updatedInvoice.getInvoiceId())) {
                invoiceList[i] = updatedInvoice; // ghi đè hóa đơn mới lên hóa đơn cũ
                System.out.println("Da cap nhat hoa don thanh cong: " + updatedInvoice.getInvoiceId() + ".");
            return;
            }
        }
            System.out.println("Khong tim thay hoa don de cap nhat!");
        }

    @Override
    public InvoiceDTO findById(String id) {
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.getInvoiceId().equals(id)) {
                return inv;
            }
        }
        return null;
    }

    @Override
    // tìm hóa đơn theo tên khách hàng
    public InvoiceDTO[] findByName(String customerName) {
        InvoiceDTO[] result = new InvoiceDTO[0];
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = inv;
            }
        }
        return result;
    }

    public InvoiceDTO[] getAll() {
        return java.util.Arrays.copyOf(invoiceList, invoiceList.length);
    }

    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("=".repeat(115));
        System.out.println("                         DANH SACH HOA DON BAN HANG                         ");
        System.out.println("=".repeat(115));
        System.out.printf("%-10s | %-20s | %-20s | %-12s | %-12s | %-15s%n",
                "Mã HD", "Khách Hàng", "Nhân Viên", "Ngày Tạo", "Trạng Thái", "Tổng Tiền");
        System.out.println("-".repeat(115));

        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.isStatus()) {
                String tenKH = inv.getCustomerName();
                String tenNV = inv.getEmployeeName();
                String trangThai = inv.isStatus() ? "Hoạt động" : "Đã hủy";

                System.out.printf("%-10s | %-20s | %-20s | %-12s | %-12s | %,15.0f VNĐ%n",
                        inv.getInvoiceId(),
                        tenKH,
                        tenNV,
                        sdf.format(inv.getCreatedDate()),
                        trangThai,
                        calculateTotalPrice(inv));
                hasActive = true;
            }
        }

        if (!hasActive) {
            System.out.println(" (Khong co hoa don nao kha dung!)");
        }
        System.out.println("=".repeat(115));
    }

    @Override
    public void readFile(String filePath) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 7) continue;

                // Format: invoiceId,customerId,employeeId,date,status,totalPrice,paymentId,paymentDate,paymentType,...paymentDetails
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setInvoiceId(parts[0].trim());

                // Tạo stub Customer với ID
                DTO.Customer customer = new DTO.Customer();
                customer.setCustomerId(parts[1].trim());
                customer.setFullName(parts[1].trim()); // Dùng ID làm tên tạm
                invoice.setCustomer(customer);

                // Tạo stub Employee với ID
                DTO.Employee employee = new DTO.Employee() {
                    @Override
                    public float calculateSalary() { return 0; }
                    @Override
                    public String getRole() { return "N/A"; }
                };
                employee.setEmployeeId(parts[2].trim());
                employee.setFullName(parts[2].trim()); // Dùng ID làm tên tạm
                invoice.setEmployee(employee);

                invoice.setCreatedDate(sdf.parse(parts[3].trim()));
                invoice.setStatus(parts[4].trim().equals("Active"));
                // parts[5] = totalPrice (tính từ items, bỏ qua)

                // Đọc thông tin thanh toán: parts[6]=paymentId, parts[7]=paymentDate, parts[8]=type
                if (parts.length >= 9) {
                    String paymentId = parts[6].trim();
                    java.util.Date paymentDate = sdf.parse(parts[7].trim());
                    String paymentType = parts[8].trim();

                    DTO.PaymentDTO payment = null;
                    switch (paymentType) {
                        case "Cash":
                            double cashReceived = Double.parseDouble(parts[9].trim());
                            payment = new DTO.Cash(paymentId, paymentDate, cashReceived);
                            break;
                        case "Credit":
                            payment = new DTO.Credit(paymentId, paymentDate,
                                    parts[9].trim(), parts[10].trim(), parts[11].trim());
                            break;
                        case "Transfer":
                            payment = new DTO.Transfer(paymentId, paymentDate,
                                    parts[9].trim(), parts[10].trim(), parts[11].trim());
                            break;
                        case "Installment":
                            payment = new DTO.Installment(paymentId, paymentDate,
                                    parts[9].trim(), parts[10].trim(),
                                    Integer.parseInt(parts[11].trim()),
                                    Double.parseDouble(parts[12].trim()));
                            break;
                    }
                    invoice.setPayment(payment);
                }

                add(invoice);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("[Thong bao] Chua co file Invoice.txt (Se tu tao khi them moi).");
        } catch (Exception e) {
            System.out.println("[Loi] Loi khi doc file Invoice: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (InvoiceDTO inv : invoiceList) {
                if (inv != null) {
                    String customerId = inv.getCustomerId();
                    String employeeId = inv.getEmployeeId();
                    double totalPrice = calculateTotalPrice(inv);

                    String status;
                    if (inv.isStatus()) {
                        status = "Active";
                    } else {
                        status = "Cancelled";
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(inv.getInvoiceId()).append(",")
                      .append(customerId).append(",")
                      .append(employeeId).append(",")
                      .append(sdf.format(inv.getCreatedDate())).append(",")
                      .append(status).append(",")
                      .append(totalPrice);

                    // Ghi thông tin thanh toán
                    DTO.PaymentDTO pay = inv.getPayment();
                    if (pay != null) {
                        sb.append(",").append(pay.getPaymentId());
                        sb.append(",").append(sdf.format(pay.getPaymentDate()));

                        if (pay instanceof DTO.Cash) {
                            DTO.Cash c = (DTO.Cash) pay;
                            sb.append(",Cash,").append(c.getCashReceived())
                              .append(",N/A,N/A,N/A");
                        } else if (pay instanceof DTO.Credit) {
                            DTO.Credit cr = (DTO.Credit) pay;
                            sb.append(",Credit,").append(cr.getNumberId())
                              .append(",").append(cr.getNameOnCard())
                              .append(",").append(cr.getBank())
                              .append(",N/A");
                        } else if (pay instanceof DTO.Transfer) {
                            DTO.Transfer t = (DTO.Transfer) pay;
                            sb.append(",Transfer,").append(t.getAccountNumber())
                              .append(",").append(t.getAccountName())
                              .append(",").append(t.getBank())
                              .append(",N/A");
                        } else if (pay instanceof DTO.Installment) {
                            DTO.Installment ins = (DTO.Installment) pay;
                            sb.append(",Installment,").append(ins.getFinanceCompanyName())
                              .append(",").append(ins.getContractNumber())
                              .append(",").append(ins.getInstallmentMonths())
                              .append(",").append(ins.getDownPayment());
                        }
                    }

                    bw.write(sb.toString());
                    bw.newLine();
                }
            }

            System.out.println("Ghi dữ liệu vào file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public static double calculateTotalPrice(InvoiceDTO invoice) {
        if (invoice.getInvoiceItemList() == null || invoice.getInvoiceItemList().length == 0)
            return 0;
        double total = 0;
        for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
            if (item != null)
                total += InvoiceItemListDAO.calculateSubTotal(item);
        }
        return total;
    }

}