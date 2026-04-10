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

    //****
    public void input(){}
    //****
    @Override
    public String toString() {
        return "Person{" +
                "address=" + address +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
