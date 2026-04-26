package DTO;

import java.util.Date;


public class InvoiceDTO {

    private String invoiceId;
    private Customer customer;
    private Employee employee;
    private Date createdDate;
    private boolean status;
    private InvoiceDetailDTO[] invoiceDetailList;
    private PaymentDTO payment;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String invoiceId, Customer customer, Employee employee,
                      Date createdDate, boolean status, PaymentDTO payment) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.employee = employee;
        this.createdDate = createdDate;
        this.status = status;
        this.payment = payment;
        this.invoiceDetailList = new InvoiceDetailDTO[0]; 
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // lấy Id khách và tên khách
    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerId(){
        return customer.getCustomerId();
    }

    public String getCustomerName(){
        return customer.getFullName();
    }
    // lấy id nhân viên và tên nhân viên
    public Employee getEmployee() {
        return employee;
    }

    public String getEmployeeId(){
        return employee.getEmployeeId();
    }

    public String getEmployeeName(){
        return employee.getFullName();
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

public InvoiceDetailDTO[] getInvoiceDetailList() {
         return invoiceDetailList;
    }

    public void setInvoiceDetailList(InvoiceDetailDTO[] invoiceDetailList) {
    this.invoiceDetailList = invoiceDetailList;
    }


   // tính tổng tiền trên hóa đơn, tui cho chạy vòng lặp 0 đến độ dài của InvoiceDetalList rồi sum các SubTotal lại
    public double getFinalTotalAmount() {
        double FinalTotalAmount = 0;
        for (int i = 0; i < invoiceDetailList.length; i++) {
            FinalTotalAmount += invoiceDetailList[i].getSubTotal();
        }
        return FinalTotalAmount;
    }
    
    public PaymentDTO getPayment() { 
        return payment; 
    }
    public void setPayment(PaymentDTO payment) { 
        this.payment = payment; 
    }
}