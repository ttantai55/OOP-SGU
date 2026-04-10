package DTO;

public class Address {
    private String houseNumber;
    private String street;
    private String ward;
    private String district;
    private String city;

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
    //chưa làm ****
    public void inPut(){}
    //****
    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", street='" + street + '\'' +
                ", ward='" + ward + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
