package DAO;

import DTO.GoodsReceiptItemDTO;
<<<<<<< HEAD
import DTO.ProductsDTO;
=======
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
>>>>>>> origin
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class GoodsReceiptItemListDAO implements IInvoiceManage<GoodsReceiptItemDTO> {
    private static GoodsReceiptItemDTO[] details = new GoodsReceiptItemDTO[0];
    private final String filePath = "data/goodsreceiptitem.txt";

   
    
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

<<<<<<< HEAD
        this.details = temp;
=======
        details = temp; // mảng chỉ còn lại các item không bị xóa
>>>>>>> origin

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
<<<<<<< HEAD
        GoodsReceiptItemDTO[] tempArr = new GoodsReceiptItemDTO[0];
        
        // Kiểm tra file tồn tại
        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            this.details = tempArr; 
            return; 
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                try {
                    // Khởi tạo đối tượng ngay lập tức
                    ProductsDTO product = new ProductsDTO();
                    product.setProductID(data[1]);
                    product.setProductName(data[2]);
                    GoodsReceiptItemDTO item = new GoodsReceiptItemDTO(product, data[0], Integer.parseInt(data[3]), Double.parseDouble(data[4]));
                    // Thêm vào mảng
                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = item;

                } catch (Exception ex) {
                    System.out.println("Loi du lieu dong: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi doc File: " + e.getMessage());
        }
        
        // Nạp mảng vào biến gốc
        this.details = tempArr;
=======
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split(",");

                GoodsReceiptItemDTO item = new GoodsReceiptItemDTO();

                item.setReceiptId(parts[0]);
                // parts[1] là productId, parts[2] là productName - cần đối tượng ProductsDTO đầy đủ
                item.setQuantity(Integer.parseInt(parts[3]));
                item.setImportPrice(Double.parseDouble(parts[4]));
                // parts[5] là subTotal - tính toán tự động, không cần đọc

                int viTri = details.length;
                details = Arrays.copyOf(details, viTri + 1);
                details[viTri] = item;

                line = br.readLine();
            }

            br.close();
            System.out.println("Đọc dữ liệu từ file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
>>>>>>> origin
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