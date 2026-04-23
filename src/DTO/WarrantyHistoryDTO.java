package DTO;
import java.util.Date;

public class WarrantyHistoryDTO {

    private String historyId;
    private Date warrantyDate;
    private int attemptNumber; // bảo hành lần thứ mấy
    private String errorDescription;// mô tả lỗi
    private String solution; // hướng giải quyết
    private String replacedParts; // linh kiện thay thế
    private double repairCost; // giá sửa
    private String note; // ghi chú
    private String technician; // nhaan viên kĩ thuật
    private String processStatus; // trạng thái

    public WarrantyHistoryDTO() {
    }
    public WarrantyHistoryDTO(String historyId, Date warrantyDate, int attemptNumber, String errorDescription,
                              String solution, String replacedParts, double repairCost,
                              String note, String technician, String processStatus) {

        this.historyId = historyId;
        this.warrantyDate = warrantyDate;
        this.attemptNumber = attemptNumber;
        this.errorDescription = errorDescription;
        this.solution = solution;
        this.replacedParts = replacedParts;
        this.repairCost = repairCost;
        this.note = note;
        this.technician = technician;
        this.processStatus = processStatus;

    }

   public String getHistoryId() {
    return historyId;
}

public void setHistoryId(String historyId) {
    this.historyId = historyId;
}

public Date getWarrantyDate() {
    return warrantyDate;
}

public void setWarrantyDate(Date warrantyDate) {
    this.warrantyDate = warrantyDate;
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

public String getTechnician() {
    return technician;
}

public void setTechnician(String technician) {
    this.technician = technician;
}

public String getProcessStatus() {
    return processStatus;
}

public void setProcessStatus(String processStatus) {
    this.processStatus = processStatus;
}
}