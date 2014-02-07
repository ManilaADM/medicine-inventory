package dao;

import java.lang.reflect.Method;
import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import com.google.common.collect.Lists;

public class JongoDAO<T extends JongoModel> implements JongoCRUD<T> {
	
	MongoCollection collections = null;
	Class<T> clazz = null;
	
	public JongoDAO(Class<T> clazz) {
		Method method = null;
		String collection = null;
		
		try {
			method = clazz.getMethod("getCollectionName");		
			collection = method.invoke(clazz.newInstance()).toString();		
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		collections = PlayJongo.getCollection(collection);
		this.clazz = clazz;
	}
	
	public void save(T obj) {
		collections.save(obj);
    }
	
	public void update(ObjectId id, T object){
		collections.update(id).merge(object);
	}
	
	public void delete(ObjectId id) {
		collections.remove(id);
	}
		
	public List<T> findAll(){
		return Lists.newArrayList(collections.find().as(clazz));
	}
	
	public T findOne(ObjectId objectId){
		return (T) collections.findOne(objectId).as(clazz);
	}    
	
	public List<T> search(String fieldName, String value){
		return Lists.newArrayList(collections.find("{"+fieldName+":#}",value).as(clazz));
	}
	
	public T searchOne(String fieldName, String value){
		return (T) collections.findOne("{"+fieldName+":#}",value).as(clazz);
	}
	
}
