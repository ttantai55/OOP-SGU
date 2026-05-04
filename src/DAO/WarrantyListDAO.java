package DAO;

import DTO.WarrantyDTO;
import DTO.ProductsDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// quản lí các bảo hành
public class WarrantyListDAO implements IRepository<WarrantyDTO> {
    private static WarrantyDTO[] warranties = new WarrantyDTO[0];
    private final String filePath = "data/warranty.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public WarrantyListDAO() {
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
    public void add(WarrantyDTO obj) {
        warranties = Arrays.copyOf(warranties, warranties.length + 1);
        warranties[warranties.length - 1] = obj;
        System.out.println("Da them bao hanh thanh cong: " + obj.getWarrantyId() + ".");
    }


    @Override
    public void remove(String id) {
        for (WarrantyDTO w : warranties) {
            if (w != null && w.getWarrantyId().equals(id)) {
                w.setStatus(false);
                System.out.println("Da huy bao hanh thanh cong: " + id + ".");
                return;
            }
        }
        System.out.println("Khong tim thay bao hanh de huy: " + id + ".");
    }

    @Override
    public void update(WarrantyDTO obj) {
        for (int i = 0; i < warranties.length; i++) {
            if (warranties[i] != null && warranties[i].getWarrantyId().equals(obj.getWarrantyId())) {
                warranties[i] = obj;
                System.out.println("Da cap nhat bao hanh thanh cong: " + obj.getWarrantyId() + ".");
                return;
            }
        }
        System.out.println("Khong tim thay bao hanh de cap nhat!");
    }

    @Override
    public WarrantyDTO findById(String id) {
        for (WarrantyDTO w : warranties) {
            if (w != null && w.getWarrantyId().equals(id)) {
                return w;
            }
        }
        return null;
    }

    // Tìm bảo hành theo tên sản phẩm
    @Override
    public WarrantyDTO[] findByName(String productName) {
        WarrantyDTO[] temp = new WarrantyDTO[0];
        for (WarrantyDTO w : warranties) {
            if (w != null && w.getProduct() != null &&
                w.getProduct().getProductName().toLowerCase().contains(productName.toLowerCase())) {
                temp = Arrays.copyOf(temp, temp.length + 1);
                temp[temp.length - 1] = w;
            }
        }
        return temp;
    }

    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("=".repeat(95));
        System.out.printf("%-10s | %-10s | %-10s | %-12s | %-12s | %-13s | %-8s%n",
                "Ma BH", "Ma HD", "Ma SP", "Ngay bat dau", "Ngay ket thuc", "Trang Thai", "So lan SC");
        System.out.println("-".repeat(95));

        for (WarrantyDTO w : warranties) {
            if (w != null && w.isStatus()) {
                String productId;
                if (w.getProduct() != null) {
                    productId = w.getProduct().getProductID();
                } else {
                    productId = "N/A";
                }
                System.out.printf("%-10s | %-10s | %-10s | %-12s | %-12s | %-13s | %-8d%n",
                        w.getWarrantyId(),
                        w.getInvoiceId(),
                        productId,
                        sdf.format(w.getStartDate()),
                        sdf.format(w.getEndDate()),
                        "Con hieu luc",
                        w.getRepairCount());
                hasActive = true;
            }
        }

        if (!hasActive) {
            System.out.println("Danh sach bao hanh trong hoac da het hieu luc het!");
        }
        System.out.println("=".repeat(95));
    }

    @Override
    public void readFile(String filePath) {
        WarrantyDTO[] tempArr = new WarrantyDTO[0];

        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            this.warranties = tempArr;
            return;
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                try {
                    String warrantyId = data[0];
                    String invoiceId  = data[1];

                    String productId = data[2];
                    ProductsDTO product = null;
                    if (!productId.equalsIgnoreCase("N/A")) {
                        product = new ProductsDTO();
                        product.setProductID(productId);
                    }

                    java.util.Date startDate = sdf.parse(data[3]);
                    java.util.Date endDate   = sdf.parse(data[4]);
                    boolean status           = data[5].equalsIgnoreCase("Active");
                    int repairCount          = Integer.parseInt(data[6]);

                    WarrantyDTO w = new WarrantyDTO(warrantyId, invoiceId, product, startDate, endDate, status);
                    w.setRepairCount(repairCount);

                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = w;

                } catch (Exception ex) {
                    System.out.println("Loi du lieu dong: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi doc File: " + e.getMessage());
        }

        this.warranties = tempArr;
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (WarrantyDTO w : warranties) {
                if (w != null) {
                    String productId;
                    if (w.getProduct() != null) {
                        productId = w.getProduct().getProductID();
                    } else {
                        productId = "N/A";
                    }

                    String status;
                    if (w.isStatus()) {
                        status = "Active";
                    } else {
                        status = "Cancelled";
                    }

                    String line = w.getWarrantyId() + "," +
                                 w.getInvoiceId() + "," +
                                 productId + "," +
                                 sdf.format(w.getStartDate()) + "," +
                                 sdf.format(w.getEndDate()) + "," +
                                 status + "," +
                                 w.getRepairCount();

                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Ghi du lieu vao file " + filePath + " thanh cong!");

        } catch (IOException e) {
            System.err.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    // Kiểm tra bảo hành còn hiệu lực không
    public boolean isActive(String id) {
        WarrantyDTO w = findById(id);
        if (w == null) return false;
        if (!w.isStatus()) return false;
        java.util.Date today = new java.util.Date();
        return today.before(w.getEndDate());
    }
}
