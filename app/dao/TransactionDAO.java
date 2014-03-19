package dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Transaction;
import models.dto.TransactionVO;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class TransactionDAO extends JongoDAO<Transaction> {
	
	private static Logger log = LoggerFactory.getLogger(TransactionDAO.class);
	
	public TransactionDAO(Class<Transaction> clazz) {
		super(clazz);
	}

	/**
	 * Retrieves transaction records per medical supply item (1:1 mapping), i.e. Each transaction can
	 * have a maximum of 3 medical supply items, so assuming there's 3 this translates into 3 records,
	 * instead of just 1 record.
	 * @param sortBy
	 * @param ascending 
	 * @param rowLimit
	 * @return list of transaction records per medical supply item
	 */
	public List<TransactionVO> fetchTransactions(String sortBy, boolean ascending, String rowLimit) {
		String order = ascending ? "1" : "-1";
		return  collections.aggregate("{ $sort:{"+ sortBy +": "+ order + "}}").
				and("{$unwind: '$medSupItems'}"). //break up med items into separate records
				and("{$limit : "+ rowLimit +"}").as(TransactionVO.class);
	}
	
	/**
	 * This operation sets to true the 'returned' status of the medical supply in the transaction. 
	 * @param txnId - transaction to be updated
	 * @param medId - returned medical supply item  
	 */
	public boolean cancelMedSupItemRequest(String txnId, String medId) {
		String query = "_id:#, medSupItems : {$elemMatch: {id:#, returned:false }}";
		Object[] criteria = {new ObjectId(txnId), medId};
		
		return update(query, criteria, "$set: {'medSupItems.$.returned': #}", true);
	}
	
	public long countEmpMedDoneRequestInCurrentDate(String employeeName, String medicineName) {
		// TODO: clean this trick for reseting time to 00:00:00
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);
		Date currentDate = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date nextDate = cal.getTime();

		DBObject query = QueryBuilder.start().put("employeeName").is(employeeName).
				and("timeStamp").greaterThanEquals(currentDate).lessThan(nextDate).
				and("medSupItems.brandName").is(medicineName).
				and("medSupItems.returned").is(false).
				and("medSupItems.quantity").greaterThan(0).
				get();
		
		return collections.count(query.toString());
	}
	
}