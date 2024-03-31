package service.serviceImpl;

import MyException.MyException;
import dao.daoImpl.DepartmentDaoImpl;
import model.Department;
import service.DepartmentService;
import service.GenericService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService, GenericService<Department> {
    DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();


    @Override
    public List<Department> getAllDepartmentByHospital(Long id) throws MyException {
        return departmentDao.getAllDepartmentByHospital(id);
    }

    @Override
    public Department findDepartmentByName(String name) throws MyException {
        return departmentDao.findDepartmentByName(name);
    }


    @Override
    public String add(Long hospitalId, Department department) throws MyException {
        return departmentDao.add(hospitalId,department);
    }

    @Override
    public void removeById(Long id) {
        departmentDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Department department) {
        return departmentDao.updateById(id,department);
    }


}
