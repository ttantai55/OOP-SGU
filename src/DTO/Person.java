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
        setFullName(BUS.Validation.getValidFullName("Moi nhap ho va ten: "));
        setPhoneNumber(BUS.Validation.getValidPhone("Moi nhap so dien thoai (10-11 so): "));
        setEmail(BUS.Validation.getValidEmail("Moi nhap email (@gmail.com): "));
        System.out.println("Moi nhap dia chi: ");
        this.address = new Address();
        this.address.inPut();
    }

    @Override
    public String toString() {
        String addr = address != null ? address.hienThiDiaChi() : "";
        // Truncate address if too long to keep table aligned
        if (addr.length() > 50) {
            addr = addr.substring(0, 47) + "...";
        }
        return String.format("%-25s | %-12s | %-25s | %-50s",
                fullName, phoneNumber, email, addr);
    }
    public void displayInfo() {
        System.out.println(toString());
    }
}

