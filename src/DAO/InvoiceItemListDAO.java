package DAO;

import DTO.InvoiceItemDTO;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InvoiceItemListDAO implements IRepository<InvoiceItemDTO> {
    private static InvoiceItemDTO[] itemList = new InvoiceItemDTO[0];

    public InvoiceItemListDAO() {
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

        itemList = temp; // mảng chỉ còn lại sản phẩm ko bị xóa

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
        System.out.println("Hãy sử dụng hàm findDetail(invoiceId, productId).");
        return null;
    }

    public InvoiceItemDTO findDetail(String invoiceId, String productId) {
        for (InvoiceItemDTO item : itemList) {
            if (item != null
                && item.getInvoiceId().equals(invoiceId)
                && item.getProductId().equals(productId)) {
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

    // tính thành tiền của một chi tiết hóa đơn (tính cả chương trình khuyến mãi nếu có)
    public static double calculateSubTotal(InvoiceItemDTO item) {
        double subTotal = item.getQuantity() * item.getUnitPrice();
        if (item.getPromotion() != null) {
            double discount = subTotal * (item.getDiscountPercent() / 100.0);
            subTotal -= discount;
        }
        return subTotal; // tiền của 1 sản phẩm 
    }

    @Override
    public void readFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split(",");

                InvoiceItemDTO item = new InvoiceItemDTO();

                item.setInvoiceId(parts[0]);
                // parts[1] là productId, parts[2] là productName - cần đối tượng ProductsDTO đầy đủ
                item.setQuantity(Integer.parseInt(parts[3]));
                // parts[4] là unitPrice - lấy từ ProductsDTO, bỏ qua
                // parts[5] là promotionId - cần đối tượng PromotionDTO đầy đủ
                // parts[6] là warrantyId - cần đối tượng WarrantyDTO đầy đủ
                // parts[7] là subTotal - tính toán tự động, không cần đọc

                int viTri = itemList.length;
                itemList = Arrays.copyOf(itemList, viTri + 1);
                itemList[viTri] = item;

                line = br.readLine();
            }

            br.close();
            System.out.println("Đọc dữ liệu từ file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (InvoiceItemDTO item : itemList) {
                if (item != null) {
                    String promotionId;
                    if (item.getPromotion() != null) {
                        promotionId = item.getPromotion().getPromotionId();
                    } else {
                        promotionId = "N/A";
                    }

                    String warrantyId;
                    if (item.getWarranty() != null) {
                        warrantyId = item.getWarranty().getWarrantyId();
                    } else {
                        warrantyId = "N/A";
                    }

                    String line = item.getInvoiceId() + "," +
                                 item.getProductId() + "," +
                                 item.getProductName() + "," +
                                 item.getQuantity() + "," +
                                 item.getUnitPrice() + "," +
                                 promotionId + "," +
                                 warrantyId + "," +
                                 calculateSubTotal(item);

                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Ghi dữ liệu vào file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
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