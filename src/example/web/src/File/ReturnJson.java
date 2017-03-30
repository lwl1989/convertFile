package example.web.src.File;

import java.util.ArrayList;

public class ReturnJson {
	private boolean status;
	private ArrayList<FileUpdateInfo> files;
	private String msg;
	public ReturnJson(boolean status, ArrayList<FileUpdateInfo> files,
			String msg) {
		super();
		this.status = status;
		this.files = files;
		this.msg = msg;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public ArrayList<FileUpdateInfo> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<FileUpdateInfo> files) {
		this.files = files;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
