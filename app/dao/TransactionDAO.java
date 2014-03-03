package dao;

import java.util.List;

import models.Transaction;
import models.dto.TransactionVO;

import com.google.common.collect.Lists;

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
}