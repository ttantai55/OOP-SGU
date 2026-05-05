package BUS; 

import java.util.Scanner;

public class Validation {
    public static final String PRODUCT_IMEI_REGEX = "^[LA]IMEI-\\d{3}$";
    public static final String PRODUCT_ID_REGEX = "^(LT|AC)\\d{3}$";
    public static final String POSITIVE_NUMBER_REGEX = "^[1-9]\\d*$";//Su dung cho nhom tien va time
    public static final String STATUS_REGEX = "^(?i)(Dang ban|Ngung ban)$";
    public static final String RAM_STORAGE_REGEX = "^(4|8|16|32)$";

    static Scanner sc = new Scanner(System.in);

    // 1. Ep nhap so dien thoai chuan (10 hoac 11 so)
    public static String getValidPhone(String prompt) {
        String phone;
        while (true) {
            System.out.print(prompt);
            phone = sc.nextLine().trim();
            // Bieu thuc chinh quy (Regex): Chi chua so, do dai tu 10-11
            if (phone.matches("^[0-9]{10,11}$")) {
                return phone; // Thoat vong lap, tra ve sdt dung
            } else {
                System.out.println("[Loi] So dien thoai phai bao gom 10 hoac 11 chu so! Vui long nhap lai.");
            }
        }
    }

    // 2. Ep nhap Email chuan (@gmail.com)
    public static String getValidEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = sc.nextLine().trim();
            // Regex: Ky tu bat ky + @gmail.com
            if (email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                return email; 
            } else {
                System.out.println("[Loi] Email khong hop le (Phai co duoi @gmail.com)! Vui long nhap lai.");
            }
        }
    }

    // 3. Ep nhap chuoi khong duoc de trong (Cho Ten, ID...)
    public static String getNonEmptyString(String prompt) {
        String str;
        while (true) {
            System.out.print(prompt);
            str = sc.nextLine().trim();
            if (!str.isEmpty()) {
                return str;
            } else {
                System.out.println("[Loi] Khong duoc de trong! Vui long nhap lai.");
            }
        }
    }


    public static boolean isValidProductIMEI(String id) {
        return id != null && id.matches(PRODUCT_IMEI_REGEX);
    }

    public static boolean isValidProductID(String id) {
        return id != null && id.matches(PRODUCT_ID_REGEX);
    }

    public static boolean isValidPositiveNumber(String numberStr) {
        return numberStr != null && numberStr.matches(POSITIVE_NUMBER_REGEX);
    }
    
    public static String getValidPhone(String prompt) {
        String phone;
        while (true) {
            System.out.print(prompt);
            phone = sc.nextLine().trim();
            // Bieu thuc chinh quy (Regex): Chi chua so, do dai tu 10-11
            if (phone.matches("^[0-9]{10,11}$")) {
                return phone; // Thoat vong lap, tra ve sdt dung
            } else {
                System.out.println("[Loi] So dien thoai phai bao gom 10 hoac 11 chu so! Vui long nhap lai.");
            }
        }
    }

    // 2. Ep nhap Email chuan (@gmail.com)
    public static String getValidEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = sc.nextLine().trim();
            // Regex: Ky tu bat ky + @gmail.com
            if (email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                return email; 
            } else {
                System.out.println("[Loi] Email khong hop le (Phai co duoi @gmail.com)! Vui long nhap lai.");
            }
        }
    }

    // 3. Ep nhap chuoi khong duoc de trong (Cho Ten, ID...)
    public static String getNonEmptyString(String prompt) {
        String str;
        while (true) {
            System.out.print(prompt);
            str = sc.nextLine().trim();
            if (!str.isEmpty()) {
                return str;
            } else {
                System.out.println("[Loi] Khong duoc de trong! Vui long nhap lai.");
            }
        }
    }
    //nhập CCCD chuẩn 12 chữ số (k được để trống)

    public static String getValidCCCD(String prompt) {
        String CCCD;
        while (true) {
            System.out.print(prompt);
            CCCD = sc.nextLine().trim();
            // Bieu thuc chinh quy (Regex): Chi chua so, do dai 12 chữ số
            if (CCCD.matches("^[0-9]{12}$")) {
                return CCCD; // Thoat vong lap, tra ve CCCD dung
            } else {
                System.out.println("[Loi] CCCD phai bao gom 12 chữ số! Vui long nhap lai.");
            }
        }
    }
    public static String getValidFullName(String prompt) {
        String FullName;
        while (true) {
            System.out.print(prompt);
            FullName = sc.nextLine().trim();
            if (FullName.matches("^[\\p{L}]+(\\s[\\p{L}]+)*$")) {
                return FullName;
            } else {
                System.out.println("[Loi] Nhap dung dinh dang ten tieng viet (viet hoa chu cai dau tien)! Vui long nhap lai.");
            }
        }
    }
}