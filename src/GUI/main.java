package GUI;
import BUS.AccountService;
import BUS.CartBUS;
import BUS.CustomerService;
import BUS.EmployeeService;
import BUS.InvoiceListBUS;
import BUS.ProductListBUS;
import DTO.CartItemDTO;
import DTO.ProductsDTO;
import java.util.Scanner;

public class main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();

        // Tự động load dữ liệu khi khởi động chương trình
        System.out.println("Dang tai du lieu tu file...");
        employeeService.loadFromFile();
        customerService.loadFromFile();

        int choice;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("  CHUONG TRINH QUAN LY CUA HANG (MAIN MENU)");
            System.out.println("=".repeat(50));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Luu toan bo du lieu ra file");
            System.out.println("  0. Thoat chuong trinh");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-3): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    // Gọi menu quản lý nhân viên từ EmployeeService
                    employeeService.showMenu();
                    break;
                case 2:
                    // Gọi menu quản lý khách hàng từ CustomerService
                    customerService.showMenu();
                    break;
                case 3:
                    System.out.println("\nLuu du lieu...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    break;
                case 0:
                    System.out.println("\nDang luu du lieu truoc khi thoat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    System.out.println("Cam on ban da su dung chuong trinh. Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    public static void showCustomerMenu(String username) {
        CartBUS cartBUS = new CartBUS(username);
        InvoiceListBUS invoiceBUS = new InvoiceListBUS();
        ProductListBUS productBUS = new ProductListBUS();
        AccountService accountBUS = new AccountService();
        int choice;
        do { 
            System.out.println("===MENU KHACH HANG===");
            System.out.println("1.Xem tat ca san pham dang ban");
            System.out.println("2.Xem san pham dua tren Danh Muc (Laptop/Phu Kien)");
            System.out.println("3.Tim kiem san pham");
            System.out.println("4.Xem Gio hang va Thanh toan");
            System.out.println("5.Xem lich su mua hang");
            System.out.println("6.Doi mat khau ca nhan");
            System.out.println("0.Dang xuat");

            choice = Integer.parseInt(sc.nextLine());
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
                    CartManage(cartBUS, invoiceBUS, username);
                    break; 
                case 5:
                    // XEm Lich su giao dich
                    break;
                case 6:
                    accountBUS.changePassword(username);
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh thanh cong!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }

        } while (choice != 0);

        System.out.println("Da dang xuat thanh cong!");
        //showLoginScreen()
    }

    //Ham nay ho tro mua hang nhanh khi dang xem Menu
    private static void quickPurchase(ProductListBUS productBUS, CartBUS cartBUS){
        while (true) { 
            System.out.println("\n"+"-".repeat(40));
            System.out.println("Quy khach co the nhap 'Ma SP' de them vao Gio hang (Hoac nhap '0' de quay lai MENU): ");
            String option = sc.nextLine().trim();

            if(option.equals("0")) {
                break;
            }

            //Goi BUS de tim sp 
            ProductsDTO sp = productBUS.getProductByID(option);

            if (sp == null) {
                System.out.println("Khong tim thay Ma SP nay hoac SP da het hang!");
            }
            else {
                System.out.println("-> Ban dang chon SP: " + sp.getProductName() + " | Gia: " + sp.getPrice() + "VND");

                try {
                    int amount = Integer.parseInt(sc.nextLine());
                    int available = productBUS.checkStock(option);

                    if(amount > available) {
                        System.out.println("Loi : So luong trong kho khong du! (So luong con lai: " + available +")");
                    }
                    else {
                        cartBUS.addToCart(sp, amount);
                    }
                } catch (Exception e) {
                    System.out.println("Loi : so luong phai la con so!");
                }
            }
        }
    }

    private static void CartManage(CartBUS cartBUS, InvoiceListBUS invoiceBUS, String username) {
        int choice;
        CartItemDTO[] myCart = new CartItemDTO[0];
        do {
            if(cartBUS.getMyCartItems().length == 0) {
                return;
            }
            System.out.println("====Quan Ly Gio Hang===");
            System.out.println("1.Chinh sua so luong san pham");
            System.out.println("2.Xoa 1 san pham khoi Gio hang");
            System.out.println("3. THANH TOAN (Chot don)");
            System.out.println("0. Quay lai Menu chinh");
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
                    myCart = cartBUS.getMyCartItems();
                    invoiceBUS.checkoutFromCart(username, myCart, cartBUS);
                    
                    // Thanh toán xong thì gán choice = 0 để thoát khỏi Menu Giỏ hàng luôn
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
