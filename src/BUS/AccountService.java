package BUS;

import DAO.AccountDAO;
import DTO.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }
    /**
     * Hàm xử lý đăng nhập.
     * @return Role (Quyền) của người dùng nếu thành công. Trả về null nếu thất bại.
     */
    public String loginAndGetRole(String username, String password) {
        Account acc = accountDAO.findByUsername(username);

        if (acc == null) {
            System.out.println("❌ Tài khoản không tồn tại!");
            return null;
        }

        if (!acc.getPassword().equals(password)) {
            System.out.println("❌ Sai mật khẩu!");
            return null;
        }

        if (!acc.isActive()) {
            System.out.println("❌ Tài khoản của bạn đang bị khóa. Vui lòng liên hệ Admin!");
            return null;
        }

        System.out.println("✅ Đăng nhập thành công! Xin chào, " + username);
        return acc.getRole(); // Trả về quyền: ví dụ "Khach hang", "Nhan vien", "Quan ly"
    }

    // --- QUẢN LÝ TÀI KHOẢN ---
    // Thêm tài khoản mới (Kiểm tra trùng username trước khi thêm)
    public boolean addAccount(Account acc) {
        if (accountDAO.findByUsername(acc.getUsername()) != null) {
            System.out.println("❌ Lỗi: Tên đăng nhập đã tồn tại!");
            return false;
        }
        accountDAO.add(acc); // Hàm add ở DAO đã tự động gọi writeFile rồi
        return true;
    }

    // Xóa tài khoản
    public void deleteAccount(String accountId) {
        accountDAO.remove(accountId);
    }

    // Khóa/Mở khóa tài khoản
    public void toggleAccountStatus(String accountId, boolean status) {
        Account acc = accountDAO.findById(accountId);
        if (acc != null) {
            acc.setActive(status);
            accountDAO.update(acc);
        }
    }

    // Lấy danh sách hiển thị
    public void showAllAccounts() {
        accountDAO.displayAll();
    }
}