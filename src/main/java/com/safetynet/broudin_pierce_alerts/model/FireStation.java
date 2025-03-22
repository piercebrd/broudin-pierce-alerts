package com.safetynet.broudin_pierce_alerts.model;

/**
 * Model representing the mapping between an address and a fire station number.
 *
 * <p>This class is used to associate a physical address with the number of the fire station
 * responsible for that location.
 */

public class FireStation {

    private String address;
    private String station;

    /**
     * Default no-arg constructor.
     */

    public FireStation() {}

    /**
     * Constructs a {@code FireStation} with the given address and station number.
     *
     * @param address the address covered by the fire station
     * @param station the fire station number assigned to the address
     */

    public FireStation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    /**
     * @return the address covered by the fire station
     */

    public String getAddress() {
        return address;
    }

    /**
     * @return the fire station number
     */

    public String getStation() {
        return station;
    }

    /**
     * Updates the fire station number assigned to this address.
     *
     * @param newStationNumber the new station number to set
     */

    public void setStation(String newStationNumber) {
        this.station = newStationNumber;
    }
}
