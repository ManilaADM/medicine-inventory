package dao;

import java.util.List;

import org.bson.types.ObjectId;


public interface JongoCRUD <T extends JongoModel> {
	
	public void save(T obj);
	
	public void update(ObjectId id, T object);
	
	public void delete(ObjectId id);
		
	public List<T> findAll();
	
	public T findOne(ObjectId objectId);    
	
	public List<T> search(String fieldName, String value);
	
	public T searchOne(String fieldName, String value);

}
