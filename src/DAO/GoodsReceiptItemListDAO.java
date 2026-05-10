package DAO;

import DTO.GoodsReceiptItemDTO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class GoodsReceiptItemListDAO implements IInvoiceManage<GoodsReceiptItemDTO> {
    private static GoodsReceiptItemDTO[] details = new GoodsReceiptItemDTO[0];


   
    
    @Override
    public void add(GoodsReceiptItemDTO obj) {
        details = Arrays.copyOf(details, details.length + 1);
        details[details.length - 1] = obj;
        System.out.println("Da them chi tiet phieu nhap thanh cong: " + obj.getProductId() + ".");
    }

    @Override
    public void remove(String receiptId, String productId) {
        boolean found = false;
        GoodsReceiptItemDTO[] temp = new GoodsReceiptItemDTO[0];

        for (GoodsReceiptItemDTO item : details) {
            if (item != null) {
                if (item.getReceiptId().equals(receiptId) && item.getProductId().equals(productId)) {
                    found = true;
                } else {
                    temp = Arrays.copyOf(temp, temp.length + 1);
                    temp[temp.length - 1] = item;
                }
            }
        }

        this.details = temp;

        if (found) {
            System.out.println("Da xoa san pham " + productId + " khoi phieu nhap " + receiptId + ".");
        } else {
            System.out.println("Khong tim thay dong chi tiet de xoa.");
        }
    }

    @Override
    public void update(GoodsReceiptItemDTO obj) {
        for (int i = 0; i < details.length; i++) {
            if (details[i] != null
                && details[i].getReceiptId().equals(obj.getReceiptId())
                && details[i].getProductId().equals(obj.getProductId())) {
                details[i] = obj;
                System.out.println("Da cap nhat chi tiet phieu nhap thanh cong: " + obj.getProductId() + ".");
                return;
            }
        }
        System.out.println("Khong tim thay dong chi tiet de cap nhat!");
    }

    @Override
    public GoodsReceiptItemDTO findById(String receiptId, String productId) {
        for (GoodsReceiptItemDTO d : details) {
            if (d != null
                && d.getReceiptId().equals(receiptId)
                && d.getProductId().equals(productId)) {
                return d;
            }
        }
        return null;
    }

    // tìm kiếm các chi tiết theo mã phiếu nhập
    public GoodsReceiptItemDTO[] findByReceiptId(String receiptId) {
        GoodsReceiptItemDTO[] result = new GoodsReceiptItemDTO[0];
        for (GoodsReceiptItemDTO d : details) {
            if (d != null && d.getReceiptId().equals(receiptId)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = d;
            }
        }
        return result;
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
    public static double calculateSubTotal(GoodsReceiptItemDTO gr) {
        double subTotal = gr.getQuantity() * gr.getImportPrice();
        return subTotal;
    }

   @Override
    public void readFile(String filePath) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                // Format: receiptId,productId,productName,quantity,importPrice,subTotal
                GoodsReceiptItemDTO item = new GoodsReceiptItemDTO();
                item.setReceiptId(parts[0].trim());

                // Tạo stub ProductsDTO với thông tin cơ bản
                DTO.ProductsDTO product = new DTO.ProductsDTO();
                product.setProductID(parts[1].trim());
                product.setProductName(parts[2].trim());
                product.setWarrantyPeriod(0);
                item.setProduct(product);

                item.setQuantity(Integer.parseInt(parts[3].trim()));
                item.setImportPrice(Double.parseDouble(parts[4].trim()));
                // parts[5] = subTotal (tính từ quantity * importPrice, bỏ qua)

                add(item);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("[Thong bao] Chua co file GoodsReceiptItem.txt (Se tu tao khi them moi).");
        } catch (Exception e) {
            System.out.println("[Loi] Loi khi doc file GoodsReceiptItem: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (GoodsReceiptItemDTO d : details) {
                if (d != null) {
                    String productId = d.getProductId();
                    String productName = d.getProductName();

                    String line = d.getReceiptId() + "," +
                                 productId + "," +
                                 productName + "," +
                                 d.getQuantity() + "," +
                                 d.getImportPrice() + "," +
                                 calculateSubTotal(d);

                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Ghi du lieu vao file " + filePath + " thanh cong!");

        } catch (IOException e) {
            System.err.println("Loi khi ghi file: " + e.getMessage());
        }
    }
}