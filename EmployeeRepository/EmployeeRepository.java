package com.uidt.ApiResful.EmployeeRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uidt.ApiResful.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
