package conf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Configuration{

    private String configFile = "";
    private Map<String,String> propertys = new HashMap<String,String>();

    public Configuration(){
        this(null);
    }

    public Configuration(String configFile) {
        if(null==configFile){
            configFile = "resource/default";
        }
        this.configFile = configFile;
        getAllMessage();
    }


    private   void getAllMessage()  {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(configFile+".properties"));
            prop.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }

        Set keyValue = prop.keySet();
        for (Iterator it = keyValue.iterator(); it.hasNext();)
        {
            String key = (String) it.next();
            String value = prop.getProperty(key);
            propertys.put(key,value);
        }
    }

    public String getConfig(String property){
        if(propertys.containsKey(property)){
            return propertys.get(property);
        }
        return null;
    }


}