package controllers;

import java.util.List;

import models.Transaction;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.transaction;
import dao.JongoDAO;

public class TransactionController extends Controller {
	
	static JongoDAO<Transaction> transactionDao = new JongoDAO<>(Transaction.class);
	
    public static Result getTransactions() {

    	int rowLimit = Integer.parseInt(Play.application().configuration().getString("transaction.table.rowLimit"));
    	List<Transaction> medLogs = transactionDao.sortBy("timeStamp", false, rowLimit);
    	
    	return ok(transaction.render(medLogs));
	 }
}
