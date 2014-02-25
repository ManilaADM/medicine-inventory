package controllers;

import java.util.List;

import models.Employee;
import models.Logs;
import models.Medicine;

import org.bson.types.ObjectId;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.medicine;
import dao.JongoDAO;

public class MedicineController extends Controller{
	
	private static JongoDAO<Medicine> medicineDao = new JongoDAO<>(Medicine.class);
	
	public static Result getMedicine(){
		List<Medicine> medicines = medicineDao.findAll();
		return ok(medicine.render("Medicine List",medicines));
	}
	
	public static Result setMedicine(){
		Medicine medicine = Form.form(Medicine.class).bindFromRequest().get();
		if(medicine.getId() == null){
			medicineDao.save(medicine);
		}else{
			medicineDao.update(medicine.getId(), medicine);
		}
		
		return redirect(routes.MedicineController.getMedicine());
	}
	
	public static Result removeMedicine(ObjectId id) {
		medicineDao.delete(id);
		return redirect(routes.MedicineController.getMedicine());
	 }
	
	public static Result test(){
		
		JongoDAO<Employee> employeeDao = new JongoDAO<>(Employee.class);
		
		List<Medicine> medicines = medicineDao.findAll();
		Employee employees = employeeDao.findAll().get(0);
		
		Logs logs = new Logs();
		
		logs.setEmployee(employees);
		logs.setMedicines(medicines);
		
		logs.setDate("now");
		
		JongoDAO<Logs> logsDao = new JongoDAO<>(Logs.class);
		
		logsDao.save(logs);
		
		
		return redirect(routes.MedicineController.getMedicine());
	}

	
	
	
}
