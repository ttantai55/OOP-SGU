package DAO;

import DTO.Account;
import java.util.Arrays;

public class AccountDAO implements IRepository<Account> {
    private Account[] accounts;
    private int count;

    public AccountDAO() {
        this.accounts = new Account[100]; // Khởi tạo mảng chứa tối đa 100 tài khoản
        this.count = 0;
    }

    //Thao tác THÊM tài khoản
    @Override
    public void add(Account obj) {
        if (count < accounts.length) {
            accounts[count++] = obj;
            System.out.println("Thêm tài khoản thành công!");
        } else {
            System.out.println("Danh sách tài khoản đã đầy!");
        }
    }

    //Thao tác XÓA tài khoản theo ID
    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(id)) {
                // Dồn mảng để lấp chỗ trống
                for (int j = i; j < count - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[--count] = null;
                System.out.println("Đã xóa tài khoản: " + id);
                return;
            }
        }
        System.out.println("Không tìm thấy tài khoản cần xóa!");
    }

    //Thao tác SỬA tài khoản
    @Override
    public void update(Account obj) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(obj.getAccountId())) {
                accounts[i] = obj; // Ghi đè thông tin mới
                System.out.println("Cập nhật tài khoản thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy tài khoản để cập nhật!");
    }

    // Hàm tìm kiếm theo ID (Hỗ trợ cho việc Sửa/Xóa)
    @Override
    public Account findById(String id) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountId().equals(id)) {
                return accounts[i];
            }
        }
        return null;
    }

    // Hàm tìm kiếm đặc thù: Tìm theo Tên đăng nhập (Dùng cho đăng nhập)
    public Account findByUsername(String username) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getUsername().equals(username)) {
                return accounts[i];
            }
        }
        return null;
    }

    // Tìm kiếm tương đối theo tên (Implement từ IRepository)
    @Override
    public Object[] findByName(String name) {
        Account[] result = new Account[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            // Tìm những username có chứa chuỗi name
            if (accounts[i].getUsername().toLowerCase().contains(name.toLowerCase())) {
                result[size++] = accounts[i];
            }
        }
        // Cắt mảng cho vừa khít với số lượng tìm được
        return Arrays.copyOf(result, size); 
    }

    // Hiển thị danh sách
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

    // Các hàm đọc/ghi file tạm thời để trống, đợi nhóm chốt form chung
    @Override
    public void readFile(String filePath) {
        System.out.println("Chức năng đọc file đang phát triển...");
    }

    @Override
    public void writeFile(String filePath) {
        System.out.println("Chức năng ghi file đang phát triển...");
    }
}