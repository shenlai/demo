package com.sl.demofile;

import org.apache.logging.log4j.util.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: sl
 * @Date: 2020/1/6
 * @Description: TODO
 */
public class FileHelper {

    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void txt2StringV2(File file, FileWriter fileWriter) throws IOException {
        try {

            // 创建BufferedReader，以gb2312的编码方式读取文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            // 按行读取文本，直到末尾（一般都这么写）
            while ((line = reader.readLine()) != null) {
                // 打印当前行字符串
                line = replaceAllBlank(line);
                if (Strings.isNotEmpty(line)) {
                    if (line.contains("|false")) {
                        String[] strings = line.split("\\|");
                        if (strings.length > 0) {
                            fileWriter.write(strings[0] + "\r\n");//写入 \r\n换行
                            System.out.println(strings[0]);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        fileWriter.flush();
        fileWriter.close();
    }


    public static void txt2StringV3(File file, FileWriter fileWriter) throws IOException {
        try {
            // 创建BufferedReader，以gb2312的编码方式读取文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            // 按行读取文本，直到末尾（一般都这么写）
            while ((line = reader.readLine()) != null) {
                // 打印当前行字符串
                line = replaceAllBlank(line);
                if (Strings.isNotEmpty(line)) {
                    if (line.contains("|true")) {
                        String res = line.replace("|true", "");
                        fileWriter.write(res + "\r\n");//写入 \r\n换行
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        fileWriter.flush();
        fileWriter.close();
    }

    //去除所有空格
    public static String replaceAllBlank(String str) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
             /*\n 回车(\u000a)
             \t 水平制表符(\u0009)
             \s 空格(\u0008)
             \r 换行(\u000d)*/
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }

    public static void main(String[] args) {

        //File file = new File("E:\\图片数据处理\\404删除文件\\404删除文件\\0.txt");
        //System.out.println(txt2String(file));

        //取删除图片数据
        //getDeletePhoto();

        getTargetPhotodate();
    }



    private static void getTargetPhotodate() {
        List<File> files = new ArrayList<>();
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.0.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.1.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.2.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.3.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.4.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.5.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.6.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.7.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.8.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.9.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.10.log"));


        List<FileWriter> fileWriters = new ArrayList<>();
        try {
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_0.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_1.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_2.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_3.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_4.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_5.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_6.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_7.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_8.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_9.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo_10.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= 10; i++) {
            try {
                txt2StringV3(files.get(i), fileWriters.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("************读取" + i + "完成***************");
        }
    }


    private static void getDeletePhoto() {
        List<File> files = new ArrayList<>();
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.0.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.1.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.2.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.3.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.4.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.5.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.6.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.7.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.8.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.9.log"));
        files.add(new File("E:\\图片数据处理\\404删除文件\\404删除文件\\hotel_photo.10.log"));


        List<FileWriter> fileWriters = new ArrayList<>();
        try {
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_0.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_1.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_2.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_3.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_4.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_5.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_6.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_7.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_8.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_9.txt"));
            fileWriters.add(new FileWriter("E:\\图片数据处理\\404删除文件\\404删除文件\\deleteId_10.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= 10; i++) {
            try {
                txt2StringV2(files.get(i), fileWriters.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("************读取" + i + "完成***************");
        }
    }
}
