package DAO;

import DTO.RepairRecordDTO;
<<<<<<< HEAD
import DTO.TechnicianEmployee;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
=======
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
>>>>>>> origin

public class RepairRecordListDAO implements IRepository<RepairRecordDTO> {
    private static RepairRecordDTO[] records = new RepairRecordDTO[0];
    private final String filePath = "data/repairrecord.txt";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    

    @Override
    public void add(RepairRecordDTO obj) {
        records = Arrays.copyOf(records, records.length + 1);
        records[records.length - 1] = obj;
        System.out.println("Da them ban ghi sua chua thanh cong: " + obj.getRepairId() + ".");
    }

    @Override
    public void remove(String repairId) {
        for (RepairRecordDTO record : records) {
            if (record != null && record.getRepairId().equals(repairId)) {
                record.setStatus(false);
                System.out.println("Da huy ban ghi sua chua: " + repairId + ".");
                return;
            }
        }
        System.out.println("Khong tim thay ban ghi sua chua: " + repairId + ".");
    }

    @Override
    public void update(RepairRecordDTO obj) {
        for (int i = 0; i < records.length; i++) {
            if (records[i] != null && records[i].getRepairId().equals(obj.getRepairId())) {
                records[i] = obj;
                System.out.println("Da cap nhat ban ghi sua chua thanh cong: " + obj.getRepairId() + ".");
                return;
            }
        }
        System.out.println("Khong tim thay ban ghi sua chua de cap nhat!");
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

    public RepairRecordDTO[] getAll() {
        return java.util.Arrays.copyOf(records, records.length);
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
        boolean hasActive = false;
        System.out.println("=".repeat(130));
        System.out.printf("%-10s | %-10s | %-12s | %-8s | %-20s | %-15s | %-15s%n",
                "Ma SR", "Ma BH", "Ngay SR", "Lan thu", "Loi", "Gia SR", "Trang thai");
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
                hasActive = true;
            }
        }

        if (!hasActive) {
            System.out.println("Danh sach ban ghi sua chua trong hoac da bi huy het!");
        }
        System.out.println("=".repeat(130));
    }

    @Override
    public void readFile(String filePath) {
<<<<<<< HEAD
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

                    boolean status = data.length > 10 && data[10].equalsIgnoreCase("Active");

                    TechnicianEmployee technician = null;
                    if (data.length > 11 && !data[11].equalsIgnoreCase("N/A")) {
                        technician = new TechnicianEmployee();
                        technician.setEmployeeId(data[11]);
                    }

                    RepairRecordDTO r = new RepairRecordDTO(warrantyId, repairId, repairDate,
                            attemptNumber, errorDescription, solution, replacedParts,
                            repairCost, note, technician, processStatus, status);

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
=======
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
>>>>>>> origin
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (RepairRecordDTO r : records) {
                if (r != null) {
                    String statusStr = r.isStatus() ? "Active" : "Cancelled";
                    String technicianId = (r.getTechnician() != null) ? r.getTechnician().getEmployeeId() : "N/A";
                    String line = r.getRepairId() + "," +
                                 r.getWarrantyId() + "," +
                                 sdf.format(r.getRepairDate()) + "," +
                                 r.getAttemptNumber() + "," +
                                 r.getErrorDescription() + "," +
                                 r.getSolution() + "," +
                                 r.getReplacedParts() + "," +
                                 r.getRepairCost() + "," +
                                 r.getNote() + "," +
                                 r.getProcessStatus() + "," +
                                 statusStr + "," +
                                 technicianId;

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