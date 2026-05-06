package DTO;
import java.util.Date;

public abstract class PaymentDTO {
    private String paymentId;
    private Date paymentDate;

    public PaymentDTO() {
    }

    public PaymentDTO(String paymentId, Date paymentDate) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}