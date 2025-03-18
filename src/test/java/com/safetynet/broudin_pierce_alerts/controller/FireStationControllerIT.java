package com.safetynet.broudin_pierce_alerts.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPeopleByStation_ShouldReturnData() throws Exception {
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.people").isArray())
                .andExpect(jsonPath("$.people[0].firstName").value("Peter"))
                .andExpect(jsonPath("$.adultCount").value(5))
                .andExpect(jsonPath("$.childrenCount").value(1));
    }

}
