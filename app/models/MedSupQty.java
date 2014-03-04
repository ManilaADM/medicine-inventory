package models;

public class MedSupQty {
	
	private String id;
	private String brandName;
	private Integer quantity;
	private boolean returned;
	
	public MedSupQty(){
		
	}
	
	public MedSupQty(String bn, Integer qty) {
		this.brandName = bn;
		this.quantity = qty;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
