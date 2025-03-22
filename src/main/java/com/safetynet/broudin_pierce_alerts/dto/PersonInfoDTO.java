package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

/**
 * Data Transfer Object representing detailed personal and medical information for a person.
 *
 * <p>This object is typically returned by the <code>/personInfo</code> endpoint
 * and includes name, address, age, contact info, medications, and allergies.
 */
public class PersonInfoDTO {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;

    /**
     * Constructs a {@code PersonInfoDTO}.
     *
     * @param firstName   the person's first name
     * @param lastName    the person's last name
     * @param address     the person's home address
     * @param age         the person's calculated age
     * @param email       the person's email address
     * @param medications the list of medications the person is taking
     * @param allergies   the list of the person's known allergies
     */

    public PersonInfoDTO(String firstName, String lastName, String address, int age, String email,
                         List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * @return the person's first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the person's last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * @return the person's home address
     */

    public String getAddress() {
        return address;
    }

    /**
     * @return the person's age
     */

    public int getAge() {
        return age;
    }

    /**
     * @return the person's email address
     */

    public String getEmail() {
        return email;
    }

    /**
     * @return the list of medications the person is taking
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
