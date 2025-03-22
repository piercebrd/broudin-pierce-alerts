package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

/**
 * Data Transfer Object representing a child and their household members.
 *
 * <p>This object is used to return information from the <code>/childAlert</code> endpoint.
 * It includes the child's name, age, and a list of other people living at the same address.
 */
public class ChildAlertResponseDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonDTO> householdMembers;

    /**
     * Constructs a {@code ChildAlertResponseDTO}.
     *
     * @param firstName         the child's first name
     * @param lastName          the child's last name
     * @param age               the child's age
     * @param householdMembers  the list of other household members
     */
    public ChildAlertResponseDTO(String firstName, String lastName, int age, List<PersonDTO> householdMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.householdMembers = householdMembers;
    }

    /**
     * @return the child's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the child's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the child's age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the list of household members excluding the child
     */
    public List<PersonDTO> getHouseholdMembers() {
        return householdMembers;
    }
}
