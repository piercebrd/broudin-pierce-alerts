package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

public class ChildAlertResponseDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonDTO> householdMembers;

    public ChildAlertResponseDTO(String firstName, String lastName, int age, List<PersonDTO> householdMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.householdMembers = householdMembers;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public List<PersonDTO> getHouseholdMembers() {
        return householdMembers;
    }
}
