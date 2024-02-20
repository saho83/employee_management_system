package saho.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import saho.backend.model.Employee;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmployeeControllerTest {

    private final String BASE_URL = "/api";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllEmployees_shouldReturnEmptyList_WhenCalledInitially() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getEmployeeById_shouldReturnEmployee1_WhenCalledWith1() throws Exception {
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);


        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(status().isOk())
                .andReturn();


        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees/1"))
                .andExpect(status().isOk())
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
    void getEmployeeById_shouldReturnNotFound_WhenEmployeeNotFound() throws Exception {
        // When
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveEmployee_shouldReturnSavedEmployee() throws Exception {
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteEmployee_shouldReturnNoContent_WhenEmployeeExists() throws Exception {

        Employee employee = new Employee("1", "Test", "Employee", "test@example.com");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);


        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(status().isOk());


        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + employee.getId()))
                .andExpect(status().isNoContent());


        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void deleteEmployee_shouldReturnNotFound_WhenEmployeeNotFound() throws Exception {
        // Given
        String nonExistingEmployeeId = "999";

        // When
        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + nonExistingEmployeeId))
                .andExpect(status().isNotFound());
    }



    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        String employeeAsJSON = objectMapper.writeValueAsString(employee);


        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJSON))
                .andExpect(status().isOk());



        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/employees/1"))
                .andExpect(status().isOk())
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
    void updateEmployee_shouldReturnUpdatedEmployee_v2() throws Exception {
        // Given
        Employee originalEmployee = new Employee("1", "firstName", "lastName", "test@test.de");
        String originalEmployeeAsJSON = objectMapper.writeValueAsString(originalEmployee);


        MvcResult postResult = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(originalEmployeeAsJSON))
                .andExpect(status().isOk())
                .andReturn();


        String employeeId = objectMapper.readValue(postResult.getResponse().getContentAsString(), Employee.class).getId();


        Employee updatedEmployeeDetails = new Employee(employeeId, "newFirstName", "newLastName", "new@test.de");
        String updatedEmployeeAsJSON = objectMapper.writeValueAsString(updatedEmployeeDetails);

        // When
        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeAsJSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employeeId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("new@test.de"));
    }

}