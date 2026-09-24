package com.devops.hello.controller;

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
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello_shouldReturn200AndDefaultMessage() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello DevOps"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }

    @Test
    void helloByName_shouldReturn200AndPersonalizedMessage() throws Exception {
        mockMvc.perform(get("/api/hello/Fabien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Fabien"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }
}
