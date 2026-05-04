package DAO;

import DTO.PromotionDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PromotionListDAO implements IRepository<PromotionDTO> {
    private static PromotionDTO[] promotionList = new PromotionDTO[0];
    private final String filePath = "data/promotion.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PromotionListDAO() {
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
    public void add(PromotionDTO promotion) {
        promotionList = Arrays.copyOf(promotionList, promotionList.length + 1);
        promotionList[promotionList.length - 1] = promotion;
        System.out.println("Da them khuyen mai thanh cong: " + promotion.getPromotionId() + ".");
    }

    @Override
    public void remove(String promotionId) {
        for (PromotionDTO p : promotionList) {
            if (p != null && p.getPromotionId().equals(promotionId)) {
                p.setStatus(false);
                System.out.println("Da huy khuyen mai: " + promotionId + ".");
                return;
            }
        }
        System.out.println("Khong tim thay khuyen mai: " + promotionId + ".");
    }

    @Override
    public void update(PromotionDTO updatedPromotion) {
        for (int i = 0; i < promotionList.length; i++) {
            if (promotionList[i] != null && promotionList[i].getPromotionId().equals(updatedPromotion.getPromotionId())) {
                promotionList[i] = updatedPromotion;
                System.out.println("Da cap nhat khuyen mai thanh cong: " + updatedPromotion.getPromotionId() + ".");
                return;
            }
        }
        System.out.println("Khong tim thay khuyen mai de cap nhat!");
    }

    @Override
    public PromotionDTO findById(String promotionId) {
        for (PromotionDTO p : promotionList) {
            if (p != null && p.getPromotionId().equals(promotionId)) {
                return p;
            }
        }
        return null;
    }

    // tìm kiếm theo tên chương trình khuyến mãi
    @Override
    public PromotionDTO[] findByName(String programName) {
        PromotionDTO[] result = new PromotionDTO[0];
        for (PromotionDTO p : promotionList) {
            if (p != null && p.getProgramName().toLowerCase().contains(programName.toLowerCase())) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = p;
            }
        }
        return result;
    }

    // tìm kiếm theo mã sản phẩm áp dụng khuyến mãi
    public PromotionDTO[] findByProductId(String productId) {
        PromotionDTO[] result = new PromotionDTO[0];
        for (PromotionDTO p : promotionList) {
            if (p != null && p.getProductID().equals(productId)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = p;
            }
        }
        return result;
    }


    @Override
    public void readFile(String filePath) {
        PromotionDTO[] tempArr = new PromotionDTO[0];

        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            this.promotionList = tempArr;
            return;
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                try {
                    String promotionId   = data[0];
                    String programName   = data[1];
                    String productID     = data[2];
                    java.util.Date startDate = sdf.parse(data[3]);
                    java.util.Date endDate   = sdf.parse(data[4]);
                    String condition         = data[5];
                    double discountPercent   = Double.parseDouble(data[6]);
                    boolean status           = data[7].equalsIgnoreCase("Active");

                    PromotionDTO p = new PromotionDTO(promotionId, programName, productID,
                            startDate, endDate, condition, discountPercent, status);

                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = p;

                } catch (Exception ex) {
                    System.out.println("Loi du lieu dong: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi doc File: " + e.getMessage());
        }

        this.promotionList = tempArr;
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (PromotionDTO p : promotionList) {
                if (p != null) {
                    String status;
                    if (p.isStatus()) {
                        status = "Active";
                    } else {
                        status = "Cancelled";
                    }

                    String line = p.getPromotionId() + "," +
                                 p.getProgramName() + "," +
                                 p.getProductID() + "," +
                                 sdf.format(p.getStartDate()) + "," +
                                 sdf.format(p.getEndDate()) + "," +
                                 p.getCondition() + "," +
                                 p.getDiscountPercent() + "," +
                                 status;

                    bw.write(line);
                    bw.newLine();
                }
            }
            System.out.println("Ghi du lieu vao file " + filePath + " thanh cong!");
        } catch (IOException e) {
            System.err.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("=".repeat(100));
        System.out.printf("%-10s | %-25s | %-10s | %-12s | %-12s | %-8s%n",
                "Ma KM", "Ten chuong trinh", "Ma SP", "Ngay bat dau", "Ngay ket thuc", "Giam (%)");
        System.out.println("-".repeat(100));

        for (PromotionDTO p : promotionList) {
            if (p != null && p.isStatus()) {
                System.out.printf("%-10s | %-25s | %-10s | %-12s | %-12s | %.0f%%%n",
                        p.getPromotionId(),
                        p.getProgramName(),
                        p.getProductID(),
                        sdf.format(p.getStartDate()),
                        sdf.format(p.getEndDate()),
                        p.getDiscountPercent());
                hasActive = true;
            }
        }

        if (!hasActive) {
            System.out.println("Danh sach khuyen mai trong hoac da bi huy het!");
        }
        System.out.println("=".repeat(100));
    }
}
