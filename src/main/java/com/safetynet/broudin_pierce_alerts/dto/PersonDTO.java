package com.safetynet.broudin_pierce_alerts.dto;

/**
 * Data Transfer Object representing a simplified view of a person.
 *
 * <p>This object is used in multiple endpoints to return basic personal information,
 * such as name, address, and phone number.
 */

public class PersonDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    /**
     * Constructs a {@code PersonDTO} with full personal information.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @param address   the person's address
     * @param phone     the person's phone number
     */

    public PersonDTO(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Constructs a {@code PersonDTO} with only first and last name.
     * Used in contexts where address and phone are not required (e.g., household members).
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     */

    public PersonDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
     * @return the person's address, or {@code null} if not provided
     */

    public String getAddress() {
        return address;
    }

    /**
     * @return the person's phone number, or {@code null} if not provided
     */

    public String getPhone() {
        return phone;
    }
}
