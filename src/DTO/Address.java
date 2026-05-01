package DTO;

import java.util.Scanner;

public class Address {
    private String houseNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
    static Scanner sc = new Scanner(System.in);
    public Address() {
    }

    public Address(String city, String district, String houseNumber, String street, String ward) {
        this.city = city;
        this.district = district;
        this.houseNumber = houseNumber;
        this.street = street;
        this.ward = ward;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String hienThiDiaChi() {
        return houseNumber + ", " + street + ", " + ward + ", " + district + ", " + city;
    }

    public void inPut(){
        System.out.println("Moi nhap so nha: ");
        setHouseNumber(sc.nextLine());
        System.out.println("Moi nhap ten duong");
        setStreet(sc.nextLine());
        System.out.println("Moi nhap ten phuong/xa: ");
        setWard(sc.nextLine());
        System.out.println("Moi nhap quan/huyen: ");
        setDistrict(sc.nextLine());
        System.out.println("moi nhap tinh: ");
        setCity(sc.nextLine());
    }
    //****
    @Override
    public String toString() {
        return String.format("%-7s | %-20s | %-15s | %-15s | %-15s |",
                houseNumber, street, ward, district, city);
    }
    public void displayInfo(){
        System.out.println(toString());
    }
}