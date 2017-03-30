package example.web.src.File;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import lib.sql.SqlOperate;

public class SqlOperateUtil {
	
	public static int insertDocument(FileUpdateInfo fileUpdateInfo,String userId){
		SqlOperate document=new SqlOperate();
		LinkedList<String> keys=new LinkedList<String>();
		keys.add("user_id");
		keys.add("title");
		keys.add("file_size");
		keys.add("file_type");
		keys.add("file_path");
		keys.add("ext");
		keys.add("upload_time");
		LinkedList<String> values=new LinkedList<String>();
		values.add(userId);
		values.add(fileUpdateInfo.getFileName());
		values.add(String.valueOf(fileUpdateInfo.getFileSize()));
		values.add(fileUpdateInfo.getFileExt());
		values.add(fileUpdateInfo.getFilePath());
		values.add(fileUpdateInfo.getFileSuffix());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		values.add(df.format(new Date()));
		return document.add("edu_db_document", keys, values);
	}
	
	public static int copyDocument(HashMap<String,String> info,String userId){
		SqlOperate document=new SqlOperate();
		LinkedList<String> keys=new LinkedList<String>();
		keys.add("title");
		keys.add("page");
		keys.add("file_size");
		keys.add("file_type");
		keys.add("file_path");
		keys.add("file_swf_path");
		keys.add("ext");
		keys.add("upload_time");
		keys.add("user_id");
		LinkedList<String> values=new LinkedList<String>();
		values.add(info.get("title"));
		values.add(info.get("page"));
		values.add(info.get("file_size"));
		values.add(info.get("file_type"));
		values.add(info.get("file_path"));
		values.add(info.get("file_swf_path"));
		values.add(info.get("exit"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		values.add(df.format(new Date()));
		values.add(userId);
		return document.add("edu_db_document",keys,values);
	}
	
	public static FileUpdateInfo searchMd5(String Md5,String inputName,String userId){
		FileUpdateInfo fileInfo=null;
		SqlOperate document=new SqlOperate();
		document.joinSearch("edu_db_document","edu_db_document_md5","id", "document_id");
		HashMap<String,String> conditions = new HashMap<String,String>();
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("limit","1");
		conditions.put("md5", Md5);
		LinkedList<HashMap<String,String>> file=document.select("edu_db_document_md5", conditions, options);
		if(file.size()>0){
			HashMap<String,String> info=file.get(0);
			fileInfo = new FileUpdateInfo(
					info.get("title"),
					Long.parseLong(info.get("file_size")),
					info.get("file_type"),
					true,info.get("ext"),
					info.get("file_path"),
					inputName);
			SqlOperateUtil.copyDocument(info,userId);
		}
		return fileInfo;
	}
	
	public static int inserDocumentMd5(int fileId,String Md5){
		SqlOperate documentMd5=new SqlOperate();
		LinkedList<String> keys=new LinkedList<String>();
		LinkedList<String> values=new LinkedList<String>();
		keys.add("document_id");
		keys.add("md5");
		values.add(fileId+"");
		values.add(Md5);
		return documentMd5.add("edu_db_document_md5", keys, values);
	}
}
