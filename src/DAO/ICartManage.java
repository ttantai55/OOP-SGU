package DAO;

public interface ICartManage<T> {
    void add(T obj);
    void remove(String username, String id);
    void update(T obj);
    T findItem(String username, String id);
    void readFile(String filepath);
    void writeFile(String filepath);
}
