package com.safetynet.broudin_pierce_alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FireStation testStation;

    @BeforeEach
    void setUp() {
        testStation = new FireStation("123 Main St", "1");
    }

    @Test
    void addFireStation_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.station").value("1"));
    }

    @Test
    void updateFireStation_ShouldReturnUpdatedStation() throws Exception {

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStation)))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/firestation")
                        .param("address", "123 Main St")
                        .param("stationNumber", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("2"));
    }

    @Test
    void updateFireStation_ShouldReturnNotFound_WhenAddressDoesNotExist() throws Exception {
        mockMvc.perform(put("/firestation")
                        .param("address", "Fake Address")
                        .param("stationNumber", "9"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Address not found"));
    }

    @Test
    void deleteFireStation_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStation)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/firestation")
                        .param("address", "123 Main St"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted FireStation"));
    }

    @Test
    void deleteFireStation_ShouldReturnNotFound_WhenAddressDoesNotExist() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "Ghost Address"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Address not found"));
    }

    @Test
    void getPeopleByStation_ShouldReturnResponse() throws Exception {
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.people").exists())
                .andExpect(jsonPath("$.adultCount").exists())
                .andExpect(jsonPath("$.childrenCount").exists());
    }
}
