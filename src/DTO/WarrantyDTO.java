package DTO;
import java.util.Date;

public class WarrantyDTO {

    private String warrantyId;
    private Date startDate;
    private Date endDate;
    private WarrantyHistoryDTO[] historyList;

    public WarrantyDTO() {
    }

    public WarrantyDTO(String warrantyId, Date startDate, Date endDate, WarrantyHistoryDTO[] historyList) {
        this.warrantyId = warrantyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.historyList = historyList;
    }

    public String getWarrantyId() {
    return warrantyId;
}

public void setWarrantyId(String warrantyId) {
    this.warrantyId = warrantyId;
}

public Date getStartDate() {
    return startDate;
}

public void setStartDate(Date startDate) {
    this.startDate = startDate;
}

public Date getEndDate() {
    return endDate;
}

public void setEndDate(Date endDate) {
    this.endDate = endDate;
}

public WarrantyHistoryDTO[] getHistoryList() {
    return historyList;
}

public void setHistoryList(WarrantyHistoryDTO[] historyList) {
    this.historyList = historyList;
}

}
