package saho.backend.service;

import org.junit.jupiter.api.Test;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    void getEmployeeById_whenCalled_thenReturnsEmployeeByID() {
        String id = "1";
        //GIVEN
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        when(employeeRepo.findById(id)).thenReturn(Optional.of(employee));

        //WHEN
        Employee actual = employeeService.getEmployeeById(id);

        //THEN
        Employee expected = new Employee("1", "firstName", "lastName", "test@test.de");
        verify(employeeRepo).findById(id);
        assertEquals(expected, actual);
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