package dao;

import MyException.MyException;
import model.Doctor;

import java.util.List;

public interface DoctorDao {
    Doctor findDoctorById(Long id) throws MyException;
    String assignDoctorToDepartment(Long departmentId, Long doctorsId);
    List<Doctor> getAllDoctorsByHospitalId(Long id) throws MyException;
    List<Doctor> getAllDoctorsByDepartmentId(Long id) throws MyException;
}
