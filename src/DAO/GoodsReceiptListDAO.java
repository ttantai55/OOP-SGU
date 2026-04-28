package DAO;

import DTO.GoodsReceiptDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;


public class GoodsReceiptListDAO implements IRepository<GoodsReceiptDTO> {
    private GoodsReceiptDTO[] receiptList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public GoodsReceiptListDAO() {
        this.receiptList = new GoodsReceiptDTO[0];
    }

    @Override
    public void add(GoodsReceiptDTO receipt) {
        receiptList = Arrays.copyOf(receiptList, receiptList.length + 1);
        receiptList[receiptList.length - 1] = receipt;
        System.out.println("Da them thanh cong phieu nhap: " + receipt.getReceiptId());
    }

    @Override
    public void remove(String receiptId) {
        boolean found = false;
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getReceiptId().equals(receiptId)) {
                rec.setStatus(false);
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Da huy phieu nhap: " + receiptId);
        } else {
            System.out.println("Khong tim thay phieu nhap: " + receiptId);
        }
    }

    @Override
    public void update(GoodsReceiptDTO updatedReceipt) {
        boolean found = false;
        for (int i = 0; i < receiptList.length; i++) {
            if (receiptList[i] != null && receiptList[i].getReceiptId().equals(updatedReceipt.getReceiptId())) {
                receiptList[i] = updatedReceipt;
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Da cap nhat thanh cong phieu nhap: " + updatedReceipt.getReceiptId());
        } else {
            System.out.println("Khong tim thay phieu nhap de cap nhat!");
        }
    }

    @Override
    public GoodsReceiptDTO findById(String receiptId) {
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getReceiptId().equals(receiptId)) {
                return rec;
            }
        }
        return null;
    }

    @Override
    // tìm phiếu nhập theo tên nhà cung cấp
    public GoodsReceiptDTO[] findByName(String supplierName) {
        GoodsReceiptDTO[] result = new GoodsReceiptDTO[0];
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getSupplierName().equals(supplierName)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = rec;
            }
        }
        return result;
    }

    public GoodsReceiptDTO[] getAll() {
        return Arrays.copyOf(receiptList, receiptList.length);
    }

   // thiếu write và read

    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("=".repeat(130));
        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-12s | %-10s%n", 
                "Ma PN", "Nguoi Nhan", "Nha Cung Cap", "Ngay Nhap", "Trang Thai", "Tong Tien");
        System.out.println("-".repeat(130));

        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.isStatus()) {
                System.out.printf("%-10s | %-15s | %-15s | %-15s | %-12s | %,.0f VNĐ%n",
                        rec.getReceiptId(),
                        rec.getConsignee(),
                        rec.getSupplierId(),
                        sdf.format(rec.getCreatedDate()),
                        "Hoat dong",
                        calculateTotalPrice(rec));
                hasActive = true;
            }
        }
        
        if (!hasActive) {
            System.out.println("Danh sach phieu nhap trong hoac da bi huy het!");
        }
        System.out.println("=".repeat(130));
    }

    // tính tiền giữ lại ở DAO để phục vụ display/write 
    public static double calculateTotalPrice(GoodsReceiptDTO rec) {
        if (rec.getItems() == null) return 0;
        double total = 0;
        for (var item : rec.getItems()) {
            if (item != null) total += item.getQuantity() * item.getImportPrice();
        }
        return total;
    }
}