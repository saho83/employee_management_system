package saho.backend.service;

import org.junit.jupiter.api.Test;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    EmployeeRepo employeeRepo = mock(EmployeeRepo.class);
    EmployeeService employeeService = new EmployeeService(employeeRepo);

    @Test
    void getAllEmployees_whenCalled_thenReturnsAllEmployees() {
        //GIVEN
        when(employeeRepo.findAll()).thenReturn(List.of(new Employee("1", "firstName", "lastName", "test@test.de")));

        //WHEN
        List<Employee> actual = employeeService.getAllEmployees();

        //THEN
        List<Employee> expected = List.of(new Employee("1", "firstName", "lastName", "test@test.de"));
        verify(employeeRepo).findAll();
        assertEquals(expected, actual);
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