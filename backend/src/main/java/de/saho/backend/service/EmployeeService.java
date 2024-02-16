package de.saho.backend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import de.saho.backend.exeption.ResourceNotFoundException;
import de.saho.backend.model.Employee;
import de.saho.backend.repo.EmployeeRepo;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepo employeeRepo;

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }


    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(String id) {
        Optional<Employee> employeeOptional = employeeRepo.findById(id);
        if (employeeOptional.isPresent()) {
            employeeRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
    }

    public Employee updateEmployee(String id, Employee employeeDetails) {
        Employee existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmailId(employeeDetails.getEmailId());

        return employeeRepo.save(existingEmployee);
    }






}
