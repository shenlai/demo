package com.sl.springlearning.reflection;

public class Person {

    public  String address;

    protected  int sex;

    private  String name;
    private  Integer age;

    public  Person(String name,Integer age){
        this.age = age;
        this.name = name;
    }

    public  Person(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //测试反射
    private  void handleAddress(){

    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
