package DAO;

import DTO.Employee;

// EmployeeDAO.java
public class EmployeeDAO implements IRepository<Employee> {
    private Employee[] employees;
    private int count;

    @Override
    public void add(Employee obj) { }

    @Override
    public void remove(String id) { }

    @Override
    public void update(Employee obj) { }

    @Override
    public Employee findById(String id) { return null; }

    @Override
    public void displayAll() { }

    @Override
    public void readFile(String filePath) { }

    @Override
    public void writeFile(String filePath) { }

    // khai báo riêng, không ở interface
    public Employee[] findByName(String name) {
        Employee[] result = new Employee[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (employees[i].getFullName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {
                result[size++] = employees[i];
            }
        }
        return result;
    }
}
