import DTO.Supplier;

// [OOP] Class: Lop thuc thi kich ban kiem thu (Driver Class)
public class TestSupplier {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("   UNIT TEST: KIEM THU NHAP NHA CUNG CAP     ");
        System.out.println("=============================================");
        
        // [OOP] Object Creation: Khoi tao mot doi tuong testSupplier
        Supplier testSupplier = new Supplier();
        
        System.out.println("[Thong bao] BAT DAU CHAY HAM INPUT()...");
        System.out.println("-> Co tinh nhap sai de xem he thong bat loi:");
        
        // Goi ham input() - Noi chua cac logic Validation (Dong goi va Tai su dung)
        testSupplier.input(); 
        
        System.out.println("\n=============================================");
        System.out.println(" KET QUA DU LIEU DA DUOC LUU VAO DOI TUONG:  ");
        System.out.println("=============================================");
        
        // Goi ham hien thi de kiem tra trang thai cua doi tuong sau khi nhap
        testSupplier.displayInfo();
    }
}