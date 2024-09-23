package com.topicals.topicalsapi.content;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndDeleteModule() throws Exception {
        String moduleJson = "{ \"moduleName\": \"test module\" }";

        // Create Module
        MvcResult result = mockMvc.perform(post("/api/v1/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moduleJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.moduleName").value("test module"))
                .andReturn();

        String moduleId = JsonPath.read(result.getResponse().getContentAsString(), "$.moduleId");

        // Delete Module
        mockMvc.perform(delete("/api/v1/modules/{moduleId}", moduleId))
                .andExpect(status().isNoContent());
    }
}
