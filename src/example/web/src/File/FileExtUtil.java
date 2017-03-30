package example.web.src.File;

import java.io.IOException;
import java.util.HashMap;

public class FileExtUtil {
	
	public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

	static {
	// images
	mFileTypes.put("FFD8FF", "jpg");
	mFileTypes.put("89504E47", "png");
	mFileTypes.put("47494638", "gif");
	mFileTypes.put("424D", "bmp");
	mFileTypes.put("7B5C727466", "rtf"); // 日记本
	mFileTypes.put("D0CF11E0", "office03");
//	mFileTypes.put("255044462D312E", "pdf");
	mFileTypes.put("504B0304", "office07-10");
	};
	public static String getExt(byte[] bt) throws IOException {
		byte[] b = new byte[4];
		for(int i=0;i<4;i++){
			b[i]=bt[i];
		}	
		return containFind(bytesToHexString(b));
	}
	
	private static String bytesToHexString(byte[] src) {  
        StringBuilder builder = new StringBuilder();  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        String hv;  
        for (int i = 0; i < src.length; i++) {  
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写  
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();  
            if (hv.length() < 2) {  
                builder.append(0);  
            }  
            builder.append(hv);  
        }  
        return builder.toString();  
    }
	private static String containFind(String Hex) {
		if(mFileTypes.containsKey(Hex)){
			return mFileTypes.get(Hex).toString();
		}
		return null;
	}
}