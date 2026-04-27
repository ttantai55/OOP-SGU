package DAO;

import DTO.ProductsDTO;
import java.util.Arrays;

public abstract class ProductListDAO  implements IRepository<ProductsDTO> {
    private ProductsDTO[] pList;

    // Phải có Constructor để khởi tạo mảng, tránh NullPointerException
    public ProductListDAO() {
        this.pList = new ProductsDTO[0]; 
        // Sau này bạn sẽ gọi hàm đọc File ở đây để nạp dữ liệu vào pList
    }

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
}