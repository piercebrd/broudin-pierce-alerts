package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

public class FireResponseDTO {

    private String fireStationNumber;
    private List<ResidentDTO> residents;

    public FireResponseDTO(String fireStationNumber, List<ResidentDTO> residents) {
        this.fireStationNumber = fireStationNumber;
        this.residents = residents;
    }

    public String getFireStationNumber() {
        return fireStationNumber;
    }

    public List<ResidentDTO> getResidents() {
        return residents;
    }

}
