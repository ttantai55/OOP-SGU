package DTO; 

import java.util.Date;

public class Cash extends PaymentDTO {
    private double cashReceived; 

    public Cash() {
    }

  
    public Cash(String paymentId, Date paymentDate, double cashReceived) {
        super(paymentId, paymentDate);
        this.cashReceived = cashReceived;
    }

    public double getCashReceived() {
        return cashReceived; 
    }

    public void setCashReceived(double cashReceived) { 
        this.cashReceived = cashReceived; 
    }
}

