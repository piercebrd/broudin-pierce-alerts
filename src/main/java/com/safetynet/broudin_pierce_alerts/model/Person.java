package com.safetynet.broudin_pierce_alerts.model;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String zip;
    private String phone;
    private String address;

    public Person() {}

    public Person(String firstName, String lastName, String email, String city, String zip, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

}

