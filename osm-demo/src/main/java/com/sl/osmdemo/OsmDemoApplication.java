package com.sl.osmdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OsmDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsmDemoApplication.class, args);
        System.out.println("OSM");
    }

}
