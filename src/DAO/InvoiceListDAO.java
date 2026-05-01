package DAO;

import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class InvoiceListDAO implements IInvoiceManage<InvoiceDTO> {
   
    private static InvoiceDTO[] invoiceList = new InvoiceDTO[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public InvoiceListDAO() {
    }

    @Override
    // thêm hóa đơn
    public void add(InvoiceDTO invoice) {
        invoiceList = Arrays.copyOf(invoiceList, invoiceList.length + 1);
        invoiceList[invoiceList.length - 1] = invoice;
        System.out.println("Đã thêm hóa đơn thành công: " + invoice.getInvoiceId() + ".");
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
            System.out.println("Đã hủy hóa đơnq: " + invoiceId + ".");
        } else {
            System.out.println("Không tìm thấy hóa đơn: " + invoiceId + ".");
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
            System.out.println("Đã cập nhật hóa đơn thành công: " + updatedInvoice.getInvoiceId() + ".");
        } else {
            System.out.println("Không tìm thấy hóa đơn để cập nhật!");
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


    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("Danh sách hóa đơn hoạt động:");
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.isStatus()) {
                System.out.println(inv.toString());
                hasActive = true;
            }
        }
        if (!hasActive) {
            System.out.println("Không có hóa đơn nào khả dụng!");
        }
    }

// còn thiếu ReadFile
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
            if (item != null) total += InvoiceItemListDAO.calculateSubTotal(item);
        }
        return total;
    }


}