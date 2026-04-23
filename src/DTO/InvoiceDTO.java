package DTO;
import java.util.Date;

public class InvoiceDTO {

    private String invoiceId;
    private CustomerDTO customer;
    private EmployeeDTO employee;
    private Date createdDate;
    private boolean status;
    private double finalTotalAmount; // tổng tiền cuối cùng trên hóa đơn
    private InvoiceDetailDTO[] invoiceDetailList;
    public InvoiceDTO() {
    }

    public InvoiceDTO(String invoiceId, CustomerDTO customer, EmployeeDTO employee, Date createdDate, boolean status, double finalTotalAmount, InvoiceDetailDTO[] invoiceDetailList) {
    this.invoiceId = invoiceId;
    this.customer = customer;
    this.employee = employee;
    this.createdDate = createdDate;
    this.status = status;
    this.finalTotalAmount = finalTotalAmount;
    this.invoiceDetailList = invoiceDetailList;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    // tính tổng tất cả trên hóa đơn, tui chưa code phần tự tính nên để như này
    // tổng tiền hóa đơn
    public double getFinalTotalAmount() {
    return finalTotalAmount;
    }
    public void setFinalTotalAmount(double finalTotalAmount) {
    this.finalTotalAmount = finalTotalAmount;
    }



    public InvoiceDetailDTO[] getInvoiceDetailList() {
         return invoiceDetailList;
    }

    public void setInvoiceDetailList(InvoiceDetailDTO[] invoiceDetailList) {
    this.invoiceDetailList = invoiceDetailList;
    }

}