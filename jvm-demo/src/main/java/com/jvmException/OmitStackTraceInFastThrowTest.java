package com.jvmException;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/3/11 19:52
 */
public class OmitStackTraceInFastThrowTest {

    private static String name;
    private static Integer age;


    public static void main(String[] args) {
        while (true) {
            try {
                name.length();
                int i = age;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
