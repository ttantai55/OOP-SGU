package DAO;

import DTO.AccessoryDTO;
import DTO.BrandDTO;
import DTO.CategoryDTO;
import DTO.CpuDTO;
import DTO.GpuDTO;
import DTO.LaptopDTO;
import DTO.ProductsDTO;
import DTO.RamDTO;
import DTO.ScreenDTO;
import DTO.StorageDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ProductListDAO  implements IProductManage<ProductsDTO> {
    private ProductsDTO[] pList;


    static Scanner sc = new Scanner(System.in);

    // Phải có Constructor để khởi tạo mảng, tránh NullPointerException
    public ProductListDAO() {
        // Khởi tạo mảng rỗng trước để tránh Null
        this.pList = new ProductsDTO[0]; 
         
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
        System.out.printf(" %-10s | %-25s | %-30s | %-15s | %-10s \n", "Ma SP", "Ten San Pham", "Thong so tom tat", "Gia ban", "So luong Ton");
        System.out.println("-------------------------------------------------------------------------");

        boolean haveProducts = false;

        // Vòng lặp duyệt qua từng sản phẩm trong kho
        for (int i = 0; i < pList.length; i++) {
            // Bỏ qua nếu là khoảng trống (null) hoặc sản phẩm này đã được đếm ở nhóm trước đó
            if (pList[i] == null || counted[i] || !pList[i].isStatus()) {
                continue;
            }

            haveProducts = true;
            
            // Lấy sản phẩm hiện tại làm "Gốc" để bắt đầu đếm
            String productIDPresent = pList[i].getProductID();
            int amount = 1; 
            counted[i] = true; // Đánh dấu là đã đếm

            // Lục lọi các sản phẩm nằm phía sau xem có anh em sinh đôi (cùng ProductID) không
            for (int j = i + 1; j < pList.length; j++) {
                //Phai kiem tra trang thai = true ms thao tac 
                if (pList[j] != null && !counted[j] && pList[j].isStatus()) {
                    // Nếu trùng mã ProductID -> Cùng một dòng máy
                    if (pList[j].getProductID().equals(productIDPresent)) {
                        amount++; // Tăng số lượng
                        counted[j] = true;  // Đánh dấu để lát nữa vòng lặp ngoài (i) không đếm lại nó nữa
                    }
                }
            }

            // Sau khi đếm xong anh em dòng họ của mã này, tiến hành in ra 1 dòng tổng hợp
            System.out.printf(" %-10s | %-25s | %-30s | %,15.2f | %7d |\n", 
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
            if (product != null && product.isStatus()) {
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
            if (product != null && product.isStatus()) {
                if ((option == 1 && product instanceof LaptopDTO) || (option == 2 && product instanceof AccessoryDTO)) {
                    filteredList[index] = product;
                    index++;
                }
            }
        }

        // Bước 3: In ra danh sách đã được lọc
        displayGroupedProducts(filteredList);
    }

    //Vua tim kiem theo ten hoac ID
    public void searchInList(){
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
            if (product != null && product.isStatus()) { //Kiem tra trang thai cua san pham
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


    //Cac Thao Tac Them, Xoa, Sua, Tim Kiem



    @Override
    public void add(ProductsDTO product){
        pList = Arrays.copyOf(pList, pList.length+1);
        pList[pList.length-1] = product;
        System.out.println("Da them thanh cong san pham");

    }

    @Override
    public void remove(String productsIMEI){
        boolean found = false;
        for (ProductsDTO product : pList) {
            if (product !=null && product.getProductIMEI().equals(productsIMEI)) {
                //Thay vi xoa khoi mang thi ta set status = false;
                product.setStatus(false);
                found = true;
                break; // Vi ma IMEI la duy nhat nen tim dc thi thoat
            }
        }
        if (found == true) {
            System.out.println("Da xoa San Pham co IMEI: " + productsIMEI);
        }
        else System.out.println("Khong tim thay San Pham de xoa!");

    }

    @Override
    public void update(ProductsDTO productUpdate) {
        boolean found = false;
        
        // SỬA Ở ĐÂY: Phải dùng for(int i) để ghi đè chính xác vào index của mảng
        for (int i = 0; i < pList.length; i++) {
            if (pList[i] != null && pList[i].getProductIMEI().equals(productUpdate.getProductIMEI())) { 
                pList[i] = productUpdate; 
                found = true;
                break;
            }
        }
        
        if (found) {
            System.out.println("Da cap nhat thanh cong san pham!");
        } else {
            System.out.println("Khong tim thay san pham can cap nhat!");
        }

    // nen co 1 ham ở main để cho ng dùng thoải mái chọn thuộc tính mà họ muốn thay đổi sau đó thì chỉ cần 1 thao tác update là đủ. 
    }

    @Override
    public ProductsDTO findById(String imei) {
        for (ProductsDTO product : pList) {
           if (product != null && product.isStatus() && product.getProductID().equalsIgnoreCase(imei)) {
                return product;
            }
        }
        return null;
    }

    @Override
    //--- Ghi du lieu--
    public void writeFile(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);

            for (ProductsDTO product : pList) {
                if (product != null) {
                    bw.write(product.toFileString());
                    bw.newLine();
                }
            }
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            System.out.println("Loi khi ghi file: "+ e.getMessage());
        }
    }

    @Override
    //---Doc du lieu---
    public void readFile(String filePath){
        ProductsDTO[] tempArr = new ProductsDTO[0];
        try {
            // Nhớ giữ lại đoạn kiểm tra File.exists() này nhé để không bị lỗi lần chạy đầu
            java.io.File file = new java.io.File(filePath);
            if (!file.exists()) {
                this.pList = tempArr; // File trống thì gán mảng rỗng cho hệ thống
                return; // Dừng hàm
            }


            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            String[] data;
            while ((line = br.readLine()) != null) { 

                if (line.trim().isEmpty()) continue; // Bo qua dong trang

                // Moi lan di vao vong lap reset product = null
                ProductsDTO product = null;
        
                // tach du lieu thanh tung cot  = dau ","

                // Laptop, 1, 2, 3, 4
                data = line.split(",");

                if (data[0].equalsIgnoreCase("Laptop")) {
                    product = assembleLaptop(data);      
                }

                else if (data[0].equalsIgnoreCase("Accessory")) {
                    product = assembleAccessory(data);
                }

                if(product != null) {
                    tempArr = Arrays.copyOf(tempArr, tempArr.length +1);
                    tempArr[tempArr.length -1] = product;
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Loi khi doc File: "+ e.getMessage());
        }
        // CỰC KỲ QUAN TRỌNG:
        // Vì hàm này là 'void' (không trả về), nên ta phải nạp trực tiếp 
        // cái mảng vừa đọc được (tempArr) vào biến gốc (pList) của hệ thống
        this.pList = tempArr;
    }

     //Ham lap rap Data vao mang 
    private LaptopDTO assembleLaptop(String[] data){
        try {
            String imei = data[1];
            String id = data[2];

            CategoryDTO cate = new CategoryDTO(data[3], data[4]);
            BrandDTO brand = new BrandDTO(data[5], data[6], Integer.parseInt(data[7]));

            String name = data[8];
            double price = Double.parseDouble(data[9]);
            int warranty = Integer.parseInt(data[10]);
            String origin = data[11];
            
            CpuDTO cpu = new CpuDTO(data[12], data[13], Integer.parseInt(data[14]), Integer.parseInt(data[15]));
            RamDTO ram = new RamDTO(Integer.parseInt(data[16]), data[17]);
            StorageDTO sto = new StorageDTO(data[18], Integer.parseInt(data[19]));
            GpuDTO gpu = new GpuDTO(data[20], data[21], Integer.parseInt(data[22]));
            ScreenDTO src = new ScreenDTO(Double.parseDouble(data[23]), data[24]);

            String battery = data[25]; 
            boolean status = data[26].trim().equalsIgnoreCase("Dang ban");

            return new LaptopDTO(imei, id, cate, brand, name, price, warranty, origin, cpu, ram, sto, gpu, src, battery, status);
        } catch (Exception e) {
            System.out.println("Loi du lieu dong Laptop: " + Arrays.toString(data));
            return null;
        }
    }

    private AccessoryDTO assembleAccessory(String[] data) {
        try {
            String imei = data[1];
            String id = data[2];

            CategoryDTO cate = new CategoryDTO(data[3], data[4]);
            BrandDTO brand = new BrandDTO(data[5], data[6], Integer.parseInt(data[7]));
            
            String name = data[8];
            double price = Double.parseDouble(data[9]);
            int warranty = Integer.parseInt(data[10]);
            String origin = data[11];
            
            
            String type = data[12];
            String desc = data[13];
            boolean status = data[14].trim().equalsIgnoreCase("Dang ban");

            return new AccessoryDTO(imei, id, cate, brand, name, price, warranty, origin, type, desc, status);
            
        } catch (Exception e) {
            System.out.println("Loi du lieu dong Accessory: " + Arrays.toString(data));
            return null;
        }
    }

    //Dem so luong sp con trong kho
    public int countAvailableStock(String productID) {
        int count = 0;
        for (ProductsDTO product : pList) {
            if (product != null && product.isStatus() && product.getProductID().equalsIgnoreCase(productID)) {
                count++;
            }
        }
        return count;
    }

}