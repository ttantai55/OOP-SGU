package DTO;


import java.util.Date;

public class Installment extends PaymentDTO {

    private String financeCompanyName;
    private String contractNumber; // mã hợp đồng
    private int installmentMonths; // số tháng trả góp
    private double downPayment;    // Tiền trả trước

    public Installment() {
    }


    public Installment(String paymentId, Date paymentDate, String financeCompanyName, 
                       String contractNumber, int installmentMonths, double downPayment) {
        super(paymentId, paymentDate);
        this.financeCompanyName = financeCompanyName;
        this.contractNumber = contractNumber;
        this.installmentMonths = installmentMonths;
        this.downPayment = downPayment;
    }

    public String getFinanceCompanyName() {
        return financeCompanyName;
    }

    public void setFinanceCompanyName(String financeCompanyName) {
        this.financeCompanyName = financeCompanyName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public int getInstallmentMonths() {
        return installmentMonths;
    }

    public void setInstallmentMonths(int installmentMonths) {
        this.installmentMonths = installmentMonths;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }
}