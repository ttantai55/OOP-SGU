package DAO;

import DTO.GoodsReceiptDTO;
import DTO.GoodsReceiptItemDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GoodsReceiptListDAO implements IRepository<GoodsReceiptDTO> {
    private static GoodsReceiptDTO[] receiptList = new GoodsReceiptDTO[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public GoodsReceiptListDAO() {
    }

    @Override
    public void add(GoodsReceiptDTO receipt) {
        receiptList = Arrays.copyOf(receiptList, receiptList.length + 1);
        receiptList[receiptList.length - 1] = receipt;
        System.out.println("Đã thêm phiếu nhập thành công: " + receipt.getReceiptId() + ".");
    }

    @Override
    public void remove(String receiptId) {
        boolean found = false;
        for (GoodsReceiptDTO rec : receiptList) {
            if (rec != null && rec.getReceiptId().equals(receiptId)) {
                rec.setStatus(false); 
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Đã hủy phiếu nhập: " + receiptId + ".");
        } else {
            System.out.println("Không tìm thấy phiếu nhập: " + receiptId + ".");
        }
    }

    @Override
    public void update(GoodsReceiptDTO updatedReceipt) {
        boolean found = false;
        for (int i = 0; i < receiptList.length; i++) {
            if (receiptList[i] != null && receiptList[i].getReceiptId().equals(updatedReceipt.getReceiptId())) {
                receiptList[i] = updatedReceipt;
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Đã cập nhật phiếu nhập thành công: " + updatedReceipt.getReceiptId() + ".");
        } else {
            System.out.println("Không tìm thấy phiếu nhập để cập nhật!");
        }
    }

    @Override
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
                "Mã PN", "Nhân Viên", "Nhà Cung Cấp", "Ngày Nhập", "Trạng Thái", "Tổng Tiền");
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

                System.out.printf("%-10s | %-15s | %-20s | %-12s | %-12s | %,15.0f VNĐ%n",
                        rec.getReceiptId(),
                        tenNV,
                        tenNCC,
                        sdf.format(rec.getCreatedDate()),
                        "Hoạt động",
                        calculateTotalPrice(rec));
                hasActive = true;
            }
        }
        
        if (!hasActive) {
            System.out.println("Danh sách phiếu nhập trống hoặc đã bị hủy hết!");
        }
        System.out.println("=".repeat(115));
    }

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
        // Sẽ bổ sung sau
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

            System.out.println("Ghi dữ liệu vào file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}