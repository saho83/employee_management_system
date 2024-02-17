package saho.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private final String BASE_URL = "/api";

    @Autowired
    private MockMvc mvc;
    @Test
    void getAllEmployees_shouldReturnEmptyList_WhenCalledInitially() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void saveEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void updateEmployee() {
    }
}