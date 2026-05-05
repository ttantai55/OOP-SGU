package BUS;

import DAO.InvoiceItemListDAO;
import DAO.InvoiceListDAO;
import DAO.ProductListDAO;
import DAO.PromotionListDAO;
import DAO.WarrantyListDAO;
import DTO.InvoiceDTO;
import DTO.InvoiceItemDTO;
import DTO.ProductsDTO;
import DTO.PromotionDTO;
import DTO.WarrantyDTO;
import java.util.Scanner;

public class InvoiceItemListBUS {
    private final String FILE_PATH = "data/invoice.txt";
    Scanner sc = new Scanner(System.in);

    private final InvoiceItemListDAO invItemDAO;
    private final InvoiceListDAO invDAO;
    private final ProductListDAO productsDAO;
    private final PromotionListDAO promotionDAO;
    private final WarrantyListDAO warrantyDAO;

    public InvoiceItemListBUS() {
        this.invDAO = new InvoiceListDAO();
        this.invItemDAO = new InvoiceItemListDAO();
        this.productsDAO = new ProductListDAO();
        this.promotionDAO = new PromotionListDAO();
        this.warrantyDAO = new WarrantyListDAO();
        
        invItemDAO.readFile(FILE_PATH);
    }

    
    

    public void inputInvoiceItem() {
        System.out.print("Nhap ma hoa don: ");
        String invoiceId = sc.nextLine();

        InvoiceDTO invoice = invDAO.findById(invoiceId);
        if (invoice == null) {
            System.out.println("Loi: Khong tim thay hoa don.");
            return;
        }

        System.out.print("Nhap ma IMEI san pham: ");
        ProductsDTO product = productsDAO.findById(sc.nextLine());

        if (product == null) {
            System.out.println("Loi: Khong tim thay san pham.");
            return;
        }

        InvoiceItemDTO item = new InvoiceItemDTO();
        item.setProduct(product);
        item.setInvoiceId(invoiceId);

        System.out.print("Nhap so luong: ");
        int quantity = Integer.parseInt(sc.nextLine());
        if (quantity <= 0) {
            System.out.println("So luong khong hop le!");
            return;
        }
        item.setQuantity(quantity);

        System.out.print("Co ap dung khuyen mai? (y/n): ");
        String choice = sc.nextLine().toLowerCase();
        if (choice.equals("y")) {
            System.out.print("Nhap ma khuyen mai: ");
            PromotionDTO promotion = promotionDAO.findById(sc.nextLine());
            if (promotion != null) {
                item.setPromotion(promotion);
            }
        } else {
            item.setPromotion(null);
        }

        System.out.print("Co tao bao hanh? (y/n): ");
        choice = sc.nextLine().toLowerCase();
        if (choice.equals("y")) {
            System.out.print("Nhap ma bao hanh: ");
            WarrantyDTO warranty = warrantyDAO.findById(sc.nextLine());
            if (warranty != null) {
                item.setWarranty(warranty);
            }
        } else {
            item.setWarranty(null);
        }

        invItemDAO.add(item);
        System.out.println("Da them chi tiet vao hoa don thanh cong!");
    }

    public void printInvoiceItems() {
        System.out.print("Nhap ma hoa don: ");
        String invoiceId = sc.nextLine();

        InvoiceDTO invoice = invDAO.findById(invoiceId);
        if (invoice == null) {
            System.out.println("Loi: Khong tim thay hoa don.");
            return;
        }

        InvoiceItemDTO[] items = invItemDAO.findByInvoiceId(invoiceId);

        if (items == null || items.length == 0) {
            System.out.println("Hoa don nay khong co chi tiet nao.");
            return;
        }

        System.out.println("\n" + "=".repeat(85));
        System.out.println("CHI TIET HOA DON: " + invoiceId);
        System.out.println("-".repeat(85));
        System.out.printf("%-5s | %-10s | %-20s | %-5s | %-12s | %-15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Don Gia", "Thanh Tien");
        System.out.println("-".repeat(85));

        double tongCong = 0;
        for (int i = 0; i < items.length; i++) {
            InvoiceItemDTO item = items[i];
            if (item != null) {
                double thanhTien = InvoiceItemListDAO.calculateSubTotal(item);
                System.out.printf("%-5d | %-10s | %-20s | %-5d | %-12.0f | %,15.0f VND%n",
                        i + 1,
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        thanhTien);
                tongCong += thanhTien;
            }
        }
        System.out.println("-".repeat(85));
        System.out.printf("%-60s TONG CONG: %,15.0f VND%n", "", tongCong);
        System.out.println("=".repeat(85) + "\n");
    }

    public void cancelInvoiceItem() {
        System.out.print("Nhap ma hoa don: ");
        String invoiceId = sc.nextLine();

        System.out.print("Nhap ma san pham can xoa: ");
        String productId = sc.nextLine();

        invItemDAO.remove(invoiceId, productId);
    }
}