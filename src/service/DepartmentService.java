package service;

import MyException.MyException;
import model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartmentByHospital(Long id) throws MyException;
    Department findDepartmentByName(String name) throws MyException;

}
