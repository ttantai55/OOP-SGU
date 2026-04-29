package DTO;

public class Person {
    protected String fullName;
    protected String phoneNumber;
    protected String email;
    protected Address address;
    
    public Person() {
    }

    public Person(Address address, String phoneNumber, String fullName, String email) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void input(){
        System.out.println("\n--- NHẬP THÔNG TIN ---");
        // Tự động bắt lỗi: Bắt buộc nhập, SDT 10-11 số, Email @gmail.com
        setFullName(BUS.Validation.getNonEmptyString("Mời nhập họ và tên: "));
        setPhoneNumber(BUS.Validation.getValidPhone("Mời nhập số điện thoại (10 hoặc 11 số): "));
        setEmail(BUS.Validation.getValidEmail("Mời nhập email (Bắt buộc đuôi @gmail.com): "));
        
        System.out.println("\n--- NHẬP ĐỊA CHỈ ---");
        this.address = new Address();
        this.address.inPut(); // Gọi hàm nhập riêng của lớp Address
    }

    @Override
    public String toString() {
        return String.format("%-30s | %-15s | %-25s | %-45s",
                fullName, phoneNumber, email, address.hienThiDiaChi());
    }
    
    public void displayInfo(){
        System.out.println(toString());
    }
}