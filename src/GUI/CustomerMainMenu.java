package GUI;

import BUS.AccountService;
import BUS.CartBUS;
import BUS.InvoiceListBUS;
import BUS.ProductListBUS;
import DTO.CartItemDTO;
import DTO.ProductsDTO;
import java.util.Scanner;
import BUS.Validation;

// Giao dien chinh danh rieng cho Khach Hang
public class CustomerMainMenu {
    static Scanner sc = new Scanner(System.in);
    private String username;

    public CustomerMainMenu() {
        this.username = "";
    }

    public CustomerMainMenu(String username) {
        this.username = username;
    }

    public void showMenu() {
        CartBUS cartBUS = new CartBUS(username);
        InvoiceListBUS invoiceBUS = new InvoiceListBUS();
        ProductListBUS productBUS = new ProductListBUS();
        AccountService accountBUS = new AccountService();
        int choice;

        do {
            accountBUS.loadFromFile();
            productBUS.loadFile();
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     KHACH HANG - HE THONG CUA HANG LAPTOP");
            System.out.println("=".repeat(50));
            System.out.println("  1. Xem tat ca san pham dang ban");
            System.out.println("  2. Xem san pham theo Danh Muc (Laptop/Phu Kien)");
            System.out.println("  3. Tim kiem san pham");
            System.out.println("  4. Xem Gio hang va Thanh toan");
            System.out.println("  5. Xem lich su mua hang");
            System.out.println("  6. Doi mat khau ca nhan");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-6): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    productBUS.displayAll();
                    quickPurchase(productBUS, cartBUS);
                    break;
                case 2:
                    productBUS.displayByCategory();
                    quickPurchase(productBUS, cartBUS);
                    break;
                case 3:
                    productBUS.searchProduct();
                    quickPurchase(productBUS, cartBUS);
                    break;
                case 4:
                    cartBUS.displayMyCart();
                    cartManage(cartBUS, invoiceBUS);
                    //Thanh toan 
                    break;
                case 5:
                    invoiceBUS.printInvoicesByCustomer(username);
                    break;
                case 6:
                    accountBUS.changePassword(username);
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Dang xuat thanh cong. Dang tro ve man hinh chinh...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    // Ham ho tro mua hang nhanh khi dang xem san pham
    private void quickPurchase(ProductListBUS productBUS, CartBUS cartBUS) {
        while (true) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("Quy khach co the nhap 'Ma SP' de them vao Gio hang (Hoac nhap '0' de quay lai MENU): ");
            String option = sc.nextLine().trim();

            if (option.equals("0")) {
                break;
            }

            // Goi BUS de tim san pham
            ProductsDTO sp = productBUS.getProductByID(option);

            if (sp == null) {
                System.out.println("Khong tim thay Ma SP nay hoac SP da het hang!");
            } else {
                System.out.printf("-> Ban dang chon SP: %s | Gia: %,.0f VND\n", sp.getProductName(), sp.getPrice());
                System.out.print("Nhap so luong muon mua: ");
                
                try {
                    int amount = Validation.inputPositiveInt(sc);
                    int available = productBUS.checkStock(option);

                    if (amount > available) {
                        System.out.println("Loi: So luong trong kho khong du! (So luong con lai: " + available + ")");
                    } else {
                        cartBUS.addToCart(sp, amount);
                    }
                } catch (Exception e) {
                    System.out.println("Loi: So luong phai la con so!");
                }
            }
        }
    }

    // Quan ly gio hang
    private void cartManage(CartBUS cartBUS, InvoiceListBUS invoiceBUS) {
        int choice;
        do {
            if (cartBUS.getMyCartItems().length == 0) {
                return;
            }
            System.out.println("\n" + "=".repeat(40));
            System.out.println("  QUAN LY GIO HANG");
            System.out.println("=".repeat(40));
            System.out.println("  1. Chinh sua so luong san pham");
            System.out.println("  2. Xoa 1 san pham khoi Gio hang");
            System.out.println("  3. THANH TOAN (Chot don)");
            System.out.println("  0. Quay lai Menu chinh");
            System.out.println("=".repeat(40));
            System.out.print("-> Chon thao tac (0-3): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhap Ma SP muon sua so luong: ");
                    String updateID = sc.nextLine().trim();
                    try {
                        cartBUS.updateQuantity(updateID);
                    } catch (Exception e) {
                        System.out.println("Loi: So luong phai la so!");
                    }
                    break;

                case 2:
                    System.out.print("Nhap Ma SP muon XOA khoi gio: ");
                    String delID = sc.nextLine().trim();
                    cartBUS.removeCartItem(delID);
                    break;

                case 3:
                    System.out.println("\n--- TIEP TUC THANH TOAN ---");
                    CartItemDTO[] myCart = cartBUS.getMyCartItems();
                    invoiceBUS.checkoutFromCart(username, myCart, cartBUS);
                    // Thanh toan xong thi thoat Menu Gio hang
                    choice = 0;
                    break;

                case 0:
                    System.out.println("-> Da quay lai Menu chinh.");
                    break;

                default:
                    System.out.println("-> Lua chon khong hop le!");
            }
        } while (choice != 0);
    }
    
    
}