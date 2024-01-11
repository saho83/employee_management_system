package saho.backend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saho.backend.model.Employee;
import saho.backend.repo.EmployeeRepo;

import java.util.List;
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
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }



}
