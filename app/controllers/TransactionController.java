package controllers;

import java.util.List;

import models.Transaction;
import models.dto.TransactionVO;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.transaction;
import dao.TransactionDAO;

public class TransactionController extends Controller {
	
	static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	
    public static Result getTransactions() {
    	int rowLimit = Integer.parseInt(Configuration.root().getString("transaction.table.rowLimit"));
    	List<TransactionVO> medLogs = transactionDao.sortBy("timeStamp", false, rowLimit);
    	
    	return ok(transaction.render(medLogs, rowLimit));
	 }
    
    public static Result returnMedSupply() {
    	
    	return getTransactions();
    }
}
