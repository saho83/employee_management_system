package saho.backend.service;

import org.junit.jupiter.api.Test;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;


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
    void saveEmployee_whenEmployeeSaved_thenReturnsSavedEmployee() {
        //GIVEN
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        when(employeeRepo.save(employee)).thenReturn(employee);

        //WHEN
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //THEN
        assertEquals(employee, savedEmployee);
    }

    @Test
    void deleteEmployee_whenEmployeeExists_thenEmployeeDeleted() {
        String id = "1";
        //GIVEN
        Employee employee = new Employee("1", "firstName", "lastName", "test@test.de");
        when(employeeRepo.findById(id)).thenReturn(Optional.of(employee));

        //WHEN
        employeeService.deleteEmployee(id);

        //THEN
        verify(employeeRepo).deleteById(id);
    }

    @Test
    void updateEmployee_whenEmployeeExists_thenEmployeeUpdated() {

        //GIVEN
        String id = "1";
        Employee existingEmployee = new Employee("1", "firstName", "lastName", "test@test.de");
        Employee employeeDetails = new Employee("1", "newFirstName", "newLastName", "new@test.de");

        when(employeeRepo.findById(id)).thenReturn(Optional.of(existingEmployee));

        //WHEN
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);

        //THEN
        verify(employeeRepo).findById(id);

        assertEquals(employeeDetails.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(employeeDetails.getLastName(), updatedEmployee.getLastName());
        assertEquals(employeeDetails.getEmailId(), updatedEmployee.getEmailId());

        verify(employeeRepo).save(existingEmployee);
    }
}