package de.genohackathon.mdm.dao;

import de.genohackathon.mdm.model.Employee;

/**
 * Created by chuff on 30.05.2017.
 */
public interface EmployeeService {
    
    Employee findEmployeeById(String id);
    
    Employee create(String firstName, String lastName);
    
    void update(Employee employee);
}
