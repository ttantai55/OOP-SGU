import BUS.InvoiceListBUS;
import BUS.PromotionListBUS;
import BUS.GoodsReceiptBUS;
import BUS.WarrantyListBUS;
import BUS.RepairRecordListBUS;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static InvoiceListBUS invoiceBUS;
    static PromotionListBUS promotionBUS;
    static GoodsReceiptBUS grBUS;
    static WarrantyListBUS warrantyBUS;
    static RepairRecordListBUS repairBUS;

    public static void main(String[] args) {
        invoiceBUS   = new InvoiceListBUS();
        promotionBUS = new PromotionListBUS();
        grBUS        = new GoodsReceiptBUS();
        warrantyBUS  = new WarrantyListBUS();
        repairBUS    = new RepairRecordListBUS();

        int choice;
        do {
            showMainMenu();
            System.out.print("Chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 1) {
                menuHoaDon();
            } else if (choice == 2) {
                menuPhieuNhap();
            } else if (choice == 3) {
                menuBaoHanh();
            } else if (choice == 4) {
                menuKhuyenMai();
            } else if (choice == 0) {
                System.out.println("Tam biet!");
            } else {
                System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    static void showMainMenu() {
        System.out.println("\n" + "=".repeat(45));
        System.out.println("      QUAN LY CUA HANG MAY TINH");
        System.out.println("=".repeat(45));
        System.out.println("  1. Hoa don ban hang");
        System.out.println("  2. Phieu nhap hang");
        System.out.println("  3. Bao hanh");
        System.out.println("  4. Khuyen mai");
        System.out.println("  0. Thoat");
        System.out.println("=".repeat(45));
    }

    static void menuHoaDon() {
        int choice;
        do {
            System.out.println("\n--- HOA DON BAN HANG ---");
            System.out.println("  1. Tao hoa don moi");
            System.out.println("  2. Xem chi tiet hoa don");
            System.out.println("  3. Huy hoa don");
            System.out.println("  0. Quay lai");
            System.out.print("Chon (0-3): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 1) {
                invoiceBUS.inputInvoice();
            } else if (choice == 2) {
                System.out.print("Nhap ma hoa don: ");
                invoiceBUS.printInvoice(sc.nextLine());
            } else if (choice == 3) {
                invoiceBUS.cancelInvoice();
            } else if (choice == 0) {
                System.out.println("Quay lai menu chinh.");
            } else {
                System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    static void menuPhieuNhap() {
        int choice;
        do {
            System.out.println("\n--- PHIEU NHAP HANG ---");
            System.out.println("  1. Tao phieu nhap hang moi");
            System.out.println("  2. Xem chi tiet phieu nhap");
            System.out.println("  3. Huy phieu nhap");
            System.out.println("  0. Quay lai");
            System.out.print("Chon (0-3): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 1) {
                grBUS.inputReceipt();
            } else if (choice == 2) {
                System.out.print("Nhap ma phieu nhap: ");
                grBUS.printReceipt(sc.nextLine());
            } else if (choice == 3) {
                grBUS.cancelReceipt();
            } else if (choice == 0) {
                System.out.println("Quay lai menu chinh.");
            } else {
                System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    static void menuBaoHanh() {
        int choice;
        do {
            System.out.println("\n--- BAO HANH & SUA CHUA ---");
            System.out.println("  1. Xem chi tiet bao hanh");
            System.out.println("  2. Them ban ghi sua chua");
            System.out.println("  3. Huy bao hanh");
            System.out.println("  4. Xem sua chua theo bao hanh");
            System.out.println("  5. Cap nhat trang thai sua chua");
            System.out.println("  6. Thong ke sua chua");
            System.out.println("  7. Tao bao hanh thu cong");
            System.out.println("  8. Xem tat ca sua chua");
            System.out.println("  9. Loc sua chua theo trang thai");
            System.out.println("  0. Quay lai");
            System.out.print("Chon (0-9): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 1) {
                warrantyBUS.printWarranty();
            } else if (choice == 2) {
                warrantyBUS.addRepairRecord();
            } else if (choice == 3) {
                warrantyBUS.cancelWarranty();
            } else if (choice == 4) {
                repairBUS.viewRepairsByWarranty();
            } else if (choice == 5) {
                repairBUS.updateRepairStatus();
            } else if (choice == 6) {
                repairBUS.calculateRepairStats();
            } else if (choice == 7) {
                warrantyBUS.inputWarranty();
            } else if (choice == 8) {
                repairBUS.viewAllRepairs();
            } else if (choice == 9) {
                repairBUS.viewRepairsByStatus();
            } else if (choice == 0) {
                System.out.println("Quay lai menu chinh.");
            } else {
                System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    static void menuKhuyenMai() {
        int choice;
        do {
            System.out.println("\n--- KHUYEN MAI ---");
            System.out.println("  1. Them khuyen mai moi");
            System.out.println("  2. Xem tat ca khuyen mai");
            System.out.println("  3. Cap nhat khuyen mai");
            System.out.println("  4. Huy khuyen mai");
            System.out.println("  5. Xem chi tiet khuyen mai theo ma");
            System.out.println("  6. Xem khuyen mai theo san pham");
            System.out.println("  0. Quay lai");
            System.out.print("Chon (0-6): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 1) {
                promotionBUS.inputPromotion();
            } else if (choice == 2) {
                promotionBUS.printAllPromotions();
            } else if (choice == 3) {
                promotionBUS.updatePromotion();
            } else if (choice == 4) {
                promotionBUS.cancelPromotion();
            } else if (choice == 5) {
                promotionBUS.printPromotionById();
            } else if (choice == 6) {
                promotionBUS.printPromotionsByProduct();
            } else if (choice == 0) {
                System.out.println("Quay lai menu chinh.");
            } else {
                System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }


   
}
