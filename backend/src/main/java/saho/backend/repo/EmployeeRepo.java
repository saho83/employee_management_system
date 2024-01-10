package saho.backend.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import saho.backend.model.Employee;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee,String> {


}
