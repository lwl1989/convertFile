package convert;

import conf.Configuration;
import util.ConfUtil;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/*interface ConvertList extends Remote{
	public void addQuery(FileInfo fileInfo) throws RemoteException;
}*/
public class ConvertFile {
	public static String RMI_PORT;
	public static String RMI_URI;
	public static String QUERY_NAME ;
	public static  String RMI_URL;
	public void init(Configuration conf){
		RMI_PORT = ConfUtil.conf.getConfig("RMI_PORT");
		RMI_URI = ConfUtil.conf.getConfig("RMI_URI");
		QUERY_NAME = ConfUtil.conf.getConfig("QUERY_NAME");
		RMI_URL = RMI_URI+":"+RMI_PORT+"/"+QUERY_NAME;
	}
	public Configuration getConf(String confPath){
			return new Configuration(confPath);
	}
	public Configuration getConf(){
			return new Configuration();
	}
	/*public static void main(String[] args){
		ConvertFile convertFile=new ConvertFile();
		Configuration conf = convertFile.getConf();
		convertFile.init(conf);
		try{
			RmiQuery rmi=new RmiQuery();
			LocateRegistry.createRegistry(Integer.parseInt(RMI_PORT));
			System.out.println(RMI_URL);
			Naming.rebind(RMI_URL, rmi);
			System.out.println("server ready success!");
			rmi.doConvert(conf);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/

	
}
