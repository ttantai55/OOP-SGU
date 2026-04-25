package BUS;

import java.util.Scanner;

import DTO.SupplierDTO;

public class SupplierListBUS {
    public SupplierDTO sList[];

    static Scanner sc = new Scanner(System.in);

    public void inputList(){
        System.out.println("Moi nhap so luong nha cung cap:");
        int amount = Integer.parseInt(sc.nextLine());
        sList = new SupplierDTO[amount];

        for (int i=0; i<sList.length; i++){
            sList[i].input();
            System.out.println("Da nhap thanh cong nha cung cap thu " + (i+1));

        }

    }

    public void displayList(){
        System.out.println("Danh sach cac nha cung cap: ");
        for (int i=0; i<sList.length; i++){
            sList[i].displayInfo();
        }
    }
}
