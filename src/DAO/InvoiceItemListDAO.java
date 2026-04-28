package DAO;

import DTO.InvoiceItemDTO;
import java.util.Arrays;

public class InvoiceItemListDAO implements IRepository<InvoiceItemDTO> {
    private InvoiceItemDTO[] itemList;

    public InvoiceItemListDAO() {
        this.itemList = new InvoiceItemDTO[0];
    }

    @Override
    public void add(InvoiceItemDTO item) {
        itemList = Arrays.copyOf(itemList, itemList.length + 1);
        itemList[itemList.length - 1] = item;
    }

    // khi xóa 1 chi tiết thì nên nhập cả mã hóa đơn(invoiceId) và mã sản phẩm(productId) để tránh xóa nhầm 1 chi tiết của hóa đơn khác
    @Override
    public void remove(String id) {
        System.out.println("Hãy sử dụng hàm removeDetails(invoiceId, productId).");
    }

    public void removeDetails(String invoiceId, String productId) {
        boolean found = false;
        InvoiceItemDTO[] temp = new InvoiceItemDTO[0]; // tạo mảng temp để duyệt và chuyển các item cần giữ lại

        for (InvoiceItemDTO item : itemList) {
            if (item != null) {
                if (item.getInvoiceId().equals(invoiceId) && item.getProductId().equals(productId)) {
                    found = true; // bỏ qua phần tử cần xóa
                } else {
                    temp = Arrays.copyOf(temp, temp.length + 1); // mở rộng mảng nếu sản phẩm đó không bị xóa
                    temp[temp.length - 1] = item;
                }
            }
        }

        this.itemList = temp; // mảng chỉ còn lại sản phẩm ko bị xóa

        if (found) {
            System.out.println("Đã xóa sản phẩm " + productId + " khỏi hóa đơn " + invoiceId);
        } else {
            System.out.println("Không tìm thấy dòng chi tiết để xóa.");
        }
    }

    @Override
    public void update(InvoiceItemDTO updatedItem) {
        for (int i = 0; i < itemList.length; i++) {
            if (itemList[i] != null &&
                itemList[i].getInvoiceId().equals(updatedItem.getInvoiceId()) &&
                itemList[i].getProductId().equals(updatedItem.getProductId())) {
                itemList[i] = updatedItem;
                return;
            }
        }
    }

    // hàm riêng, tìm kiếm theo id của hóa đơn
    public InvoiceItemDTO[] findByInvoiceId(String invoiceId) {
        InvoiceItemDTO[] result = new InvoiceItemDTO[0];
        for (InvoiceItemDTO item : itemList) {
            if (item != null && item.getInvoiceId().equals(invoiceId)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = item;
            }
        }
        return result;
    }

    @Override
    public InvoiceItemDTO findById(String productId) {
        for (InvoiceItemDTO item : itemList) {
            if (item != null && item.getProductId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public InvoiceItemDTO[] findByName(String productName) {
        InvoiceItemDTO[] result = new InvoiceItemDTO[0];
        for (InvoiceItemDTO item : itemList) {
            if (item != null && item.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = item;
            }
        }
        return result;
    }

    @Override
    public InvoiceItemDTO[] getAll() {
        return Arrays.copyOf(itemList, itemList.length);
    }

    // tính thành tiền của một chi tiết hóa đơn
    public static double calculateSubTotal(InvoiceItemDTO item) {
        return item.getQuantity() * item.getUnitPrice();
    }

    // tính tổng tiền toàn bộ chi tiết hóa đơn
    public double getTotalValue() {
        double total = 0;
        for (InvoiceItemDTO item : itemList) {
            if (item != null) total += calculateSubTotal(item);
        }
        return total;
    }

    @Override
    public void readFile(String filePath) {
        // Sẽ bổ sung sau
    }

    @Override
    public void writeFile(String filePath) {
        // Sẽ bổ sung sau
    }

    @Override
    public void displayAll() {
        if (itemList.length == 0) {
            System.out.println("Danh sách chi tiết trống!");
            return;
        }
        System.out.println("=".repeat(85));
        System.out.printf("%-10s | %-10s | %-8s | %-15s | %-15s%n",
                "Mã HD", "Mã SP", "SL", "Đơn Giá", "Thành Tiền");
        System.out.println("-".repeat(85));

        for (InvoiceItemDTO item : itemList) {
            if (item != null) {
                System.out.printf("%-10s | %-10s | %-8d | %-15.0f | %,15.0f VNĐ%n",
                        item.getInvoiceId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        calculateSubTotal(item));
            }
        }
        System.out.println("=".repeat(85));
    }
}