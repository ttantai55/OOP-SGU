package DAO;

import DTO.GoodsReceiptItemDTO;
import java.util.Arrays;

public class GoodsReceiptItemDAO implements IRepository<GoodsReceiptItemDTO> {
    private GoodsReceiptItemDTO[] details;

    public GoodsReceiptItemDAO() {
        details = new GoodsReceiptItemDTO[0];
    }

    @Override
    public void add(GoodsReceiptItemDTO obj) {
        details = Arrays.copyOf(details, details.length + 1);
        details[details.length - 1] = obj;
    }

    @Override
    // nên nhập cả 2 tham số receiptId, productId để không bị xóa nhầm 1 chi tiết của 1 phiếu nhập khác
    public void remove(String productId) {
        System.out.printf(" Hãy sử dụng hàm removeDetails(receiptId, productId). ");
    }

    public void removeDetails(String receiptId, String productId) {
        boolean found = false;
        GoodsReceiptItemDTO[] temp = new GoodsReceiptItemDTO[0]; // tạo mảng temp để giữ lại các item không bị xóa

        for (GoodsReceiptItemDTO item : details) {
            if (item != null) {
                if (item.getReceiptId().equals(receiptId) && item.getProductId().equals(productId)) {
                    found = true; // bỏ qua phần tử cần xóa
                } else {
                    temp = Arrays.copyOf(temp, temp.length + 1);
                    temp[temp.length - 1] = item;
                }
            }
        }

        this.details = temp; // mảng chỉ còn lại các item không bị xóa

        if (found) {
            System.out.println("Đã xóa sản phẩm " + productId + " khỏi phiếu nhập " + receiptId);
        } else {
            System.out.println("Không tìm thấy dòng chi tiết để xóa.");
        }
    }

    @Override
    public void update(GoodsReceiptItemDTO obj) {
        for (int i = 0; i < details.length; i++) {
            if (details[i] != null && details[i].getProductId().equals(obj.getProductId())) {
                details[i] = obj;
                return;
            }
        }
    }

    @Override
    public GoodsReceiptItemDTO findById(String productId) {
        for (GoodsReceiptItemDTO d : details) {
            if (d != null && d.getProductId().equals(productId)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public GoodsReceiptItemDTO[] findByName(String productName) {
        GoodsReceiptItemDTO[] temp = new GoodsReceiptItemDTO[0];
        for (GoodsReceiptItemDTO d : details) {
            if (d != null && d.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                temp = Arrays.copyOf(temp, temp.length + 1);
                temp[temp.length - 1] = d;
            }
        }
        return temp;
    }

    @Override
    public void displayAll() {
        for (GoodsReceiptItemDTO d : details) {
            if (d != null) {
                System.out.println("San pham: " + d.getProductName()
                        + " | So luong: " + d.getQuantity()
                        + " | Gia nhap: " + d.getImportPrice()
                        + " | Thanh tien: " + calculateSubTotal(d));
            }
        }
    }

    // Tính thành tiền của một chi tiết nhập
    public static double calculateSubTotal(GoodsReceiptItemDTO d) {
        return d.getQuantity() * d.getImportPrice();
    }

  // thiếu readFile, writeFile ( bổ sung sau)
    public GoodsReceiptItemDTO[] getAll() {
        return Arrays.copyOf(details, details.length);
    }

    // tính tổng tiền toàn bộ chi tiết nhập
    public double getTotalValue() {
        double total = 0;
        for (GoodsReceiptItemDTO d : details) {
            if (d != null) total += calculateSubTotal(d);
        }
        return total;
    }

    
}
