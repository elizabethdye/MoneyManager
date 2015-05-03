package Model;


public class TransactionInfo {
	Double amount;
	String date;
	TType type;
	String category;
	
	public void setAmount(String amount){
		this.amount = Double.valueOf(amount);
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setType(String type){
		this.type = TType.fromString(type);
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	
	public Double getAmount(){
		return amount;
	}
	
	public String getDate(){
		return date;
	}
	
	public TType getType(){
		return type;
	}
	
	public String getCategory(){
		return category;
	}
}
