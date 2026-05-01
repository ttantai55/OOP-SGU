package DAO;

import DTO.CartItemDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class CartDAO implements ICartManage<CartItemDTO>{
    private CartItemDTO[] cartList; // day la gio hang cua ca he thong 
    //private final String filePath = "data/cart.txt";

    public CartDAO() {
        cartList = new CartItemDTO[0];
        
    }

    public CartItemDTO[] getAllCarts() {
        return cartList;
    }

    public void saveAllCarts(CartItemDTO[] newList) {
        this.cartList = newList; // Cập nhật mảng mới từ BUS đưa xuống
    }
    
    @Override
    public  void readFile(String filePath) {
        try {

            // Nhớ giữ lại đoạn kiểm tra File.exists() này nhé để không bị lỗi lần chạy đầu
            java.io.File file = new java.io.File(filePath);
            if (!file.exists()) {
                return; 
            }
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr); 

            String[] data;
            String line;
            while((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                data = line.split(",");

                CartItemDTO item = null;
                
              
                item = new CartItemDTO(data[0], data[1], data[2], Double.parseDouble(data[3]), Integer.parseInt(data[4]));              

                //Gan cac pt tu txt vao mang den khi het phan tu
                cartList = Arrays.copyOf(cartList, cartList.length + 1);
                cartList[cartList.length - 1] = item;
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Loi ghi doc file Cart:" + e.getMessage());
        }
    }


    @Override
    public void writeFile(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw =  new BufferedWriter(fw);

            for (CartItemDTO cart : cartList) {
                if (cart != null) {
                    bw.write(cart.toFileString());
                    bw.newLine();
                }
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Loi khi ghi file Cart: " + e.getMessage());
    }
}

    //Thao tac them xoa sua 
    @Override
    public void add(CartItemDTO newItem) {
        cartList = Arrays.copyOf(cartList, cartList.length + 1);
        cartList[cartList.length - 1] = newItem;
        System.out.println("Da them thanh cong san pham");
    }

    @Override
    public void update(CartItemDTO item) {
        boolean found = false;

        for (int i = 0; i < cartList.length; i++) {
            if (cartList[i] != null && cartList[i].getUsername().equalsIgnoreCase(item.getUsername()) && cartList[i].getProductID().equalsIgnoreCase(item.getProductID())) {
                cartList[i] = item;
                break;
            }
            
        }

         if (found) {
            System.out.println("Da cap nhat thanh cong san pham trong gio hang!");
        } else {
            System.out.println("Khong tim thay san pham can cap nhat trong gio hang !");
        }
    }

    @Override
    public  void remove(String username, String productID) {
        CartItemDTO[] tempArr = new CartItemDTO[0];
        for (CartItemDTO item : cartList) {
                // chi can nhet cac phan tu khong trung voi phan tu can xoa vao mang moi
                if (!item.getUsername().equals(username) && item.getProductID().equals(productID)) {
                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = item;
                }
            }
            cartList = tempArr;
    }
    

    //Xoa sach gio hang cua 1 nguoi(Chuc Nang Cua QL)

    public void clearCartByUser(String username) {
        CartItemDTO[] tempArr = new CartItemDTO[0];
        for (CartItemDTO item : cartList) {
            if(item != null && !item.getUsername().equals(username)) {
                tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                tempArr[tempArr.length - 1] = item;
            }
            cartList = tempArr;
        }
    }

    //Tim san pham trong gio hang

    @Override
    public CartItemDTO findItem(String username, String productID) {
        for (CartItemDTO item : cartList) {
           if (item != null && item.getUsername().equals(username) && item.getProductID().equalsIgnoreCase(productID)) {
                return item;
            }
        }
        return null;
    }


    // Hien thi gio hang 

    public void displayProduct(String username) { 
        double totalBill = 0;
        boolean hasItem = false;

        for (CartItemDTO item : cartList) {
            if (item != null && item.getUsername().equals(username)) {
                hasItem = true;
                double subTotal = item.getSubTotal();
                totalBill += subTotal;
                
                System.out.printf(" %-10s | %-25s | %,15.2f | %8d | %,15.2f \n", 
                        item.getProductID(), item.getProductName(), item.getPrice(), item.getQuantity(), subTotal);
            }
        }

        if (!hasItem) {
            System.out.println(" Gio hang cua ban hien tai dang trong! Hay di mua sam them nhe.");
        } else {
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println(" TONG CONG CAN THANH TOAN: "+ totalBill +" %,.2f VNĐ" );
        }

    }

    
 }


   
