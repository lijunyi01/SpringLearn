package allcom.hello;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

public class test {


    public static void main(String[] args) {
        String url = "http://10.196.37.48/Citrix/Store/resources/v2";
        Map<String, String> postHeaderMap = new HashMap<String, String>();
        postHeaderMap.put("Content-Type", "application/vnd.citrix.requesttoken+xml");
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            System.out.println("\r------------第一步-----------------------------");
            closeableHttpResponse = HttpClient.get(url);
            //获取请求状态
            int statusCode = HttpClient.getStatusCode(closeableHttpResponse);
            System.out.println("-----------------------------------------------");
            System.out.println("statusCode："+statusCode);
            System.out.println("-----------------------------------------------");

            //获取页面头信息
            Map<String, String> header_map = HttpClient.getHreader(closeableHttpResponse, "WWW-Authenticate");
            for (Map.Entry<String, String> map : header_map.entrySet()) {
                System.out.println(map.getKey()+":"+map.getValue());
            }
            System.out.println("-----------------------------------------------");

            String CitrixAuth_realm = header_map.get("CitrixAuth realm");
            String locations = header_map.get("locations");
            String serviceroot_hint = header_map.get("serviceroot-hint");
            if(statusCode!=401) return;
            if(CitrixAuth_realm==null||locations==null||serviceroot_hint==null) return;

            System.out.println("\r------------第二步-----------------------------");
            String xml_str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requesttoken xmlns=\"http://citrix.com/delivery-services/1-0/auth/requesttoken\"><for-service>"+CitrixAuth_realm+"</for-service><for-service-url>"+serviceroot_hint+"</for-service-url><reqtokentemplate /><requested-lifetime>0.08:00:00</requested-lifetime></requesttoken>";
            url = locations;
            url = url.replace("cpicctxdc01.cpic.local", "10.196.37.48");
            closeableHttpResponse = HttpClient.postXml(url, xml_str, postHeaderMap);
            //获取请求状态
            statusCode = HttpClient.getStatusCode(closeableHttpResponse);
            System.out.println("-----------------------------------------------");
            System.out.println("statusCode："+statusCode);
            System.out.println("-----------------------------------------------");
            //获取页面头信息
            header_map = HttpClient.getHreader(closeableHttpResponse, "WWW-Authenticate");
            for (Map.Entry<String, String> map : header_map.entrySet()) {
                System.out.println(map.getKey()+":"+map.getValue());
            }
            System.out.println("-----------------------------------------------");

            CitrixAuth_realm = header_map.get("CitrixAuth realm");
            locations = header_map.get("locations");
            serviceroot_hint = header_map.get("serviceroot-hint");
            if(statusCode!=401) return;
            if(CitrixAuth_realm==null||locations==null||serviceroot_hint==null) return;

            System.out.println("\r------------第三步-----------------------------");
            xml_str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requesttoken xmlns=\"http://citrix.com/delivery-services/1-0/auth/requesttoken\"><for-service>"+CitrixAuth_realm+"</for-service><for-service-url>"+serviceroot_hint+"</for-service-url><reqtokentemplate /><requested-lifetime>0.08:00:00</requested-lifetime></requesttoken>";
            url = locations;
            url = url.replace("cpicctxdc01.cpic.local", "10.196.37.48");
            closeableHttpResponse = HttpClient.postXml(url, xml_str, postHeaderMap);
            //获取请求状态
            statusCode = HttpClient.getStatusCode(closeableHttpResponse);
            System.out.println("-----------------------------------------------");
            System.out.println("statusCode："+statusCode);
            System.out.println("-----------------------------------------------");
            //获取页面内容
            String entitycontent = HttpClient.getEntitycontent(closeableHttpResponse);
            System.out.println("entitycontent："+entitycontent);
            System.out.println("-----------------------------------------------");
            locations = dom4j.dom4jvaluebyStringxml(entitycontent).get("ExplicitForms");
            System.out.println("location："+locations);
            System.out.println("-----------------------------------------------");

            System.out.println("\r------------第四步-----------------------------");
            //xml与第三步一样 不用拼 直接用
            //xml_str = "";
            url = locations;
            url = url.replace("cpicctxdc01.cpic.local", "10.196.37.48");
            postHeaderMap.put("Accept", "application/vnd.citrix.requesttokenresponse+xml, text/xml, application/vnd.citrix.authenticateresponse-1+xml");
            closeableHttpResponse = HttpClient.postXml(url, xml_str, postHeaderMap);
            //获取请求状态
            statusCode = HttpClient.getStatusCode(closeableHttpResponse);
            System.out.println("-----------------------------------------------");
            System.out.println("statusCode："+statusCode);
            System.out.println("-----------------------------------------------");
            //获取页面头信息
            header_map = HttpClient.getHreader(closeableHttpResponse, "Set-Cookie");
            for (Map.Entry<String, String> map : header_map.entrySet()) {
                System.out.println(map.getKey()+":"+map.getValue());
            }
            System.out.println("-----------------------------------------------");
            //获取页面信息
            entitycontent = HttpClient.getEntitycontent(closeableHttpResponse);
            System.out.println("entitycontent："+entitycontent);
            System.out.println("-----------------------------------------------");
            locations = "http://10.196.37.48" + dom4j.dom4jvaluebyStringxml(entitycontent).get("PostBack");
            System.out.println("location："+locations);
            System.out.println("-----------------------------------------------");

            System.out.println("\r------------第五步-----------------------------");
            String Cookie = "ASP.NET_SessionId=" + header_map.get("ASP.NET_SessionId");
            url = locations;
            postHeaderMap.put("Cookie", Cookie);
//			postHeaderMap.remove("Content-Type");
            postHeaderMap.put("Content-Type", "application/x-www-form-urlencoded");
            Map<String, String> map_5_canshu = new HashMap<String, String>();
            map_5_canshu.put("username", "test");
            map_5_canshu.put("password", "Cpic12341");
            map_5_canshu.put("loginBtn", "Log+On");
            map_5_canshu.put("StateContext", "");
            System.out.println(postHeaderMap);
            closeableHttpResponse = HttpClient.post(url, map_5_canshu, postHeaderMap);
            //获取请求状态
            statusCode = HttpClient.getStatusCode(closeableHttpResponse);
            System.out.println("-----------------------------------------------");
            System.out.println("statusCode："+statusCode);
            System.out.println("-----------------------------------------------");
            //获取页面信息
            entitycontent = HttpClient.getEntitycontent(closeableHttpResponse);
            System.out.println("entitycontent："+entitycontent);
            System.out.println("-----------------------------------------------");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
