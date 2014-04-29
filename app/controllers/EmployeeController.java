package controllers;

import java.util.List;

import models.Employee;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.EmployeeManager;
import service.impl.EmployeeManagerImpl;
import views.html.employee;

public class EmployeeController extends Controller {

	//static JongoDAO<Employee> employeeDao = new JongoDAO<>(Employee.class);
	static EmployeeManager employeeManager = new EmployeeManagerImpl();
	static Form<Employee> empForm = Form.form(Employee.class);

	@Security.Authenticated(Secured.class)
	public static Result getEmployee() {
		List<Employee> employees = employeeManager.findAll();
		Play.application().configuration().getString("application.langs");

		return ok(employee.render("Employee Information", employees, empForm));
	}

	public static Result setEmployee() {
		Employee employee = Form.form(Employee.class).bindFromRequest().get();
		if (StringUtils.isEmpty(employee.getObjectId())) {
			employeeManager.save(employee);
		} else {
			employeeManager.update( employee);
		}
		return redirect(routes.EmployeeController.getEmployee());
	}

	public static Result removeEmployee(ObjectId id) {
		employeeManager.delete(id);
		return redirect(routes.EmployeeController.getEmployee());
	}

}
