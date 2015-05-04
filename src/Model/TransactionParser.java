package Model;

import java.util.ArrayList;

public class TransactionParser {
	String[] parts;
	ArrayList<TransactionInfo> transactions;
	
	public String[] parseString(String s){
		parts = s.split("~;~");
		return parts;
	}
	
	public void setInfo(String[] parts, TransactionInfo t){
		t.setType(parts[0]);
		t.setAmount(parts[1]); 
		t.setCategory(parts[2]);
		t.setDate(parts[3]);
	}
	
	public ArrayList<TransactionInfo> getTransactionList(ArrayList<String> list){
		for(String transaction: list){
			parseString(transaction);
			TransactionInfo t = new TransactionInfo();
			setInfo(parts, t);
			System.out.println(t.getDate());
			transactions.add(t);
			System.out.println("added transaction");
		}
		return transactions;
	}
}
