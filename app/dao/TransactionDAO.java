package dao;

import java.util.List;
import java.util.regex.Pattern;

import models.Transaction;
import models.dto.TransactionVO;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import com.google.common.collect.Lists;

public class TransactionDAO {

	MongoCollection collections = null;
	
	public TransactionDAO(String collectionName) {
		collections = PlayJongo.getCollection(collectionName);
	}
	
	public void save(Transaction obj) {
		collections.save(obj);
    }
	
	public void update(ObjectId id, Transaction object){
		collections.update(id).merge(object);
	}
	
	public void delete(ObjectId id) {
		collections.remove(id);
	}
	
	public List<Transaction> findAll(){
		return Lists.newArrayList(collections.find().as(Transaction.class));
	}
	
	public Transaction findOne(ObjectId objectId){
		return (Transaction) collections.findOne(objectId).as(Transaction.class);
	}    
	
	public List<Transaction> search(String fieldName, String value){
		return Lists.newArrayList(collections.find("{"+fieldName+":#}",Pattern.compile("*"+value+"*")).as(Transaction.class));
	}
	
	public Transaction searchOne(String fieldName, String value){
		return (Transaction) collections.findOne("{"+fieldName+":#}",Pattern.compile("*"+value+"*")).as(Transaction.class);
	}
	
	public long count(String fieldName, String value) {
		return collections.count("{"+fieldName+":#}",value);
	}
	
	public List<TransactionVO> sortBy(String fieldName, boolean ascending, int sizeLimit) {
		String order = ascending ? "1" : "-1";
		return Lists.newArrayList(collections.aggregate("{ $sort:{timeStamp: "+ order + "}}").
				and("{$unwind: '$medSupItems'}").
				and("{$limit : "+ String.valueOf(sizeLimit) +"}").as(TransactionVO.class));
	}
	
}
