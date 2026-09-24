package com.devops.hello.controller;

import com.devops.hello.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService.clear();
    }
    

    @Test
    void create_shouldReturn201AndClient() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Alice",
                                  "email": "alice@example.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void create_withInvalidPayload_shouldReturn400() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "email": "pas-un-email"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Requête invalide"));
    }

    @Test
    void findAll_shouldReturnListOfClients() throws Exception {
        createClient("Alice", "alice@example.com");
        createClient("Bob", "bob@example.com");

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findById_shouldReturnClient() throws Exception {
        Long id = createClient("Alice", "alice@example.com");

        mockMvc.perform(get("/api/clients/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void findById_whenMissing_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/clients/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Client introuvable avec l'id : 99"));
    }

    @Test
    void update_shouldReturnUpdatedClient() throws Exception {
        Long id = createClient("Alice", "alice@example.com");

        mockMvc.perform(put("/api/clients/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Alice Martin",
                                  "email": "alice.martin@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.intValue()))
                .andExpect(jsonPath("$.name").value("Alice Martin"))
                .andExpect(jsonPath("$.email").value("alice.martin@example.com"));
    }

    @Test
    void update_whenMissing_shouldReturn404() throws Exception {
        mockMvc.perform(put("/api/clients/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Alice",
                                  "email": "alice@example.com"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        Long id = createClient("Alice", "alice@example.com");

        mockMvc.perform(delete("/api/clients/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/clients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_whenMissing_shouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/clients/99"))
                .andExpect(status().isNotFound());
    }

    private Long createClient(String name, String email) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "email": "%s"
                                }
                                """.formatted(name, email)))
                .andExpect(status().isCreated())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        // Extraction simple de l'id dans une réponse {"id":1,...}
        String idPart = body.replaceAll("(?s).*\"id\"\\s*:\\s*(\\d+).*", "$1");
        return Long.parseLong(idPart);
    }


}
