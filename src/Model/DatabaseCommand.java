package Model;

public enum DatabaseCommand {
	CREATE_TABLES, GET_USER_TYPE, ADD_USER, DELETE_TABLES, 
	CREATE_TRANSFER, CREATE_WITHDRAWAL, CREATE_DEPOSIT, 
	GET_BALANCE, UPDATE_BALANCE, CREATE_TRANSACTION, GET_ACCTS, ADD_ACCT;
	
	public static DatabaseCommand fromString(String command){
    	command = command.toUpperCase();
        switch (command){
            case "CREATE_TABLES":
                return CREATE_TABLES;
            case "GET_USER_TYPE":
            	return GET_USER_TYPE;
            case "ADD_USER":
            	return ADD_USER;
            case "CREATE_TRANSACTION":
            	return CREATE_TRANSACTION;
            case "CREATE_TRANSFER":
            	return CREATE_TRANSFER;
            case "CREATE_DEPOSIT":
            	return CREATE_DEPOSIT;
            case "CREATE_WITHDRAWAL":
            	return CREATE_WITHDRAWAL;
            case "GET_ACCT_BALANCE":
            	return GET_BALANCE;
            case "UPDATE_ACCT_BALANCE":
            	return UPDATE_BALANCE;
            case "GET_ACCTS":
            	return GET_ACCTS;
            case "DELETE_TABLES":
            	return DELETE_TABLES;
            case "ADD_ACCT":
            	return ADD_ACCT;
        }
        return null;
    }

}
