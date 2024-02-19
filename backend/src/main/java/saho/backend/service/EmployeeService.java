package saho.backend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saho.backend.exeption.ResourceNotFoundException;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepo employeeRepo;

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id)); //new RuntimeException("Not Found"));
    }


    public Employee saveEmployee(Employee employee) {

        if (employee.getId() == null || employee.getId().isEmpty()) {

            String id = generateUniqueId();

            employee.setId(id);
        }

        return employeeRepo.save(employee);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
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

        employeeRepo.save(existingEmployee);

        return employeeRepo.findById(existingEmployee.getId())
                .orElseThrow() -> new ResourceNotFoundException("Employee not found with id: " + id);

    }






}
