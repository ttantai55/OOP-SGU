<<<<<<< HEAD
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
=======
package DAO;

import DTO.PaymentDTO;
import java.util.Scanner;

public class PaymentDAO{
    private PaymentDTO[] list = new PaymentDTO[100];
    private int count = 0;
    private Scanner sc = new Scanner(System.in);

    public void addPayment(PaymentDTO payment) {
        if (count < list.length) {
            list[count++] = payment;
        }
    }

    public PaymentDTO findByPaymentId(String paymentId) {
        for (int i = 0; i < count; i++) {
            if (list[i] != null && list[i].getPaymentId().equals(paymentId)) {
                return list[i];
            }
        }
        return null;
    }
}
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
