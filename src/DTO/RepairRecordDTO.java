package DTO;

import java.util.Date;

public class RepairRecordDTO {

    private String warrantyId; 
    private String repairId;
    private Date repairDate;
    private int attemptNumber;      // bảo hành lần thứ mấy
    private String errorDescription; // mô tả lỗi
    private String solution;         // hướng giải quyết
    private String replacedParts;    // linh kiện thay thế
    private double repairCost;       // giá sửa
    private String note;             // ghi chú
    private TechnicianEmployee technician; // nhân viên kĩ thuật
    private String processStatus;    // trạng thái

    public RepairRecordDTO() {
    }

    public RepairRecordDTO(String warrantyId, String repairId, Date repairDate, int attemptNumber,
                              String errorDescription, String solution, String replacedParts,
                              double repairCost, String note, TechnicianEmployee technician, String processStatus) {
        this.warrantyId = warrantyId;
        this.repairId = repairId;
        this.repairDate = repairDate;
        this.attemptNumber = attemptNumber;
        this.errorDescription = errorDescription;
        this.solution = solution;
        this.replacedParts = replacedParts;
        this.repairCost = repairCost;
        this.note = note;
        this.technician = technician;
        this.processStatus = processStatus;
    }

    // Cập nhật Getter/Setter
    public String getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(String warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getReplacedParts() {
        return replacedParts;
    }

    public void setReplacedParts(String replacedParts) {
        this.replacedParts = replacedParts;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TechnicianEmployee getTechnician() {
        return technician;
    }

    public void setTechnician(TechnicianEmployee technician) {
        this.technician = technician;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
}