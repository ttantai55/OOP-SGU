package DAO;

import java.util.Scanner;
import java.util.Date;

public class PaymentDAO{
    private PaymentDTO[] list = new PaymentDTO[100];
    private Scanner sc = new Scanner(System.in);

    public void inputPayment(){
PaymentDAO payment =  new PaymentDAO();
System.out.print("Nhập ID thanh toán: ");
payment.setPaymentID(sc.nextLine());  
    }

    public void hoanThanhThanhToan() {
        
}
