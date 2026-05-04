package DAO;

import DTO.RepairRecordDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RepairRecordListDAO implements IRepository<RepairRecordDTO> {
    private static RepairRecordDTO[] records = new RepairRecordDTO[0];
    private final String filePath = "data/repairrecord.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public RepairRecordListDAO() {
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
            System.out.println("Da huy ban ghi sua chua: " + repairId + ".");
        } else {
            System.out.println("Khong tim thay ban ghi sua chua: " + repairId + ".");
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

    public RepairRecordDTO[] getAll() {
        return java.util.Arrays.copyOf(records, records.length);
    }

    @Override
    public RepairRecordDTO[] findByName(String name) {
        return new RepairRecordDTO[0];
    }


    @Override
    public void displayAll() {
        if (records.length == 0) {
            System.out.println("Danh sach ban ghi sua chua trong!");
            return;
        }
        System.out.println("=".repeat(130));
        System.out.printf("%-10s | %-10s | %-12s | %-8s | %-20s | %-15s | %-15s%n",
                "Ma SR", "Ma BH", "Ngay SR", "Lan thu", "Loi", "Gia SR", "Trang thai");
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
        RepairRecordDTO[] tempArr = new RepairRecordDTO[0];

        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            this.records = tempArr;
            return;
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                try {
                    String repairId          = data[0];
                    String warrantyId        = data[1];
                    java.util.Date repairDate = sdf.parse(data[2]);
                    int attemptNumber        = Integer.parseInt(data[3]);
                    String errorDescription  = data[4];
                    String solution          = data[5];
                    String replacedParts     = data[6];
                    double repairCost        = Double.parseDouble(data[7]);
                    String note              = data[8];
                    String processStatus     = data[9];

                    RepairRecordDTO r = new RepairRecordDTO(warrantyId, repairId, repairDate,
                            attemptNumber, errorDescription, solution, replacedParts,
                            repairCost, note, null, processStatus, true);

                    tempArr = Arrays.copyOf(tempArr, tempArr.length + 1);
                    tempArr[tempArr.length - 1] = r;

                } catch (Exception ex) {
                    System.out.println("Loi du lieu dong: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi doc File: " + e.getMessage());
        }

        this.records = tempArr;
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
