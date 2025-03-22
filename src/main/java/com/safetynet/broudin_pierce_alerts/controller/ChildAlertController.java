package com.safetynet.broudin_pierce_alerts.controller;


import com.safetynet.broudin_pierce_alerts.dto.ChildAlertResponseDTO;
import com.safetynet.broudin_pierce_alerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller that provides child alert information based on a given address.
 *
 * <p>This endpoint is used to retrieve a list of children (aged 18 or below)
 * living at a specified address, along with their household members.
 *
 * <p>Endpoint: <code>GET /childAlert?address={address}</code>
 */
@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertController.class);

    private final ChildAlertService childAlertService;

    /**
     * Constructs a new {@code ChildAlertController} with the required service dependency.
     *
     * @param childAlertService the service that provides child alert data
     */

    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

    /**
     * Returns a list of children residing at the specified address,
     * along with their household members.
     *
     * @param address the address to search for children
     * @return a {@code ResponseEntity} containing:
     *         <ul>
     *             <li>a list of {@link ChildAlertResponseDTO} if children are found</li>
     *             <li>an empty string if no children are found</li>
     *         </ul>
     */
    @GetMapping
    public ResponseEntity<?> getChildrenAddress(@RequestParam String address) {
        LOGGER.info("GET /childAlert called with address={}", address);

        List<ChildAlertResponseDTO> children = childAlertService.getChildrenAddress(address);

        if (children.isEmpty()) {
            LOGGER.info("No children found at address: {}", address);
            return ResponseEntity.ok("");
        } else {
            LOGGER.info("{} child(ren) found at address: {}", children.size(), address);
            return ResponseEntity.ok(children);
        }
    }
}
