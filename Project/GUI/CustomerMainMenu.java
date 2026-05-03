package GUI;

import java.util.Scanner;

// [OOP] Class: Giao dien chinh danh rieng cho Doi tuong Khach Hang
public class CustomerMainMenu {
    @SuppressWarnings("resource")
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        
        do { 
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     KHACH HANG - HE THONG CUA HANG LAPTOP");
            System.out.println("=".repeat(50));
            System.out.println("  1. Xem danh sach san pham");
            System.out.println("  2. Xem gio hang cua toi");
            System.out.println("  3. Xem lich su mua hang");
            System.out.println("  4. Thoat chuong trinh (Tat toan bo he thong)");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1; // Chu dong bat loi nhap sai dinh dang
            }

            switch (choice) {
                case 1: 
                    System.out.println("\n[Thong bao] Tinh nang Xem san pham dang duoc phat trien...");
                    // Sau nay ban se goi: ProductService.showProductsForCustomer();
                    break;
                case 2: 
                    System.out.println("\n[Thong bao] Tinh nang Xem gio hang dang duoc phat trien...");
                    break;
                case 3: 
                    System.out.println("\n[Thong bao] Tinh nang Xem lich su mua hang dang duoc phat trien...");
                    break;
                case 4:
                    System.out.println("\n[Thong bao] Chuong trinh tat an toan. Tam biet!");
                    System.exit(0); // [OOP] Tuong tac truc tiep voi JVM de dong bo nho
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Dang xuat thanh cong. Dang tro ve man hinh chinh...");
                    break;
                default: 
                    System.out.println("[Loi] Lua chon khong hop le. Vui long chon lai!");
            }
        // Vong lap ket thuc khi choice == 0, tra quyen dieu khien ve cho LoginUI
        } while (choice != 0); 
    }
}