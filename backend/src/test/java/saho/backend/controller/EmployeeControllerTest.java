package saho.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private final String BASE_URL = "/api";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    void getAllEmployees_shouldReturnEmptyList_WhenCalledInitially() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getEmployeeById_shouldReturnEmployee1_WhenCalledWith1() throws Exception {
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);

       MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

       Employee savedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);


       mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees/1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "id": "1",
                            "firstName": "firstName",
                            "lastName": "lastName",
                            "emailId": "test@test.de"
                        }
                """));

    }

    @Test
    void saveEmployee_shouldReturnSavedEmployee() throws Exception {
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
/*
    @Test
    void deleteEmployee_shouldReturnNoContent_WhenEmployeeExists() throws Exception {
        // Erstellen Sie einen Mitarbeiter, um ihn dann zu löschen
        Employee employee = new Employee("1", "Test", "Employee", "test@example.com");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);

        // Fügen Sie den Mitarbeiter hinzu
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Löschen Sie den Mitarbeiter
        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Überprüfen Sie, ob der Mitarbeiter nicht mehr gefunden wird
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
*/



    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Employee savedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);


        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "id": "1",
                            "firstName": "firstName",
                            "lastName": "lastName",
                            "emailId": "test@test.de"
                        }
                """));
    }
}