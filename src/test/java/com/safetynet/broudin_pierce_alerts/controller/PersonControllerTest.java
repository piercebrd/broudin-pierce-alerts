package com.safetynet.broudin_pierce_alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void addPerson_ShouldReturnCreated() throws Exception {
        Person person = new Person("John","Doe","john@example.com","City","12345","555-5555","123 Street");
        Mockito.when(personService.addPerson(Mockito.any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));

    }

    @Test
    void deletePerson_ShouldReturnOk() throws Exception {

        Mockito.when(personService.deletePerson("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson_ShouldReturnUpdatedPerson() throws Exception {

        Person updatedPerson = new Person("John", "Doe", "updated@example.com", "NewCity", "67890", "555-9999", "456 New Street");


        Mockito.when(personService.updatePerson(Mockito.eq("John"), Mockito.eq("Doe"), Mockito.any(Person.class)))
                .thenReturn(updatedPerson);


        mockMvc.perform(put("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.address").value("456 New Street"))
                .andExpect(jsonPath("$.phone").value("555-9999"));
    }


}
