package BUS;

import DAO.CartDAO;
import DTO.CartItemDTO;
import DTO.ProductsDTO;
import java.util.Scanner;

public class CartBUS {
    private final CartDAO cartDAO;
    private final String username;
    private final String FILE_CART = "data/cart.txt";
    static Scanner sc = new Scanner(System.in);

    public CartBUS(String username) {
        this.cartDAO = new CartDAO();
        this.username = username;
    }

    //====LOAD/SAVE====

    public void loadFile(){
        cartDAO.readFile(FILE_CART);
        System.out.println("Da tai du lieu thanh cong tu file" + FILE_CART);
    }

    public void saveFile(){
        cartDAO.writeFile(FILE_CART);
        System.out.println("Da luu du lieu vao file: "+ FILE_CART);
    }

    // Them SP vao Gio Hang 

    public void addToCart(ProductsDTO sp, int quantity) {
        System.out.println("Moi nhap so luong SP ban muon mua: ");
        quantity = Integer.parseInt(sc.nextLine()); // nho viet ham kiem tra

        // Tu giac lay thong tin san pham

        String id = sp.getProductID();
        String name = sp.getProductName();
        double price = sp.getPrice();

        // Kiem tra trong Gio hang da co muon nay chua

        CartItemDTO existingItem = cartDAO.findItem(username, id);

        if (existingItem != null) {

            int newQuantity = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(newQuantity);
                
                cartDAO.update(existingItem);
                System.out.println("Da cap nhat thanh cong !");
        }
        else {
            CartItemDTO newItem = new CartItemDTO(username, id, name, price, quantity);
            cartDAO.add(newItem);
            System.out.println("-> Da them [" + quantity +"] san pham " + name + " vao gio hang!");
        }
        saveFile();
    }

    public void removeCartItem(String productID) {
        if (cartDAO.findItem(username, productID) != null) {
            cartDAO.remove(username, productID);
            System.out.println("-> Da xoa san pham khoi gio hang!");
        } else {
            System.out.println("-> Khong tim thay san pham trong gio hang!");
        }
        saveFile();
    }

    public void updateCartItem(CartItemDTO item){
        cartDAO.update(item);
    }

    public void clearMyCart() {
        cartDAO.clearCartByUser(username);
        saveFile();
    }

    //======Hien thi======
    
    public void displayMyCart() {
        cartDAO.displayProduct(username);
    }

    

}
