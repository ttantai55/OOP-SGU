package DAO;

public interface  IInvoiceManage<T> {
    void add(T invoice);
    void update(T invoice);
    void remove(String invoiceID, String productID);
    T findById(String invoiceID, String productID);
    T[] findByName(String name);
    void displayAll();
    void readFile(String filepath);
    void writeFile(String filepath);

}
