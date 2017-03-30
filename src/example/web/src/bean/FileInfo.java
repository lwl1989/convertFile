package example.web.src.bean;

import java.io.Serializable;

public class FileInfo implements Serializable {
	private String userId;
	private String filePathString;
	private int fileId;
	public FileInfo(String userId, String filePathString,int fileId) {
		super();
		this.userId = userId;
		this.filePathString = filePathString;
		this.fileId=fileId;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFilePathString() {
		return filePathString;
	}
	public void setFilePathString(String filePathString) {
		this.filePathString = filePathString;
	}
	@Override
	public String toString() {
		return "FileInfo [userId=" + userId + ", filePathString="
				+ filePathString + "]";
	}
	
	
	
}
