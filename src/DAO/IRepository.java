package DAO;

<<<<<<< HEAD
// IRepository.java
public interface IRepository<T> {
    void add(T obj);
    void remove(String id);
    void update(T obj);
    T findById(String id);
    Object[] findByName(String name);   // trả Object[] thay vì T[]
    void displayAll();
    void readFile(String filePath);
    void writeFile(String filePath);
}
=======

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
>>>>>>> e25a198e0c18bda19ec2df4ce8e12bb68acfddb7
