package saho.backend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;

import java.util.List;


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
        employeeRepo.deleteById(id);
    }



}
