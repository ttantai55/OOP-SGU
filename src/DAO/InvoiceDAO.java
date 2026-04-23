package DAO;

import DTO.InvoiceDTO;
import java.util.Scanner;

public class InvoiceDAO{

    static Scanner sc = new Scanner(System.in);

    InvoiceDTO[] = new InvoiceDTO[100];
    int count = 0;

    public void inputInvoice(){
        
        System.out.println("Nhap ma hoa don: ");
        setInvoiceId(sc.nextLine());

        System.out.println("nhap ten khach hang: ");
        setCustomer(sc.nextLine());

        System.out.println("Nhap ten nhan vien: ");
        setEmployee(sc.nextLine());

        System.out.println("Ngay lap hoa don: ");
        this.createdDate = new Date();

        System.out.println("trang thai: (True/False) = (Da ban/Chua ban) ");
        setStatus(Boolean.parseBoolean(sc.nextLine()));

        System.out.println("tong tien: ");
        setFinalTotalAmount(Double.parseDouble(sc.nextLine()));

        System.out.println("chi tiet hoa don: ");
        this.invoiceDetailList = new InvoiceDetailDTO[100];
        
       
        
    }








































    public void printInvoice(){
        
    }
    
    public void cancelInvoice(){
        
    }














}
