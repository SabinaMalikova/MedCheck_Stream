package dao.daoImpl;

import MyException.MyException;
import dao.GenericDao;
import dao.PatientDao;
import dataBase.DataBase;
import model.Doctor;
import model.Hospital;
import model.Patient;

import java.util.*;

public class PatientDaoImpl implements PatientDao, GenericDao<Patient> {
    @Override
    public String add(Long hospitalId, Patient patient) throws MyException {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();
            if (hospital.getPatients().stream()
                    .anyMatch(patient1 -> patient1.getFirstName().equalsIgnoreCase(patient.getFirstName()))
                    && hospital.getPatients().stream()
                    .anyMatch(patient1 -> patient1.getLastName().equalsIgnoreCase(patient.getLastName()))) {
                throw new MyException("Пациент с таким ФИО уже существует");
            } else {
                hospital.getPatients().add(patient);
                return "Пациент успешно добавлен";
            }
        } else {
            throw new MyException("Больницы с таким ID не существует");
        }
    }

    @Override
    public void removeById(Long id) {
        boolean removed = DataBase.hospitals.stream()
                .anyMatch(hospital -> hospital.getPatients().removeIf(patient -> patient.getId().equals(id)));
        if (removed) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("пациент с таким ID не найден");
        }
    }

    @Override
    public String updateById(Long id, Patient patient) {
        boolean updated = DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .filter(patient1 -> patient1.getId().equals(id))
                .findFirst()
                .map(patient1 -> {
                    patient1.setFirstName(patient1.getFirstName());
                    patient1.setLastName(patient.getLastName());
                    patient1.setGender(patient.getGender());
                    patient1.setAge(patient.getAge());
                    patient1.setId(patient.getId());
                    return true;
                })
                .orElse(false);
        if (updated) {
            return "пациент успешно обновлен";
        } else {
            return "пациент с таким ID не найден";
        }
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) throws MyException {
        return  DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(hospital -> {
                    hospital.getPatients().addAll(patients);
                    return "успешно добавлено";
                })
                .orElseThrow(()->new MyException("больница с таким ID  не найдена"));
    }

    @Override
    public Patient getPatientById(Long id) throws MyException {
        return DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .filter(patient -> patient.getId().equals(id))
                .findFirst().orElseThrow(()->new MyException("пациент с таким ID  не найден"));
    }

    @Override
    public Map<Integer, Patient> getPatientByAge(int age) {
        Map<Integer, Patient> patientMapByAge = new LinkedHashMap<>();
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Patient patient : hospital.getPatients()) {
                    if (patient.getAge() == age) {
                        patientMapByAge.put(age, patient);
                        return patientMapByAge;
                    }
                }
            }
            throw new MyException("пациентов с таким возрастом нет");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        try {
            if (ascOrDesc.equals("1")) {
                for (Hospital hospital : DataBase.hospitals) {
                    Collections.sort(hospital.getPatients(), Comparator.comparing(Patient::getAge));
                    return hospital.getPatients();
                }
            } else if (ascOrDesc.equals("2")) {
                for (Hospital hospital : DataBase.hospitals) {
                    Collections.sort(hospital.getPatients(), Comparator.comparing(Patient::getAge).reversed());
                    return hospital.getPatients();
                }
            }
            throw new MyException("такого выбора нет");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
