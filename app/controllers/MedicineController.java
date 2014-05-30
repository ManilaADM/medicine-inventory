package controllers;

import java.util.List;

import models.Medicine;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.MedicineManager;
import service.impl.MedicineManagerImpl;
import views.html.medicine;

public class MedicineController extends Controller{
	
	//private static JongoDAO<Medicine> medicineDao = new JongoDAO<>(Medicine.class);
	private static MedicineManager medicineManager = new MedicineManagerImpl();
	
	@Security.Authenticated(Secured.class)
	public static Result getMedicine(){
		List<Medicine> medicines = medicineManager.findAll();
		return ok(medicine.render("Medicine List",medicines));
	}
	
	public static Result setMedicine(){
		List<Medicine> medicines = medicineManager.findAll();
		Form<Medicine> form = Form.form(Medicine.class).bindFromRequest();

		if(form.hasErrors()) {
			return badRequest(medicine.render("Medicine List", medicines));
	    }
		else {
			Medicine medicineObj = form.get();
			if(StringUtils.isEmpty(medicineObj.getObjectId())){
				medicineManager.save(medicineObj);
			}else{
				medicineManager.update(new ObjectId(medicineObj.getObjectId()), medicineObj);
			}
		}
		return redirect(routes.MedicineController.getMedicine());
	}
	
	public static Result removeMedicine(ObjectId id) {
		medicineManager.delete(id);
		return redirect(routes.MedicineController.getMedicine());
	 }
	
	// TODO: create logic for searching medical supply using their brand or generic name
	public static Result searchMedicalSupply(){
		List<Medicine> medicines = medicineManager.findAll();
		return ok(medicine.render("Medicine List",medicines));
	}
	
}
