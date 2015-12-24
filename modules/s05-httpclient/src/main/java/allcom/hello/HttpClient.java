package allcom.hello;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClient {

    /**
     * httppost data xml
     * @param url
     * @param xml
     * @return
     */
    public static CloseableHttpResponse postXml(String url,String xml,Map<String, String> map){
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置传输和请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(10000).build();
        httpPost.setConfig(requestConfig);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(xml);
            httpPost.setEntity(stringEntity);
            for (Map.Entry<String, String> map_f : map.entrySet()) {
                httpPost.setHeader(map_f.getKey(), map_f.getValue());
            }
            System.out.println("正在请求："+httpPost.getURI());
            response = httpClient.execute(httpPost);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接,释放资源
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static String getContentByPostXml(String url,String xml,Map<String, String> map){
        String ret = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置传输和请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(10000).build();
        httpPost.setConfig(requestConfig);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(xml);
            httpPost.setEntity(stringEntity);
            for (Map.Entry<String, String> map_f : map.entrySet()) {
                httpPost.setHeader(map_f.getKey(), map_f.getValue());
            }
            System.out.println("正在请求："+httpPost.getURI());
            response = httpClient.execute(httpPost);
            if(getStatusCode(response)== HttpStatus.SC_OK) {
                ret = EntityUtils.toString(response.getEntity());
            }else{
                ret = "getStatusCode:"+getStatusCode(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接,释放资源
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static CloseableHttpResponse post(String url,Map<String, String> map,Map<String, String> headermap){
        CloseableHttpResponse response = null;
        //创建默认的httpclient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建httppost
        HttpPost httppost = new HttpPost(url);
        //设置传输和请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(10000).build();
        httppost.setConfig(requestConfig);

        //创建参数队列
        List<BasicNameValuePair> formparams = null;
        UrlEncodedFormEntity uefe = null;
        try {
            if(map.size()>0){
                formparams = new ArrayList<BasicNameValuePair>();
                //拼装参数
                for (Map.Entry<String, String> entry:map.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                //引入参数
                uefe = new UrlEncodedFormEntity(formparams,"UTF-8");
                httppost.setEntity(uefe);
            }
            for (Map.Entry<String, String> map_f : map.entrySet()) {
                httppost.setHeader(map_f.getKey(), map_f.getValue());
            }
            System.out.println("正在请求："+httppost.getURI());
            //执行POST请求
            response = httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭连接,释放资源
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }


    public static String getContentByPost(String url,Map<String, String> map,Map<String, String> headermap){
        String ret = "";
        CloseableHttpResponse response = null;
        //创建默认的httpclient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建httppost
        HttpPost httppost = new HttpPost(url);
        //设置传输和请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(10000).build();
        httppost.setConfig(requestConfig);

        //创建参数队列
        List<BasicNameValuePair> formparams = null;
        UrlEncodedFormEntity uefe = null;
        try {
            if(map.size()>0){
                formparams = new ArrayList<BasicNameValuePair>();
                //拼装参数
                for (Map.Entry<String, String> entry:map.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                //引入参数
                uefe = new UrlEncodedFormEntity(formparams,"UTF-8");
                httppost.setEntity(uefe);
            }
            for (Map.Entry<String, String> map_f : map.entrySet()) {
                httppost.setHeader(map_f.getKey(), map_f.getValue());
            }
            System.out.println("正在请求："+httppost.getURI());
            //执行POST请求
            response = httpclient.execute(httppost);
            if(getStatusCode(response)== HttpStatus.SC_OK) {
                ret = EntityUtils.toString(response.getEntity());
            }else{
                ret = "getStatusCode:"+getStatusCode(response);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭连接,释放资源
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    //httpclient get
    public static CloseableHttpResponse get(String url){
        CloseableHttpResponse response = null;
        //创建默认的httpclient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //创建httpget
            HttpGet httpget = new HttpGet(url);
            //设置传输和请求超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(10000).build();
            httpget.setConfig(requestConfig);

            System.out.println("正在请求："+httpget.getURI());
            //执行get请求
            response = httpclient.execute(httpget);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接,释放资源
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    //httpclient get（返回页面内容）
    public static String getContentByGet(String url){
        String ret = "";
        CloseableHttpResponse response = null;
        //创建默认的httpclient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //创建httpget
            HttpGet httpget = new HttpGet(url);
            //设置传输和请求超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(10000).build();
            httpget.setConfig(requestConfig);

            System.out.println("正在请求："+httpget.getURI());
            //执行get请求
            response = httpclient.execute(httpget);
            if(getStatusCode(response)== HttpStatus.SC_OK) {
                ret = EntityUtils.toString(response.getEntity());
            }else{
                ret = "getStatusCode:"+getStatusCode(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接,释放资源
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    //获取页面头信息
    public static Map<String, String> getHreader(CloseableHttpResponse response,String headerkey){
        Map<String, String> map = new HashMap<String, String>();
        Header[] header = response.getHeaders(headerkey);
        if(header.length>0){
            HeaderElement[] headerElement = header[0].getElements();
            for (HeaderElement headerElement2 : headerElement) {
                map.put(headerElement2.getName(), headerElement2.getValue());
            }
        }
        return map;
    }

    //获取请求状态
    public static int getStatusCode(CloseableHttpResponse closeableHttpResponse){
        return closeableHttpResponse.getStatusLine().getStatusCode();
    }

    //获取页面内容(不能在关闭连接后调用，可能会获取不到，放到连接关闭前)
//    public static String getEntitycontent(CloseableHttpResponse closeableHttpResponse) throws ParseException, IOException{
//        return EntityUtils.toString(closeableHttpResponse.getEntity());
//    }

}