package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

/**
 * Data Transfer Object representing a resident's personal and medical information.
 *
 * <p>This object is used in endpoints such as <code>/fire</code> and <code>/flood/stations</code>,
 * where it provides relevant details about individuals at a given address.
 */

public class ResidentDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    /**
     * Constructs a {@code ResidentDTO}.
     *
     * @param firstName   the resident's first name
     * @param lastName    the resident's last name
     * @param phone       the resident's phone number
     * @param age         the resident's age
     * @param medications the list of medications the resident is taking
     * @param allergies   the list of the resident's known allergies
     */

    public ResidentDTO(String firstName, String lastName, String phone, int age,
                       List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * @return the resident's first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the resident's last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * @return the resident's phone number
     */

    public String getPhone() {
        return phone;
    }

    /**
     * @return the resident's age
     */

    public int getAge() {
        return age;
    }

    /**
     * @return the list of medications the resident is taking
     */

    public List<String> getMedications() {
        return medications;
    }

    /**
     * @return the list of known allergies
     */

    public List<String> getAllergies() {
        return allergies;
    }
}
