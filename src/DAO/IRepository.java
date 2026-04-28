package DAO;

// IRepository.java
public interface IRepository<T> {
    void add(T obj);
    void remove(String id);
    void update(T obj);
    T findById(String id);
    Object[] findByName(String name);   // trả Object[] thay vì T[]
    void displayAll();
  //  void readFile(String filePath);
   // void writeFile(String filePath);
}
