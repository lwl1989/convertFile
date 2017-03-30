package example;

import conf.Configuration;
import convert.ConvertFile;
import convert.RmiQuery;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by li on 15-10-5.
 */
public class Do_Convert {
    public static void main(String[] ars) throws Exception{
        ConvertFile convertFile=new ConvertFile();
        Configuration conf = convertFile.getConf();
        convertFile.init(conf);
        try{
            RmiQuery rmi=new RmiQuery();
            LocateRegistry.createRegistry(Integer.parseInt(ConvertFile.RMI_PORT));
            System.out.println(ConvertFile.RMI_URL);
            Naming.rebind(ConvertFile.RMI_URL, rmi);
            System.out.println("server ready success!");
            rmi.doConvert(conf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
