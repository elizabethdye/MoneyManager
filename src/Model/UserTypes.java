package Model;

public enum UserTypes {
	USER, ADMIN;

    public static UserTypes fromString(String element){
    	element = element.toUpperCase();
        switch (element){
            case "USER":
                return USER;
            case "ADMIN":
            	return ADMIN;
        }
        return null;
    }
}
