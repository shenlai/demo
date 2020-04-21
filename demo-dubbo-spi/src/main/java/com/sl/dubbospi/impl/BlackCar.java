package com.sl.dubbospi.impl;

import com.sl.dubbospi.api.Car;
import org.apache.dubbo.common.Extension;
import org.apache.dubbo.common.URL;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
@Extension("bbb")
public class BlackCar implements Car {

    public void getColor() {
        System.out.println("black");
    }

    @Override
    public void getColorForUrl(URL url) {
        System.out.println("blackUrl");
    }
}
