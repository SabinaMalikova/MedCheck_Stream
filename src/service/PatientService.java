package service;

import MyException.MyException;
import model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService {
    String addPatientsToHospital(Long id, List<Patient> patients) throws MyException;
    Patient getPatientById(Long id) throws MyException;
    Map<Integer, Patient> getPatientByAge(int age);
    List<Patient> sortPatientsByAge(String ascOrDesc);
}
