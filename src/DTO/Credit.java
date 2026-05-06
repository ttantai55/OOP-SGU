package DTO;

import java.util.Date;

public class Credit extends PaymentDTO {

    private String numberId;
    private String nameOnCard;
    private String bank;

    public Credit() {
    }

    public Credit(String paymentId, Date paymentDate, String numberId, String nameOnCard, String bank) {
        super(paymentId, paymentDate);
        this.numberId = numberId;
        this.nameOnCard = nameOnCard;
        this.bank = bank;
    }

    public String getNumberId() { 
        return numberId; 
    }

    public void setNumberId(String numberId) { 
        this.numberId = numberId; 
    }

    public String getNameOnCard() { 
        return nameOnCard; 
    }

    public void setNameOnCard(String nameOnCard) { 
        this.nameOnCard = nameOnCard; 
    }

    public String getBank() { 
        return bank; 
    }

    public void setBank(String bank) { 
        this.bank = bank; 
    }
}