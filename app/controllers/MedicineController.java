package controllers;

import java.util.List;

import models.Medicine;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.medicine;
import dao.JongoDAO;

public class MedicineController extends Controller{
	
	private static JongoDAO<Medicine> medicineDao = new JongoDAO<>(Medicine.class);
	
	@Security.Authenticated(Secured.class)
	public static Result getMedicine(){
		List<Medicine> medicines = medicineDao.findAll();
		return ok(medicine.render("Medicine List",medicines));
	}
	
	public static Result setMedicine(){
		List<Medicine> medicines = medicineDao.findAll();
		Form<Medicine> form = Form.form(Medicine.class).bindFromRequest();

		if(form.hasErrors()) {
			return badRequest(medicine.render("Medicine List", medicines));
	    }
		else {
			Medicine medicineObj = form.get();
			if(StringUtils.isEmpty(medicineObj.getObjectId())){
				medicineDao.save(medicineObj);
			}else{
				medicineDao.update(new ObjectId(medicineObj.getObjectId()), medicineObj);
			}
		}
		return redirect(routes.MedicineController.getMedicine());
	}
	
	public static Result removeMedicine(ObjectId id) {
		medicineDao.delete(id);
		return redirect(routes.MedicineController.getMedicine());
	 }	
	
}
