package Model;

public enum DatabaseCommand {
	CREATE_TABLES, ADD_COURSE, ADD_STUDENT, ADD_GRADE, 
	RETRIEVE_GRADE, RETRIEVE_OVERALL_GRADE, GET_COURSES,
	GET_ASSIGNMENTS, GET_STUDENTS, GET_GRADE_INFO, GET_USER_TYPE, ADD_USER,
	ADD_ASSIGNMENT, DELETE_TABLES, REMOVE_COURSE, SET_TOTAL_POSSIBLE, GET_TOTAL_POSSIBLE,
	GET_TOTAL_GRADES, GET_STUDENT_GRADES, REMOVE_ASSIGNMENT, REMOVE_STUDENT,
	GET_STUDENT_INFO;
	
	public static DatabaseCommand fromString(String command){
    	command = command.toUpperCase();
        switch (command){
            case "CREATE_TABLES":
                return CREATE_TABLES;
            case "ADD_COURSE":
                return ADD_COURSE;
            case "ADD_STUDENT":
                return ADD_STUDENT;
            case "ADD_GRADE":
            	return ADD_GRADE;
            case "RETRIEVE_GRADE":
            	return RETRIEVE_GRADE;
            case "RETRIEVE_OVERALL_GRADE":
            	return RETRIEVE_OVERALL_GRADE;
            case "GET_COURSES":
            	return GET_COURSES;
            case "GET_ASSIGNMENTS":
            	return GET_ASSIGNMENTS;
            case "GET_STUDENTS":
            	return GET_STUDENTS;
            case "GET_GRADE_INFO":
            	return GET_GRADE_INFO;
            case "GET_USER_TYPE":
            	return GET_USER_TYPE;
            case "ADD_USER":
            	return ADD_USER;
            case "ADD_ASSIGNMENT":
            	return ADD_ASSIGNMENT;
            case "DELETE_TABLES":
            	return DELETE_TABLES;
            case "REMOVE_COURSE":
            	return REMOVE_COURSE;
            case "SET_TOTAL_POSSIBLE":
            	return SET_TOTAL_POSSIBLE;
            case "GET_TOTAL_POSSIBLE":
            	return GET_TOTAL_POSSIBLE;
            case "GET_TOTAL_GRADES":
            	return GET_TOTAL_GRADES;
            case "GET_STUDENT_GRADES":
            	return GET_STUDENT_GRADES;
            case "REMOVE_ASSIGNMENT":
            	return REMOVE_ASSIGNMENT;
            case "REMOVE_STUDENT":
            	return REMOVE_STUDENT;
            case "GET_STUDENT_INFO":
            	return GET_STUDENT_INFO;
        }
        return null;
    }

}
