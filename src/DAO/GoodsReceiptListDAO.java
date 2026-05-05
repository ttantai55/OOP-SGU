package DAO;

import DTO.GoodsReceiptDTO;
import DTO.GoodsReceiptItemDTO;
import DTO.SalesEmployee;
import DTO.Supplier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class GoodsReceiptListDAO implements IRepository<GoodsReceiptDTO>{

    private static GoodsReceiptDTO[] receiptList = new GoodsReceiptDTO[0];
    private final String filePath = "data/goodsreceipt.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

// load dữ liệu từ file vào mảng khi khởi tạo đối tượng DAO
    public GoodsReceiptListDAO() {
        loadFile();
    }

    public void loadFile() {
        readFile(this.filePath);
        System.out.println("Da tai du lieu thanh cong tu file: " + filePath);
    }

    public void saveFile() {
        writeFile(this.filePath);
        System.out.println("Da luu du lieu vao file: " + filePath);
    }

    @Override
    // tương tự với cách hoạt động của hóa đơn bán hàng
    public void add(GoodsReceiptDTO receipt) {
        receiptList = Arrays.copyOf(receiptList, receiptList.length + 1);
        receiptList[receiptList.length - 1] = receipt;
        System.out.println("Da them phieu nhap thanh cong: " + receipt.getReceiptId() + ".");
    }

    @Override
    // sử dụng xóa mềm (sort delete)
    public void remove(String goodsReceiptId) {
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getReceiptId().equals(goodsReceiptId)) {
                rec.setStatus(false);
                System.out.println("Da xoa phieu nhap thanh cong: " + goodsReceiptId + ".");
                return;
            }
        }
        System.out.println("Khong tim thay phieu nhap de xoa!");
    }

    @Override
    // tương tự cách code như remove
    public void update(GoodsReceiptDTO updatedReceipt) {
        for (int i = 0; i < receiptList.length; i++) {
            if (receiptList[i] != null && receiptList[i].getReceiptId().equals(updatedReceipt.getReceiptId())) {
                receiptList[i] = updatedReceipt; // ghi đè phiếu nhập mới lên phiếu nhập cũ
                System.out.println("Da cap nhat phieu nhap thanh cong: " + updatedReceipt.getReceiptId() + ".");
            return;
            }
        }
            System.out.println("Khong tim thay phieu nhap de cap nhat!");
        }

    @Override
    // tìm phiếu nhập theo mã phiếu nhập
    public GoodsReceiptDTO findById(String receiptId) {
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getReceiptId().equals(receiptId)) {
                return rec;
            }
        }
        return null;
    }

    @Override
    // Tìm phiếu nhập theo tên nhà cung cấp
    public GoodsReceiptDTO[] findByName(String supplierName) {
        GoodsReceiptDTO[] result = new GoodsReceiptDTO[0];
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getSupplier() != null && 
                rec.getSupplier().getSupplierName().toLowerCase().contains(supplierName.toLowerCase())) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = rec;
            }
        }
        return result;
    }

    public GoodsReceiptDTO[] getAll() {
        return Arrays.copyOf(receiptList, receiptList.length);
    }

    @Override
    public void displayAll() {
        boolean hasActive = false;
        System.out.println("=".repeat(115));
        System.out.printf("%-10s | %-15s | %-20s | %-12s | %-12s | %-15s%n", 
                "Ma PN", "Nhan Vien", "Nha Cung Cap", "Ngay Nhap", "Trang Thai", "Tong Tien");
        System.out.println("-".repeat(115));

        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.isStatus()) {
                String tenNV;
                if (rec.getReceiver() != null) {
                    tenNV = rec.getReceiver().getFullName();
                } else {
                    tenNV = "N/A";
                }

                String tenNCC;
                if (rec.getSupplier() != null) {
                    tenNCC = rec.getSupplier().getSupplierName();
                } else {
                    tenNCC = "N/A";
                }

                System.out.printf("%-10s | %-15s | %-20s | %-12s | %-12s | %,15.0f VND%n",
                        rec.getReceiptId(),
                        tenNV,
                        tenNCC,
                        sdf.format(rec.getCreatedDate()),
                        "Hoat dong",
                        calculateTotalPrice(rec));
                hasActive = true;
            }
        }
        
        if (!hasActive) {
            System.out.println("Danh sach phieu nhap trong hoac da bi huy het!");
        }
        System.out.println("=".repeat(115));
    }
// tính tổng tiền của phiếu nhập
    public static double calculateTotalPrice(GoodsReceiptDTO rec) {
        if (rec == null || rec.getItems() == null) return 0;
        double total = 0;
        for (GoodsReceiptItemDTO gr : rec.getItems()) {
           if (gr != null)
            total += GoodsReceiptItemListDAO.calculateSubTotal(gr);
        }
        return total;
    }

       @Override
    public void readFile(String filePath) {
        GoodsReceiptDTO[] tempArr = new GoodsReceiptDTO[0];
        
        // Kiểm tra file tồn tại
        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            this.receiptList = tempArr; 
            return; 
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                try {
                    String receiptId = data[0];
                    java.util.Date createdDate = sdf.parse(data[1]);
    
                    String supplierId = data[2];
                    Supplier supplier = null;
                    if (!supplierId.equalsIgnoreCase("N/A")) {
                        supplier = new Supplier();
                        supplier.setSupplierId(supplierId); 
                    }

                    String receiverId = data[3];
                    SalesEmployee receiver = null;
                    if (!receiverId.equalsIgnoreCase("N/A")) {
                        receiver = new SalesEmployee();
                        receiver.setEmployeeId(receiverId);
                    }
                    boolean status = data[4].equalsIgnoreCase("Active");

                    GoodsReceiptDTO rec = new GoodsReceiptDTO();
                    rec.setReceiptId(receiptId);
                    rec.setCreatedDate(createdDate);
                    rec.setSupplier(supplier);
                    rec.setReceiver(receiver);
                    rec.setStatus(status);

                    // Thêm vào mảng
                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = rec;

                } catch (Exception ex) {
                    System.out.println("Loi du lieu dong: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi doc File: " + e.getMessage());
        }
        
        // Nạp mảng vào biến gốc
        this.receiptList = tempArr;
    }


    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (GoodsReceiptDTO rec : receiptList) {
                if (rec != null) {
                    String supplierId;
                    if (rec.getSupplier() != null) {
                        supplierId = rec.getSupplier().getSupplierId();
                    } else {
                        supplierId = "N/A";
                    }

                    String receiverId;
                    if (rec.getReceiver() != null) {
                        receiverId = rec.getReceiver().getEmployeeId();
                    } else {
                        receiverId = "N/A";
                    }

                    String status;
                    if (rec.isStatus()) {
                        status = "Active";
                    } else {
                        status = "Cancelled";
                    }

                    String line = rec.getReceiptId() + "," +
                                 sdf.format(rec.getCreatedDate()) + "," +
                                 supplierId + "," +
                                 receiverId + "," +
                                 status + "," +
                                 calculateTotalPrice(rec);

                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Ghi du lieu vao file " + filePath + " thanh cong!");

        } catch (IOException e) {
            System.err.println("Loi khi ghi file: " + e.getMessage());
        }
    }
}