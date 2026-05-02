package DTO;

// [OOP] Class: Ban thiet ke cho cac thuc the Tai khoan trong he thong
public class Account {
    
    // [OOP] Encapsulation: Bao ve du lieu bang access modifier 'private'
    private String accountId;
    private String username;
    private String password;
    private String role;   
    private boolean isActive;
    
    // [OOP] Loose Coupling: Moi quan he "Has-A" linh hoat thong qua Khoa ngoai (Foreign Key)
    // Co the luu ma Nhan vien (VD: NV001) hoac ma Khach hang (VD: KH001)
    private String ownerId; 

    public Account() {
        this.isActive = true; 
    }

    // Constructor day du 6 tham so
    public Account(String accountId, String username, String password, String role, boolean isActive, String ownerId) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.ownerId = ownerId;
    }

    // --- GETTER / SETTER ---
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean hasPermission(String function) {
        return this.role != null && this.role.equalsIgnoreCase(function);
    }

    @Override
    public String toString() {
        String status = isActive ? "Hoat dong" : "Bi khoa";
        String ownerInfo = (ownerId != null && !ownerId.isEmpty()) ? ownerId : "Chua gan ID";
        
        return String.format("%-10s | %-15s | %-10s | %-12s | %-10s | %-10s",
                accountId, username, "********", role, status, ownerInfo);
    }
    
    public void displayInfo() {
        System.out.println(toString());
    }
}