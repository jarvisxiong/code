/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月18日 下午2:03:02
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.utils;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月18日 下午2:03:02
 * @version V1.0
 */
public class WechatAPIHander {

    /**
     * 获取token接口
     */
    private String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    /**
     * 拉微信用户信息接口
     */
    private String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}";
    /**
     * 主动推送信息接口(群发)
     */
    private String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token={0}";



    private HttpClient webClient;
    private Log log = LogFactory.getLog(getClass());
    public void initWebClient(String proxyHost, int proxyPort){
        this.initWebClient();
        if(webClient != null && !StringUtils.isEmpty(proxyHost)){
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            webClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
    }
    /**
     * @desc 初始化创建 WebClient
     */
    public void initWebClient() {
        log.info("initWebClient start....");
        try {
            PoolingClientConnectionManager tcm = new PoolingClientConnectionManager();
            tcm.setMaxTotal(10);
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };
            ctx.init(null, new X509TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, ssf);
            tcm.getSchemeRegistry().register(sch);
            webClient = new DefaultHttpClient(tcm);
        } catch (Exception ex) {
            log.error("initWebClient exception", ex);
        } finally {
            log.info("initWebClient end....");
        }
    }
    /**
     * @desc 获取授权token
     * @param appid
     * @param secret
     * @return
     */
    public String getAccessToken(String appid, String secret) {
        String accessToken = null;
        try {
            log.info("getAccessToken start.{appid=" + appid + ",secret:" + secret + "}");
            String url = MessageFormat.format(this.getTokenUrl, appid, secret);
            String response = executeHttpGet(url);
            JSONObject  jasonObject = JSONObject.parseObject(response);
            Map map = (Map)jasonObject;
            accessToken = (String) map.get("access_token");
           /* Object Object = JSONUtils.parse(response);

            accessToken = jsonObject.getString("access_token");*/
//            accessToken = JsonUtils.read(response, "access_token");
        } catch (Exception e) {
            log.error("get access toekn exception", e);
        }
        return accessToken;
    }
    /**
     * @desc 推送信息
     * @param token
     * @param msg
     * @return
     */
    public String sendMessage(String token,String msg){
        try{
            log.info("\n\nsendMessage start.token:"+token+",msg:"+msg);
            String url = MessageFormat.format(this.sendMsgUrl, token);
            HttpPost post = new HttpPost(url);
            ResponseHandler<?> responseHandler = new BasicResponseHandler();

            //这里必须是一个合法的json格式数据,每个字段的意义可以查看上面连接的说明,content后面的test是要发送给用户的数据,这里是群发给所有人
            msg = "{\"filter\":{\"is_to_all\":true},\"text\":{\"content\":\"test\"},\"msgtype\":\"text\"}\"";

            //设置发送消息的参数
            StringEntity entity = new StringEntity(msg);

            //解决中文乱码的问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            //发送请求
            String response = (String) this.webClient.execute(post, responseHandler);
            log.info("return response=====start======");
            log.info(response);
            log.info("return response=====end======");
            return response;

        }catch (Exception e) {
            log.error("get user info exception", e);
            return null;
        }
    }

    /**
     * @desc 发起HTTP GET请求返回数据
     * @param url
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String executeHttpGet(String url) throws IOException, ClientProtocolException {
        ResponseHandler<?> responseHandler = new BasicResponseHandler();
        String response = (String) this.webClient.execute(new HttpGet(url), responseHandler);
        log.info("return response=====start======");
        log.info(response);
        log.info("return response=====end======");
        return response;
    }

}
