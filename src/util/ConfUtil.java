package util;

import conf.Configuration;

/**
 * Created by li on 15-10-5.
 */
public class ConfUtil {
    public static Configuration conf;
    static {
        conf = new Configuration();
    }
    public static void getConfig(String configFilePath){
        conf = new Configuration(configFilePath);
    }
}
