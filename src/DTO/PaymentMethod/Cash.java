package DTO.PaymentMethod;
import DTO.PaymentDTO;
import DTO.InvoiceDTO;
import java.util.Date;


public class Cash extends PaymentDTO  {
    private double cashReceived;

    public Cash() {
    }

public Cash(InvoiceDTO invoice, String paymentId, Date paymentDate, double cashReceived) {
    super(invoice, paymentId, paymentDate);
    this.cashReceived = cashReceived;
}

    public double getCashReceived() {
        return cashReceived; 
    }

 public void setCashReceived(double cashReceived) { 
        this.cashReceived = cashReceived; 
    }

public double getChange() {
    return cashReceived - getTotalNeedToPay();
}

@Override
public void processPayment() {
    System.out.println("Tiền thối lại: " + getChange()  );
    System.out.println("Thanh toán thành công!");
}

}
