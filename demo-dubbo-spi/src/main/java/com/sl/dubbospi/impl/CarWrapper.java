package com.sl.dubbospi.impl;

import com.sl.dubbospi.api.Car;
import org.apache.dubbo.common.URL;



public class CarWrapper implements Car {

    private Car car;

    public CarWrapper(Car car) {
        System.out.println("12");
        this.car = car;
    }

    @Override
    public void getColor() {
        car.getColor();
    }

    @Override
    public void getColorForUrl(URL url) {

    }
}
