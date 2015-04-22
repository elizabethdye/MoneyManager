package Model;

public class ServerRequestResult implements java.io.Serializable {
	private static final long serialVersionUID = -8541442557256279544L;
	private Object result;
	
	public Object getResult(){
		return result;
	}
	
	public void setResult(Object obj){
		result = obj;
	}
}
