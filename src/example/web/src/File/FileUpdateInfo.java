package example.web.src.File;

public class FileUpdateInfo {

	private String fileName;
	private long fileSize;
	private String fileExt;
	private boolean fileUploadState=true;
	private String fileSuffix;
	private String filePath;
	private String fileInputFormName;
	private String errorMsg;
	private static final String[] errorMessage={
			"您上传的文件过大，请优化后上传",
			"文件超出大小",
			"没有文件正在上传",
			"缺少一个临时文件夹",
			"此文件类型不允许上传"
	};
	
	public FileUpdateInfo(String fileName, long fileSize, String fileExt,
			 String fileSuffix, String filePath,String fileInputFormName) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.fileSuffix = fileSuffix;
		this.filePath = filePath;
		this.fileInputFormName = fileInputFormName;
	}
	
	public FileUpdateInfo(String fileName, long fileSize, String fileExt,
			boolean fileUploadState, String fileSuffix, String filePath,
			String fileInputFormName) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.fileUploadState = fileUploadState;
		this.fileSuffix = fileSuffix;
		this.filePath = filePath;
		this.fileInputFormName = fileInputFormName;
	}

	public FileUpdateInfo(String fileInputFormName,boolean fileUploadState) {
		this.fileInputFormName=fileInputFormName;
		this.fileUploadState=fileUploadState;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public boolean isFileUploadState() {
		return fileUploadState;
	}

	public void setFileUploadState(boolean fileUploadState) {
		this.fileUploadState = fileUploadState;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileInputFormName() {
		return fileInputFormName;
	}

	public void setFileInputFormName(String fileInputFormName) {
		this.fileInputFormName = fileInputFormName;
	}

	
	public void setErrorMsg(int index){
		this.errorMsg=errorMessage[index];
	}
	
	public String getErrorMsg(){
		return this.errorMsg; 
	}

	
	
}
