package BUS;

import DAO.WarrantyListDAO;
import DAO.RepairRecordListDAO;
import DAO.InvoiceListDAO;
import DAO.ProductListDAO;
import DAO.EmployeeDAO;
import DTO.WarrantyDTO;
import DTO.RepairRecordDTO;
import DTO.InvoiceDTO;
import DTO.ProductsDTO;
import DTO.TechnicianEmployee;
import DTO.Employee;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class WarrantyListBUS {
    Scanner sc = new Scanner(System.in);

    private WarrantyListDAO warDAO = new WarrantyListDAO();
    private RepairRecordListDAO repairDAO = new RepairRecordListDAO();
    private InvoiceListDAO invDAO = new InvoiceListDAO();
    private ProductListDAO productsDAO = new ProductListDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public void inputWarranty() {
        System.out.print("Nhap ma hoa don: ");
        String invoiceId = sc.nextLine();
        InvoiceDTO invoice = invDAO.findById(invoiceId);

        if (invoice == null) {
            System.out.println("Loi: Khong tim thay hoa don.");
            return;
        }

        System.out.print("Nhap ma IMEI san pham can tao bao hanh: ");
        ProductsDTO product = productsDAO.findById(sc.nextLine());

        if (product == null) {
            System.out.println("Loi: Khong tim thay san pham.");
            return;
        }

        // Tao ma bao hanh (có thể là invoiceId-WAR-productId)
        String warrantyId = invoiceId + "-WAR-" + product.getProductID();

        // Ngày bắt đầu = hôm nay
        Date startDate = new Date();

        // Tính ngày kết thúc = startDate + warrantyPeriod (tháng)
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, product.getWarrantyPeriod());
        Date endDate = cal.getTime();

        // Tạo WarrantyDTO
        WarrantyDTO warranty = new WarrantyDTO(warrantyId, invoiceId, product, startDate, endDate, true);

        warDAO.add(warranty);
        System.out.println("Da tao bao hanh [" + warrantyId + "] thanh cong!");
    }

    public void printWarranty() {
        System.out.print("Nhap ma bao hanh: ");
        String warrantyId = sc.nextLine();

        WarrantyDTO warranty = warDAO.findById(warrantyId);
        if (warranty == null) {
            System.out.println("Loi: Khong tim thay bao hanh co ma " + warrantyId + ".");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n" + "=".repeat(90));
        System.out.println("                    CHI TIET BAO HANH                      ");
        System.out.printf(" Ma BH: %-20s | HD: %s%n", warranty.getWarrantyId(), warranty.getInvoiceId());
        System.out.printf(" San pham: %-15s | Tu: %s | Den: %s%n",
                warranty.getProduct().getProductName(),
                sdf.format(warranty.getStartDate()),
                sdf.format(warranty.getEndDate()));
        String trangThai;
        if (warranty.isStatus()) {
            trangThai = "Con hieu luc";
        } else {
            trangThai = "Het hieu luc";
        }

        System.out.printf(" Trang thai: %-10s | So lan sua: %d / 10%n",
                trangThai,
                warranty.getRepairCount());
        System.out.println("-".repeat(90));

        // In danh sách sửa chữa
        RepairRecordDTO[] repairs = repairDAO.findByWarrantyId(warrantyId);

        if (repairs == null || repairs.length == 0) {
            System.out.println(" (Khong co lich su sua chữa.)");
        } else {
            System.out.printf("%-5s | %-10s | %-12s | %-8s | %-20s | %-15s%n",
                    "STT", "Ma SR", "Ngay SR", "Lan", "Loi", "Chi phi");
            System.out.println("-".repeat(90));

            for (int i = 0; i < repairs.length; i++) {
                if (repairs[i] != null) {
                    System.out.printf("%-5d | %-10s | %-12s | %-8d | %-20s | %,15.0f VNĐ%n",
                            i + 1,
                            repairs[i].getRepairId(),
                            sdf.format(repairs[i].getRepairDate()),
                            repairs[i].getAttemptNumber(),
                            repairs[i].getErrorDescription(),
                            repairs[i].getRepairCost());
                }
            }
        }

        System.out.println("=".repeat(90) + "\n");
    }

    public void addRepairRecord() {
        System.out.print("Nhap ma bao hanh: ");
        String warrantyId = sc.nextLine();

        WarrantyDTO warranty = warDAO.findById(warrantyId);
        if (warranty == null) {
            System.out.println("Loi: Khong tim thay bao hanh.");
            return;
        }

        if (!warranty.isStatus()) {
            System.out.println("Loi: Bao hanh da bi huy.");
            return;
        }
        Date today = new Date();
        if (today.after(warranty.getEndDate())) {
            System.out.println("Loi: Bao hanh da het han.");
            return;
        }
        if (warranty.getRepairCount() >= 10) {
            System.out.println("Loi: Bao hanh da vuot toi da so lan sua chữa (10 lan).");
            return;
        }

        // Nhập thông tin sửa chữa
        RepairRecordDTO repair = new RepairRecordDTO();

        // Tạo mã sửa chữa (có thể là warrantyId-REP-attemptNumber)
        int attemptNumber = warranty.getRepairCount() + 1;
        String repairId = warrantyId + "-REP-" + attemptNumber;
        repair.setRepairId(repairId);
        repair.setWarrantyId(warrantyId);

        repair.setRepairDate(new Date());
        repair.setAttemptNumber(attemptNumber);

        System.out.print("Nhap mo ta loi: ");
        repair.setErrorDescription(sc.nextLine());

        System.out.print("Nhap huong giai quyet: ");
        repair.setSolution(sc.nextLine());

        System.out.print("Nhap linh kien thay the: ");
        repair.setReplacedParts(sc.nextLine());

        System.out.print("Nhap chi phi sua chữa (VND): ");
        double repairCost = Double.parseDouble(sc.nextLine());
        if (repairCost >= 0) {
            repair.setRepairCost(repairCost);
        } else {
            repair.setRepairCost(0);
        }

        System.out.print("Nhap ghi chu: ");
        repair.setNote(sc.nextLine());

        TechnicianEmployee technician = null;
        while (technician == null) {
            System.out.print("Nhap ma ky thuat vien: ");
            String techId = sc.nextLine();
            Employee emp = employeeDAO.findById(techId);
            if (emp instanceof TechnicianEmployee) {
                technician = (TechnicianEmployee) emp;
            }
            if (technician == null) {
                System.out.println("Loi: Khong tim thay ky thuat vien. Vui long nhap lai!");
            }
        }
        repair.setTechnician(technician);

        System.out.print("Nhap trang thai xu ly: ");
        repair.setProcessStatus(sc.nextLine());

        // Lưu bản ghi sửa chữa
        repairDAO.add(repair);

        // Cập nhật repairCount của warranty
        warranty.setRepairCount(attemptNumber);
        warDAO.update(warranty);

        System.out.println("Da them bản ghi sua chữa [" + repairId + "] thanh cong!");
    }

    public void cancelWarranty() {
        System.out.print("Nhap ma bao hanh can huy: ");
        String warrantyId = sc.nextLine();

        warDAO.remove(warrantyId);
    }
}
