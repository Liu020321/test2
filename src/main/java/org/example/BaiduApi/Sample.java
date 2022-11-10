package org.example.BaiduApi;

import org.example.Baidu.Base64Util;
import org.example.Baidu.FileUtil;
import org.example.Baidu.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 身份证识别
 */
public class Sample {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */

    /**
     * 获取权限token
     * 
     * @return 返回示例：
     *         {
     *         "access_token":
     *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     *         "expires_in": 2592000
     *         }
     */

    private static String clientId;
    private static String clientSecret;
    private static String SECRET_KEY;

    static {
        // 读取baidu.properties文件的内容
        InputStream is = Sample.class.getClassLoader().getResourceAsStream("auth.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            clientId = p.getProperty("AK");
            clientSecret = p.getProperty("SK");
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    // public static String getAuth() {
    // // 官网获取的 API Key 更新为你注册的
    // String clientId = "百度云应用的AK";
    // // 官网获取的 Secret Key 更新为你注册的
    // String clientSecret = "百度云应用的SK";
    // return getAuth(clientId, clientSecret);
    // }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * 
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + clientId
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + clientSecret;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject();
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static String idcard(String name) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            // String filePath = "D://16475//Pictures//服务器转让//刘海涛反面.jpg";
            byte[] imgData = FileUtil.readFileByBytes(name);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.883b640cf4f1f9d1763171829268c50f.2592000.1670297962.282335-28273145";

            String result = HttpUtil.post(url, accessToken, param);

            JSONObject res = JSON.parseObject(result);

            JSONObject wr = res.getJSONObject("words_result");

            String user = "";

            user = (String) wr.getJSONObject("姓名").get("words");

            System.out.println(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String idcard1(String name) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            // String filePath = "D://16475//Pictures//服务器转让//刘海涛反面.jpg";
            byte[] imgData = FileUtil.readFileByBytes(name);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.883b640cf4f1f9d1763171829268c50f.2592000.1670297962.282335-28273145";

            String result = HttpUtil.post(url, accessToken, param);

            JSONObject res = JSON.parseObject(result);

            JSONObject wr = res.getJSONObject("words_result");

            String user = "";

            user = (String) wr.getJSONObject("性别").get("words");

            System.out.println(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String idcard2(String name) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            // String filePath = "D://16475//Pictures//服务器转让//刘海涛反面.jpg";
            byte[] imgData = FileUtil.readFileByBytes(name);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.883b640cf4f1f9d1763171829268c50f.2592000.1670297962.282335-28273145";

            String result = HttpUtil.post(url, accessToken, param);

            JSONObject res = JSON.parseObject(result);

            JSONObject wr = res.getJSONObject("words_result");

            String user = "";

            user = (String) wr.getJSONObject("民族").get("words");

            System.out.println(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String idcard3(String name) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            // String filePath = "D://16475//Pictures//服务器转让//刘海涛反面.jpg";
            byte[] imgData = FileUtil.readFileByBytes(name);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.883b640cf4f1f9d1763171829268c50f.2592000.1670297962.282335-28273145";

            String result = HttpUtil.post(url, accessToken, param);

            JSONObject res = JSON.parseObject(result);

            JSONObject wr = res.getJSONObject("words_result");

            String user = "";

            user = (String) wr.getJSONObject("住址").get("words");

            System.out.println(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String idcard4(String name) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            // String filePath = "D://16475//Pictures//服务器转让//刘海涛反面.jpg";
            byte[] imgData = FileUtil.readFileByBytes(name);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.883b640cf4f1f9d1763171829268c50f.2592000.1670297962.282335-28273145";

            String result = HttpUtil.post(url, accessToken, param);

            JSONObject res = JSON.parseObject(result);

            JSONObject wr = res.getJSONObject("words_result");

            String user = "";

            user = (String) wr.getJSONObject("公民身份号码").get("words");

            System.out.println(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}