package models;


public class MedSupQty {
	
	//private ObjectId id;
	
	public String brandName;
	public Integer quantity;
	
	public MedSupQty(){
		
	}
	
	public MedSupQty(String bn, Integer qty){
		this.brandName = bn;
		this.quantity = qty;
	}

}
