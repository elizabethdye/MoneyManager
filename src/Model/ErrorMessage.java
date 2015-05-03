package Model;

public enum ErrorMessage {
	USERPASSWORD, DATE, AMOUNT, ACCT_EXISTS, ACCT_SAME;
	
	public static ErrorMessage fromString(String element){
		element = element.toUpperCase();
		switch( element ){
			case "USERPASSWORD":
				return USERPASSWORD;
			case "DATE":
				return DATE;
			case "AMOUNT":
				return AMOUNT;
			case "ACCT_EXISTS":
				return ACCT_EXISTS;
		}
		return null;
	}
	
	public static String returnMessage(ErrorMessage error){
		switch( error ){
			case USERPASSWORD:
				return "Invalid Username/Password.";
			case DATE:
				return "Please use choose a date.";
			case AMOUNT:
				return "Please use only digits and a single decimal point.";
			case ACCT_EXISTS:
				return "That account exists already, please choose another name.";
			case ACCT_SAME:
				return "You entered the same account twice, please choose two different accounts for the transfer.";
		}
		return null;
	}
}
