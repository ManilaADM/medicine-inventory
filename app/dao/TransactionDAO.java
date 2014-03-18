package dao;

import java.util.List;

import models.Medicine;
import models.Transaction;
import models.dto.TransactionVO;

import org.bson.types.ObjectId;
import org.jongo.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionDAO extends JongoDAO<Transaction> {
	
	private static Logger log = LoggerFactory.getLogger(TransactionDAO.class);
	private static JongoDAO<Medicine> medicineDao = new JongoDAO<>(Medicine.class);
	
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
	 * This operation first adds back a medical supply quantity from a transaction into the 
	 * medicine inventory. If this succeeds, 'returned' status of medical supply in the transaction is set to true. 
	 * @param txnId - transaction to be updated
	 * @param medId - returned medical supply item  
	 * @param param - 
	 */
	public void cancelMedSupItemRequest(String txnId, String medId, String... param ) {
		Update update = collections.update("{_id:#, 'medSupItems.id':#}", new ObjectId(txnId), medId);
		update.with("{$set: {'medSupItems.$.returned': true}}");
	}
}