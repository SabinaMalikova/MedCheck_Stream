package service.serviceImpl;

import MyException.MyException;
import dao.daoImpl.PatientDaoImpl;
import model.Patient;
import service.GenericService;
import service.PatientService;

import java.util.List;
import java.util.Map;

public class PatientServiceImpl implements PatientService, GenericService<Patient> {
    PatientDaoImpl patientDao = new PatientDaoImpl();

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) throws MyException {
        return patientDao.addPatientsToHospital(id,patients) ;
    }

    @Override
    public Patient getPatientById(Long id) throws MyException {
        return patientDao.getPatientById(id);
    }

    @Override
    public Map<Integer, Patient> getPatientByAge(int age) {
        return patientDao.getPatientByAge(age);
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        return patientDao.sortPatientsByAge(ascOrDesc);
    }


    @Override
    public String add(Long hospitalId, Patient patient) throws MyException {
        return patientDao.add(hospitalId,patient);
    }

    @Override
    public void removeById(Long id) {
        patientDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Patient patient) {
        return patientDao.updateById(id,patient);
    }


}
