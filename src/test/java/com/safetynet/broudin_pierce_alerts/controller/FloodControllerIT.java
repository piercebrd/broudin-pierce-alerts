package com.safetynet.broudin_pierce_alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FloodControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFloodInfo_ShouldReturnData_ForValidStation() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void getFloodInfo_ShouldReturnEmptyMap_ForUnknownStation() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }

    @Test
    void getFloodInfo_ShouldReturnBadRequest_WhenNoParamProvided() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
