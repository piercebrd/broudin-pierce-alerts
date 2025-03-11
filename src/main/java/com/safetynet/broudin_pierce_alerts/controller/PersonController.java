package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        if(person == null) {
            logger.error("Request body is missing or invalid.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Person createdPerson = personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PutMapping
    public ResponseEntity<?> updatePerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody Person updatedPerson) {

        Person result = personService.updatePerson(firstName, lastName, updatedPerson);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        boolean deleted = personService.deletePerson(firstName, lastName);
        return deleted ? ResponseEntity.ok("Person deleted successfully.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
    }
}