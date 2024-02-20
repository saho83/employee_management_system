package saho.backend.service;

import org.junit.jupiter.api.Test;
import saho.backend.exeption.ResourceNotFoundException;
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
    void generateUniqueId_shouldReturnValidUUID() {
        // WHEN
        String uniqueId = employeeService.generateUniqueId();

        // THEN
        assertNotNull(uniqueId);
        assertTrue(uniqueId.matches("^\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}$"));
    }

    @Test
    void saveEmployee_generatesUniqueId_whenEmployeeHasNoId() {
        // GIVEN
        Employee employeeWithoutId = new Employee(null, "firstName", "lastName", "test@test.de");
        when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setId("generatedId");
            return savedEmployee;
        });

        // WHEN
        Employee savedEmployee = employeeService.saveEmployee(employeeWithoutId);

        // THEN
        assertNotNull(savedEmployee.getId());
        verify(employeeRepo).save(employeeWithoutId);
    }


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
    void saveEmployee_whenEmployeeIdIsNull_generatesUniqueId() {
        // GIVEN
        Employee employee = new Employee(null, "firstName", "lastName", "test@test.de");


        when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setId("generatedId"); // Setzen Sie eine generierte ID
            return savedEmployee;
        });

        // WHEN
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // THEN
        assertNotNull(savedEmployee.getId());
        assertEquals("generatedId", savedEmployee.getId());
    }

    @Test
    void saveEmployee_whenEmployeeIdIsNotEmpty_doesNotGenerateNewId() {
        // GIVEN
        Employee employee = new Employee("existingId", "firstName", "lastName", "test@test.de");


        when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // THEN
        assertEquals("existingId", savedEmployee.getId());
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
    void deleteEmployee_whenEmployeeDoesNotExist_thenThrowException() {
        String id = "1";
        // GIVEN
        when(employeeRepo.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(id);
        });
        verify(employeeRepo, never()).deleteById(id);
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
        verify(employeeRepo, times(2)).findById(id);

        assertEquals(employeeDetails.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(employeeDetails.getLastName(), updatedEmployee.getLastName());
        assertEquals(employeeDetails.getEmailId(), updatedEmployee.getEmailId());

        verify(employeeRepo).save(existingEmployee);
    }
}