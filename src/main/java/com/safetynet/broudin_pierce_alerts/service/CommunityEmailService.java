package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityEmailService.class);

    private final DataRepository dataRepository;

    public CommunityEmailService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

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
