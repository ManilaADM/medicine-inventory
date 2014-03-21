package dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.MedReqDailySum;
import models.Transaction;
import models.dto.TransactionVO;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class TransactionDAO extends JongoDAO<Transaction> {
	
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
	
	public int countEmpDoneMedRequestInCurrentDate(String employeeName, String medSupId) {
		int totalReqMed = 0;
		// TODO: clean this trick for reseting time to 00:00:00
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);
		Date currentDate = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date nextDate = cal.getTime();

		DBObject searchQuery = QueryBuilder.start().put("employeeName").is(employeeName).
				and("timeStamp").greaterThanEquals(currentDate).lessThan(nextDate).
				and("medSupItems.id").is(medSupId).
				and("medSupItems.returned").is(false).
				get();
		
		List<MedReqDailySum> sums = collections.aggregate("{$unwind: '$medSupItems'}").
				and("{$match: " + searchQuery + "}").
				and("{$group: { _id: 'sumQty_ID', sum: { $sum: '$medSupItems.quantity' } } }").as(MedReqDailySum.class);
		
		for (MedReqDailySum sum : sums)
		{
			totalReqMed = sum.getSum();
		}
		
		return totalReqMed;
	}
	
}