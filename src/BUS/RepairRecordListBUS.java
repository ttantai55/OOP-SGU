package BUS;

import DAO.RepairRecordListDAO;
import DTO.RepairRecordDTO;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class RepairRecordListBUS {
    Scanner sc = new Scanner(System.in);

    private RepairRecordListDAO repairDAO = new RepairRecordListDAO();

    public void viewAllRepairs() {
        repairDAO.displayAll();
    }

    public void viewRepairsByWarranty() {
        System.out.print("Nhap ma bao hanh: ");
        String warrantyId = sc.nextLine();

        RepairRecordDTO[] repairs = repairDAO.findByWarrantyId(warrantyId);

        if (repairs == null || repairs.length == 0) {
            System.out.println("Khong co bản ghi sua chữa cho bao hanh nay.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(130));
        System.out.println("DANH SACH SUA CHỮA - BAO HANH: " + warrantyId);
        System.out.println("-".repeat(130));
        System.out.printf("%-10s | %-12s | %-8s | %-20s | %-20s | %-15s | %-15s%n",
                "Ma SR", "Ngay SR", "Lan", "Loi", "Giai phap", "Chi phi", "Trang thai");
        System.out.println("-".repeat(130));

        for (RepairRecordDTO repair : repairs) {
            if (repair != null) {
                System.out.printf("%-10s | %-12s | %-8d | %-20s | %-20s | %,15.0f | %-15s%n",
                        repair.getRepairId(),
                        sdf.format(repair.getRepairDate()),
                        repair.getAttemptNumber(),
                        repair.getErrorDescription(),
                        repair.getSolution(),
                        repair.getRepairCost(),
                        repair.getProcessStatus());
            }
        }
        System.out.println("=".repeat(130) + "\n");
    }

    public void viewRepairsByStatus() {
        System.out.print("Nhap trang thai can tim (VD: 'Dang xu ly', 'Hoan thanh'): ");
        String status = sc.nextLine();

        RepairRecordDTO[] allRepairs = repairDAO.getAll();
        RepairRecordDTO[] result = new RepairRecordDTO[0];

        for (RepairRecordDTO repair : allRepairs) {
            if (repair != null && repair.getProcessStatus() != null &&
                repair.getProcessStatus().toLowerCase().contains(status.toLowerCase())) {
                result = java.util.Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = repair;
            }
        }

        if (result.length == 0) {
            System.out.println("Khong co bản ghi sua chữa voi trang thai: " + status);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(130));
        System.out.println("DANH SACH SUA CHỮA - TRANG THAI: " + status);
        System.out.println("-".repeat(130));
        System.out.printf("%-10s | %-10s | %-12s | %-8s | %-20s | %-15s%n",
                "Ma SR", "Ma BH", "Ngay SR", "Lan", "Loi", "Chi phi");
        System.out.println("-".repeat(130));

        for (RepairRecordDTO repair : result) {
            if (repair != null) {
                System.out.printf("%-10s | %-10s | %-12s | %-8d | %-20s | %,15.0f VNĐ%n",
                        repair.getRepairId(),
                        repair.getWarrantyId(),
                        sdf.format(repair.getRepairDate()),
                        repair.getAttemptNumber(),
                        repair.getErrorDescription(),
                        repair.getRepairCost());
            }
        }
        System.out.println("=".repeat(130) + "\n");
    }

    public void updateRepairStatus() {
        System.out.print("Nhap ma sua chữa can cap nhat: ");
        String repairId = sc.nextLine();

        RepairRecordDTO repair = repairDAO.findById(repairId);
        if (repair == null) {
            System.out.println("Loi: Khong tim thay bản ghi sua chữa.");
            return;
        }

        System.out.print("Nhap trang thai moi: ");
        String newStatus = sc.nextLine();

        repair.setProcessStatus(newStatus);
        repairDAO.update(repair);

        System.out.println("Da cap nhat trang thai sua chữa [" + repairId + "] thanh: " + newStatus + ".");
    }

    public void calculateRepairStats() {
        RepairRecordDTO[] allRepairs = repairDAO.getAll();

        if (allRepairs == null || allRepairs.length == 0) {
            System.out.println("Khong co bản ghi sua chữa nao.");
            return;
        }

        int totalRepairs = 0;
        double totalCost = 0;
        int completedCount = 0;
        int processingCount = 0;

        for (RepairRecordDTO repair : allRepairs) {
            if (repair != null) {
                totalRepairs++;
                totalCost += repair.getRepairCost();

                if (repair.getProcessStatus() != null) {
                    if (repair.getProcessStatus().toLowerCase().contains("hoan thanh")) {
                        completedCount++;
                    } else if (repair.getProcessStatus().toLowerCase().contains("dang")) {
                        processingCount++;
                    }
                }
            }
        }

        double averageCost;
        if (totalRepairs > 0) {
            averageCost = totalCost / totalRepairs;
        } else {
            averageCost = 0;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  THONG KE SUA CHỮA                ");
        System.out.println("-".repeat(60));
        System.out.printf("  Tong so lan sua chữa       : %d%n", totalRepairs);
        System.out.printf("  Tong chi phi sua chữa      : %,.0f VNĐ%n", totalCost);
        System.out.printf("  Chi phi sua binh quan      : %,.0f VNĐ%n", averageCost);
        System.out.printf("  So lan hoan thanh          : %d%n", completedCount);
        System.out.printf("  So lan dang xu ly          : %d%n", processingCount);
        System.out.println("=".repeat(60) + "\n");
    }
}
