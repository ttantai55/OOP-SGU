package GUI;

import BUS.GoodsReceiptBUS;
import BUS.InvoiceListBUS;
import BUS.PromotionListBUS;
import BUS.RepairRecordListBUS;
import BUS.WarrantyListBUS;
import java.util.Scanner;

// Sub-menu Quan ly Hoa don cho Manager
public class BillMenu {
    static Scanner sc = new Scanner(System.in);

    private InvoiceListBUS invoiceBUS;
    private PromotionListBUS promotionBUS;
    private GoodsReceiptBUS grBUS;
    private WarrantyListBUS warrantyBUS;
    private RepairRecordListBUS repairBUS;

    public BillMenu() {
        invoiceBUS   = new InvoiceListBUS();
        promotionBUS = new PromotionListBUS();
        grBUS        = new GoodsReceiptBUS();
        warrantyBUS  = new WarrantyListBUS();
        repairBUS    = new RepairRecordListBUS();

        // Tự động tải dữ liệu từ file khi khởi tạo
        loadData();
    }

    // ==================== LOAD / SAVE ====================

    public void loadData() {
        System.out.println("Dang tai du lieu hoa don va phieu nhap...");
        invoiceBUS.loadFile();
        grBUS.loadFile();
        System.out.println("Tai du lieu hoa don va phieu nhap thanh cong!");
    }

    public void saveData() {
        System.out.println("Dang luu du lieu hoa don va phieu nhap...");
        invoiceBUS.saveFile();
        grBUS.saveFile();
        System.out.println("Luu du lieu hoa don va phieu nhap thanh cong!");
    }

    public void showMenu() {
        int choice;
        do {
            
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  QUAN LY HOA DON & NGHIEP VU");
            System.out.println("=".repeat(55));
            System.out.println("  1. Hoa don ban hang");
            System.out.println("  2. Phieu nhap hang");
            System.out.println("  3. Bao hanh & Sua chua");
            System.out.println("  4. Khuyen mai");
            System.out.println("  0. Quay lai menu chinh");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    menuHoaDon();
                    break;
                case 2:
                    menuPhieuNhap();
                    break;
                case 3:
                    menuBaoHanh();
                    break;
                case 4:
                    menuKhuyenMai();
                    break;
                case 0:
                    saveData();
                    System.out.println("Quay lai menu chinh...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    // === HOA DON BAN HANG ===
    private void menuHoaDon() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  HOA DON BAN HANG");
            System.out.println("=".repeat(55));
            System.out.println("  1. Tao hoa don moi");
            System.out.println("  2. Xem chi tiet hoa don");
            System.out.println("  3. Huy hoa don");
            System.out.println("  4. Xem danh sach hoa don ban hang");
            System.out.println("  0. Quay lai");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-5): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    invoiceBUS.inputInvoice();
                    invoiceBUS.saveFile(); // Tự động lưu sau khi thêm
                    pause();
                    break;
                case 2:
                    System.out.print("Nhap ma hoa don: ");
                    invoiceBUS.printInvoice(sc.nextLine().trim());
                    pause();
                    break;
                case 3:
                    invoiceBUS.cancelInvoice();
                    invoiceBUS.saveFile(); // Tự động lưu sau khi hủy
                    pause();
                    break;
                case 4:
                    invoiceBUS.printAllInvoices();
                    pause();
                    break;
                case 0:
                    System.out.println("Quay lai...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    // === PHIEU NHAP HANG ===
    private void menuPhieuNhap() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  PHIEU NHAP HANG");
            System.out.println("=".repeat(55));
            System.out.println("  1. Tao phieu nhap hang moi");
            System.out.println("  2. Xem chi tiet phieu nhap");
            System.out.println("  3. Huy phieu nhap");
            System.out.println("  4. Xem danh sach phieu nhap hang");
            System.out.println("  0. Quay lai");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    grBUS.inputReceipt();
                    grBUS.saveFile(); // Tự động lưu sau khi thêm
                    pause();
                    break;
                case 2:
                    System.out.print("Nhap ma phieu nhap: ");
                    grBUS.printReceipt(sc.nextLine().trim());
                    pause();
                    break;
                case 3:
                    grBUS.cancelReceipt();
                    grBUS.saveFile(); // Tự động lưu sau khi hủy
                    pause();
                    break;
                case 4:
                    grBUS.printAllReceipts();
                    pause();
                    break;
                case 0:
                    System.out.println("Quay lai...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    // === BAO HANH & SUA CHUA ===
    private void menuBaoHanh() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  BAO HANH & SUA CHUA");
            System.out.println("=".repeat(55));
            System.out.println("  1. Xem chi tiet bao hanh");
            System.out.println("  2. Them ban ghi sua chua");
            System.out.println("  3. Huy bao hanh");
            System.out.println("  4. Xem sua chua theo bao hanh");
            System.out.println("  5. Cap nhat trang thai sua chua");
            System.out.println("  6. Thong ke sua chua");
            System.out.println("  0. Quay lai");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-6): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    warrantyBUS.printWarranty();
                    pause();
                    break;
                case 2:
                    warrantyBUS.addRepairRecord();
                    pause();
                    break;
                case 3:
                    warrantyBUS.cancelWarranty();
                    pause();
                    break;
                case 4:
                    repairBUS.viewRepairsByWarranty();
                    pause();
                    break;
                case 5:
                    repairBUS.updateRepairStatus();
                    pause();
                    break;
                case 6:
                    repairBUS.calculateRepairStats();
                    pause();
                    break;
                case 0:
                    System.out.println("Quay lai...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    // === KHUYEN MAI ===
    private void menuKhuyenMai() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  KHUYEN MAI");
            System.out.println("=".repeat(55));
            System.out.println("  1. Them khuyen mai moi");
            System.out.println("  2. Xem tat ca khuyen mai");
            System.out.println("  3. Cap nhat khuyen mai");
            System.out.println("  4. Huy khuyen mai");
            System.out.println("  0. Quay lai");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    promotionBUS.inputPromotion();
                    pause();
                    break;
                case 2:
                    promotionBUS.printAllPromotions();
                    pause();
                    break;
                case 3:
                    promotionBUS.updatePromotion();
                    pause();
                    break;
                case 4:
                    promotionBUS.cancelPromotion();
                    pause();
                    break;
                case 0:
                    System.out.println("Quay lai...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    private void pause() {
        System.out.print("\nNhan Enter de tiep tuc...");
        sc.nextLine();
    }
}