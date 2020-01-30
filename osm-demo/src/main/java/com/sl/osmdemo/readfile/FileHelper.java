package com.sl.osmdemo.readfile;

import com.sl.osmdemo.entity.Photo;
import com.sl.osmdemo.mapper.PhotoMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: sl
 * @Date: 2020/1/6
 * @Description: TODO
 */
@Component
public class FileHelper {

    @Resource
    private PhotoMapper photoMapper;

    public String txt2String(File file) {
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

    public void txt2StringV2(File file, FileWriter fileWriter) throws IOException {
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


    public void txt2StringV3(File file, FileWriter fileWriter) throws IOException {
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

    public void txt2StringV4(File file) {
        try {
            // 创建BufferedReader，以gb2312的编码方式读取文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            //List<Photo> photoList = new ArrayList<>();
            System.out.println("开始执行：" + file.getPath());
            // 按行读取文本，直到末尾（一般都这么写）
            while ((line = reader.readLine()) != null) {
                line = replaceAllBlank(line);
                if (Strings.isNotEmpty(line)) {
                    if (line.contains("|false")) {
                        //TODO delete
                        continue;
                    }
                    if (line.contains("|true")) {
                        String[] strings = line.split("\\|");
                        if (strings.length == 4) {
                            //insert to db
                            Photo photo = new Photo();
                            photo.setPhotoId(Long.valueOf(strings[0]));
                            String url = strings[1];
                            url = url.replace("/picbak2/upload", "uploads");
                            photo.setUrl(url);
                            photo.setSize(strings[3]);
                            photoMapper.updatePhoto(photo);
                        }
                    }
                }
            }
            System.out.println("执行成功：" + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败：" + file.getPath());
        }
    }

    public void txt2Sql(File file) {
        try {
            // 创建BufferedReader，以gb2312的编码方式读取文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            //List<Photo> photoList = new ArrayList<>();
            System.out.println("开始执行：" + file.getPath());
            // 按行读取文本，直到末尾（一般都这么写）
            int i = 0;
            List<String> photoIds = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                line = replaceAllBlank(line);
                if (Strings.isNotEmpty(line)) {
                    photoIds.add(line);
                }

                if (photoIds.size() == 2000000) {
                    String fileName = "E:\\图片数据处理\\跑size文件\\delete_photo_id_insert_" + i + ".txt";
                    FileWriter fileWriter = new FileWriter(fileName);
                    String insertSql = "INSERT INTO photo_delete_id (photo_id) VALUES";
                    StringBuilder insertBuilder = new StringBuilder();
                    photoIds.forEach(x -> {
                        insertBuilder.append("(").append(x).append("),");
                    });
                    String insertValues = insertBuilder.toString();
                    insertValues = insertValues.substring(0, insertValues.length() - 1) + ";";
                    fileWriter.write(insertSql + insertValues + "\r\n");//写入 \r\n换行  字符串太长，丢失？？
                    fileWriter.flush();
                    fileWriter.close();
                    System.out.println("生成sql脚本：" + fileName);
                    photoIds.clear();
                    i++;
                }
            }

            //最后一批photoIds
            if (photoIds.size() > 0) {
                String fileName = "E:\\图片数据处理\\跑size文件\\delete_photo_id_insert_" + i + ".txt";
                String insertSql = "INSERT INTO photo_delete_id (photo_id) VALUES";
                FileWriter fileWriter = new FileWriter(fileName);
                StringBuilder insertBuilder = new StringBuilder();
                photoIds.forEach(x -> {
                    insertBuilder.append("(").append(x).append("),");
                });
                String insertValues = insertBuilder.toString();
                insertValues = insertValues.substring(0, insertValues.length() - 1) + ";";
                fileWriter.write(insertSql + insertValues + "\r\n");//写入 \r\n换行
                fileWriter.flush();
                fileWriter.close();
                photoIds.clear();
                System.out.println("生成sql脚本：" + fileName);
            }
            System.out.println("执行成功：" + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败：" + file.getPath());
        }
    }


    //去除所有空格
    public String replaceAllBlank(String str) {
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
        //new FileHelper().getDeletePhoto();

        //移除待删除数据
        //new FileHelper().getTargetPhotodata();


        File file = new File("E:\\图片数据处理\\跑size文件\\hotel_photo_deleteId.txt");
        new FileHelper().txt2Sql(file);
        System.out.println("执行完成：" + file.getPath());


    }


    private void getTargetPhotodata() {
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


    private void getDeletePhoto() {
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
