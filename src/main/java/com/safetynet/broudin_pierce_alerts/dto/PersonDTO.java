package com.safetynet.broudin_pierce_alerts.dto;

public class PersonDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonDTO(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    // Constructor for ChildAlertService
    public PersonDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
