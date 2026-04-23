package DTO.PaymentMethod;
import DTO.PaymentDTO;
import DTO.InvoiceDTO;
import java.util.Date;



public class Credit extends PaymentDTO {

    private String numberId;
    private String nameOnCard;
    private String bank;

    public Credit() {

    }
    public Credit(String numberId, String nameOnCard, String bank, InvoiceDTO invoice, String paymentId, Date paymentDate) {
        super(invoice, paymentId, paymentDate);

        this.numberId = numberId;
        this.nameOnCard = nameOnCard;
        this.bank = bank;
    }


    public String getNumberId() { 
        return numberId; 
    }

    public String getNameOnCard() { 
        return nameOnCard; 
    }

    public String getBank() { 
        return bank; 
    }

    public void setNumberId(String numberId) { 
        this.numberId = numberId; 
    }

    public void setNameOnCard(String nameOnCard) { 
        this.nameOnCard = nameOnCard; 
    }

    public void setBank(String bank) { 
        this.bank = bank; 
    }
    
  
    @Override
    public void processPayment() {
        System.out.println("Giao dịch thành công!");
    }
}