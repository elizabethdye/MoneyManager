package Model;

public enum UserType {
	USER, ADMIN, INVALID;

    public static UserType fromString(String element){
    	element = element.toUpperCase();
        switch (element){
            case "USER":
                return USER;
            case "ADMIN":
            	return ADMIN;
            case "INVALID":
            	return INVALID;
        }
        return null;
    }
}
