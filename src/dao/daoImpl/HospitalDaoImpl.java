package dao.daoImpl;

import MyException.MyException;
import dao.GenericDao;
import dao.HospitalDao;
import dataBase.DataBase;
import model.Hospital;
import model.Patient;

import java.util.*;
import java.util.stream.Collectors;

public class HospitalDaoImpl implements HospitalDao {

    @Override
    public String addHospital(Hospital hospital) {
        DataBase.hospitals.add(hospital);
        return "успешно добавлено";
    }

    @Override
    public Hospital findHospitalById(Long id) throws MyException {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst().orElseThrow(() -> new MyException("больница с таким ID не найдена"));
    }

    @Override
    public List<Hospital> getAllHospital() {
        return DataBase.hospitals;
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .flatMap(hospital -> hospital.getPatients().stream())
                .collect(Collectors.toList());
    }

    @Override
    public String deleteHospitalById(Long id) throws MyException {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(hospital -> {
                    DataBase.hospitals.remove(hospital);
                    return "успешно удалено";
                })
                .orElseThrow(() -> new MyException("больница с таким ID не найдена"));
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        Map<String, Hospital> hospitalsByAddress = new LinkedHashMap<>();
        try {
            ListIterator<Hospital> iterator = DataBase.hospitals.listIterator();
            while (iterator.hasNext()) {
                Hospital hospital = iterator.next();
                if (hospital.getAddress().equalsIgnoreCase(address)) {
                    hospitalsByAddress.put(hospital.getHospitalName(), hospital);
                }
            }
            throw new MyException("больницы с таким адресом нет");
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
        return hospitalsByAddress;
    }
}
