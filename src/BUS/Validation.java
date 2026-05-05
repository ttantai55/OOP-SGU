package BUS; 

import DAO.ProductListDAO;
import DTO.ProductsDTO;
import java.util.Scanner;

public class Validation {

    ProductListDAO productDAO;
    public static final String PRODUCT_IMEI_REGEX = "^[LA]IMEI-\\d{3}$";
    public static final String PRODUCT_ID_REGEX = "^(LT|AC)\\d{3}$";
    public static final String POSITIVE_NUMBER_REGEX = "^[1-9]\\d*$";//Su dung cho nhom tien va time
    public static final String STATUS_REGEX = "^(?i)(Dang ban| Tam ngung)$";
    public static final String RAM_REGEX = "^(4|8|16|32)$";
    public static final String STORAGE_REGEX = "^(128|256|512|1024|2048)$";

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

     // Hàm ép nhập IMEI không bị trùng lặp
    public static String inputUniqueIMEI(Scanner sc) {
        String imei;
        while (true) {
            System.out.print("Moi nhap ma IMEI (VD: LIMEI-001, AIMEI-099): ");
            imei = sc.nextLine().trim();

            // Ải 1: Kiểm tra định dạng bằng Regex (Gọi từ class Validator)
            if (!imei.matches(PRODUCT_IMEI_REGEX)) { 
                System.out.println("-> Loi: Ma IMEI sai dinh dang! Phai bat dau bang 'LIMEI-' hoac 'AIMEI-' kem 3 chu so.");
                continue; // Sai thì bắt vòng lặp chạy lại từ đầu
            }

            // Ải 2: Kiểm tra trùng lặp trong kho dữ liệu
            boolean isDuplicate = false;
            // Lấy toàn bộ danh sách sản phẩm hiện có lên để đối chiếu
            ProductsDTO[] pList = productDAO.getALL(); // (Tùy tên hàm bên DAO của bro, có thể là getArray() hoặc pList)
            
            for (ProductsDTO p : pList) {
                if (p != null && p.getProductIMEI().equalsIgnoreCase(imei)) {
                    isDuplicate = true;
                    break; // Thấy có 1 thằng trùng là thoát vòng lặp for ngay lập tức
                }
            }

            if (isDuplicate) {
                System.out.println("-> Loi: Ma IMEI [" + imei.toUpperCase() + "] DA TON TAI trong kho! Vui long kiem tra lai hang hoa.");
                continue; // Trùng thì bắt nhập lại
            }

            // Vượt qua cả 2 ải thành công -> Trả về IMEI viết hoa cho đẹp
            return imei.toUpperCase();
        }
    }


    // Ép nhập Mã Sản Phẩm chuẩn
    public static String inputProductID(Scanner sc) {
        String id;
        while (true) {
            System.out.print("Nhap Ma San Pham (VD: LT001, AC099): ");
            id = sc.nextLine().trim();
            if (id.matches(PRODUCT_ID_REGEX)) {
                return id.toUpperCase(); // Trả về dạng viết hoa cho đồng bộ
            }
            System.out.println("-> Loi: Ma SP phai bat dau bang 'LT' hoac 'AC', kem theo 3 chu so!");
        }
    }

    // Ép nhập Tên, Xuất xứ, Mô tả (Cấm để trống)
    public static String inputNonEmptyString(Scanner sc, String message) {
        String input;
        while (true) {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("-> Loi: Truong nay khong duoc bo trong hoac chi chua khoang trang!");
        }
    }

    // Ép nhập Tiền (Giá bán phải là số thực dương)
    public static double inputPrice(Scanner sc) {
        double price;
        while (true) {
            System.out.print("Nhap gia ban san pham (VND): ");
            try {
                price = Double.parseDouble(sc.nextLine().trim());
                if (price > 0) {
                    return price;
                }
                System.out.println("-> Loi: Gia ban phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("-> Loi: Vui long chi nhap so, khong nhap chu hoac ky tu la!");
            }
        }
    }

    // Ép nhập Số nguyên dương (Dùng cho Thời gian bảo hành, Số nhân CPU...)
    public static int inputPositiveInt(Scanner sc) {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(sc.nextLine().trim());
                if (number >= 0) {
                    return number; // Trả về nếu là số nguyên >= 0
                }
                System.out.println("-> Loi: Gia tri phai lon hon hoac bang 0!");
            } catch (NumberFormatException e) {
                System.out.println("-> Loi: Vui long chi nhap so nguyen!");
            }
        }
    }

    // Ép nhập RAM / Storage theo đúng chuẩn (Kết hợp Regex)
    public static int inputTechSpec(Scanner sc, String message, String regexRegex) {
        String input;
        while (true) {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
            if (input.matches(regexRegex)) {
                return Integer.parseInt(input); // Khớp khuôn thì mới ép kiểu về số
            }
            System.out.println("-> Loi: Thong so khong dat chuan. Vui long kiem tra lai!");
        }
    }
}