#convert file
###这个小插件是用来进行将office系列文件转换为flash以便于在浏览器上进行观看，在HTML5还未普及时，网页预览文件多以FLASH为主。###




###配置文件说明###

####openoffice####
```
PARAMETER_OFFICE_PORT = 8100  //openoffice转换文件端口
PARAMETER_OFFICE_HOME = /opt/openoffice4   //openoffice主目录
DO_CONVERT_SUBFIX = .doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt   //需要转换的文件，以逗号分割
```

####swftools####
```
SINGLEDOC = /usr/local/swf/bin/pdf2swf path.pdf -o path.swf -f -T 9 -t -s storeallcharacters      //swf执行命令   修改路径就行
```

####http proxy####
```
//用于HTTP通知其他客户端的转发器，默认关闭
PROXY_HOST = 127.0.0.1
PROXY_PORT =  8087
```
####需要通知的http客户端####
```
SINGE_URL = http://local.edusys.com/api/document/convert
```

####RMI协议####
```
RMI_PORT = 12355   //RMI协议的段口号
RMI_URI = rmi://127.0.0.1   //rmi协议的IP 
QUERY_NAME = FileAdd     //协议执行名
```

####其他配置（SQL功能暂时没开启，暂时不更改）####
###阿里百川提供的免费云
``` 
//阿里百川提供的免费云媒体，  AK  SK  和命名空间
WanpituAk = *     
WanpituSk = *
WanpituNamespace = *


#set sql info
SQL_DATABASE =
SQL_USERNAME =
SQL_PASSWORD =
```



###使用方式###
```
//server:
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

//web client:
//当上传文件后：
//FileInfo 这个JAVABEAN必须和服务器端一致
//且服务器和客户端拥有共同一致的接口   ConvertList
FileInfo info = new FileInfo(userId, filePath, Integer.parseInt(fileId));
// RMI写入本地转换进程
InsertFileList(info);

private void InsertFileList(FileInfo fileInfo) {
		try {
			String rmiUrl = "rmi://127.0.0.1:13355/FileAdd";
			ConvertList list = (ConvertList) Naming.lookup(rmiUrl);
			list.addQuery(fileInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
```