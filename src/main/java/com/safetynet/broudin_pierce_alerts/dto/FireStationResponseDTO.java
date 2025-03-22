package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

/**
 * Data Transfer Object representing the response from the fire station coverage endpoint.
 *
 * <p>This object is returned by the <code>/firestation</code> endpoint and includes a list of people
 * covered by a specific fire station, along with counts of adults and children.
 */

public class FireStationResponseDTO {

    private List<PersonDTO> people;
    private int adultCount;
    private int childrenCount;

    /**
     * Constructs a {@code FireStationResponseDTO}.
     *
     * @param people        the list of persons covered by the fire station
     * @param adultCount    the number of adults in the list
     * @param childrenCount the number of children in the list
     */

    public FireStationResponseDTO(List<PersonDTO> people, int adultCount, int childrenCount) {
        this.people = people;
        this.adultCount = adultCount;
        this.childrenCount = childrenCount;
    }

    /**
     * @return the list of people covered by the fire station
     */

    public List<PersonDTO> getPeople() {
        return people;
    }

    /**
     * @return the number of adults
     */

    public int getAdultCount() {
        return adultCount;
    }

    /**
     * @return the number of children
     */

    public int getChildrenCount() {
        return childrenCount;
    }
}
