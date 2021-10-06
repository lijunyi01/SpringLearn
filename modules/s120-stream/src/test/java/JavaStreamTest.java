import org.junit.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaStreamTest {

    @Test
    public void streamTest1(){
        System.out.println("------filter demo-----------");
        List<String> strings = Arrays.asList("abc","","bc","efg","tykasdf","","jkl");
        List<String> filtered = strings.stream().filter(str -> str.contains("b")).collect(Collectors.toList());
        System.out.println(filtered);

        System.out.println("------distinct demo-----------");
        List<String> distincted= strings.stream().distinct().collect(Collectors.toList());
        System.out.println(distincted);

        System.out.println("------limit demo-----------");
        // limit(n)  获取流中的前n项（组成新的流）
        List<String> limited= strings.stream().limit(3).collect(Collectors.toList());
        System.out.println(limited);

        System.out.println("------skip demo-----------");
        // skip(n)  抛弃流中的前n项（组成新的流）
        List<String> skipped= strings.stream().skip(3).collect(Collectors.toList());
        System.out.println(skipped);
    }

    @Test
    public void streamTest2(){
        System.out.println("------map demo-----------");
        List<String> strings = Arrays.asList("abc","","bc","efg","tykasdf","","jkl");
        List<String> mapped = strings.stream().map(str -> str+"-tail").collect(Collectors.toList());
        System.out.println(mapped);

        System.out.println("------map / flatmap demo-----------");
        // map后: [abc],[],[bc],[efg]...[jkl]
        // Stream<Stream<Character>> streamStream = strings.stream().map(str -> getCharacterStream(str));
        // 以上写法等效于：
        Stream<Stream<Character>> streamStream = strings.stream().map(this::getCharacterStream);
        streamStream.forEach(s-> System.out.println(s));
        // flatmap后-1: [abc],[],[bc],[efg]...[jkl]
        // flatmap后-2: [abcbcefgtykasdfjkl]
        Stream<Character> characterStream = strings.stream().flatMap(this::getCharacterStream);
        characterStream.forEach(System.out::print);

    }
    private Stream<Character> getCharacterStream(String str){
        List<Character> characters = new ArrayList<>();
        for(Character c: str.toCharArray()){
            characters.add(c);
        }
        return characters.stream();
    }

    @Test
    public void distinctUser(){
        System.out.println("------distinct User demo-----------");
        List<User> users = new ArrayList<>();
        users.add(new User("Jason",45));
        users.add(new User("James",15));
        users.add(new User("George",8));
        users.add(new User("George",8));
        // 对象比较的是地址，无法去重，除非对象本身同时重载hashCode方法和equals方法
        List<User> filtered = users.stream().distinct().collect(Collectors.toList());
        System.out.println(filtered);
    }

    @Test
    public void streamTest3(){
        System.out.println("------sort demo-----------");
        List<String> strings = Arrays.asList("abc","","bc","efg","tykasdf","","jkl");
        // 默认排序
        List<String> sortedAsc = strings.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedAsc);
        // 倒叙排序
        List<String> sortedDesc = strings.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        System.out.println(sortedDesc);
        // 中文排序
        List<String> strings2 = Arrays.asList("张三","李四","王五","李嘉皓","李嘉佑","汪昊","蒋蓉");
        // 实际这样排序是无效的
        List<String> sortedChinese = strings2.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedChinese);
        // 按拼音升序
        // public abstract class Collator implements java.util.Comparator<Object>, Cloneable
        List<String> sortedChinese2 = strings2.stream().sorted(Collator.getInstance(Locale.CHINESE)).collect(Collectors.toList());
        System.out.println(sortedChinese2);
        // 按拼音降序
        List<String> sortedChinese3 = strings2.stream().sorted(Collections.reverseOrder(Collator.getInstance(Locale.CHINESE))).collect(Collectors.toList());
        System.out.println(sortedChinese3);

        System.out.println("------sort demo about Object-----------");
        List<User> users = new ArrayList<>();
        users.add(new User("张三",45));
        users.add(new User("James",15));
        users.add(new User("George",8));
        users.add(new User("王五",8));
        users.add(new User("Alice",8));
        List<User> sortedUsers = users.stream()
                .sorted(Comparator.comparing(User::getName,Collator.getInstance(Locale.CHINESE)))
                .collect(Collectors.toList());
        System.out.println(sortedUsers);

        System.out.println("------sort demo about Customed Comparator-----------");
        List<User> sortedUsers2 = users.stream()
                // 自定义比较器（箭头函数）
                .sorted(Comparator.comparing(User::getName,(name1,name2)->{
                    List<String> targetSortedList = Arrays.asList("James","George","张三","王五","Alice");
                    int index1 = targetSortedList.indexOf(name1);
                    int index2 = targetSortedList.indexOf(name2);
                    return Integer.compare(index1, index2);
                    // 上句等效于下段：
//                    if(index1 > index2){
//                        return 1;    // 交换位置
//                    }else if(index1 == index2){
//                        return 0;
//                    }else{
//                        return -1;
//                    }
                }))
                .collect(Collectors.toList());
        System.out.println(sortedUsers2);

    }

    @Test
    public void streamTest4(){
        System.out.println("------findAny demo -----------");
        List<String> strings = Arrays.asList("abc","","bc","efg","tykasdf","","jkl");
        // stream() 是串行的，总会取到第一项，效果等同与findFirst()
        // Optional<String> any = strings.stream().findAny();
        Optional<String> any = strings.parallelStream().findAny();
        if(any.isPresent()){
            System.out.println(any.get());
        }else{
            System.out.println(any);
        }

        System.out.println("------forEach demo -----------");
        // 以下两种写法等效
        strings.stream().forEach(System.out::print);
        //strings.stream().forEach(s-> System.out.print(s));

        System.out.println("------count demo -----------");
        long count = strings.stream().count();
        System.out.println(count);

        System.out.println("------reduce demo -----------");
        Optional<String> reduced = strings.stream().reduce((result, item) -> result + item);
        if(reduced.isPresent()) {
            System.out.println(reduced.get());
        }



    }
}
