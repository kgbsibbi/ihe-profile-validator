package iheprofilevalidator.bean;

import java.util.ArrayList;

public class ResultBean {
	private String fileName;
	private String resultFile;
	public String getResultFile() {
		return resultFile;
	}

	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}

	private ArrayList<String> errorMessages;
	
	public ResultBean(){
		errorMessages = new ArrayList<>();
	}
	
	public ArrayList<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(ArrayList<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
	
	public void addErrorMessage(String message){
		errorMessages.add(message);
	}
}
