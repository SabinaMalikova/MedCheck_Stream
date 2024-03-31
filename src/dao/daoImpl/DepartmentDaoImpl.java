package dao.daoImpl;

import MyException.MyException;
import dao.DepartmentDao;
import dao.GenericDao;
import dataBase.DataBase;
import model.Department;
import model.Hospital;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class DepartmentDaoImpl implements DepartmentDao, GenericDao<Department> {
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) throws MyException {
        try {
            return DataBase.hospitals.stream()
                    .filter(hospital -> hospital.getId().equals(id))
                    .flatMap(hospital -> hospital.getDepartments().stream())
                    .collect(Collectors.toList());
        }catch (NoSuchElementException e){
            throw new MyException("больница с таким ID не найдена");
        }
    }

    @Override
    public Department findDepartmentByName(String name) throws MyException {
        return DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getDepartmentName().equalsIgnoreCase(name))
                .findFirst().orElseThrow(()-> new MyException("отделения с таким названием не найдено"));
    }

    @Override
    public String add(Long hospitalId, Department department) throws MyException {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();
            if (hospital.getDepartments().stream().anyMatch(department1 -> department1.getDepartmentName().equals(department.getDepartmentName()))) {
                throw new MyException("Отделение с таким названием уже существует");
            }
            hospital.getDepartments().add(department);
            return "успешно добавлено";
        } else {
            throw new MyException("Больницы с таким ID не существует");
        }
    }

    @Override
    public void removeById(Long id) {
        boolean removed = DataBase.hospitals.stream()
                .anyMatch(hospital -> hospital.getDepartments().removeIf(department -> department.getId() == id));
        if (removed) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("Отделения с таким ID не найдено");
        }
    }

    @Override
    public String updateById(Long id, Department department) {
        boolean updated = DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(dept -> dept.getId().equals(id))
                .findFirst()
                .map(department1 -> {
                    department1.setDepartmentName(department.getDepartmentName());
                    department1.setId(department.getId());
                    return true;
                })
                .orElse(false);
        if (updated) {
            return "Отделение успешно обновлено";
        } else {
            return "Отделение с таким ID не найдено";
        }
    }
}
