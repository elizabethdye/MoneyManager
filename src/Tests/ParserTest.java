package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Model.TType;
import Model.TransactionInfo;
import Model.TransactionParser;

public class ParserTest {

	@Test
	public void testParser() {
		ArrayList<String> transactions = new ArrayList<String>();
		transactions.add("Withdrawal~;~50~;~Walmart~;~04/15/2015");
		transactions.add("Withdrawal~;~20~;~Panera~;~04/16/2015");
		TransactionParser parser = new TransactionParser();
		ArrayList<TransactionInfo> info = parser.getTransactionList(transactions);
		for( TransactionInfo t : info){
			if(t.getCategory().equals("Walmart")){
				assertEquals(t.getAmount(), (Double) 50.0);
				assertEquals(t.getDate(), "04/15/2015");
				assertEquals(t.getType(), TType.WITHDRAWAL);
			}
			else if(t.getCategory().equals("Panera")){
				assertEquals(t.getDate(), "04/16/2015");
				assertEquals(t.getAmount(), (Double) 20.0);
				assertEquals(t.getType(), TType.WITHDRAWAL);
			}
		}

	}

}
