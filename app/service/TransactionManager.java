package service;

import java.util.List;

import org.bson.types.ObjectId;

import models.Transaction;
import models.dto.TransactionVO;

public interface TransactionManager {

	List<TransactionVO> fetchTransactions(String sortBy, boolean isAscending,
			String rowLimit);

	boolean cancelMedSupItemRequest(String txnId, String medId);

	Transaction findOne(ObjectId objectId);

	void save(Transaction transactionObj);

	void update(ObjectId id, Transaction transactionObj);

}
