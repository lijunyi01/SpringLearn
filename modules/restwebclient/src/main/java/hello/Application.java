package hello;

import org.springframework.web.client.RestTemplate;

public class Application {

    public static void main(String args[]) {

//        String amount = "12.0934";
//        if(amount.contains(".")){
//            String tmp[] = amount.split("\\.");
//            if(tmp[1].length()>2){
//                amount = tmp[0]+"."+tmp[1].substring(0,2);
//            }
//        }
//        System.out.print(amount);


        RestTemplate restTemplate = new RestTemplate();
//        Page page = restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", Page.class)
//        System.out.println("Name:    " + page.getName());
//        System.out.println("About:   " + page.getAbout());
//        System.out.println("Phone:   " + page.getPhone());
//        System.out.println("Website: " + page.getWebsite());

        Page2 page2 = restTemplate.getForObject("http://localhost:8080/aaa/greeting", Page2.class);
        System.out.println("Id: " + page2.getId());
        System.out.println("Content: " + page2.getContent());


    }

}
