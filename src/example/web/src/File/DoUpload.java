package example.web.src.File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import convert.ConvertList;
import convert.FileInfo;

public class DoUpload {
	private HttpServletRequest request;
	private String path;
	private DiskFileItemFactory factory = new DiskFileItemFactory();
	private ServletFileUpload upload;
	private List<FileItem> list;
	private String userId;
	private String fileId;
	
	public DoUpload(HttpServletRequest request, String path, int sizeThreshold) {
		this.request = request;
		

		this.path = path;
		getFileList(sizeThreshold);
	}

	public DoUpload() {
	}

	private void getFileList(int sizeThreshold) {
		factory.setRepository(new File(path));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(sizeThreshold);
		upload = new ServletFileUpload(factory);
		try {
			list = (List<FileItem>) upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	public String uploadFile() {
		if (list.isEmpty()  || list.size() < 1) {
			return JsonReturn(false, null, "没有文件上传");
		}
		OutputStream out = null;
		InputStream in = null;
		try {
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				if(name.equals("user_id")){
					userId = item.getString();
				}
				if(name.equals("file_id")){
					fileId = item.getString();
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				if (!item.isFormField()) {
					// 获取路径名
					String value = item.getName();
					int start = value.lastIndexOf("\\");
					if(start<2){
						start = value.lastIndexOf("/");
					}
					String filename = value.substring(start+1);
					System.out.println(filename);
					String filePath=path+"/"+filename;
					// 手动写的
					out = new FileOutputStream(new File(filePath));
					in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					length = in.read(buf);
					out.write(buf, 0, length);
					while ((length = in.read(buf)) != -1) { // 在 buf 数组中 取出数据 写到								// （输出流）磁盘上
						out.write(buf, 0, length);
					}
					filePath = filePath.replaceAll("\\\\", "/");

					FileInfo info = new FileInfo(userId, filePath, Integer.parseInt(fileId));
					// RMI写入本地转换进程
					InsertFileList(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				list.remove(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return JsonReturn(true, null, "结束");
	}

	public void doMakeDir() {
		File file = new File(path);
		// System.out.println(file.getAbsolutePath());
		if (!file.exists()) {
			file.mkdir();
		}
	}

	private String JsonReturn(boolean status, ArrayList<FileUpdateInfo> files,
			String msg) {
		ReturnJson json = new ReturnJson(status, files, msg);
		return JSONObject.fromObject(json).toString();
	}

	private void InsertFileList(FileInfo fileInfo) {
		try {
			String rmiUrl = "rmi://127.0.0.1:13355/FileAdd";
			ConvertList list = (ConvertList) Naming.lookup(rmiUrl);
			list.addQuery(fileInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String sPath) {
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

}
