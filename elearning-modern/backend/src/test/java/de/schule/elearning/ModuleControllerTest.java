package de.schule.elearning;

import de.schule.elearning.domain.ModuleService;
import de.schule.elearning.interfaces.rest.ModuleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ModuleController.class)
@Import(ModuleService.class)
class ModuleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getModules_returnsNonEmptyList() throws Exception {
        mockMvc.perform(get("/api/v1/modules").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThan(0)));
    }

    @Test
    void getModuleById_knownId_returnsModule() throws Exception {
        mockMvc.perform(get("/api/v1/modules/M-A").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("M-A"));
    }

    @Test
    void getModuleById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/v1/modules/UNKNOWN").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
