package DAO;

import DTO.Cash;
import DTO.Credit;
import DTO.Customer;
import DTO.Installment;
import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;
import DTO.PaymentDTO;
import DTO.SalesEmployee;
import DTO.Transfer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;


public class InvoiceListDAO implements IRepository<InvoiceDTO> {
   
    private static InvoiceDTO[] invoiceList = new InvoiceDTO[0];
    private final String filePath = "data/invoice.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public InvoiceListDAO() {
        loadFile();
    }

    public void loadFile() {
        readFile(this.filePath);
        System.out.println("Da tai du lieu thanh cong tu file: " + filePath);
    }

    public void saveFile() {
        writeFile(this.filePath);
        System.out.println("Da luu du lieu vao file: " + filePath);
    }

    @Override
    // thêm hóa đơn bằng cách đặt biến tạm, tạo mảng mới, copy mảng cũ qua mảng mới và thêm hóa đơn mới vào cuối mảng mới
    public void add(InvoiceDTO invoice) {// tạo 1 mảng có độ dài = mảng cũ + 1
        invoiceList = Arrays.copyOf(invoiceList, invoiceList.length + 1); // copy mảng cũ sang mảng mới(mảng mới thừa 1 phần tử)
        invoiceList[invoiceList.length - 1] = invoice; // hóa đơn cũ nằm ở cuối mảng mới
        System.out.println("Da them hoa don thanh cong: " + invoice.getInvoiceId() + ".");
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

    // tìm hóa đơn theo mã khách hàng
    public InvoiceDTO[] findByCustomerId(String customerId) {
        InvoiceDTO[] result = new InvoiceDTO[0];
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.getCustomerId().equals(customerId)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = inv;
            }
        }
        return result;
    }


    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("=".repeat(100));
        System.out.printf("%-10s | %-10s | %-15s | %-12s | %-12s | %-15s%n",
                "Ma HD", "Ma KH", "Nhan Vien", "Ngay Lap", "Trang Thai", "Tong Tien");
        System.out.println("-".repeat(100));

        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.isStatus()) {
                System.out.printf("%-10s | %-10s | %-15s | %-12s | %-12s | %,15.0f VND%n",
                        inv.getInvoiceId(),
                        inv.getCustomerId(),
                        inv.getEmployeeId(),
                        sdf.format(inv.getCreatedDate()),
                        "Hoat dong",
                        calculateTotalPrice(inv));
                hasActive = true;
            }
        }

        if (!hasActive) {
            System.out.println("Danh sach hoa don trong hoac tat ca da bi huy!");
        }
        System.out.println("=".repeat(100));
    }

@Override
public void readFile(String filePath) {
    InvoiceDTO[] tempArr = new InvoiceDTO[0];

    java.io.File file = new java.io.File(filePath);
    if (!file.exists()) {
        this.invoiceList = tempArr;
        return;
    }

    try (java.util.Scanner scanner = new java.util.Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;

            String[] data = line.split(",");

            try {
                String invoiceId = data[0];

                String customerId = data[1];
                Customer customer = null;
                if (!customerId.equalsIgnoreCase("N/A")) {
                    customer = new Customer();
                    customer.setCustomerId(customerId);
                }

                String employeeId = data[2];
                SalesEmployee employee = null;
                if (!employeeId.equalsIgnoreCase("N/A")) {
                    employee = new SalesEmployee();
                    employee.setEmployeeId(employeeId);
                }

                java.util.Date createdDate = sdf.parse(data[3]);
                boolean status = data[4].equalsIgnoreCase("Active");
                // data[5] is totalPrice — skip, it is calculated

                String paymentId = data[6];
                PaymentDTO payment = null;

                if (!paymentId.equalsIgnoreCase("N/A")) {
                    if (data.length > 8) {
                        java.util.Date paymentDate = data[7].equalsIgnoreCase("N/A") ? null : sdf.parse(data[7]);
                        String paymentType = data[8];
                        switch (paymentType) {
                            case "Cash":
                                payment = new Cash(paymentId, paymentDate, Double.parseDouble(data[9]));
                                break;
                            case "Credit":
                                payment = new Credit(paymentId, paymentDate, data[9], data[10], data[11]);
                                break;
                            case "Transfer":
                                payment = new Transfer(paymentId, paymentDate, data[9], data[10], data[11]);
                                break;
                            case "Installment":
                                payment = new Installment(paymentId, paymentDate, data[9], data[10],
                                        Integer.parseInt(data[11]), Double.parseDouble(data[12]));
                                break;
                            default:
                                Cash stub = new Cash();
                                stub.setPaymentId(paymentId);
                                payment = stub;
                        }
                    } else {
                        // file cũ — chỉ có paymentId
                        Cash stub = new Cash();
                        stub.setPaymentId(paymentId);
                        payment = stub;
                    }
                }

                InvoiceDTO inv = new InvoiceDTO(invoiceId, customer, employee, createdDate, payment);
                inv.setStatus(status);

                tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                tempArr[tempArr.length - 1] = inv;

            } catch (Exception ex) {
                System.out.println("Loi du lieu dong: " + line);
            }
        }
    } catch (Exception e) {
        System.out.println("Loi khi doc File: " + e.getMessage());
    }

    this.invoiceList = tempArr;
}

