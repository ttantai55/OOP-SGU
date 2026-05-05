package DAO;

import DTO.InvoiceItemDTO;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InvoiceItemListDAO implements IInvoiceManage<InvoiceItemDTO> {
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
    public void remove(String invoiceId, String productId) {
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
    public InvoiceItemDTO findById(String invoiceId, String productId) {
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
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 9) continue;

                // Format: invoiceId,productId,productName,quantity,unitPrice,promotionId,discountPercent,warrantyId,subTotal
                InvoiceItemDTO item = new InvoiceItemDTO();
                item.setInvoiceId(parts[0].trim());

                // Tạo stub ProductsDTO với thông tin cơ bản
                DTO.ProductsDTO product = new DTO.ProductsDTO();
                product.setProductID(parts[1].trim());
                product.setProductName(parts[2].trim());
                product.setPrice(Double.parseDouble(parts[4].trim()));
                product.setWarrantyPeriod(0);
                item.setProduct(product);

                item.setQuantity(Integer.parseInt(parts[3].trim()));

                // Promotion (parts[5] = promotionId, parts[6] = discountPercent)
                String promotionId = parts[5].trim();
                if (!promotionId.equals("N/A")) {
                    DTO.PromotionDTO promo = new DTO.PromotionDTO();
                    promo.setPromotionId(promotionId);
                    promo.setDiscountPercent(Double.parseDouble(parts[6].trim()));
                    item.setPromotion(promo);
                } else {
                    item.setPromotion(null);
                }

                // Warranty (parts[7] = warrantyId)
                String warrantyId = parts[7].trim();
                if (!warrantyId.equals("N/A")) {
                    DTO.WarrantyDTO warranty = new DTO.WarrantyDTO();
                    warranty.setWarrantyId(warrantyId);
                    item.setWarranty(warranty);
                } else {
                    item.setWarranty(null);
                }

                add(item);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("[Thong bao] Chua co file InvoiceItem.txt (Se tu tao khi them moi).");
        } catch (Exception e) {
            System.out.println("[Loi] Loi khi doc file InvoiceItem: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (InvoiceItemDTO item : itemList) {
                if (item != null) {
                    String promotionId;
                    double discountPercent;
                    if (item.getPromotion() != null) {
                        promotionId = item.getPromotion().getPromotionId();
                        discountPercent = item.getDiscountPercent();
                    } else {
                        promotionId = "N/A";
                        discountPercent = 0.0;
                    }

                    String warrantyId;
                    if (item.getWarranty() != null) {
                        warrantyId = item.getWarranty().getWarrantyId();
                    } else {
                        warrantyId = "N/A";
                    }

                    // Format: invoiceId,productId,productName,quantity,unitPrice,promotionId,discountPercent,warrantyId,subTotal
                    String line = item.getInvoiceId() + "," +
                                 item.getProductId() + "," +
                                 item.getProductName() + "," +
                                 item.getQuantity() + "," +
                                 item.getUnitPrice() + "," +
                                 promotionId + "," +
                                 discountPercent + "," +
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
        System.out.println("=" + "=".repeat(84));
    }

    // Alias cho remove, dung trong BUS
    public void removeDetails(String invoiceId, String productId) {
        remove(invoiceId, productId);
    }
}