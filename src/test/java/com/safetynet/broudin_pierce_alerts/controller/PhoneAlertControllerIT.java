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
public class PhoneAlertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPhoneNumbers_ShouldReturnList_ForValidFirestation() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getPhoneNumbers_ShouldReturnEmptyList_ForUnknownFirestation() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getPhoneNumbers_ShouldReturnBadRequest_WhenParamMissing() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
