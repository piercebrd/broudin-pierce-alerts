package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service that provides email addresses of residents based on their city.
 *
 * <p>Used by the <code>/communityEmail</code> endpoint to retrieve a list of
 * unique email addresses for all people living in a specified city.
 */

@Service
public class CommunityEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityEmailService.class);

    private final DataRepository dataRepository;

    /**
     * Constructs a {@code CommunityEmailService} with access to the data repository.
     *
     * @param dataRepository the repository providing access to resident data
     */

    public CommunityEmailService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Retrieves a set of email addresses for all residents in the given city.
     *
     * @param city the city to search for
     * @return a set of non-empty, non-null email addresses
     */

    public Set<String> getEmailsByCity(String city) {
        LOGGER.info("Fetching emails for city: {}", city);

        Set<String> emails = dataRepository.getPeople().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toSet());

        if (emails.isEmpty()) {
            LOGGER.info("No emails found for city: {}", city);
        } else {
            LOGGER.info("Found {} email(s) for city: {}", emails.size(), city);
        }

        return emails;
    }
}
