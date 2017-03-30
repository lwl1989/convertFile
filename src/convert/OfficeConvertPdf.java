package convert;

import java.io.File;
import java.util.regex.Pattern;

import conf.Configuration;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import util.ConfUtil;


public class OfficeConvertPdf {
 	private  static OfficeManager officeManager;
	private static OfficeDocumentConverter documentConverter;

	public static String getOutputFilePath(String inputFilePath,Configuration conf) {
		String[] subfix= conf.getConfig("DO_CONVERT_SUBFIX").split(",");
		String outputFilePath=null;
		for (String string : subfix) {
			if(inputFilePath.endsWith(string)){
				outputFilePath = inputFilePath.replaceAll(string, ".pdf");
				break;
			}
		}
		return outputFilePath;
	}
	public static String officeToPdf(String inputFilepath, Configuration conf){
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		configuration.setPortNumber(Integer.parseInt(conf.getConfig("PARAMETER_OFFICE_PORT")));
		String officeHomeParam = conf.getConfig("PARAMETER_OFFICE_HOME");
		configuration.setOfficeHome(new File(officeHomeParam));		
		
		try {
			officeManager = configuration.buildOfficeManager();
			officeManager.start();
			documentConverter = new OfficeDocumentConverter(officeManager);
			File inputFile = new File(inputFilepath);
			String outputFilePath = getOutputFilePath(inputFilepath,conf);

			if(outputFilePath==null){
				throw new Exception("document can't do convert");
			}
			
			if (inputFile.exists()) {
				File outputFile = new File(outputFilePath);
				if(outputFile.exists()){
					throw new Exception("document is ready convert");
				}
				if (!outputFile.getParentFile().exists()) {
					outputFile.getParentFile().mkdirs();
				}
				documentConverter.convert(inputFile, outputFile);
				return outputFilePath;
			}		
		} catch (Exception e) {
			System.out.println("document server start error");
			return null;
		}finally{
			officeManager.stop();
		}	
		return null;
	}


}
