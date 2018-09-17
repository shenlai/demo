package com.sl.springlearning.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


@Component
public class CarFactoryBean implements FactoryBean<Car> {
    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Car getObject() throws Exception {
        return new Car();
    }
}
