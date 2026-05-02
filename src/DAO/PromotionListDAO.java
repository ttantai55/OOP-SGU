package DAO;

import DTO.PromotionDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class PromotionListDAO implements IRepository<PromotionDTO> {
    private static PromotionDTO[] promotionList = new PromotionDTO[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PromotionListDAO() {
    }

    @Override
    public void add(PromotionDTO promotion) {
        promotionList = Arrays.copyOf(promotionList, promotionList.length + 1);
        promotionList[promotionList.length - 1] = promotion;
    }

    @Override
    public void remove(String promotionId) {
        for (PromotionDTO p : promotionList) {
            if (p != null && p.getPromotionId().equals(promotionId)) {
                p.setStatus(false);
                System.out.println("Đã hủy khuyến mãi: " + promotionId + ".");
                return;
            }
        }
        System.out.println("Không tìm thấy khuyến mãi: " + promotionId + ".");
    }

    @Override
    public void update(PromotionDTO updatedPromotion) {
        for (int i = 0; i < promotionList.length; i++) {
            if (promotionList[i] != null && promotionList[i].getPromotionId().equals(updatedPromotion.getPromotionId())) {
                promotionList[i] = updatedPromotion;
                return;
            }
        }
        System.out.println("Không tìm thấy khuyến mãi để cập nhật!");
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
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split(",");

                PromotionDTO p = new PromotionDTO();

                p.setPromotionId(parts[0]);
                p.setProgramName(parts[1]);
                p.setProductID(parts[2]);

                try {
                    Date ngayBatDau = sdf.parse(parts[3]);
                    p.setStartDate(ngayBatDau);
                    Date ngayKetThuc = sdf.parse(parts[4]);
                    p.setEndDate(ngayKetThuc);
                } catch (Exception e) {
                    // bỏ qua nếu không đọc được ngày
                }

                p.setCondition(parts[5]);
                p.setDiscountPercent(Double.parseDouble(parts[6]));

                if (parts[7].equals("Active")) {
                    p.setStatus(true);
                } else {
                    p.setStatus(false);
                }

                int viTri = promotionList.length;
                promotionList = Arrays.copyOf(promotionList, viTri + 1);
                promotionList[viTri] = p;

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
            System.out.println("Ghi dữ liệu vào file " + filePath + " thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void displayAll() {
        if (promotionList.length == 0) {
            System.out.println("Danh sách khuyến mãi trống!");
            return;
        }
        System.out.println("=".repeat(100));
        System.out.printf("%-10s | %-25s | %-10s | %-12s | %-12s | %-8s%n",
                "Mã KM", "Tên chương trình", "Mã SP", "Ngày bắt đầu", "Ngày kết thúc", "Giảm (%)");
        System.out.println("-".repeat(100));

        for (PromotionDTO p : promotionList) {
            if (p != null) {
                System.out.printf("%-10s | %-25s | %-10s | %-12s | %-12s | %.0f%%%n",
                        p.getPromotionId(),
                        p.getProgramName(),
                        p.getProductID(),
                        sdf.format(p.getStartDate()),
                        sdf.format(p.getEndDate()),
                        p.getDiscountPercent());
            }
        }
        System.out.println("=".repeat(100));
    }
}
