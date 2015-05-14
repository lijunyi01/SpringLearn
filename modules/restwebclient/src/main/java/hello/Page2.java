package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page2 {

    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
