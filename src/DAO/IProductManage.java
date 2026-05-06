package DAO;

public interface IProductManage<T> {
    void add(T obj);
    void remove(String id);
    void update(T obj);
    T findById(String imei);
    void readFile(String filePath);
    void writeFile(String filePath);
}

