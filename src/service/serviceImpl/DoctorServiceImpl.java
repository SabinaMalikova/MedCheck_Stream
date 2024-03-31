package service.serviceImpl;

import MyException.MyException;
import dao.daoImpl.DoctorDaoImpl;
import model.Doctor;
import service.DoctorService;
import service.GenericService;

import java.util.List;

public class DoctorServiceImpl implements DoctorService, GenericService<Doctor> {
    DoctorDaoImpl doctorDao = new DoctorDaoImpl();
    @Override
    public Doctor findDoctorById(Long id) throws MyException {
        return doctorDao.findDoctorById(id);
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, Long doctorsId) {
        return doctorDao.assignDoctorToDepartment(departmentId, doctorsId);
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) throws MyException {
        return doctorDao.getAllDoctorsByHospitalId(id);
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) throws MyException {
        return doctorDao.getAllDoctorsByDepartmentId(id);
    }


    @Override
    public String add(Long hospitalId, Doctor doctor) throws MyException {
        return doctorDao.add(hospitalId,doctor);
    }

    @Override
    public void removeById(Long id) {
        doctorDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        return doctorDao.updateById(id, doctor);
    }


}
