package com.safetynet.broudin_pierce_alerts.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private MedicalRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new MedicalRecord("John","Doe","01/01/1990",List.of("Aspirin"), List.of("Peanuts"));
    }

    @Test
    void addMedicalRecord_shouldReturnCreated() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecord)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.medications[0]").value("Aspirin"))
                .andExpect(jsonPath("$.allergies[0]").value("Peanuts"));
    }

    @Test
    void updateMedicalRecord_ShouldReturnUpdatedRecord() throws Exception {

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecord)))
                .andExpect(status().isCreated());


        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("Ibuprofen"), List.of("None"));

        mockMvc.perform(put("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medications[0]").value("Ibuprofen"))
                .andExpect(jsonPath("$.allergies[0]").value("None"));
    }

    @Test
    void deleteMedicalRecord_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecord)))
                .andExpect(status().isCreated());


        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(content().string("Medical record deleted successfully."));
    }

    @Test
    void updateMedicalRecord_NotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(put("/medicalRecord")
                        .param("firstName", "Ghost")
                        .param("lastName", "User")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecord)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Medical record not found."));
    }

    @Test
    void deleteMedicalRecord_NotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "Ghost")
                        .param("lastName", "User"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Medical record not found."));
    }



}
