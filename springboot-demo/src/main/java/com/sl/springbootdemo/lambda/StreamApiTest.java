package com.sl.springbootdemo.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: sl
 * @Date: 2019/2/22
 * @Description: TODO
 */
public class StreamApiTest {

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("temp1", 19, 1000),
                new User("temp2", 58, 2000),
                new User("temp3", 38, 3000),
                new User("temp4", 48, 4000)
        );

        List<User> collect = users.stream().filter(x -> x.getAge() < 20).collect(Collectors.toList());
        users.stream().filter(x -> x.getAge() < 20).forEach(x -> {
            x.setAge(x.getAge() + 1);
        });

        users.stream().forEach(x -> {
            if (x.getAge() < 20) {
                x.setAge(x.getAge() + 1);
            }
        });
        System.out.println(collect);

//        List<String> collect = users.stream().map(x -> x.getName()).collect(Collectors.toList());
//        Set<String> collect1 = users.stream().map(x -> x.getName()).collect(Collectors.toSet());
//        Map<Integer, String> collect2 = users.stream().collect(Collectors.toMap(x -> x.getAge(), x -> x.getName()));


//        System.out.println(collect);
//        System.out.println(collect1);
//        System.out.println(collect2);
    }


}
