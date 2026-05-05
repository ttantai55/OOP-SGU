package BUS;

import DAO.GoodsReceiptItemListDAO;
import DTO.GoodsReceiptDTO;
import DTO.GoodsReceiptItemDTO;
import DTO.ProductsDTO;
import java.util.Scanner;

public class GoodsReceiptItemListBUS {
    private static final String FILE_RECEIPT_ITEM = "data/Goodsreceiptitem.txt";

    static Scanner sc = new Scanner(System.in);

    private final GoodsReceiptItemListDAO grItemDAO;
    private final GoodsReceiptBUS         goodsReceiptBUS;
    private final ProductListBUS          productBUS;

    public GoodsReceiptItemListBUS() {
        grItemDAO       = new GoodsReceiptItemListDAO();
        goodsReceiptBUS = new GoodsReceiptBUS();
        productBUS      = new ProductListBUS();
        
        grItemDAO.readFile(FILE_RECEIPT_ITEM);
    }


    // ========= LOAD / SAVE =========

    public void loadFile() {
       
        System.out.println("Da tai du lieu thanh cong tu file: " + FILE_RECEIPT_ITEM);
    }

    public void saveFile() {
        grItemDAO.writeFile(FILE_RECEIPT_ITEM);
        System.out.println("Da luu du lieu vao file: " + FILE_RECEIPT_ITEM);
    }


    // ========= THEM CHI TIET PHIEU NHAP =========

    public void inputGoodsReceiptItem() {
        GoodsReceiptDTO receipt = selectReceipt();
        if (receipt == null) return;

        ProductsDTO product = selectProduct();
        if (product == null) return;

        int quantity = readQuantity();
        if (quantity <= 0) return;

        double importPrice = readImportPrice();
        if (importPrice < 0) return;

        GoodsReceiptItemDTO item = new GoodsReceiptItemDTO();
        item.setProduct(product);
        item.setReceiptId(receipt.getReceiptId());
        item.setQuantity(quantity);
        item.setImportPrice(importPrice);

        grItemDAO.add(item);
        saveFile();
        System.out.println("Da them chi tiet vao phieu nhap thanh cong!");
    }

    private GoodsReceiptDTO selectReceipt() {
        System.out.print("Nhap ma phieu nhap: ");
        GoodsReceiptDTO receipt = goodsReceiptBUS.findById(sc.nextLine());
        if (receipt == null) System.out.println("Loi: Khong tim thay phieu nhap.");
        return receipt;
    }

    private ProductsDTO selectProduct() {
        System.out.print("Nhap ma IMEI san pham: ");
        ProductsDTO product = productBUS.getProductByID(sc.nextLine());
        if (product == null) System.out.println("Loi: Khong tim thay san pham.");
        return product;
    }

    private int readQuantity() {
        System.out.print("Nhap so luong: ");
        int quantity;
        try {
            quantity = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Loi: Vui long nhap so nguyen!");
            return -1;
        }
        if (quantity <= 0) System.out.println("So luong khong hop le!");
        return quantity;
    }

    private double readImportPrice() {
        System.out.print("Nhap gia nhap (VND): ");
        double importPrice;
        try {
            importPrice = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Loi: Vui long nhap so thuc!");
            return -1;
        }
        if (importPrice < 0) System.out.println("Gia nhap khong hop le!");
        return importPrice;
    }


    // ========= HUY CHI TIET PHIEU NHAP =========

    public void cancelGoodsReceiptItem() {
        System.out.print("Nhap ma phieu nhap: ");
        String receiptId = sc.nextLine();
        System.out.print("Nhap ma san pham can xoa: ");
        String productId = sc.nextLine();
        grItemDAO.remove(receiptId, productId);
        saveFile();
    }


    // ========= HIEN THI =========

    public void printGoodsReceiptItems() {
        GoodsReceiptDTO receipt = selectReceipt();
        if (receipt == null) return;

        GoodsReceiptItemDTO[] items = grItemDAO.findByReceiptId(receipt.getReceiptId());
        if (items == null || items.length == 0) {
            System.out.println("Phieu nhap nay khong co chi tiet nao.");
            return;
        }

        printItemsHeader(receipt.getReceiptId());
        double tongCong = printItemsTable(items);
        System.out.printf("%-65s TONG PHIEU: %,15.0f VND%n", "", tongCong);
        System.out.println("=".repeat(90) + "\n");
    }

    private void printItemsHeader(String receiptId) {
        System.out.println("\n" + "=".repeat(90));
        System.out.println("CHI TIET PHIEU NHAP: " + receiptId);
        System.out.println("-".repeat(90));
        System.out.printf("%-5s | %-15s | %-20s | %-8s | %-15s | %-15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Gia Nhap", "Thanh Tien");
        System.out.println("-".repeat(90));
    }

    private double printItemsTable(GoodsReceiptItemDTO[] items) {
        double tongCong = 0;
        for (int i = 0; i < items.length; i++) {
            GoodsReceiptItemDTO item = items[i];
            if (item == null) continue;
            double thanhTien = item.getQuantity() * item.getImportPrice();
            System.out.printf("%-5d | %-15s | %-20s | %-8d | %-15.0f | %,15.0f VND%n",
                    i + 1,
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getImportPrice(),
                    thanhTien);
            tongCong += thanhTien;
        }
        System.out.println("-".repeat(90));
        return tongCong;
    }
}
