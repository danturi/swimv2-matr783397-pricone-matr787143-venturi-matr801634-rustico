package sessionbeans.logic;

import java.util.List;

public class SwimResponse{

	private int status;
	private String statusMsg;
	private Object data;
	public static final int FAILED = 0;
	public static final int SUCCESS = 1;


	public SwimResponse(){
	}

	public SwimResponse(int status, String statusMsg) {
		this.setStatus(status);
		this.setStatusMsg(statusMsg);
	}
	
	public SwimResponse(int status, String statusMsg, Object data) {
		this.setStatus(status);
		this.setStatusMsg(statusMsg);
		this.setData(data);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	

}
