package com.safetynet.broudin_pierce_alerts.model;

import java.util.List;

/**
 * Model representing a person's medical record.
 *
 * <p>This includes personal identifiers (first name, last name, birthdate),
 * as well as medical information such as medications and allergies.
 *
 * <p>Used in endpoints like <code>/medicalRecord</code> and for age calculations or risk assessment.
 */

public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    /**
     * Default no-argument constructor.
     */

    public MedicalRecord() {}

    /**
     * Constructs a {@code MedicalRecord} with all personal and medical information.
     *
     * @param firstName   the person's first name
     * @param lastName    the person's last name
     * @param birthdate   the person's birthdate in MM/dd/yyyy format
     * @param medications the list of medications the person is taking
     * @param allergies   the list of the person's known allergies
     */

    public MedicalRecord(String firstName, String lastName, String birthdate,
                         List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
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
     * @return the person's birthdate (format: MM/dd/yyyy)
     */

    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @return the list of medications
     */

    public List<String> getMedications() {
        return medications;
    }

    /**
     * @return the list of allergies
     */

    public List<String> getAllergies() {
        return allergies;
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
     * Sets the person's last name.
     *
     * @param lastName the new last name
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
