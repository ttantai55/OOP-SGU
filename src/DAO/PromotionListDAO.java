package DAO;

import DTO.PromotionDTO;
import java.util.Arrays;

public class PromotionListDAO implements IRepository<PromotionDTO> {
    private PromotionDTO[] promotionList;

    public PromotionListDAO() {
        this.promotionList = new PromotionDTO[0];
    }

    @Override
    public void add(PromotionDTO promotion) {
        promotionList = Arrays.copyOf(promotionList, promotionList.length + 1);
        promotionList[promotionList.length - 1] = promotion;
    }

    // PromotionDTO không có status -> xây mảng mới bỏ phần tử cần xóa
    @Override
    public void remove(String promotionId) {
        boolean found = false;
        PromotionDTO[] temp = new PromotionDTO[0]; // tạo mảng temp để giữ lại các phần tử không bị xóa

        for (PromotionDTO p : promotionList) {
            if (p != null) {
                if (p.getPromotionId().equals(promotionId)) {
                    found = true; // bỏ qua phần tử cần xóa
                } else {
                    temp = Arrays.copyOf(temp, temp.length + 1); // giữ lại phần tử không bị xóa
                    temp[temp.length - 1] = p;
                }
            }
        }

        this.promotionList = temp; // mảng chỉ còn lại các khuyến mãi không bị xóa

        if (found) {
            System.out.println("Đã xóa khuyến mãi: " + promotionId);
        } else {
            System.out.println("Không tìm thấy khuyến mãi: " + promotionId);
        }
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

    public PromotionDTO[] getAll() {
        return Arrays.copyOf(promotionList, promotionList.length);
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
                        p.getStartDate(),
                        p.getEndDate(),
                        p.getDiscountPercent());
            }
        }
        System.out.println("=".repeat(100));
    }
}
