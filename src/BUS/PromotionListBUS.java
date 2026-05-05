package BUS;

import DAO.ProductListDAO;
import DAO.PromotionListDAO;
import DTO.ProductsDTO;
import DTO.PromotionDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PromotionListBUS {
    Scanner sc = new Scanner(System.in);

    private PromotionListDAO promDAO; 
    private ProductListDAO productsDAO;
    
    public PromotionListBUS(){
        this.productsDAO = new ProductListDAO();
        this.promDAO = new PromotionListDAO();
    }

    public void inputPromotion() {
        System.out.print("Moi tao ma khuyen mai: ");
        String promotionId = sc.nextLine();

        System.out.print("Nhap ten chuong trinh khuyen mai: ");
        String programName = sc.nextLine();

        System.out.print("Nhap ma san pham: ");
        ProductsDTO product = productsDAO.findById(sc.nextLine());
        if (product == null) {
            System.out.println("Loi: Khong tim thay san pham.");
            return;
        }

        System.out.print("Nhap ngay bat dau (dd/MM/yyyy): ");
        String startDateStr = sc.nextLine();
        Date startDate = parseDate(startDateStr);
        if (startDate == null) {
            System.out.println("Loi: Dinh dang ngay khong hop le!");
            return;
        }

        System.out.print("Nhap ngay ket thuc (dd/MM/yyyy): ");
        String endDateStr = sc.nextLine();
        Date endDate = parseDate(endDateStr);
        if (endDate == null) {
            System.out.println("Loi: Dinh dang ngay khong hop le!");
            return;
        }

        if (endDate.before(startDate)) {
            System.out.println("Loi: Ngay ket thuc phai sau ngay bat dau!");
            return;
        }

        System.out.print("Nhap dieu kien ap dung: ");
        String condition = sc.nextLine();

        System.out.print("Nhap phan tram giam gia (0-100): ");
        double discountPercent = Double.parseDouble(sc.nextLine());
        if (discountPercent < 0 || discountPercent > 100) {
            System.out.println("Loi: Phan tram giam gia phai tu 0-100!");
            return;
        }

        PromotionDTO promotion = new PromotionDTO(promotionId, programName, product.getProductID(),
                startDate, endDate, condition, discountPercent, true);

        promDAO.add(promotion);
        System.out.println("Da them khuyen mai [" + promotionId + "] thanh cong!");
    }

    public void printAllPromotions() {
        promDAO.displayAll();
    }

    public void printPromotionById() {
        System.out.print("Nhap ma khuyen mai: ");
        String promotionId = sc.nextLine();

        PromotionDTO promotion = promDAO.findById(promotionId);
        if (promotion == null) {
            System.out.println("Loi: Khong tim thay khuyen mai.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CHI TIET KHUYEN MAI");
        System.out.println("-".repeat(80));
        System.out.printf(" Ma KM           : %s%n", promotion.getPromotionId());
        System.out.printf(" Ten chuong trinh: %s%n", promotion.getProgramName());
        System.out.printf(" Ma san pham     : %s%n", promotion.getProductID());
        System.out.printf(" Ngay bat dau    : %s%n", sdf.format(promotion.getStartDate()));
        System.out.printf(" Ngay ket thuc   : %s%n", sdf.format(promotion.getEndDate()));
        System.out.printf(" Dieu kien       : %s%n", promotion.getCondition());
        System.out.printf(" Phan tram giam  : %.0f%%%n", promotion.getDiscountPercent());
        System.out.println("=".repeat(80) + "\n");
    }

    public void printPromotionsByProduct() {
        System.out.print("Nhap ma san pham: ");
        String productId = sc.nextLine();

        PromotionDTO[] promotions = promDAO.findByProductId(productId);

        if (promotions == null || promotions.length == 0) {
            System.out.println("Khong co khuyen mai nao cho san pham nay.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(100));
        System.out.println("KHUYEN MAI CHO SAN PHAM: " + productId);
        System.out.println("-".repeat(100));
        System.out.printf("%-10s | %-25s | %-12s | %-12s | %-8s%n",
                "Ma KM", "Ten chuong trinh", "Ngay bat dau", "Ngay ket thuc", "Giam (%)");
        System.out.println("-".repeat(100));

        for (PromotionDTO promo : promotions) {
            if (promo != null) {
                System.out.printf("%-10s | %-25s | %-12s | %-12s | %.0f%%%n",
                        promo.getPromotionId(),
                        promo.getProgramName(),
                        sdf.format(promo.getStartDate()),
                        sdf.format(promo.getEndDate()),
                        promo.getDiscountPercent());
            }
        }
        System.out.println("=".repeat(100) + "\n");
    }

    public void cancelPromotion() {
        System.out.print("Nhap ma khuyen mai can huy: ");
        String promotionId = sc.nextLine();

        promDAO.remove(promotionId);
    }

    public void updatePromotion() {
        System.out.print("Nhap ma khuyen mai can cap nhat: ");
        String promotionId = sc.nextLine();

        PromotionDTO promotion = promDAO.findById(promotionId);
        if (promotion == null) {
            System.out.println("Loi: Khong tim thay khuyen mai.");
            return;
        }

        System.out.print("Nhap ten chuong trinh (Enter de giu nguyen): ");
        String programName = sc.nextLine();
        if (!programName.isEmpty()) {
            promotion.setProgramName(programName);
        }

        System.out.print("Nhap dieu kien ap dung (Enter de giu nguyen): ");
        String condition = sc.nextLine();
        if (!condition.isEmpty()) {
            promotion.setCondition(condition);
        }

        System.out.print("Nhap phan tram giam gia (Enter de giu nguyen): ");
        String discountStr = sc.nextLine();
        if (!discountStr.isEmpty()) {
            double discount = Double.parseDouble(discountStr);
            if (discount >= 0 && discount <= 100) {
                promotion.setDiscountPercent(discount);
            } else {
                System.out.println("Loi: Phan tram giam gia phai tu 0-100!");
                return;
            }
        }

        promDAO.update(promotion);
        System.out.println("Da cap nhat khuyen mai [" + promotionId + "] thanh cong!");
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
