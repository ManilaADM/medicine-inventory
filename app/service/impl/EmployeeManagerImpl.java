package service.impl;

import java.util.List;

import models.Employee;

import org.bson.types.ObjectId;

import dao.JongoDAO;

import service.EmployeeManager;

public class EmployeeManagerImpl implements EmployeeManager {
	static JongoDAO<Employee> employeeDao = new JongoDAO<>(Employee.class);

	@Override
	public List<Employee> findAll() {
		return employeeDao.findAll();
	}

	@Override
	public void save(Employee employee) {
		employeeDao.save(employee);
	}

	@Override
	public void update(Employee employee) {		
		employeeDao.update(new ObjectId(employee.getObjectId()), employee);
	}

	@Override
	public void delete(ObjectId id) {
		employeeDao.delete(id);
	}

}
