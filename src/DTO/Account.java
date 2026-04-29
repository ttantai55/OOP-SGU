package DTO;

public class Account {
    private String accountId;
    private String username;
    private String password;
    private String role;   // Lấy từ employee.getRole()
    private boolean isActive;
    private Employee employee; // Liên kết 1-1

    // 1. Constructor không tham số 
    public Account() {
        this.isActive = true; // Mặc định tài khoản mới sẽ hoạt động
    }

    public Account(String accountId, String username, String password, String role, boolean isActive, Employee employee) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.employee = employee;
    }

    // Getters và Setters
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    // Phương thức kiểm tra đăng nhập
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Đổi mật khẩu
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Kiểm tra quyền
    public boolean hasPermission(String function) {
        return this.role != null && this.role.equalsIgnoreCase(function);
    }

    // Cập nhật toString theo phong cách bảng của các file khác
    @Override
    public String toString() {
        String status = isActive ? "Hoạt động" : "Bị khóa";
        String empName = (employee != null) ? employee.getFullName() : "Chưa gán NV";
        
        return String.format("%-15s | %-15s | %-15s | %-15s | %-15s | %-20s",
                accountId, username, "********", role, status, empName);
    }
    
    public void displayInfo() {
        System.out.println(toString());
    }
}