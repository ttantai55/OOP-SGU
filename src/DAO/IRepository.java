package DAO;


// IRepository.java
public interface IRepository<T> {
    void add(T obj);

    void remove(String id);

    void update(T obj);

    T findById(String id);

    T[] findByName(String name);

    void displayAll();

    void readFile(String filePath);

    void writeFile(String filePath);
}