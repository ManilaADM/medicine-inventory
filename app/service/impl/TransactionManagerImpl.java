package service.impl;

import java.util.List;

import models.Transaction;
import models.dto.TransactionVO;

import org.bson.types.ObjectId;

import service.TransactionManager;
import dao.TransactionDAO;

public class TransactionManagerImpl implements TransactionManager {
	private static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	@Override
	public List<TransactionVO> fetchTransactions(String sortBy,
			boolean isAscending, String rowLimit) {
		// TODO Auto-generated method stub
		return transactionDao.fetchTransactions(sortBy, isAscending, rowLimit);
	}

	@Override
	public boolean cancelMedSupItemRequest(String txnId, String medId) {
		// TODO Auto-generated method stub
		return transactionDao.cancelMedSupItemRequest(txnId, medId);
	}

	@Override
	public Transaction findOne(ObjectId objectId) {
		// TODO Auto-generated method stub
		return transactionDao.findOne(objectId);
	}

	@Override
	public void save(Transaction transactionObj) {
		// TODO Auto-generated method stub
		transactionDao.save(transactionObj);
	}

	@Override
	public void update(ObjectId id, Transaction transactionObj) {
		// TODO Auto-generated method stub
		transactionDao.update(id, transactionObj);
	}

}
