package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireService {

    private final DataService dataservice;
    private final MedicalRecordService recordService;

    public FireService(DataService dataservice, MedicalRecordService recordService) {
        this.dataservice = dataservice;
        this.recordService = recordService;
    }

    public FireResponseDTO getInfoByAddress(String address) {
        String fireStationNumber = dataservice.getFireStations().stream()
                .filter(fs -> fs.getAddress().equals(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse(null);

        List<ResidentDTO> residents = dataservice.getPeople().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> {
                    Optional<MedicalRecord> medicalRecordOpt = recordService.getMedicalRecordForPerson(person.getFirstName(), person.getLastName());

                    int age = medicalRecordOpt.map(mr -> recordService.calculateAge(mr.getBirthdate())).orElse(-1);
                    List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(Collections.emptyList());
                    List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(Collections.emptyList());

                    return new ResidentDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications, allergies);
                })
                .collect(Collectors.toList());
        return new FireResponseDTO(fireStationNumber, residents);
    }
}
