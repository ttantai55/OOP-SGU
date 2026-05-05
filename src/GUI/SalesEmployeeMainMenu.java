package GUI;

import BUS.CartBUS;
import BUS.CustomerService;
import BUS.InvoiceListBUS;
import BUS.ProductListBUS;
import DTO.CartItemDTO;
import DTO.ProductsDTO;
import java.util.Scanner;

// Giao dien danh cho Nhan vien Ban hang
public class
SalesEmployeeMainMenu {
    static Scanner sc = new Scanner(System.in);
    private String username;

    public SalesEmployeeMainMenu() {
        this.username = "";
    }

    public SalesEmployeeMainMenu(String username) {
        this.username = username;
    }

    public void showMenu() {
        ProductListBUS productBUS = new ProductListBUS();
        CustomerService customerService = new CustomerService();
        customerService.loadFromFile();

        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  NHAN VIEN BAN HANG - HE THONG CUA HANG LAPTOP");
            System.out.println("=".repeat(55));
            System.out.println("  1. Xem tat ca san pham");
            System.out.println("  2. Xem san pham theo Danh Muc (Laptop/Phu Kien)");
            System.out.println("  3. Tim kiem san pham");
            System.out.println("  4. Tao hoa don ban hang cho khach");
            System.out.println("  5. Xem danh sach khach hang");
            System.out.println("  6. Tim khach hang theo ten");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-6): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    productBUS.displayAll();
                    pause();
                    break;
                case 2:
                    productBUS.displayByCategory();
                    pause();
                    break;
                case 3:
                    productBUS.searchProduct();
                    pause();
                    break;
                case 4:
                    createInvoiceForCustomer(productBUS);
                    break;
                case 5:
                    customerService.displayAll();
                    pause();
                    break;
                case 6:
                    System.out.print("Nhap ten khach hang can tim: ");
                    String name = sc.nextLine().trim();
                    DTO.Customer[] results = customerService.findByName(name);
                    if (results.length == 0) {
                        System.out.println("[Thong bao] Khong tim thay khach hang nao.");
                    } else {
                        System.out.println("\n--- KET QUA TIM KIEM ---");
                        for (DTO.Customer c : results) {
                            c.displayInfo();
                        }
                    }
                    pause();
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Dang xuat thanh cong!");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    // Tao hoa don ban hang cho khach
    private void createInvoiceForCustomer(ProductListBUS productBUS) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  TAO HOA DON BAN HANG");
        System.out.println("=".repeat(50));

        System.out.print("Nhap ten dang nhap cua khach hang: ");
        String customerUsername = sc.nextLine().trim();

        if (customerUsername.isEmpty()) {
            System.out.println("[Loi] Ten dang nhap khong duoc de trong!");
            return;
        }

        CartBUS cartBUS = new CartBUS(customerUsername);
        InvoiceListBUS invoiceBUS = new InvoiceListBUS();

        // Hien thi san pham de chon
        productBUS.displayAll();

        // Cho nhan vien them san pham vao gio hang cua khach
        System.out.println("\n--- THEM SAN PHAM VAO HOA DON ---");
        while (true) {
            System.out.println("\nNhap 'Ma SP' de them vao hoa don (Nhap '0' de ket thuc): ");
            String productId = sc.nextLine().trim();

            if (productId.equals("0")) {
                break;
            }

            ProductsDTO sp = productBUS.getProductByID(productId);
            if (sp == null) {
                System.out.println("[Loi] Khong tim thay san pham hoac san pham het hang!");
            } else {
                System.out.println("-> SP: " + sp.getProductName() + " | Gia: " + sp.getPrice() + " VND");
                System.out.print("Nhap so luong: ");
                try {
                    int amount = Integer.parseInt(sc.nextLine().trim());
                    int available = productBUS.checkStock(productId);

                    if (amount <= 0) {
                        System.out.println("[Loi] So luong phai lon hon 0!");
                    } else if (amount > available) {
                        System.out.println("[Loi] Khong du hang! (Con lai: " + available + ")");
                    } else {
                        cartBUS.addToCart(sp, amount);
                        System.out.println("[OK] Da them " + amount + " x " + sp.getProductName());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Loi] So luong phai la so!");
                }
            }
        }

        // Hien thi gio hang va xac nhan thanh toan
        CartItemDTO[] myCart = cartBUS.getMyCartItems();
        if (myCart.length == 0) {
            System.out.println("[Thong bao] Gio hang trong, khong tao hoa don.");
            return;
        }

        cartBUS.displayMyCart();
        System.out.print("\nXac nhan thanh toan cho khach? (Y/N): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            invoiceBUS.checkoutFromCart(customerUsername, myCart, cartBUS);
        } else {
            System.out.println("[Thong bao] Da huy tao hoa don.");
        }
    }

    private void pause() {
        System.out.print("\nNhan Enter de tiep tuc...");
        sc.nextLine();
    }
}