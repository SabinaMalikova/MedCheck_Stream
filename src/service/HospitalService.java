package service;

import MyException.MyException;
import model.Hospital;
import model.Patient;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    String addHospital(Hospital hospital);
    Hospital findHospitalById(Long id) throws MyException;
    List<Hospital> getAllHospital();
    List<Patient> getAllPatientFromHospital(Long id) throws MyException;
    String deleteHospitalById(Long id) throws MyException;
    Map<String, Hospital> getAllHospitalByAddress(String address) throws MyException;
}
