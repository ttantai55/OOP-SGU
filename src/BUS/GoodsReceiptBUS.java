package BUS;

import DAO.EmployeeDAO;
import DAO.ProductListDAO;
import DAO.SupplierDAO;
import DTO.GoodsReceiptDTO;
import DTO.GoodsReceiptItemDTO;
import DTO.ProductsDTO;
import DTO.Employee;
import DTO.Supplier;
import DAO.GoodsReceiptListDAO;
import DAO.GoodsReceiptItemListDAO;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class GoodsReceiptBUS {
    Scanner sc = new Scanner(System.in);

    private GoodsReceiptListDAO grDAO = new GoodsReceiptListDAO();
    private GoodsReceiptItemListDAO grItemDAO = new GoodsReceiptItemListDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private ProductListDAO productsDAO = new ProductListDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();

    // --- NHẬP PHIẾU NHẬP HÀNG ---
    public void inputReceipt() {
        GoodsReceiptDTO rec = new GoodsReceiptDTO();

        System.out.print("Nhap ma phieu nhap: ");
        rec.setReceiptId(sc.nextLine());

        rec.setCreatedDate(new Date());

        // Sử dụng vòng lặp kiểm tra Nhà Cung Cấp
        Supplier supplier = null;
        while (supplier == null) {
            System.out.print("Nhap ma nha cung cap: ");
            String supId = sc.nextLine();
            supplier = supplierDAO.findById(supId);
            if (supplier == null) {
                System.out.println("Loi: Khong tim thay nha cung cap. Vui long nhap lai!");
            }
        }
        rec.setSupplier(supplier);

        // Sử dụng vòng lặp kiểm tra Nhân viên nhận hàng
        Employee receiver = null;
        while (receiver == null) {
            System.out.print("Nhap ma nhan vien nhan hang: ");
            String empId = sc.nextLine();
            receiver = employeeDAO.findById(empId);
            if (receiver == null) {
                System.out.println("Loi: Khong tim thay nhan vien. Vui long nhap lai!");
            }
        }
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

        // Nhập chi tiết mặt hàng
        GoodsReceiptItemDTO[] items = new GoodsReceiptItemDTO[0];
        String themTiep = "y";
        int soThuTu = 1;
        while (themTiep.equals("y")) {
            System.out.println("--- Mat hang " + soThuTu + " ---");
            GoodsReceiptItemDTO item = new GoodsReceiptItemDTO();

            System.out.print("  Ma IMEI: ");
            ProductsDTO product = productsDAO.findByIMEI(sc.nextLine());
            if (product == null) {
                System.out.println("  Khong tim thay san pham! Vui long nhap lai.");
                continue;
            }
            item.setProduct(product);

            System.out.print("  So luong: ");
            int soLuong = Integer.parseInt(sc.nextLine());
            if (soLuong > 0) {
                item.setQuantity(soLuong);
            } else {
                item.setQuantity(1);
            }

            System.out.print("  Gia nhap (VND): ");
            double giaNhap = Double.parseDouble(sc.nextLine());
            if (giaNhap >= 0) {
                item.setImportPrice(giaNhap);
            } else {
                item.setImportPrice(0);
            }

            // Gán mã phiếu nhập vào từng chi tiết để liên kết
            item.setReceiptId(rec.getReceiptId());
            items = Arrays.copyOf(items, items.length + 1);
            items[items.length - 1] = item;
            soThuTu++;

            System.out.print("Them mat hang tiep theo? (y/n): ");
            themTiep = sc.nextLine().toLowerCase();
        }
        rec.setItems(items);

        grDAO.add(rec);

        for (GoodsReceiptItemDTO item : items) {
            if (item != null) {
                grItemDAO.add(item); 
            }
        }

        System.out.println("Da them phieu nhap [" + rec.getReceiptId() + "] thanh cong!");
    }


    public void printReceipt(String receiptId) {
        GoodsReceiptDTO rec = grDAO.findById(receiptId);
        if (rec == null) {
            System.out.println("Loi: Khong tim thay phieu nhap co ma " + receiptId + ".");
            return;
        }

       
        GoodsReceiptItemDTO[] items = grItemDAO.findByReceiptId(receiptId);

        System.out.println("\n" + "=".repeat(85));
        System.out.println("                PHIEU NHAP KHO HANG                ");
        System.out.printf(" Ma phieu: %-15s | Ngay: %s%n", 
                rec.getReceiptId(), 
                new SimpleDateFormat("dd/MM/yyyy").format(rec.getCreatedDate()));
        System.out.printf(" NCC     : %-15s | NV Nhan: %s%n", 
                rec.getSupplier().getSupplierName(), 
                rec.getReceiver().getFullName());
        System.out.printf(" Giao    : %-15s | Nhan   : %s%n", 
                rec.getCourier(), rec.getConsignee());
        System.out.println("-".repeat(85));

        // In tiêu đề bảng
        System.out.printf("%-5s | %-15s | %-20s | %8s | %15s | %15s%n",
                "STT", "Ma SP", "Ten SP", "SL", "Gia nhap", "Thanh tien");
        System.out.println("-".repeat(85));

        if (items == null || items.length == 0) {
            System.out.println(" (Khong co chi tiet mat hang.)");
        } else {
            for (int j = 0; j < items.length; j++) {
                GoodsReceiptItemDTO item = items[j];
                if (item == null) continue;
                
                double subTotal = item.getQuantity() * item.getImportPrice();
                System.out.printf("%-5d | %-15s | %-20s | %8d | %,15.0f | %,15.0f%n",
                        j + 1, 
                        item.getProduct().getProductID(), 
                        item.getProduct().getProductName(),
                        item.getQuantity(), 
                        item.getImportPrice(),
                        subTotal);
            }
        }

        System.out.println("-".repeat(85));
        System.out.printf("%-63s TONG PHIEU: %,15.0f VNĐ%n", "", GoodsReceiptListDAO.calculateTotalPrice(rec));
        System.out.println("=".repeat(85) + "\n");
    }


    public void cancelReceipt() {
        System.out.print("Nhap ma phieu nhap can huy: ");
        String id = sc.nextLine();
        grDAO.remove(id); 
    }
}