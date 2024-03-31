package dao;

import MyException.MyException;
import model.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> getAllDepartmentByHospital(Long id) throws MyException;
    Department findDepartmentByName(String name) throws MyException;
}
