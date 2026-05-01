
import BUS.CartBUS;
import BUS.ProductListBUS;
import DTO.ProductsDTO;
import java.util.Scanner;

public class main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
       

    }


    //showLoginScreen()
    //MENU khach hang

    public static void showCustomerMenu(String username) {
        CartBUS cartBUS = new CartBUS(username);
        ProductListBUS productBUS = new ProductListBUS();
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
                    //Thanh toan
                    break; 
                case 5:
                    // XEm Lich su giao dich
                    break;
                case 6:
                    //Doi mat khau
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

}
