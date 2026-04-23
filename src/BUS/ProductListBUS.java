package BUS;

import DTO.AccessoryDTO;
import DTO.LaptopDTO;
import DTO.ProductsDTO;
import java.util.Scanner;

public class ProductListBUS {
    public ProductsDTO pList[];

    static Scanner sc = new Scanner(System.in);
    
    public void menu(){

    }

    public void inputList(){
        int temp;
        System.out.println("Moi nhap so luong San Pham can nhap:");
        int amount = Integer.parseInt(sc.nextLine());
        pList = new ProductsDTO[amount];
        
        for (int i = 0; i < pList.length; i++) {
            System.out.println("Moi chon Danh Muc cho San pham: \n1.Laptop \n2.Phu kien");
            temp = Integer.parseInt(sc.nextLine());
            if(temp == 1) { 
                pList[i] = new LaptopDTO();
            }
            if (temp == 2) {
                pList[i] = new AccessoryDTO();
            }
            pList[i].input();
            System.out.println(" Da nhap thanh cong San pham thu " + (i + 1));
        }
    }

    public void displayGroupedProducts(ProductsDTO[] pList) {
        // kiem tra mang 
        if (pList == null || pList.length == 0) {
            System.out.println("Kho hang hien tai chua co du lieu! Vui long nhap du lieu truoc.");
            return; // Dừng hàm luôn
    }
        // Tạo một mảng cờ hiệu để đánh dấu xem sản phẩm nào đã được đếm/gom nhóm rồi
        boolean[] counted = new boolean[pList.length];
        
        // In tiêu đề bảng cho đẹp
        System.out.printf(" %-10s | %-25s | %-30s | %-15s | %-10s |\n", "Ma SP", "Ten San Pham", "Thong so tom tat", "Gia ban", "So luong Ton");
        System.out.println("-------------------------------------------------------------------------");

        boolean haveProducts = false;

        // Vòng lặp duyệt qua từng sản phẩm trong kho
        for (int i = 0; i < pList.length; i++) {
            // Bỏ qua nếu là khoảng trống (null) hoặc sản phẩm này đã được đếm ở nhóm trước đó
            if (pList[i] == null || counted[i]) {
                continue;
            }

            haveProducts = true;
            
            // Lấy sản phẩm hiện tại làm "Gốc" để bắt đầu đếm
            String productIDPresent = pList[i].getProductID();
            int amount = 1; 
            counted[i] = true; // Đánh dấu là đã đếm

            // Lục lọi các sản phẩm nằm phía sau xem có anh em sinh đôi (cùng ProductID) không
            for (int j = i + 1; j < pList.length; j++) {
                if (pList[j] != null && !counted[j]) {
                    // Nếu trùng mã ProductID -> Cùng một dòng máy
                    if (pList[j].getProductID().equals(productIDPresent)) {
                        amount++; // Tăng số lượng
                        counted[j] = true;  // Đánh dấu để lát nữa vòng lặp ngoài (i) không đếm lại nó nữa
                    }
                }
            }

            // Sau khi đếm xong anh em dòng họ của mã này, tiến hành in ra 1 dòng tổng hợp
            System.out.printf(" %-10s | %-25s | %-30s | %,15.2f | %7d c |\n", 
                    productIDPresent, 
                    pList[i].getProductName(),
                    pList[i].getSpecSummary(), // Lay thong so chi tiet
                    pList[i].getPrice(), 
                    amount);
        }

        if (!haveProducts) {
            System.out.println(" Kho hang hien tai đang trong!");
        }
        System.out.println("=========================================================================");
    }
    //Xuat tat ca SP
    public void displayAllProducts() {
        System.out.println("===DS tat ca san pham (gom theo ProductID)===:");
        displayGroupedProducts(pList);
    }

    // Xuất theo danh mục và gom theo ProductID
    public void displayByCategory() {
        System.out.println("Moi chon Danh muc San pham: \n1. Laptop \n2. Phu kien ");
        int option;
        try {
            option = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lua chon khong hop le!");
            return;
        }

        // Bước 1: Đếm số lượng sản phẩm thuộc danh mục để tạo mảng
        int count = 0;
        for (ProductsDTO product : pList) {
            if (product != null) {
                if ((option == 1 && product instanceof LaptopDTO) || (option == 2 && product instanceof AccessoryDTO)) {
                    count++;
                }
            }
        }

        // Kiểm tra hết hàng
        if (count == 0) {
            System.out.println("Khong co san pham nao thuoc danh muc nay trong kho!");
            return;
        }

        // Bước 2: Tạo mảng mới và đưa các sản phẩm đã lọc vào
        ProductsDTO[] filteredList = new ProductsDTO[count];
        int index = 0;
        for (ProductsDTO product : pList) {
            if (product != null) {
                if ((option == 1 && product instanceof LaptopDTO) || (option == 2 && product instanceof AccessoryDTO)) {
                    filteredList[index] = product;
                    index++;
                }
            }
        }

        // Bước 3: In ra danh sách đã được lọc
        displayGroupedProducts(filteredList);
    }

    public void findAtProducts(){
        String keyword;
        System.out.println("Moi nhap ten san pham hoac ma san pham (ID) can tim: ");
        while(true){
            keyword = sc.nextLine().trim(); 
            if(!keyword.isEmpty()) {
                break;
            }
            else System.out.println("Ban chua nhap gi ca! Moi nhap lai!");
        }
        
        System.out.println("\n === KET QUA TIM KiEM CHO: '"+ keyword +"' ===");

        //Chuyen tu khoa ve dang chu thuong cho de so sanh
        String lowerKeyword = keyword.toLowerCase();
        boolean found = false;

        // In tieu de cho bang tim kiem 
        System.out.printf(" %-10s | %-25s | %-15s |\n", "Ma SP", "Ten San Pham", "Gia ban");
        System.out.println("-------------------------------------------------------------------------");

        for (ProductsDTO product : pList) {
            if (product != null) {
                //Lay ID SP va Ten SP, chuyen het ve dang chu thuong
                String id = product.getProductID();
                String name = product.getProductName().toLowerCase();
                //Kiem tra xem ID va ten SP 
                if (id.equalsIgnoreCase(lowerKeyword) || name.contains(lowerKeyword)) {
                    System.out.printf(" %-10s | %-25s | %,15.2f |\n", product.getProductID(), product.getProductName(), product.getPrice());
                    found = true; // Tinh hieu tim thay it nhat 1 sp
                }
            }
        }
        if (!found) {
            System.out.println("Khong tim thay san pham khop voi tu khoa: "+ keyword);
        }
        System.out.println("====================================================");
    }

}
