public class User {
    String name;
    Integer age;

    User(){}

    User(String name, int age){
        this.name = name;
        this.age = age;
    }

//    @Override
//    public int hashCode(){
//        return age;
//    }

    // hashCode 为何需要和equals方法同时存在且为何是这么写，参见印象笔记
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (age == null ? 0 : age.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        boolean ret = false;
        if(obj instanceof User) {
            if (this.name.equals(((User)obj).name) && this.age == ((User)obj).age) {
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("name:").append(name).append("/");
        sb.append("age:").append(age);
        return sb.toString();
    }

    public String getName(){
        return this.name;
    }
}
