package service;

import java.util.List;

import org.bson.types.ObjectId;

import models.Employee;

public interface EmployeeManager {
	public List<Employee> findAll();
	public void save(Employee employee);
	public void update(Employee employee);
	public void delete(ObjectId id);
}
