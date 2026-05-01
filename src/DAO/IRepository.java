package DAO;

<<<<<<< HEAD
<<<<<<< HEAD
// IRepository.java
=======

>>>>>>> 04d0353e124e69ea34fa9ab99d5b10ad63e2a221
public interface IRepository<T> {
    void add(T obj);
    void remove(String id);
    void update(T obj);
    T findById(String id);
<<<<<<< HEAD
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
=======
    T[] findByName(String name);
    void displayAll();
    void readFile(String filePath);
    void writeFile(String filePath);
}
>>>>>>> 04d0353e124e69ea34fa9ab99d5b10ad63e2a221
