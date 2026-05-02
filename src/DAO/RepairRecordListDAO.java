package DAO;

import DTO.RepairRecordDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

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
            if (record != null && record.isStatus() && record.getWarrantyId().equals(warrantyId)) {
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

    public RepairRecordDTO[] getAll() {
        return Arrays.copyOf(records, records.length);
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
            if (record != null && record.isStatus()) {
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
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split(",");

                RepairRecordDTO r = new RepairRecordDTO();

                r.setRepairId(parts[0]);
                r.setWarrantyId(parts[1]);

                try {
                    Date ngaySuaChua = sdf.parse(parts[2]);
                    r.setRepairDate(ngaySuaChua);
                } catch (Exception e) {
                    // bỏ qua nếu không đọc được ngày
                }

                r.setAttemptNumber(Integer.parseInt(parts[3]));
                r.setErrorDescription(parts[4]);
                r.setSolution(parts[5]);
                r.setReplacedParts(parts[6]);
                r.setRepairCost(Double.parseDouble(parts[7]));
                r.setNote(parts[8]);
                r.setProcessStatus(parts[9]);
                // technician bỏ qua vì cần đối tượng TechnicianEmployee đầy đủ

                int viTri = records.length;
                records = Arrays.copyOf(records, viTri + 1);
                records[viTri] = r;

                line = br.readLine();
            }

            br.close();
            System.out.println("Đọc dữ liệu từ file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
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
