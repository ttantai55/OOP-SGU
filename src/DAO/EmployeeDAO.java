package DAO;

import DTO.Address;
import DTO.Employee;
import DTO.Manager;
import DTO.SalesEmployee;
import DTO.DeliveryEmployee;
import DTO.TechnicianEmployee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

// EmployeeDAO.java - luu tru tat ca loai nhan vien (polymorphism)
public class EmployeeDAO implements IRepository<Employee> {
    private Employee[] employees;
    private int count;

    public EmployeeDAO() {
        employees = new Employee[100];
        count = 0;
    }

    @Override
    public void add(Employee obj) {
        if (count < employees.length) {
            employees[count++] = obj;
        }
    }

    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(id)) {
                for (int j = i; j < count - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--count] = null;
                return;
            }
        }
    }

    @Override
    public void update(Employee obj) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(obj.getEmployeeId())) {
                employees[i] = obj;
                return;
            }
        }
    }

    @Override
    public Employee findById(String id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(id)) {
                return employees[i];
            }
        }
        return null;
    }

    @Override
    public Employee[] findByName(String name) {
        Employee[] temp = new Employee[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (employees[i].getFullName().toLowerCase().contains(name.toLowerCase())) {
                temp[size++] = employees[i];
            }
        }
        Employee[] result = new Employee[size];
        for (int i = 0; i < size; i++)
            result[i] = temp[i];
        return result;
    }

    @Override
    public void displayAll() {
        if (count == 0) {
            System.out.println("Danh sach nhan vien rong.");
            return;
        }
        System.out.println("=".repeat(210));
        System.out.printf("%-5s | %-30s | %-13s | %-25s | %-45s | %-10s | %-15s | %-15s | %-12s | %s%n",
                "STT", "Ho Ten", "So DT", "Email", "Dia Chi", "Ma NV", "Chuc Vu", "Luong CB", "Ngay VL",
                "Thong Tin Them");
        System.out.println("=".repeat(210));
        for (int i = 0; i < count; i++) {
            System.out.printf("%-5d | ", i + 1);
            employees[i].displayInfo();
        }
        System.out.println("=".repeat(210));
        System.out.println("Tong so: " + count + " nhan vien.");
    }

    // Format file: type(M/S/D/T),employeeId,fullName,phoneNumber,email,
    // houseNumber,street,ward,district,city,
    // position,baseSalary,startDate(dd/MM/yyyy),
    // [tham so rieng tung loai]
    @Override
    public void readFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                Address addr = new Address();
                addr.setHouseNumber(parts[5]);
                addr.setStreet(parts[6]);
                addr.setWard(parts[7]);
                addr.setDistrict(parts[8]);
                addr.setCity(parts[9]);

                switch (type) {
                    case "M": { // Manager
                        Manager m = new Manager();
                        m.setEmployeeId(parts[1]);
                        m.setFullName(parts[2]);
                        m.setPhoneNumber(parts[3]);
                        m.setEmail(parts[4]);
                        m.setAddress(addr);
                        m.setPosition(parts[10]);
                        m.setBaseSalary(Float.parseFloat(parts[11]));
                        m.setStartDate(sdf.parse(parts[12]));
                        m.setAllowance(Float.parseFloat(parts[13]));
                        m.setDepartment(parts[14]);
                        add(m);
                        break;
                    }
                    case "S": { // SalesEmployee
                        SalesEmployee s = new SalesEmployee();
                        s.setEmployeeId(parts[1]);
                        s.setFullName(parts[2]);
                        s.setPhoneNumber(parts[3]);
                        s.setEmail(parts[4]);
                        s.setAddress(addr);
                        s.setPosition(parts[10]);
                        s.setBaseSalary(Float.parseFloat(parts[11]));
                        s.setStartDate(sdf.parse(parts[12]));
                        s.setBonus(Float.parseFloat(parts[13]));
                        s.setCommissionAmount(Float.parseFloat(parts[14]));
                        add(s);
                        break;
                    }
                    case "D": { // DeliveryEmployee
                        DeliveryEmployee d = new DeliveryEmployee();
                        d.setEmployeeId(parts[1]);
                        d.setFullName(parts[2]);
                        d.setPhoneNumber(parts[3]);
                        d.setEmail(parts[4]);
                        d.setAddress(addr);
                        d.setPosition(parts[10]);
                        d.setBaseSalary(Float.parseFloat(parts[11]));
                        d.setStartDate(sdf.parse(parts[12]));
                        d.setDeliveryArea(parts[13]);
                        d.setLicensePlate(parts[14]);
                        d.setFeePerOrder(Float.parseFloat(parts[15]));
                        d.setDeliveryCount(Integer.parseInt(parts[16]));
                        add(d);
                        break;
                    }
                    case "T": { // TechnicianEmployee
                        TechnicianEmployee t = new TechnicianEmployee();
                        t.setEmployeeId(parts[1]);
                        t.setFullName(parts[2]);
                        t.setPhoneNumber(parts[3]);
                        t.setEmail(parts[4]);
                        t.setAddress(addr);
                        t.setPosition(parts[10]);
                        t.setBaseSalary(Float.parseFloat(parts[11]));
                        t.setStartDate(sdf.parse(parts[12]));
                        t.setRepairCount(Integer.parseInt(parts[13]));
                        t.setExpenditure(Float.parseFloat(parts[14]));
                        add(t);
                        break;
                    }
                    default:
                        System.out.println("Loai nhan vien khong hop le: " + type);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < count; i++) {
                Employee e = employees[i];
                String common = e.getEmployeeId() + ","
                        + e.getFullName() + ","
                        + e.getPhoneNumber() + ","
                        + e.getEmail() + ","
                        + e.getAddress().getHouseNumber() + ","
                        + e.getAddress().getStreet() + ","
                        + e.getAddress().getWard() + ","
                        + e.getAddress().getDistrict() + ","
                        + e.getAddress().getCity() + ","
                        + e.getPosition() + ","
                        + e.getBaseSalary() + ","
                        + sdf.format(e.getStartDate());

                if (e instanceof Manager) {
                    Manager m = (Manager) e;
                    bw.write("M," + common + "," + m.getAllowance() + "," + m.getDepartment());
                } else if (e instanceof SalesEmployee) {
                    SalesEmployee s = (SalesEmployee) e;
                    bw.write("S," + common + "," + s.getBonus() + "," + s.getCommissionAmount());
                } else if (e instanceof DeliveryEmployee) {
                    DeliveryEmployee d = (DeliveryEmployee) e;
                    bw.write("D," + common + "," + d.getDeliveryArea() + "," + d.getLicensePlate()
                            + "," + d.getFeePerOrder() + "," + d.getDeliveryCount());
                } else if (e instanceof TechnicianEmployee) {
                    TechnicianEmployee t = (TechnicianEmployee) e;
                    bw.write("T," + common + "," + t.getRepairCount() + "," + t.getExpenditure());
                }
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

    public Employee[] getAll() {
        Employee[] result = new Employee[count];
        for (int i = 0; i < count; i++)
            result[i] = employees[i];
        return result;
    }

    // Sap xep theo ten A-Z (bubble sort)
    public void sortByName() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (employees[j].getFullName().compareToIgnoreCase(employees[j + 1].getFullName()) > 0) {
                    Employee temp = employees[j];
                    employees[j] = employees[j + 1];
                    employees[j + 1] = temp;
                }
            }
        }
    }

    // Sap xep theo luong giam dan
    public void sortBySalary() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (employees[j].calculateSalary() < employees[j + 1].calculateSalary()) {
                    Employee temp = employees[j];
                    employees[j] = employees[j + 1];
                    employees[j + 1] = temp;
                }
            }
        }
    }
}
