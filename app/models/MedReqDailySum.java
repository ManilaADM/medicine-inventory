package models;

import dao.JongoModel;

public class MedReqDailySum implements JongoModel{

	private String _id;
	
	private int sum;
	
	@Override
	public String getCollectionName() {
		return "transactions";
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

}
