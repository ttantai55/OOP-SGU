package DAO;

import DTO.InvoiceDTO;
import java.util.Arrays;


public class InvoiceListDAO implements IRepository<InvoiceDTO> {
   
    private InvoiceDTO[] invoiceList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày

    public InvoiceListDAO() {
        this.invoiceList = new InvoiceDTO[0];

    }

    @Override
    // thêm hóa đơn
    public void add(InvoiceDTO invoice) {
        invoiceList = Arrays.copyOf(invoiceList, invoiceList.length + 1);
        invoiceList[invoiceList.length - 1] = invoice;
        System.out.println("Da them thanh cong hoa don: " + invoice.getInvoiceId());
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
            System.out.println("Da huy hoa don: " + invoiceId);
        } else {
            System.out.println("Khong tim thay hoa don: " + invoiceId);
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
            System.out.println("Da cap nhat thanh cong hoa don: " + updatedInvoice.getInvoiceId());
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
    // tìm hóa đơn theo mã khách hàng
    public InvoiceDTO[] findByName(String customerName) {
        InvoiceDTO[] result = new InvoiceDTO[0];
        for (InvoiceDTO inv : invoiceList) {
            if (inv != null && inv.getCustomerName().equals(customerName)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = inv;
            }
        }
        return result;
    }

    public InvoiceDTO[] getAll() {
        return Arrays.copyOf(invoiceList, invoiceList.length);
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

// còn thiếu WriteFile và ReadFile



}