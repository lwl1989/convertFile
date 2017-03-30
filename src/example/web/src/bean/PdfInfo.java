package example.web.src.bean;

public class PdfInfo {
	
	private String pdfName;
	private String pdfPath;
	private int pdfPage;
	public PdfInfo(String pdfName, String pdfPath, int pdfPage) {
		super();
		this.pdfName = pdfName;
		this.pdfPath = pdfPath;
		this.pdfPage = pdfPage;
	}
	
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public int getPdfPage() {
		return pdfPage;
	}
	public void setPdfPage(int pdfPage) {
		this.pdfPage = pdfPage;
	}

	
	
}
