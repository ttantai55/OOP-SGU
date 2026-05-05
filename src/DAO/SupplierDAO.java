package DAO;

import DTO.Supplier; // Nho import dung ten class Supplier
import java.util.Arrays;
import java.io.*;

public class SupplierDAO implements IRepository<Supplier> {
    private Supplier[] suppliers;
    private int count;
    private final String defaultPath = "src/data/suppliers.txt";

    public SupplierDAO() {
        this.suppliers = new Supplier[100]; 
        this.count = 0;
        readFile(defaultPath); // Tai du lieu khi khoi dong
    }

    @Override
    public void add(Supplier obj) {
        if (count < suppliers.length) {
            suppliers[count++] = obj;
            writeFile(defaultPath); // Luu ra file
            System.out.println("[Thong bao] Them nha cung cap thanh cong!");
        } else {
            System.out.println("[Loi] Danh sach nha cung cap da day!");
        }
    }

    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(id)) {
                for (int j = i; j < count - 1; j++) {
                    suppliers[j] = suppliers[j + 1];
                }
                suppliers[--count] = null;
                writeFile(defaultPath); // Luu ra file
                System.out.println("[Thong bao] Da xoa nha cung cap: " + id);
                return;
            }
        }
        System.out.println("[Loi] Khong tim thay nha cung cap can xoa!");
    }

    @Override
    public void update(Supplier obj) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(obj.getSupplierId())) {
                suppliers[i] = obj;
                writeFile(defaultPath); // Luu ra file
                System.out.println("[Thong bao] Cap nhat nha cung cap thanh cong!");
                return;
            }
        }
        System.out.println("[Loi] Khong tim thay nha cung cap de cap nhat!");
    }

    @Override
    public Supplier findById(String id) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(id)) {
                return suppliers[i];
            }
        }
        return null;
    }

    @Override
    public Supplier[] findByName(String name) {
        Supplier[] result = new Supplier[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierName().toLowerCase().contains(name.toLowerCase())) {
                result[size++] = suppliers[i];
            }
        }
        return Arrays.copyOf(result, size); 
    }

    @Override
    public void displayAll() {
        if (count == 0) {
            System.out.println("[Thong bao] Danh sach nha cung cap dang trong!");
            return;
        }
        // In tieu de bang cho dep
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-15s | %-30s\n", "Ma NCC", "Ten Nha Cung Cap", "So Dien Thoai", "Email");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            suppliers[i].displayInfo(); 
        }
    }

    // --- CAP NHAT HAM DOC FILE ---
    @Override
    public void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Cau truc file TXT moi: id,name,phone,email
                String[] data = line.split(",");
                if (data.length >= 4) { // Dam bao du 4 truong
                    Supplier sup = new Supplier(
                        data[0].trim(), // ID
                        data[1].trim(), // Ten
                        data[2].trim(), // SDT
                        data[3].trim()  // Email (Truong moi them)
                    );
                    if (count < suppliers.length) {
                        suppliers[count++] = sup;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Thong bao] Chua co file du lieu Nha cung cap (Se tu tao khi them moi).");
        } catch (IOException e) {
            System.out.println("[Loi] Loi khi doc file Supplier: " + e.getMessage());
        }
    }

    // --- CAP NHAT HAM GHI FILE ---
    @Override
    public void writeFile(String filePath) {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs(); 
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                Supplier sup = suppliers[i];
                // Ghi 4 truong du lieu, cach nhau bang dau phay
                String line = String.format("%s,%s,%s,%s",
                    sup.getSupplierId(),
                    sup.getSupplierName(),
                    sup.getContactPhone(),
                    sup.getEmail() // Them email vao cuoi
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[Loi] Loi khi ghi file Supplier: " + e.getMessage());
        }
    }
}