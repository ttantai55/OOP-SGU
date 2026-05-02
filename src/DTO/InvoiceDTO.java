package DTO;
import java.util.Date;

public class InvoiceDTO {

    private String invoiceId;
    private Customer customer;
    private Employee employee;
    private Date createdDate;
    private boolean status;
    private InvoiceItemDTO[] invoiceItemList; // tạo mảng này là mảng để xuất các item đi kèm, không phải mảng lưu trữ các hóa đơn
    private PaymentDTO payment;

  // mặc định tạo mẳng ở Invocie tại InvoiceItem nằm trong Invoice
    public InvoiceDTO() {
       this.invoiceItemList = new InvoiceItemDTO[0];
       this.status = true; // mặc định là true
    }

    public InvoiceDTO(String invoiceId, Customer customer, Employee employee,
                      Date createdDate, PaymentDTO payment) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.employee = employee;
        this.createdDate = createdDate;
        this.payment = payment;
      //  this.invoiceItemList = new InvoiceItemDTO[0];
        this.status = true;
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

    // Lấy Id khách và tên khách
    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerId(){
        if (this.customer != null) {
            return this.customer.getCustomerId();
        }
        return "N/A"; // nếu tìm không thấy id khách thì để là N/a
    }

    public String getCustomerName(){
        if (this.customer != null) {
        return this.customer.getFullName();
    }
    return "N/A"; // nếu tìm không thấy tên khách thì để là N/a
}
    
    // Lấy id nhân viên và tên nhân viên
    public Employee getEmployee() {
        return employee;
    }

    public String getEmployeeId(){
        if (this.employee != null) {
            return this.employee.getEmployeeId();
        }
        return "N/A"; // nếu tìm không thấy id nhân viên thì để là N/a
    }

    public String getEmployeeName() {
    if (this.employee != null) {
        return this.employee.getFullName();
    }
    return "N/A";
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

    public InvoiceItemDTO[] getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(InvoiceItemDTO[] invoiceItemList) {
       this.invoiceItemList = invoiceItemList;
    }

    public PaymentDTO getPayment() {
        return payment; 
    }
    
    public void setPayment(PaymentDTO payment) { 
        this.payment = payment; 
    }
}