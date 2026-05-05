package BUS;

import DAO.GoodsReceiptItemListDAO;
import DAO.GoodsReceiptListDAO;
import DAO.SupplierDAO;
import DTO.Employee;
import DTO.GoodsReceiptDTO;
import DTO.GoodsReceiptItemDTO;
import DTO.ProductsDTO;
import DTO.Supplier;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class GoodsReceiptBUS {
    private static final String FILE_RECEIPT      = "data/GoodsReceipt.txt";
    private static final String FILE_RECEIPT_ITEM = "data/Goodsreceiptitem.txt";

    static Scanner sc = new Scanner(System.in);

    private final GoodsReceiptListDAO     grDAO;
    private final GoodsReceiptItemListDAO grItemDAO;
    private final SupplierDAO             supplierDAO;
    private final EmployeeService         employeeService;
    private final ProductListBUS          productBUS;

    public GoodsReceiptBUS() {
        grDAO           = new GoodsReceiptListDAO();
        grItemDAO       = new GoodsReceiptItemListDAO();
        supplierDAO     = new SupplierDAO();
        employeeService = new EmployeeService();
        productBUS      = new ProductListBUS();
        
        grDAO.readFile(FILE_RECEIPT);
        grItemDAO.readFile(FILE_RECEIPT_ITEM);

    }


    // ========= LOAD / SAVE =========

    public void loadFile() {
        employeeService.loadFromFile();
        grDAO.readFile(FILE_RECEIPT);
        grItemDAO.readFile(FILE_RECEIPT_ITEM);

        // Resolve stub objects (chỉ có ID) → object đầy đủ, và gắn items vào receipt
        for (GoodsReceiptDTO rec : grDAO.getAll()) {
            if (rec == null) continue;
            rec.setItems(grItemDAO.findByReceiptId(rec.getReceiptId()));
            if (rec.getReceiver() != null) {
                Employee emp = employeeService.findById(rec.getReceiver().getEmployeeId());
                if (emp != null) rec.setReceiver(emp);
            }
            if (rec.getSupplier() != null) {
                Supplier sup = supplierDAO.findById(rec.getSupplier().getSupplierId());
                if (sup != null) rec.setSupplier(sup);
            }
        }

        System.out.println("Da tai du lieu thanh cong tu file: " + FILE_RECEIPT + " va " + FILE_RECEIPT_ITEM);
    }

    public void saveFile() {
        grDAO.writeFile(FILE_RECEIPT);
        grItemDAO.writeFile(FILE_RECEIPT_ITEM);
        System.out.println("Da luu du lieu vao file: " + FILE_RECEIPT + " va " + FILE_RECEIPT_ITEM);
    }


    // ========= TIM KIEM =========

    public GoodsReceiptDTO findById(String id) {
        return grDAO.findById(id);
    }


    // ========= THEM PHIEU NHAP =========

    public void inputReceipt() {
        System.out.print("Nhap ma phieu nhap: ");
        String id         = sc.nextLine();
        Supplier supplier = selectSupplier();
        Employee receiver = selectReceiver();

        GoodsReceiptDTO rec = new GoodsReceiptDTO();
        rec.setReceiptId(id);
        rec.setCreatedDate(new Date());
        rec.setSupplier(supplier);
        rec.setReceiver(receiver);

        System.out.print("Nhap ten nguoi tao: ");
        rec.setCreator(sc.nextLine());
        System.out.print("Nhap ghi chu: ");
        rec.setNote(sc.nextLine());
        System.out.print("Nhap ten ben giao hang: ");
        rec.setCourier(sc.nextLine());
        System.out.print("Nhap SDT ben giao hang: ");
        rec.setPhoneOfCourier(sc.nextLine());
        System.out.print("Nhap ten ben nhan hang: ");
        rec.setConsignee(sc.nextLine());
        System.out.print("Nhap SDT ben nhan hang: ");
        rec.setPhoneOfConsignee(sc.nextLine());
        rec.setItems(collectItems(id));

        commitReceipt(rec);
        System.out.println("Da them phieu nhap [" + id + "] thanh cong!");
    }

    private Supplier selectSupplier() {
        Supplier supplier = null;
        while (supplier == null) {
            System.out.print("Nhap ma nha cung cap: ");
            supplier = supplierDAO.findById(sc.nextLine());
            if (supplier == null) System.out.println("Loi: Khong tim thay nha cung cap. Vui long nhap lai!");
        }
        return supplier;
    }

    private Employee selectReceiver() {
        Employee receiver = null;
        while (receiver == null) {
            System.out.print("Nhap ma nhan vien nhan hang: ");
            receiver = employeeService.findById(sc.nextLine());
            if (receiver == null) System.out.println("Loi: Khong tim thay nhan vien. Vui long nhap lai!");
        }
        return receiver;
    }

    private GoodsReceiptItemDTO[] collectItems(String receiptId) {
        GoodsReceiptItemDTO[] items = new GoodsReceiptItemDTO[0];
        String themTiep = "y";
        int soThuTu = 1;
        while (themTiep.equals("y")) {
            System.out.println("Mat hang thu: " + soThuTu);
            GoodsReceiptItemDTO item = buildSingleItem(receiptId);
            if (item == null) continue;

            items = Arrays.copyOf(items, items.length + 1);
            items[items.length - 1] = item;
            soThuTu++;

            System.out.print("Them mat hang tiep theo? (y/n): ");
            themTiep = sc.nextLine().toLowerCase();
        }
        return items;
    }

    private GoodsReceiptItemDTO buildSingleItem(String receiptId) {
        System.out.print("  Ma IMEI: ");
        ProductsDTO product = productBUS.getProductByID(sc.nextLine());
        if (product == null) {
            System.out.println("  Khong tim thay san pham! Vui long nhap lai.");
            return null;
        }

        System.out.print("  So luong: ");
        int quantity;
        try {
            quantity = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("  Loi: Vui long nhap so nguyen!");
            return null;
        }
        if (quantity <= 0) {
            System.out.println("  So luong khong hop le! Vui long nhap lai.");
            return null;
        }

        System.out.print("  Gia nhap (VND): ");
        double importPrice;
        try {
            importPrice = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.out.println("  Loi: Vui long nhap so thuc!");
            return null;
        }
        if (importPrice < 0) {
            System.out.println("  Gia nhap khong hop le! Vui long nhap lai.");
            return null;
        }

        GoodsReceiptItemDTO item = new GoodsReceiptItemDTO();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setImportPrice(importPrice);
        item.setReceiptId(receiptId);
        return item;
    }

    private void commitReceipt(GoodsReceiptDTO rec) {
        grDAO.add(rec);
        for (GoodsReceiptItemDTO item : rec.getItems()) {
            if (item != null) grItemDAO.add(item);
        }
        saveFile();
    }


    // ========= HIEN THI TAT CA =========

    public void displayAll() {
        grDAO.displayAll();
    }


    // ========= HUY PHIEU NHAP =========

    public void cancelReceipt() {
        System.out.print("Nhap ma phieu nhap can huy: ");
        String id = sc.nextLine();
        grDAO.remove(id);
        saveFile();
    }


    // ========= HIEN THI =========

    public void printReceipt(String receiptId) {
        GoodsReceiptDTO rec = grDAO.findById(receiptId);
        if (rec == null) {
            System.out.println("Khong tim thay phieu nhap: " + receiptId + ".");
            return;
        }
        GoodsReceiptItemDTO[] items = grItemDAO.findByReceiptId(receiptId);

        printReceiptHeader(rec);
        double tongCong = printReceiptItems(items);
        System.out.printf("%-63s TONG PHIEU: %,15.0f VND%n", "", tongCong);
        System.out.println("=".repeat(85) + "\n");
    }

    private void printReceiptHeader(GoodsReceiptDTO rec) {
        String supplierInfo = rec.getSupplier() == null       ? "N/A"
                : rec.getSupplier().getSupplierName() != null ? rec.getSupplier().getSupplierName()
                : rec.getSupplier().getSupplierId();
        String receiverInfo = rec.getReceiver() == null       ? "N/A"
                : rec.getReceiver().getFullName() != null     ? rec.getReceiver().getFullName()
                : rec.getReceiver().getEmployeeId();

        System.out.println("\n" + "=".repeat(85));
        System.out.println("                PHIEU NHAP KHO HANG                ");
        System.out.printf(" Ma phieu: %-15s | Ngay: %s%n",
                rec.getReceiptId(),
                new SimpleDateFormat("dd/MM/yyyy").format(rec.getCreatedDate()));
        System.out.printf(" NCC     : %-15s | NV Nhan: %s%n", supplierInfo, receiverInfo);
        System.out.printf(" Giao    : %-15s | Nhan   : %s%n", rec.getCourier(), rec.getConsignee());
        System.out.println("-".repeat(85));
        System.out.printf("%-5s | %-15s | %-20s | %8s | %15s | %15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Gia nhap", "Thanh tien");
        System.out.println("-".repeat(85));
    }

    private double printReceiptItems(GoodsReceiptItemDTO[] items) {
        double tongCong = 0;
        if (items == null || items.length == 0) {
            System.out.println(" (Khong co chi tiet mat hang.)");
        } else {
            for (int i = 0; i < items.length; i++) {
                GoodsReceiptItemDTO item = items[i];
                if (item == null) continue;
                double subTotal = item.getQuantity() * item.getImportPrice();
                tongCong += subTotal;
                System.out.printf("%-5d | %-15s | %-20s | %8d | %,15.0f | %,15.0f%n",
                        i + 1,
                        item.getProduct().getProductID(),
                        item.getProduct().getProductName(),
                        item.getQuantity(),
                        item.getImportPrice(),
                        subTotal);
            }
        }
        System.out.println("-".repeat(85));
        return tongCong;
    }
}
