package DTO;
import java.util.Date;


public abstract class PaymentDTO {
    private InvoiceDTO invoice;
    private Date paymentDate;
    private String paymentId;

    public PaymentDTO() {
    }

public PaymentDTO(InvoiceDTO invoice, String paymentId, Date paymentDate) {
        this.invoice = invoice;
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

public InvoiceDTO getInvoice() {
    return invoice;
}

public void setInvoice(InvoiceDTO invoice) {
    this.invoice = invoice;
}

public double getTotalNeedToPay() {
    if (invoice == null) {
        return 0;
    } else {
        return invoice.getFinalTotalAmount();
    }
}

public abstract void processPayment();

}