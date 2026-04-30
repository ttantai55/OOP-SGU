package DAO;

import DTO.RepairRecordDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RepairRecordListDAO implements IRepository<RepairRecordDTO> {
    private static RepairRecordDTO[] records = new RepairRecordDTO[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public RepairRecordListDAO() {
    }

    @Override
    public void add(RepairRecordDTO obj) {
        records = Arrays.copyOf(records, records.length + 1);
        records[records.length - 1] = obj;
    }

    @Override
    public void remove(String repairId) {
        boolean found = false;
        for (RepairRecordDTO record : records) {
            if (record != null && record.getRepairId().equals(repairId)) {
                record.setStatus(false);
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Đã hủy bản ghi sửa chữa: " + repairId + ".");
        } else {
            System.out.println("Không tìm thấy bản ghi sửa chữa: " + repairId + ".");
        }
    }

    @Override
    public void update(RepairRecordDTO obj) {
        for (int i = 0; i < records.length; i++) {
            if (records[i] != null && records[i].getRepairId().equals(obj.getRepairId())) {
                records[i] = obj;
                return;
            }
        }
    }

    @Override
    public RepairRecordDTO findById(String repairId) {
        for (RepairRecordDTO record : records) {
            if (record != null && record.getRepairId().equals(repairId)) {
                return record;
            }
        }
        return null;
    }
// tìm kiếm danh sách sửa chữa theo mã bảo hành
    public RepairRecordDTO[] findByWarrantyId(String warrantyId) {
        RepairRecordDTO[] result = new RepairRecordDTO[0];
        for (RepairRecordDTO record : records) {
            if (record != null && record.getWarrantyId().equals(warrantyId)) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = record;
            }
        }
        return result;
    }

    @Override
    public RepairRecordDTO[] findByName(String name) {
        return new RepairRecordDTO[0];
    }


    @Override
    public void displayAll() {
        if (records.length == 0) {
            System.out.println("Danh sách bản ghi sửa chữa trống!");
            return;
        }
        System.out.println("=".repeat(130));
        System.out.printf("%-10s | %-10s | %-12s | %-8s | %-20s | %-15s | %-15s%n",
                "Mã SR", "Mã BH", "Ngày SR", "Lần thứ", "Lỗi", "Giá SR", "Trạng thái");
        System.out.println("-".repeat(130));

        for (RepairRecordDTO record : records) {
            if (record != null) {
                System.out.printf("%-10s | %-10s | %-12s | %-8d | %-20s | %,15.0f | %-15s%n",
                        record.getRepairId(),
                        record.getWarrantyId(),
                        sdf.format(record.getRepairDate()),
                        record.getAttemptNumber(),
                        record.getErrorDescription(),
                        record.getRepairCost(),
                        record.getProcessStatus());
            }
        }
        System.out.println("=".repeat(130));
    }

    @Override
    public void readFile(String filePath) {
        // Sẽ bổ sung sau
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (RepairRecordDTO r : records) {
                if (r != null) {
                    String line = r.getRepairId() + "," +
                                 r.getWarrantyId() + "," +
                                 sdf.format(r.getRepairDate()) + "," +
                                 r.getAttemptNumber() + "," +
                                 r.getErrorDescription() + "," +
                                 r.getSolution() + "," +
                                 r.getReplacedParts() + "," +
                                 r.getRepairCost() + "," +
                                 r.getNote() + "," +
                                 r.getProcessStatus();

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
