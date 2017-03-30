package convert;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import bean.FileInfo;

import conf.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

import ali.wanpitu.WanpituUpload;

import com.lowagie.text.pdf.PdfReader;

public class RmiQuery extends UnicastRemoteObject implements ConvertList {
	/**
	 * 
	 */
	public RmiQuery() throws RemoteException {
		super();
	}

	public static LinkedBlockingQueue<FileInfo> fileInfos = new LinkedBlockingQueue<FileInfo>();
	public void addQuery(FileInfo fileInfo) throws RemoteException {
		try {
			fileInfos.put(fileInfo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doConvert(Configuration conf) {
		while (true) {
			FileInfo fileinfo;
			try {
				fileinfo = fileInfos.take();
				String outFile ="";
				if(getSuffix(fileinfo.getFilePathString()).equals(".pdf") ||
						getSuffix(fileinfo.getFilePathString()).equals("pdf")){
					outFile = fileinfo.getFilePathString();
				}else{
					outFile = OfficeConvertPdf.officeToPdf(fileinfo
							.getFilePathString(),conf);
					Thread.sleep(1000);
				}
				PdfReader pdfReader;
				try {
					pdfReader = new PdfReader(outFile);// 新建一个pdf对象
					int pdfPage = pdfReader.getNumberOfPages();// 获取pdf的页数
					String swfPath = PDF2SWFUtil.pdf2swf(outFile,conf); // 生成swf

					String uploadName = randomDocumentName();
					String docUpload = WanpituUpload.upload(
							fileinfo.getUserId(), uploadName
									+ getSuffix(fileinfo.getFilePathString()),
							fileinfo.getFilePathString());
					System.out.println(docUpload);
					String swfUpload = WanpituUpload.upload(
							fileinfo.getUserId(), uploadName + ".swf", swfPath);
					System.out.println(swfUpload);
					// 解析json
					HashMap<String, String> docinfo = jsonToMap(docUpload);
					HashMap<String, String> swfinfo = jsonToMap(swfUpload);
					
					
					String url = "http://local.edusys.com/api/document/convert";
					StringBuffer params = new StringBuffer();
			        // 表单参数与get形式一样
			        params.append("document_id").append("=").append(fileinfo.getFileId()).append("&")
			              .append("docpath").append("=").append(docinfo.get("url")).append("&")
			              .append("type").append("=").append(docinfo.get("mimeType")).append("&")
			              .append("swfpath").append("=").append(swfinfo.get("url")).append("&")
			              .append("size").append("=").append(docinfo.get("fileSize")).append("&")
			              .append("page").append("=").append(pdfPage);
			        String sr = HttpRequestUtil.sendPost(conf.getConfig("SINGE_URL"), params.toString(), false);
					System.out.println(sr);
					pdfReader.close();
				} catch (IOException e) {
					
				} catch (JSONException e) {
					
				}
			} catch (InterruptedException e) {
				
			}finally{
				
			}

		}
	}

	public static HashMap<String, String> jsonToMap(String json)
			throws JSONException {
		HashMap<String, String> result = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(json);
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	public static String randomDocumentName() {
		return UUID.randomUUID().toString();
	}

	public static String getSuffix(String name) {
		String extName = "";
		if (name.lastIndexOf(".") >= 0) {
			extName = name.substring(name.lastIndexOf("."));
		}
		return extName;
	}

	public static void deleteFile(String sPath) {
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}
	
	public static void main(String[] args) throws JSONException, UnsupportedEncodingException {
		/*String a="{\"dir\":\"/5\",\"fileModified\":1439463431036,\"fileSize\":153088,\"mimeType\":\"application/msword\",\"name\":\"42898d06-43a1-453e-928b-fdd4b57d3e37.doc\",\"url\":\"http://xueba.file.alimmdn.com/5/42898d06-43a1-453e-928b-fdd4b57d3e37.doc\"}";
		String b="{\"dir\":\"/5\",\"fileModified\":1439463431036,\"fileSize\":153088,\"mimeType\":\"application/msword\",\"name\":\"42898d06-43a1-453e-928b-fdd4b57d3e37.doc\",\"url\":\"http://xueba.file.alimmdn.com/5/42898d06-43a1-453e-928b-fdd4b57d3e37.doc\"}";
		HashMap<String, String> docinfo = jsonToMap(a);
		HashMap<String, String> swfinfo = jsonToMap(b);
		String url = "http://local.edusys.com/api/document/convert";
		//String para = "document_id="+6+"&docpath=";//+
				//docinfo.get("url")+"&type="+docinfo.get("mimeType")+
				//"&swfpath="+swfinfo.get("url")+"&page="+7;
	
		String sr = HttpRequestUtil.sendPost(url, params.toString(), false);
		System.out.println(sr);*/
		
	}
}
