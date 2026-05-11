package BUS;

import static BUS.Validation.PRODUCT_IMEI_REGEX;
import DAO.ProductListDAO;
import DTO.AccessoryDTO;
import DTO.LaptopDTO;
import DTO.ProductsDTO;
import java.util.Arrays;
import java.util.Scanner;

public class ProductListBUS {
    private static ProductListDAO productDAO;
    private static final String FILE_PRODUCTS = "src/data/product.txt";

    static Scanner sc = new Scanner(System.in);

    public ProductListBUS(){
        
        productDAO = new ProductListDAO();
        productDAO.readFile(FILE_PRODUCTS);
    }

    //=========LOAD / SAVE ========

    public void loadFile(){
        productDAO.readFile(FILE_PRODUCTS);
        
    }

    public void saveFile(){
        productDAO.writeFile(FILE_PRODUCTS);
        
    }

    //=========THEM SP=============

    public void addProducts() {
        ProductsDTO product = new ProductsDTO();
        System.out.println("Moi chon Danh Muc cho San pham: \n1. Laptop \n2. Phu kien");
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Loi: Vui long nhap so!");
            return;
        }
        
        switch (choice) {
            case 1:
                product = new LaptopDTO();
                break;
            case 2:
                product = new AccessoryDTO();
                break;
            default:
                System.out.println("Lua chon khong hop le. Huy thao tac!");
                return;
        }
        product.setProductIMEI(inputUniqueIMEI());
        product.input();
        productDAO.add(product);
        System.out.println("Ban them san pham thanh cong!");
        saveFile();
    }

    //===========Xoa SP===========

    public void removeProduct() {
    productDAO.displayAllProducts();
    System.out.print("Nhap ma SP (VD: LT001): ");
    String productId = sc.nextLine().trim();

    // Lấy tất cả IMEI cùng dòng
    ProductsDTO[] all = productDAO.getALL();
    ProductsDTO[] found = new ProductsDTO[0];
    for (ProductsDTO p : all) {
        if (p != null && p.isStatus() && p.getProductID().equalsIgnoreCase(productId)) {
            found = Arrays.copyOf(found, found.length + 1);
            found[found.length - 1] = p;
        }
    }

    if (found.length == 0) {
        System.out.println("Khong tim thay san pham!");
        return;
    }

    // Hiển thị danh sách IMEI
    System.out.println("--- Danh sach IMEI cua [" + productId + "] ---");
    for (int i = 0; i < found.length; i++) {
        System.out.printf("%d. %s%n", i + 1, found[i].getProductIMEI());
    }

    System.out.print("Chon so thu tu IMEI can xoa (0 = huy): ");
    try {
        int idx = Integer.parseInt(sc.nextLine().trim());
        if (idx == 0) return;
        if (idx < 1 || idx > found.length) {
            System.out.println("Lua chon khong hop le!");
            return;
        }
        productDAO.remove(found[idx - 1].getProductIMEI()); // Xóa đúng IMEI
        saveFile();
    } catch (NumberFormatException e) {
        System.out.println("Nhap so khong hop le!");
    }
}
    
    //======= CAP NHAT SP==============

    public void updateProduct() {
        productDAO.displayAllProducts();
        System.out.println("Moi ban nhap ma IMEI SP can cap nhat: ");
        String imei = sc.nextLine();
        
        ProductsDTO p = productDAO.findById(imei);
        p.input();
        productDAO.update(p);

        saveFile();
    }

    // ======== Tim Kiem ============
    
    public void searchProduct(){
        productDAO.searchInList();
    }

    

    // ======== Hien thi ============
    
    public void displayAll() {
        productDAO.displayAllProducts();
    }
    
    public void displayByCategory() {
        productDAO.displayByCategory();
    }

    //Lay san Pham theo IMEI/ID
    public ProductsDTO getProductByID(String id) {
        return productDAO.findById(id);
    }
    
    //Kiem tra kho
    public int checkStock(String id) {
        return productDAO.countAvailableStock(id);
    }
    
    // Hàm ép nhập IMEI không bị trùng lặp
    public static String inputUniqueIMEI() {
        String imei;
        while (true) {
            System.out.print("Moi nhap ma IMEI (VD: LIMEI-001, AIMEI-099): ");
            imei = sc.nextLine().trim();

            // Ải 1: Kiểm tra định dạng bằng Regex (Gọi từ class Validator)
            if (!imei.matches(PRODUCT_IMEI_REGEX)) { 
                System.out.println("-> Loi: Ma IMEI sai dinh dang! Phai bat dau bang 'LIMEI-' hoac 'AIMEI-' kem 3 chu so.");
                continue; // Sai thì bắt vòng lặp chạy lại từ đầu
            }

            // Ải 2: Kiểm tra trùng lặp trong kho dữ liệu
            boolean isDuplicate = false;
            // Lấy toàn bộ danh sách sản phẩm hiện có lên để đối chiếu
            ProductsDTO[] pList = productDAO.getALL(); // (Tùy tên hàm bên DAO của bro, có thể là getArray() hoặc pList)
            
            for (ProductsDTO p : pList) {
                if (p != null && p.getProductIMEI().equalsIgnoreCase(imei)) {
                    isDuplicate = true;
                    break; // Thấy có 1 thằng trùng là thoát vòng lặp for ngay lập tức
                }
            }

            if (isDuplicate) {
                System.out.println("-> Loi: Ma IMEI [" + imei.toUpperCase() + "] DA TON TAI trong kho! Vui long kiem tra lai hang hoa.");
                continue; // Trùng thì bắt nhập lại
            }

            // Vượt qua cả 2 ải thành công -> Trả về IMEI viết hoa cho đẹp
            return imei.toUpperCase();
        }
            
    }
    
}
