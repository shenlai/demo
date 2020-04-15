package com.sl.luban.aop.annoations;

@Entity(value = "city")
public class CityModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
