package com.sl.springbootdemo.lambda;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: sl
 * @Date: 2019/2/25
 * @Description: TODO
 */
@Data
@ToString
public class User {

    public User(String name, Integer age, Integer salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    private String name;

    private Integer age;

    private Integer salary;
}
