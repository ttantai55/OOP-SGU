package DAO;

import DTO.Account;
import java.util.Arrays;
import java.io.*; 

public class AccountDAO implements IRepository<Account> {
    private Account[] accounts;
    private int count;
    private final String defaultPath = "src/data/accounts.txt"; 

    public AccountDAO() {
        this.accounts = new Account[100];
        this.count = 0;
        readFile(defaultPath);
    }

    @Override
    public void add(Account obj) {
        if (count < accounts.length) {
            accounts[count++] = obj;
            writeFile(defaultPath); 
            System.out.println("Thêm tài khoản thành công!");
        } else {
            System.out.println("Danh sách tài khoản đã đầy!");
        }
    }

    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(id)) {
                for (int j = i; j < count - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[--count] = null;
                writeFile(defaultPath); 
                System.out.println("Đã xóa tài khoản: " + id);
                return;
            }
        }
        System.out.println("Không tìm thấy tài khoản cần xóa!");
    }

    @Override
    public void update(Account obj) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(obj.getAccountId())) {
                accounts[i] = obj;
                writeFile(defaultPath); 
                System.out.println("Cập nhật tài khoản thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy tài khoản để cập nhật!");
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
    public Object[] findByName(String name) {
        Account[] result = new Account[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i].getUsername().toLowerCase().contains(name.toLowerCase())) {
                result[size++] = accounts[i];
            }
        }
        return Arrays.copyOf(result, size); 
    }

    @Override
    public void displayAll() {
        if (count == 0) {
            System.out.println("Danh sách tài khoản đang trống!");
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
                // Cấu trúc file: id,user,pass,role,isActive
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Account acc = new Account(
                        data[0], // id
                        data[1], // user
                        data[2], // pass
                        data[3], // role
                        Boolean.parseBoolean(data[4]), // isActive
                        null 
                    );
                    if (count < accounts.length) {
                        accounts[count++] = acc;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Chưa có file dữ liệu. Sẽ tự tạo khi có tài khoản mới.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        // Tạo thư mục nếu chưa có
        File file = new File(filePath);
        file.getParentFile().mkdirs(); 

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                Account acc = accounts[i];
                String line = String.format("%s,%s,%s,%s,%b",
                    acc.getAccountId(),
                    acc.getUsername(),
                    acc.getPassword(),
                    acc.getRole(),
                    acc.isActive()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}