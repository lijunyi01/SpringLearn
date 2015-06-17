package allcom.oxmapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ljy on 15/6/17.
 * ok
 */
public class OXMExample {
    private static final String FILE_NAME = "simplebean.xml";
    private SimpleBean simpleBean;

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

//    object -> xml
    public void saveSimpleBean() throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(FILE_NAME);
            this.marshaller.marshal(simpleBean, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

//    xml - > object
    public void loadSimpleBean() throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(FILE_NAME);
            this.simpleBean
                    = (SimpleBean) this.unmarshaller.unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext appContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        OXMExample ex = (OXMExample) appContext.getBean("oxmExample");
        ex.go();
    }

    private void go() throws IOException {
        simpleBean = getSimpleBean();

        saveSimpleBean();
        loadSimpleBean();

        System.out.println("name: " + simpleBean.getName());
        System.out.println("job description: " + simpleBean.getJobDescription());
        System.out.println("age: " + simpleBean.getAge());
        System.out.println("executive: " + simpleBean.isExecutive());
    }


    private SimpleBean getSimpleBean() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setAge(35);
        simpleBean.setExecutive(false);
        simpleBean.setJobDescription("Janitor");
        simpleBean.setName("Mister Jones");

        return simpleBean;

    }
}
