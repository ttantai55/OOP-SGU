package DAO;

import DTO.Account;
import java.util.Arrays;
<<<<<<< HEAD
import java.io.*; 

// [OOP] Tinh Truu tuong (Abstraction) & Da hinh (Polymorphism): Implement interface IRepository
public class AccountDAO implements IRepository<Account> {
    
    // [OOP] Tinh Dong goi (Encapsulation): Bao ve du lieu mang bang private
    private Account[] accounts;
    private int count;
    private final String defaultPath = "src/data/accounts.txt"; 
=======
import java.io.*;

public class AccountDAO implements IRepository<Account> {

    private Account[] accounts;
    private int count;
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5

    public AccountDAO() {
        this.accounts = new Account[100];
        this.count = 0;
<<<<<<< HEAD
        readFile(defaultPath); // Tu dong tai du lieu khi khoi tao
=======
        // [ĐÃ SỬA LỖI] Tắt tính năng tự động đọc file ở hàm khởi tạo
        // Để DAO trở nên thụ động, chờ Service ra lệnh mới được đọc
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
    }

    @Override
    public void add(Account obj) {
        if (count < accounts.length) {
            accounts[count++] = obj;
<<<<<<< HEAD
            writeFile(defaultPath); 
            System.out.println("[Thong bao] Them tai khoan thanh cong!");
=======
            System.out.println("[Thong bao] Them tai khoan vao bo nho thanh cong!");
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
        } else {
            System.out.println("[Loi] Danh sach tai khoan da day!");
        }
    }

    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(id)) {
<<<<<<< HEAD
                // Thuat toan don mang: Dich cac phan tu sang trai 1 vi tri
                for (int j = i; j < count - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[--count] = null; // Xoa phan tu cuoi, giam so luong
                writeFile(defaultPath); 
                System.out.println("[Thong bao] Da xoa tai khoan: " + id);
=======
                for (int j = i; j < count - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[--count] = null;
                System.out.println("[Thong bao] Da xoa tai khoan khoi bo nho: " + id);
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
                return;
            }
        }
        System.out.println("[Loi] Khong tim thay tai khoan can xoa!");
    }

    @Override
    public void update(Account obj) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(obj.getAccountId())) {
                accounts[i] = obj;
<<<<<<< HEAD
                writeFile(defaultPath); 
                System.out.println("[Thong bao] Cap nhat tai khoan thanh cong!");
=======
                System.out.println("[Thong bao] Cap nhat tai khoan trong bo nho thanh cong!");
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
                return;
            }
        }
        System.out.println("[Loi] Khong tim thay tai khoan de cap nhat!");
    }

    @Override
    public Account findById(String id) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(id)) {
                return accounts[i];
            }
        }
        return null;
    }

    public Account findByUsername(String username) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getUsername().equals(username)) {
                return accounts[i];
            }
        }
        return null;
    }

    @Override
<<<<<<< HEAD
    public Object[] findByName(String name) {
=======
    public Account[] findByName(String name) {
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
        Account[] result = new Account[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i].getUsername().toLowerCase().contains(name.toLowerCase())) {
                result[size++] = accounts[i];
            }
        }
<<<<<<< HEAD
        return Arrays.copyOf(result, size); // Tra ve mang vua khit voi ket qua
=======
        return Arrays.copyOf(result, size);
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
    }

    @Override
    public void displayAll() {
        if (count == 0) {
            System.out.println("[Thong bao] Danh sach tai khoan dang trong!");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(accounts[i].toString());
        }
    }

    @Override
    public void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
<<<<<<< HEAD
                // Cau truc file: id,user,pass,role,isActive
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Account acc = new Account(
                        data[0], // id
                        data[1], // user
                        data[2], // pass
                        data[3], // role
                        Boolean.parseBoolean(data[4]), // isActive
                        null 
=======
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String ownerId = (data.length >= 6) ? data[5] : "Chua_Gan";
                    Account acc = new Account(
                            data[0], // id
                            data[1], // user
                            data[2], // pass
                            data[3], // role
                            Boolean.parseBoolean(data[4]), // isActive
                            ownerId
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
                    );
                    if (count < accounts.length) {
                        accounts[count++] = acc;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Thong bao] Chua co file du lieu. He thong se tu tao khi co tai khoan moi.");
        } catch (IOException e) {
            System.out.println("[Loi] Xay ra loi khi doc file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
<<<<<<< HEAD
            file.getParentFile().mkdirs(); // Tao thu muc neu chua co
=======
            file.getParentFile().mkdirs();
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                Account acc = accounts[i];
<<<<<<< HEAD
                String line = String.format("%s,%s,%s,%s,%b",
                    acc.getAccountId(),
                    acc.getUsername(),
                    acc.getPassword(),
                    acc.getRole(),
                    acc.isActive()
=======
                String line = String.format("%s,%s,%s,%s,%b,%s",
                        acc.getAccountId(),
                        acc.getUsername(),
                        acc.getPassword(),
                        acc.getRole(),
                        acc.isActive(),
                        acc.getOwnerId()
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[Loi] Xay ra loi khi ghi file: " + e.getMessage());
        }
    }
}