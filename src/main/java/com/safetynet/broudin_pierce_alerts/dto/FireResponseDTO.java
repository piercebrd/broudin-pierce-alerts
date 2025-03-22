package com.safetynet.broudin_pierce_alerts.dto;

import java.util.List;

/**
 * Data Transfer Object representing the response for a fire emergency request.
 *
 * <p>This object is returned by the <code>/fire</code> endpoint.
 * It contains the fire station number serving the address and the list of residents
 * (including their medical information) living at that address.
 */

public class FireResponseDTO {

    private String fireStationNumber;
    private List<ResidentDTO> residents;

    /**
     * Constructs a {@code FireResponseDTO}.
     *
     * @param fireStationNumber the fire station number responsible for the address
     * @param residents         the list of residents living at the address
     */

    public FireResponseDTO(String fireStationNumber, List<ResidentDTO> residents) {
        this.fireStationNumber = fireStationNumber;
        this.residents = residents;
    }

    /**
     * @return the fire station number responsible for the address
     */

    public String getFireStationNumber() {
        return fireStationNumber;
    }

    /**
     * @return the list of residents at the address
     */

    public List<ResidentDTO> getResidents() {
        return residents;
    }
}
