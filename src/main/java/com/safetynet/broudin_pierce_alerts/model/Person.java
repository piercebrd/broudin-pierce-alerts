package com.safetynet.broudin_pierce_alerts.model;

/**
 * Model representing a person and their contact/location information.
 *
 * <p>This class is used throughout the application for storing and retrieving
 * basic identity and communication details such as name, address, phone, and email.
 */

public class Person {

    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String zip;
    private String phone;
    private String address;

    /**
     * Default no-argument constructor.
     */

    public Person() {}

    /**
     * Constructs a {@code Person} with full identity and contact details.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @param email     the person's email address
     * @param city      the city the person lives in
     * @param zip       the ZIP/postal code
     * @param phone     the person's phone number
     * @param address   the full address
     */

    public Person(String firstName, String lastName, String email,
                  String city, String zip, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.address = address;
    }

    /**
     * @return the person's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the person's first name.
     *
     * @param firstName the new first name
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the person's last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the person's last name.
     *
     * @param lastName the new last name
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the person's email address
     */

    public String getEmail() {
        return email;
    }

    /**
     * Sets the person's email address.
     *
     * @param email the new email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the city where the person lives
     */

    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the person lives.
     *
     * @param city the new city
     */

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the ZIP code
     */

    public String getZip() {
        return zip;
    }

    /**
     * Sets the ZIP code.
     *
     * @param zip the new ZIP code
     */

    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the person's phone number
     */

    public String getPhone() {
        return phone;
    }

    /**
     * Sets the person's phone number.
     *
     * @param phone the new phone number
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the full address
     */

    public String getAddress() {
        return address;
    }

    /**
     * Sets the full address.
     *
     * @param address the new address
     */

    public void setAddress(String address) {
        this.address = address;
    }
}
