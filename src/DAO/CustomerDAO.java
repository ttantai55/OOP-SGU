package DAO;

import DTO.Customer;

// CustomerDAO.java
public class CustomerDAO implements IRepository<Customer> {
    private Customer[] customers;
    private int count;

    public CustomerDAO() {
        customers = new Customer[100];
        count = 0;
    }

    @Override
    public void add(Customer obj) {
        if (count < customers.length) {
            customers[count++] = obj;
        }
    }

    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (customers[i].getCustomerId().equals(id)) {
                for (int j = i; j < count - 1; j++) {
                    customers[j] = customers[j + 1];
                }
                customers[--count] = null;
                return;
            }
        }
    }

    @Override
    public void update(Customer obj) {
        for (int i = 0; i < count; i++) {
            if (customers[i].getCustomerId().equals(obj.getCustomerId())) {
                customers[i] = obj;
                return;
            }
        }
    }

    @Override
    public Customer findById(String id) {
        for (int i = 0; i < count; i++) {
            if (customers[i].getCustomerId().equals(id)) {
                return customers[i];
            }
        }
        return null;
    }

    public Customer[] findByName(String name) {
        Customer[] temp = new Customer[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (customers[i].getFullName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {
                temp[size++] = customers[i];
            }
        }
        Customer[] result = new Customer[size];
        for (int i = 0; i < size; i++) result[i] = temp[i];
        return result;
    }

    public Customer findByUsername (String username) {
        boolean found = false;
        for (Customer cus : customers) {
            if(cus != null && cus.getUsername().equals(username)) {
                found = true;
                return cus;
            }
        }
        return null;
    }

    @Override
    public void displayAll() {
        for (int i = 0; i < count; i++) {
            System.out.println(customers[i].toString());
        }
    }

    @Override
    public void readFile(String filePath) {
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader(filePath));
            String line;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Customer c = new Customer();
                c.setCustomerId(parts[0]);
                c.setFullName(parts[1]);
                c.setPhoneNumber(parts[2]);
                c.setEmail(parts[3]);
                DTO.Address addr = new DTO.Address();
                addr.setHouseNumber(parts[4]);
                addr.setStreet(parts[5]);
                addr.setWard(parts[6]);
                addr.setDistrict(parts[7]);
                addr.setCity(parts[8]);
                c.setAddress(addr);
                c.setLoyaltyPoints(Integer.parseInt(parts[9]));
                c.setCustomerType(parts[10]);
                c.setRegisteredDate(sdf.parse(parts[11]));
                add(c);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try {
            java.io.File file = new java.io.File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            java.io.BufferedWriter bw = new java.io.BufferedWriter(
                    new java.io.FileWriter(filePath));
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < count; i++) {
                Customer c = customers[i];
                bw.write(c.getCustomerId() + ","
                        + c.getFullName() + ","
                        + c.getPhoneNumber() + ","
                        + c.getEmail() + ","
                        + c.getAddress().getHouseNumber() + ","
                        + c.getAddress().getStreet() + ","
                        + c.getAddress().getWard() + ","
                        + c.getAddress().getDistrict() + ","
                        + c.getAddress().getCity() + ","
                        + c.getLoyaltyPoints() + ","
                        + c.getCustomerType() + ","
                        + sdf.format(c.getRegisteredDate()));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public int getCount() {
        return count;
    }

    public Customer[] getAll() {
        Customer[] result = new Customer[count];
        for (int i = 0; i < count; i++) result[i] = customers[i];
        return result;
    }
    public void hienThiDanhSach() {
        if (this.count == 0) {
            System.out.println("Danh sach khach hang rong.");
        } else {
            System.out.println("=".repeat(140));
            System.out.printf("%-10s | %-25s | %-13s | %-25s | %-40s | %6s | %-8s | %-12s%n", "Ma KH", "Ho Ten", "So DT", "Email", "Dia Chi", "Diem", "Loai", "Ngay DK");
            System.out.println("=".repeat(140));

            for(int var1 = 0; var1 < this.count; ++var1) {
                this.customers[var1].toString();
            }

            System.out.println("=".repeat(140));
            System.out.println("Tong so: " + this.count + " khach hang.");
        }
    }
    // Sắp xếp theo tên A-Z (bubble sort)
    public void sortByName() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (customers[j].getFullName()
                        .compareToIgnoreCase(customers[j + 1].getFullName()) > 0) {
                    Customer temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
    }

    // Sắp xếp theo điểm tích lũy giảm dần
    public void sortByPoints() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (customers[j].getLoyaltyPoints()
                        < customers[j + 1].getLoyaltyPoints()) {
                    Customer temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
    }

    // Thống kê theo loại khách hàng
    public void statsbyType() {
        int VIP = 0, thuong = 0, moi = 0;
        for (int i = 0; i < count; i++) {
            switch (customers[i].getCustomerType().toUpperCase()) {
                case "VIP": VIP++; break;
                case "thuong":  thuong++;  break;
                case "moi":     moi++;     break;
            }
        }
        System.out.println("VIP : " + VIP);
        System.out.println("thuong  : " +  thuong);
        System.out.println("moi     : " + moi);
    }
    //
    public void sapXepTheoTen() {
        for(int var1 = 0; var1 < this.count - 1; ++var1) {
            for(int var2 = 0; var2 < this.count - var1 - 1; ++var2) {
                if (this.customers[var2].getFullName().compareToIgnoreCase(this.customers[var2 + 1].getFullName()) > 0) {
                    Customer var3 = this.customers[var2];
                    this.customers[var2] = this.customers[var2 + 1];
                    this.customers[var2 + 1] = var3;
                }
            }
        }

        System.out.println("Da sap xep theo ten.");
    }
}