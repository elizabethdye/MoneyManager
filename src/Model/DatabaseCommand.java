package Model;

public enum DatabaseCommand {
	CREATE_TABLES, GET_USER_TYPE, ADD_USER, DELETE_TABLES, 
	CREATE_TRANSFER, CREATE_WITHDRAWAL, CREATE_DEPOSIT, 
	GET_BALANCE, UPDATE_BALANCE, CREATE_TRANSACTION, 
	GET_ACCTS, ADD_ACCT, GET_TRANSACTIONS,GET_T_FOR_CAT,
	GET_CATEGORIES;
	
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
            case "GET_TRANSACTIONS":
            	return GET_TRANSACTIONS;
            case "GET_CATEGORIES":
            	return GET_CATEGORIES;
            case "GET_T_FOR_CAT":
            	return GET_T_FOR_CAT;
        }
        return null;
    }

}
