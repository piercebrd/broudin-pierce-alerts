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
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        if (person == null) {
            LOGGER.error("POST /person - Request body is missing or invalid.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("POST /person - Adding person: {} {}", person.getFirstName(), person.getLastName());
        Person createdPerson = personService.addPerson(person);
        LOGGER.info("Person created successfully: {} {}", createdPerson.getFirstName(), createdPerson.getLastName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PutMapping
    public ResponseEntity<?> updatePerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody Person updatedPerson) {

        LOGGER.info("PUT /person - Updating person: {} {}", firstName, lastName);
        Person result = personService.updatePerson(firstName, lastName, updatedPerson);

        if (result != null) {
            LOGGER.info("Person updated successfully: {} {}", firstName, lastName);
            return ResponseEntity.ok(result);
        } else {
            LOGGER.warn("Update failed - Person not found: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        LOGGER.info("DELETE /person - Attempting to delete: {} {}", firstName, lastName);
        boolean deleted = personService.deletePerson(firstName, lastName);

        if (deleted) {
            LOGGER.info("Person deleted successfully: {} {}", firstName, lastName);
            return ResponseEntity.ok("Person deleted successfully.");
        } else {
            LOGGER.warn("Delete failed - Person not found: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
    }
}
