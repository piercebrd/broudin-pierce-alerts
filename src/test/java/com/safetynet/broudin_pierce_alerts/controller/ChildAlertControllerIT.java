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
public class ChildAlertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getChildrenAddress_ShouldReturnChildrenList_WhenChildrenPresent() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getChildrenAddress_ShouldReturnEmptyString_WhenNoChildren() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "123 Empty St")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getChildrenAddress_ShouldReturnBadRequest_WhenNoParam() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
