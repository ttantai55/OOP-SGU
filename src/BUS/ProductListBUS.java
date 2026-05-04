package BUS;

import DAO.ProductListDAO;
import DTO.AccessoryDTO;
import DTO.LaptopDTO;
import DTO.ProductsDTO;
import java.util.Scanner;

public class ProductListBUS {
    private final ProductListDAO productDAO;
    private static final String FILE_PRODUCTS = "OOP-SGU/src/data/product.txt";

    static Scanner sc = new Scanner(System.in);

    public ProductListBUS(){
        productDAO = new ProductListDAO();
        //loadFile();
    }


    //=========LOAD / SAVE ========

    public void loadFile(){
        productDAO.readFile(FILE_PRODUCTS);
        System.out.println("Da tai du lieu thanh cong tu file" + FILE_PRODUCTS);
    }

    public void saveFile(){
        productDAO.writeFile(FILE_PRODUCTS);
        System.out.println("Da luu du lieu vao file: "+ FILE_PRODUCTS);
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
        product.input();
        productDAO.add(product);
        System.out.println("Ban them san pham thanh cong!");
        saveFile();
    }

    //===========Xoa SP===========

    public void removeProduct() {
        System.out.println("Moi ban nhap ma SP can xoa: ");
        String id = sc.nextLine();
        productDAO.remove(id);

        saveFile();
    }
    
    //======= CAP NHAT SP==============

    public void updateProduct() {
        System.out.println("Moi ban nhap ma IMEI SP can update: ");
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

    



}
