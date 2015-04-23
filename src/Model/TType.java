package Model;

public enum TType {
	TRANSFERTO, TRANSFERFROM, DEPOSIT, WITHDRAWAL;
	public static TType fromString(String element){
    	element = element.toUpperCase();
        switch (element){
            case "TRANSFERTO":
                return TRANSFERTO;
            case "TRANSFERFROM":
            	return TRANSFERFROM;
            case "DEPOSIT":
            	return DEPOSIT;
            case "WITHDRAWAL":
            	return WITHDRAWAL;
        }
        return null;
    }
}
