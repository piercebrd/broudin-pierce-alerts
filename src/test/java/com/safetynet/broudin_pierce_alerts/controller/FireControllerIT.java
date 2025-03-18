package com.safetynet.broudin_pierce_alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class FireControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFireInfo_ShouldReturnResidents() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents").isArray())
                .andExpect(jsonPath("$.residents[0].firstName").value("John"))
                .andExpect(jsonPath("$.residents[0].age").value(41))
                .andExpect(jsonPath("$.residents[0].medications").isArray())
                .andExpect(jsonPath("$.fireStationNumber").value("3"));
    }

    @Test
    void getFireInfo_InvalidAddress_ShouldReturnEmpty() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "NonExistent Address"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]")); // Empty response
    }
}

