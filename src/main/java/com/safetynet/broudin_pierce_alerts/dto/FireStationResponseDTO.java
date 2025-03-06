package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

public class FireStationResponseDTO {
    private List<PersonDTO> people;
    private int adultCount;
    private int childrenCount;

    public FireStationResponseDTO(List<PersonDTO> people, int adultCount, int childrenCount) {
        this.people = people;
        this.adultCount = adultCount;
        this.childrenCount = childrenCount;
    }

    public List<PersonDTO> getPeople() {
        return people;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }
}
