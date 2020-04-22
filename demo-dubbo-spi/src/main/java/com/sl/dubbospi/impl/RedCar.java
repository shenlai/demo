package com.sl.dubbospi.impl;

import com.sl.dubbospi.api.Car;
import org.apache.dubbo.common.URL;


public class RedCar implements Car {

    public void getColor() {
        System.out.println("red");
    }

    @Override
    public void getColorForUrl(URL url) {

    }
}
