package convert;
import conf.Configuration;
import util.ConfUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

  
public class PDF2SWFUtil {

    private static String SINGLEDOC;
    private static String LITPAGES;
    private static String TRACTTEXT;

    public static void init(Configuration conf) {
        SINGLEDOC = conf.getConfig("SINGLEDOC");
        SINGLEDOC = conf.getConfig("LITPAGES");
        SINGLEDOC = conf.getConfig("TRACTTEXT");
    }

    public static synchronized String pdf2swf(String fileDir,Configuration conf) throws IOException {

        init(conf);

        String filePath = fileDir.substring(0, fileDir.lastIndexOf("/"));
        String fileName = fileDir.substring((filePath.length() + 1), fileDir.lastIndexOf("."));
        String swfPath =filePath +"/" + fileName + ".swf";
        filePath =filePath +"/" + fileName + ".pdf";
        Process pro = null;

        String cmd = getCmd();
        cmd = cmd.replaceAll("path.pdf", filePath);
        cmd = cmd.replaceAll("path.swf", swfPath);
        pro = Runtime.getRuntime().exec(cmd);
        new DoOutput(pro.getInputStream()).start();
        new DoOutput(pro.getErrorStream()).start();
        try {
             pro.waitFor();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }finally{
        	pro.destroy();
        }
        return swfPath;
    }

    private static String getCmd(){
        return SINGLEDOC;
    }
      

    private static class DoOutput extends Thread {
        public InputStream is;
       

         public DoOutput(InputStream is) {
            this.is = is;
        }
       
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
            @SuppressWarnings("unused")
			String str = null;
            try {
                  while ((str = br.readLine()) != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
      
   
    /*public static void main(String[] args) {
        //杞崲鍣ㄥ畨瑁呰矾寰?
        String s ="C:\\SWFTools\\pdf2swf.exe path.pdf -o path.swf -f -T 9 -t -s storeallcharacters";
        String s1="E:\\tomcat8\\me-webapps\\FileUpload\\uploads";
        s1=s1.replaceAll("\\\\", "/");
        System.err.println(s1);
        System.out.println(s.replaceAll("path.pdf", s1));
    }*/
}