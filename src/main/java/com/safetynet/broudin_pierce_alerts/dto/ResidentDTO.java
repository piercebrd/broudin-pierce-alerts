package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

public class ResidentDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public ResidentDTO(String firstName, String lastName, String phone, int age, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public int getAge() { return age; }
    public List<String> getMedications() { return medications; }
    public List<String> getAllergies() { return allergies; }
}
