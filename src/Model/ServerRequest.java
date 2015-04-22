package Model;


public class ServerRequest implements java.io.Serializable {
	private static final long serialVersionUID = 296654890971248346L;
	DatabaseCommand command;
	String[] args;
	
	public ServerRequest (DatabaseCommand cmd, String[] args){
		this.command = cmd;
		this.args = args;
	}
	
	public DatabaseCommand getCommand(){
		return command;
	}
	
	public String[] getArgs(){
		return args;
	}
}
