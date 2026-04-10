package DTO;

public class Account {
    private String accountId;
    private String username;
    private String password;
    private String role;   // lấy từ employee.getRole()
    private boolean isActive;
    private Employee employee;// liên kết 1-1
    public Account(){}
    public Account(String accountId, String username, String password, String role, boolean isActive, Employee employee) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.employee = employee;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    //phương thức kiểm tra đăng nhập
    public boolean login (String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    //method đổi mật khẩu
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    // method có quyền (chưa làm)****
    public boolean hasPermission(String function){
        return this.role.equals(function);
    }
    //chưa làm ****
    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                ", employee=" + employee +
                '}';
    }
}