@Override
public void writeFile(String filePath) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null) {
                String customerId = inv.getCustomerId();
                String employeeId = inv.getEmployeeId();
                
                String status = inv.isStatus() ? "Active" : "Cancelled";
                double totalPrice = calculateTotalPrice(inv);

                String paymentId, paymentDateStr, paymentType, f1, f2, f3, f4;
                PaymentDTO pay = inv.getPayment();
                if (pay == null) {
                    paymentId = "N/A";
                    paymentDateStr = "N/A";
                    paymentType = "N/A";
                    f1 = "N/A"; f2 = "N/A"; f3 = "N/A"; f4 = "N/A";
                } else {
                    paymentId = pay.getPaymentId();
                    paymentDateStr = pay.getPaymentDate() != null ? sdf.format(pay.getPaymentDate()) : "N/A";
                    if (pay instanceof Cash) {
                        paymentType = "Cash";
                        f1 = String.valueOf(((Cash) pay).getCashReceived());
                        f2 = "N/A"; f3 = "N/A"; f4 = "N/A";
                    } else if (pay instanceof Credit) {
                        Credit cr = (Credit) pay;
                        paymentType = "Credit";
                        f1 = cr.getNumberId(); f2 = cr.getNameOnCard(); f3 = cr.getBank(); f4 = "N/A";
                    } else if (pay instanceof Transfer) {
                        Transfer t = (Transfer) pay;
                        paymentType = "Transfer";
                        f1 = t.getAccountNumber(); f2 = t.getAccountName(); f3 = t.getBank(); f4 = "N/A";
                    } else if (pay instanceof Installment) {
                        Installment ins = (Installment) pay;
                        paymentType = "Installment";
                        f1 = ins.getFinanceCompanyName(); f2 = ins.getContractNumber();
                        f3 = String.valueOf(ins.getInstallmentMonths());
                        f4 = String.valueOf(ins.getDownPayment());
                    } else {
                        paymentType = "N/A";
                        f1 = "N/A"; f2 = "N/A"; f3 = "N/A"; f4 = "N/A";
                    }
                }

                String line = inv.getInvoiceId() + "," +
                             customerId + "," +
                             employeeId + "," +
                             sdf.format(inv.getCreatedDate()) + "," +
                             status + "," +
                             totalPrice + "," +
                             paymentId + "," +
                             paymentDateStr + "," +
                             paymentType + "," +
                             f1 + "," + f2 + "," + f3 + "," + f4;
                
                bw.write(line);
                bw.newLine();
            }
        }
        
        System.out.println("Ghi du lieu vao file " + filePath + " thanh cong!");
        
    } catch (IOException e) {
        System.err.println("Loi khi ghi file: " + e.getMessage());
    }
}


// hàm tự động tính tiền của 1 hóa đơn
 public static double calculateTotalPrice(InvoiceDTO invoice) {
    if (invoice.getInvoiceItemList() == null || invoice.getInvoiceItemList().length == 0)
        return 0;
        double total = 0;
        for (InvoiceItemDTO item : invoice.getInvoiceItemList()) {
            if (item != null) total += InvoiceItemListDAO.calculateSubTotal(item);
        }
        return total;
    }


}