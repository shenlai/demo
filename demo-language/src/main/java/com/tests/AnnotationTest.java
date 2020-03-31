package com.tests;

import com.annotations.SlAnnotation;

public class AnnotationTest {

    @SlAnnotation(source = "test.com")
    public void execute() {
        System.out.println("do something~");
    }





}
