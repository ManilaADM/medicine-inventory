package dao;

import java.util.List;

import org.bson.types.ObjectId;


public interface JongoCRUD <T extends JongoModel> {
	
	public void save(T obj);
	
	public void update(ObjectId id, T object);
	
	public void delete(ObjectId id);
		
	public List<T> findAll();
	
	public T findOne(ObjectId objectId);
	
	public T findUser(String email, String password);
	
	public List<T> search(String fieldName, String value);
	
	public T searchOne(String fieldName, String value);
	
	public long count(String fieldName, String value);

	void update(ObjectId id, String modifier, Object... params);

	void update(String queryTemplate, Object[] qParams, String modifier,
			Object... mParams);
}
