package ali.wanpitu;

import com.alibaba.fastjson.JSON;
import com.alibaba.media.MediaConfiguration;
import com.alibaba.media.Result;
import com.alibaba.media.upload.*;
import com.alibaba.media.upload.impl.DefaultUploadClient;
import com.alibaba.media.upload.impl.DefaultUploadTokenClient;
import conf.Configuration;
import util.ConfUtil;

import java.io.File;

/**
 * @author hyzhou.zhy 2015.03.05
 *         这个Demo展示了 UploadClient 的接口使用方法
 *         Media SDK 为这个接口提供了一个默认实现 DefaultUploadClient
 *         <p/>
 *         这个Demo展示了 UploadTokenClient 的接口使用方法
 *         Media SDK 为这个接口提供了一个默认实现 DefaultUploadTokenClient
 */
public class WanpituUpload  {

  //  private static final Integer PART_SIZE = 100 * 1024;

    private static final UploadClient client;

    private static String TOKEN;


    static {
        /**
         * 初始化 MediaConfiguration
         * 配置图片服务的AK:           AccessKey、AccessKeySecret
         * 配置图片服务的namespace:
         */
        MediaConfiguration configuration = new MediaConfiguration();
        configuration.setType(MediaConfiguration.TYPE_TOP);
        configuration.setAk(ConfUtil.conf.getConfig("WanpituAk"));
        configuration.setSk(ConfUtil.conf.getConfig("WanpituSk"));
        configuration.setNamespace(ConfUtil.conf.getConfig("WanpituNamespace"));
        UploadTokenClient tokenClient = new DefaultUploadTokenClient(configuration);

        /**
         * 为用户指定上传策略
         * 下面的上传策略指定了:
         *      用户上传文件允许覆盖
         *      该用户凭证的失效时间为当前时间之后的一个小时, 在之后的一个小时之内Token都可以作为用户的上传凭证
         *      失效时间设置为-1时表示永不失效
         */
        UploadPolicy uploadPolicy = new UploadPolicy();
        uploadPolicy.setInsertOnly(UploadPolicy.INSERT_ONLY_NONE);
        uploadPolicy.setExpiration(System.currentTimeMillis() + 3600 * 1000);

        /**
         * 请求Token服务,为该用户申请该上传策略对应的Token
         */
        TOKEN = tokenClient.getUploadToken(uploadPolicy);

        /**
         * 初始化 UploadClient
         */
        client = new DefaultUploadClient();
    }

    public static String upload(String userId,String fileName,String filePath){
        UploadRequest uploadRequest = new UploadRequest(TOKEN);
        uploadRequest.setFile(new File(filePath));
        uploadRequest.setDir(userId);
        uploadRequest.setName(fileName);
        Result<UploadResponse> result = client.upload(uploadRequest);
        if (result.isSuccess()) {
            // 调用接口成功,打印出上传接口的返回信息
        	return JSON.toJSONString(result.getData());
        } else {
            // 调用接口失败,输出错误信息便于排查问题
            return JSON.toJSONString(result);
        }
    }


}