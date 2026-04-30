package DTO;

// [OOP] Class: Lớp cha (Base class) đại diện cho một con người nói chung
public class Person {
    
    // [OOP] Inheritance: Dùng 'protected' để các lớp con kế thừa có thể truy cập trực tiếp
    protected String fullName;
    protected String phoneNumber;
    protected String email;
    
    // [OOP] Composition (Mối quan hệ "Has-A"): Một người sở hữu một địa chỉ
    protected Address address;
    
    // 1. Constructor mặc định
    public Person() {
    }

    // 2. Constructor đầy đủ tham số
    public Person(Address address, String phoneNumber, String fullName, String email) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.email = email;
    }

    // [OOP] Encapsulation: Getter/Setter
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void input(){
        System.out.println("\n--- NHAP THONG TIN ---");
        // Tu dong bat loi: Bat buoc nhap, SDT 10-11 so, Email @gmail.com
        setFullName(BUS.Validation.getNonEmptyString("Moi nhap ho va ten: "));
        setPhoneNumber(BUS.Validation.getValidPhone("Moi nhap so dien thoai (10 hoac 11 so): "));
        setEmail(BUS.Validation.getValidEmail("Moi nhap email (Bat buoc duoi @gmail.com): "));
        
        System.out.println("\n--- NHAP DIA CHI ---");
        this.address = new Address();
        this.address.inPut(); // Goi ham nhap rieng cua lop Address
    }

    // [OOP] Polymorphism: Ghi đè phương thức toString()
    @Override
    public String toString() {
        return String.format("%-30s | %-15s | %-25s | %-45s",
                fullName, phoneNumber, email, address.hienThiDiaChi());
    }
    
    public void displayInfo(){
        System.out.println(toString());
    }
}