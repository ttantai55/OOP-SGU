package BUS; 

import java.util.Scanner;

public class Validation {
    static Scanner sc = new Scanner(System.in);

    // 1. Ép nhập số điện thoại chuẩn (10 hoặc 11 số)
    public static String getValidPhone(String prompt) {
        String phone;
        while (true) {
            System.out.print(prompt);
            phone = sc.nextLine().trim();
            // Biểu thức chính quy (Regex): Chỉ chứa số, độ dài từ 10-11
            if (phone.matches("^[0-9]{10,11}$")) {
                return phone; // Thoát vòng lặp, trả về sdt đúng
            } else {
                System.out.println("❌ Lỗi: Số điện thoại phải bao gồm 10 hoặc 11 chữ số! Vui lòng nhập lại.");
            }
        }
    }

    // 2. Ép nhập Email chuẩn (@gmail.com)
    public static String getValidEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = sc.nextLine().trim();
            // Regex: Ký tự bất kỳ + @gmail.com
            if (email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                return email; 
            } else {
                System.out.println("❌ Lỗi: Email không hợp lệ (Phải có đuôi @gmail.com)! Vui lòng nhập lại.");
            }
        }
    }

    // 3. Ép nhập chuỗi không được để trống (Cho Tên, ID...)
    public static String getNonEmptyString(String prompt) {
        String str;
        while (true) {
            System.out.print(prompt);
            str = sc.nextLine().trim();
            if (!str.isEmpty()) {
                return str;
            } else {
                System.out.println("❌ Lỗi: Không được để trống! Vui lòng nhập lại.");
            }
        }
    }
}