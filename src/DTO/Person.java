package DTO;

import java.util.Scanner;

public class Person {
    protected String fullName;
    protected String phoneNumber;
    protected String email;
    protected Address address;
    static Scanner sc = new Scanner(System.in);
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
        setFullName(BUS.Validation.getNonEmptyString("Moi nhap ho va ten: "));
        setPhoneNumber(BUS.Validation.getValidPhone("Moi nhap so dien thoai (10-11 so): "));
        setEmail(BUS.Validation.getValidEmail("Moi nhap email (@gmail.com): "));
        System.out.println("Moi nhap dia chi: ");
        this.address = new Address();
        this.address.inPut();
    }

    @Override
    public String toString() {
        return String.format("%-30s | %-13s | %-25s | %-45s",
                fullName, phoneNumber, email, address.hienThiDiaChi());
    }
    public void displayInfo() {
        System.out.println(toString());
    }
}

