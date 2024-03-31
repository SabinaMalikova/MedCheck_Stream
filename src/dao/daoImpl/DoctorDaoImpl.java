package dao.daoImpl;

import MyException.MyException;
import dao.DoctorDao;
import dao.GenericDao;
import dataBase.DataBase;
import model.Department;
import model.Doctor;
import model.Hospital;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class DoctorDaoImpl implements DoctorDao, GenericDao<Doctor> {
    @Override
    public Doctor findDoctorById(Long id) throws MyException {
        return DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getDoctors().stream())
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst().orElseThrow(() -> new MyException("больница с таким ID не найдена"));
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, Long doctorsId) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Department department : hospital.getDepartments()) {
                    if (department.getId().equals(departmentId)) {
                        for (Doctor doctor : hospital.getDoctors()) {
                            if (doctor.getId().equals(doctorsId)){
                                department.getDoctors().add(doctor);
                            }
                        }
                        return "доктор успешно назначен в отделение";
                    }
                }
            }
            throw new MyException("департамент с таким ID не найден");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) throws MyException {
        try {
            return DataBase.hospitals.stream()
                    .filter(hospital -> hospital.getId().equals(id))
                    .flatMap(hospital -> hospital.getDoctors().stream())
                    .collect(Collectors.toList());
        }catch (NoSuchElementException e){
            throw new MyException("больница с таким ID не найдена");
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) throws MyException {
        try {
            return DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getDepartments().stream())
                    .filter(department -> department.getId().equals(id))
                    .flatMap(department -> department.getDoctors().stream())
                    .collect(Collectors.toList());
        }catch (NoSuchElementException e){
            throw new MyException("отделение с таким ID не найдено");
        }

    }

    @Override
    public String add(Long hospitalId, Doctor doctor) throws MyException {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();
            if (hospital.getDoctors().stream()
                    .anyMatch(doctor1 -> doctor1.getFirstName().equalsIgnoreCase(doctor.getFirstName()))
                    && hospital.getDoctors().stream()
                    .anyMatch(doctor1 -> doctor1.getLastName().equalsIgnoreCase(doctor.getLastName()))) {
                throw new MyException("Доктор с таким ФИО уже существует");
            } else {
                hospital.getDoctors().add(doctor);
                return "Доктор успешно добавлен";
            }
        } else {
            throw new MyException("Больницы с таким ID не существует");
        }
    }

    @Override
    public void removeById(Long id) {
        boolean removed = DataBase.hospitals.stream()
                .anyMatch(hospital -> hospital.getDoctors().removeIf(doctor -> doctor.getId().equals(id)));
        if (removed) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("доктор с таким ID не найден");
        }
    }


    @Override
    public String updateById(Long id, Doctor doctor) {
        boolean updated = DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getDoctors().stream())
                .filter(doctor1 -> doctor1.getId().equals(id))
                .findFirst()
                .map(doctor1 -> {
                    doctor1.setFirstName(doctor.getFirstName());
                    doctor1.setLastName(doctor.getLastName());
                    doctor1.setGender(doctor.getGender());
                    doctor1.setExperienceYear(doctor.getExperienceYear());
                    doctor1.setId(doctor.getId());
                    return true;
                })
                .orElse(false);
        if (updated) {
            return "доктор успешно обновлен";
        } else {
            return "доктор с таким ID не найден";
        }
    }
}
