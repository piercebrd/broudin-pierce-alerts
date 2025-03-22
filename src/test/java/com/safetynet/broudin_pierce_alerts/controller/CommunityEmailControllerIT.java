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
public class CommunityEmailControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEmails_ShouldReturnEmailList_ForValidCity() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Culver")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getEmails_ShouldReturnEmptySet_ForUnknownCity() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "GhostCity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getEmails_ShouldReturnBadRequest_WhenMissingParam() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
