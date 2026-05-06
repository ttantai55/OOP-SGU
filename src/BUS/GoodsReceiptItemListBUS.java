package BUS;

import DAO.GoodsReceiptItemListDAO;
import DAO.GoodsReceiptListDAO;
import DAO.ProductListDAO;
import DTO.GoodsReceiptDTO;
import DTO.GoodsReceiptItemDTO;
import DTO.ProductsDTO;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class GoodsReceiptItemListBUS {
    Scanner sc = new Scanner(System.in);

    private GoodsReceiptItemListDAO grItemDAO = new GoodsReceiptItemListDAO();
    private GoodsReceiptListDAO grDAO = new GoodsReceiptListDAO();
    private ProductListDAO productsDAO = new ProductListDAO();

    public void inputGoodsReceiptItem() {
        System.out.print("Nhap ma phieu nhap: ");
        String receiptId = sc.nextLine();

        GoodsReceiptDTO receipt = grDAO.findById(receiptId);
        if (receipt == null) {
            System.out.println("Loi: Khong tim thay phieu nhap.");
            return;
        }

        System.out.print("Nhap ma IMEI san pham: ");
        ProductsDTO product = productsDAO.findById(sc.nextLine());

        if (product == null) {
            System.out.println("Loi: Khong tim thay san pham.");
            return;
        }

        GoodsReceiptItemDTO item = new GoodsReceiptItemDTO();
        item.setProduct(product);
        item.setReceiptId(receiptId);

        System.out.print("Nhap so luong: ");
        int quantity = Integer.parseInt(sc.nextLine());
        if (quantity <= 0) {
            System.out.println("So luong khong hop le!");
            return;
        }
        item.setQuantity(quantity);

        System.out.print("Nhap gia nhap (VND): ");
        double importPrice = Double.parseDouble(sc.nextLine());
        if (importPrice < 0) {
            System.out.println("Gia nhap khong hop le!");
            return;
        }
        item.setImportPrice(importPrice);

        grItemDAO.add(item);
        System.out.println("Da them chi tiet vao phieu nhap thanh cong!");
    }

    public void printGoodsReceiptItems() {
        System.out.print("Nhap ma phieu nhap: ");
        String receiptId = sc.nextLine();

        GoodsReceiptDTO receipt = grDAO.findById(receiptId);
        if (receipt == null) {
            System.out.println("Loi: Khong tim thay phieu nhap.");
            return;
        }

        GoodsReceiptItemDTO[] items = grItemDAO.findByReceiptId(receiptId);

        if (items == null || items.length == 0) {
            System.out.println("Phieu nhap nay khong co chi tiet nao.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(90));
        System.out.println("CHI TIET PHIEU NHAP: " + receiptId);
        System.out.println("-".repeat(90));
        System.out.printf("%-5s | %-15s | %-20s | %-8s | %-15s | %-15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Gia Nhap", "Thanh Tien");
        System.out.println("-".repeat(90));

        double tongCong = 0;
        for (int i = 0; i < items.length; i++) {
            GoodsReceiptItemDTO item = items[i];
            if (item != null) {
                double thanhTien = item.getQuantity() * item.getImportPrice();
                System.out.printf("%-5d | %-15s | %-20s | %-8d | %-15.0f | %,15.0f VNĐ%n",
                        i + 1,
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getImportPrice(),
                        thanhTien);
                tongCong += thanhTien;
            }
        }
        System.out.println("-".repeat(90));
        System.out.printf("%-65s TONG PHIEU: %,15.0f VNĐ%n", "", tongCong);
        System.out.println("=".repeat(90) + "\n");
    }

    public void cancelGoodsReceiptItem() {
        System.out.print("Nhap ma phieu nhap: ");
        String receiptId = sc.nextLine();

        System.out.print("Nhap ma san pham can xoa: ");
        String productId = sc.nextLine();

        grItemDAO.remove(receiptId, productId);
    }
}
