package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService {

    private final DataService dataService;

    public CommunityEmailService(DataService dataService) {
        this.dataService = dataService;
    }

    public Set<String> getEmailsByCity(String city) {
        return dataService.getPeople().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toSet());
    }
}
