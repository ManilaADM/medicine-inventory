package dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Transaction;
import models.dto.TransactionVO;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class TransactionDAO extends JongoDAO<Transaction> {
	
	public TransactionDAO(Class<Transaction> clazz) {
		super(clazz);
	}

	public List<TransactionVO> sortBy(String fieldName, boolean ascending, int sizeLimit) {
		String order = ascending ? "1" : "-1";
		return Lists.newArrayList(collections.aggregate("{ $sort:{timeStamp: "+ order + "}}").
				and("{$unwind: '$medSupItems'}"). //break up meds into separate records
				and("{$limit : "+ String.valueOf(sizeLimit) +"}").as(TransactionVO.class));
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