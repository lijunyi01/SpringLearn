package allcom.oxmapper;

/**
 * Created by ljy on 15/6/17.
 * ok
 */
public class SimpleBean {
    private int age;
    private boolean executive;
    private String jobDescription;
    private String name;

    public void setAge(int age){
        this.age = age;
    }

    public void setExecutive(boolean executive){
        this.executive = executive;
    }

    public void setJobDescription(String jobDescription){
        this.jobDescription = jobDescription;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return this.age;
    }

    public String getJobDescription(){
        return this.jobDescription;
    }

    public String getName(){
        return this.name;
    }

    public boolean isExecutive(){
        return this.executive;
    }
}

