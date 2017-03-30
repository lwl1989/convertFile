package example.web.src.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class FileMd5Util {
	public static String getFileMd5(String filePath) throws FileNotFoundException, IOException{
		
		return DigestUtils.md5Hex(new FileInputStream(new File(filePath)));
	}

}
