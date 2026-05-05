package DTO;

// [OOP] Class: Ban thiet ke cho cac thuc the Tai khoan trong he thong
public class Account {
    
    // [OOP] Encapsulation: Bao ve du lieu bang access modifier 'private'
    private String accountId;
    private String username;
    private String password;
    private String role;   // Lay tu employee.getRole()
    private boolean isActive;
    
    // [OOP] Association: Moi quan he "Has-A" (Mot tai khoan thuoc ve/lien ket voi mot nhan vien)
    private Employee employee; 
<<<<<<< HEAD
=======
    private String ownerId; // Ma chu so huu (khi chua co doi tuong Employee)
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5

    // 1. Constructor khong tham so (Khoi tao doi tuong mac dinh)
    public Account() {
        this.isActive = true; // Mac dinh tai khoan moi se hoat dong
    }

    // 2. Constructor day du tham so de nap du lieu nhanh
    public Account(String accountId, String username, String password, String role, boolean isActive, Employee employee) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.employee = employee;
<<<<<<< HEAD
=======
        this.ownerId = (employee != null) ? employee.getEmployeeId() : null;
    }

    // 3. Constructor voi ownerId (String) - dung khi doc tu file
    public Account(String accountId, String username, String password, String role, boolean isActive, String ownerId) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.ownerId = ownerId;
        this.employee = null;
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
    }

    // [OOP] Encapsulation: Cac phuong thuc Getter/Setter giao tiep an toan voi thuoc tinh
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

<<<<<<< HEAD
=======
    public String getOwnerId() {
        if (ownerId != null) return ownerId;
        if (employee != null) return employee.getEmployeeId();
        return "Chua_Gan";
    }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
    // Phuong thuc kiem tra dang nhap
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Phuong thuc doi mat khau
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Phuong thuc kiem tra quyen
    public boolean hasPermission(String function) {
        return this.role != null && this.role.equalsIgnoreCase(function);
    }

    // [OOP] Polymorphism: Ghi de phuong thuc toString cua lop Object
    @Override
    public String toString() {
        // Chuan hoa thanh khong dau de tranh loi font tren Terminal
        String status = isActive ? "Hoat dong" : "Bi khoa";
        String empName = (employee != null) ? employee.getFullName() : "Chua gan NV";
        
        // Van giu nguyen tinh bao mat bang cach che mat khau "********"
        return String.format("%-15s | %-15s | %-15s | %-15s | %-15s | %-20s",
                accountId, username, "********", role, status, empName);
    }
    
    public void displayInfo() {
        System.out.println(toString());
    }
}