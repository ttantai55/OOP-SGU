package DAO;

import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;
import DTO.Customer;
import DTO.SalesEmployee;
import DTO.Cash;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


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
    // thêm hóa đơn
    public void add(InvoiceDTO invoice) {
        invoiceList = Arrays.copyOf(invoiceList, invoiceList.length + 1);
        invoiceList[invoiceList.length - 1] = invoice;
        System.out.println("Da them hoa don thanh cong: " + invoice.getInvoiceId() + ".");
    }

    @Override
    public void remove(String invoiceId) {
        boolean found = false;
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.getInvoiceId().equals(invoiceId)) {
                inv.setStatus(false);
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Da huy hoa donq: " + invoiceId + ".");
        } else {
            System.out.println("Khong tim thay hoa don: " + invoiceId + ".");
        }
    }

    @Override
    public void update(InvoiceDTO updatedInvoice) {
        boolean found = false;
        for (int i = 0; i < invoiceList.length; i++) {
            if (invoiceList[i] != null && invoiceList[i].getInvoiceId().equals(updatedInvoice.getInvoiceId())) {
                invoiceList[i] = updatedInvoice;
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Da cap nhat hoa don thanh cong: " + updatedInvoice.getInvoiceId() + ".");
        } else {
            System.out.println("Khong tim thay hoa don de cap nhat!");
        }
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
        System.out.println("Danh sach hoa don hoat dong:");
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.isStatus()) {
                System.out.println(inv.toString());
                hasActive = true;
            }
        }
        if (!hasActive) {
            System.out.println("Khong co hoa don nao kha dung!");
        }
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
                Cash payment = null;
                if (!paymentId.equalsIgnoreCase("N/A")) {
                    payment = new Cash();
                    payment.setPaymentId(paymentId);
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
                
                String paymentId;
                if (inv.getPayment() != null) {
                    paymentId = inv.getPayment().getPaymentId();
                } else {
                    paymentId = "N/A";
                }
                
                double totalPrice = calculateTotalPrice(inv);
                
                String status;
                if (inv.isStatus()) {
                    status = "Active";
                } else {
                    status = "Cancelled";
                }
                
                String line = inv.getInvoiceId() + "," +
                             customerId + "," +
                             employeeId + "," +
                             sdf.format(inv.getCreatedDate()) + "," +
                             status + "," +
                             totalPrice + "," +
                             paymentId;
                
                bw.write(line);
                bw.newLine();
            }
        }
        
        System.out.println("Ghi du lieu vao file " + filePath + " thanh cong!");
        
    } catch (IOException e) {
        System.err.println("Loi khi ghi file: " + e.getMessage());
    }
}



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