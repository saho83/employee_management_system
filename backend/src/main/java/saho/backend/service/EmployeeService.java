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
}
