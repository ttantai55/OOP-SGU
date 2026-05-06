package DTO;

import java.util.Date;

public class Transfer extends PaymentDTO {

    private String accountNumber;
    private String accountName;
    private String bank;

    public Transfer() {
    }

    public Transfer(String paymentId, Date paymentDate, String accountNumber, String accountName, String bank) {
        super(paymentId, paymentDate);
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}