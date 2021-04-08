package com.sl.opcv.imgQuality;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_16U;


/**
 * @author shenlai
 * @Description TODO
 * @create 2021/3/4 11:04
 */
public class ImageQuality {


    /**
     * https://www.cnblogs.com/niulang/p/11578328.html
     * Laplacian梯度方法
     */
    public static void Laplacian() {

        String path = "D:\\Users\\xiancheng\\Desktop\\pic\\downLoad";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        List<Double> percent750 = new ArrayList<>();
        List<Double> percent450 = new ArrayList<>();
        List<Temp> scores = new ArrayList<>();
        //Double dtest1 = Double.valueOf(String.format("%.2f", test1 ));
        for (File jpegFile : fs) {

            opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());
            //高斯去
            //opencv_imgproc.GaussianBlur(srcImage, img2, new opencv_core.Size(3, 3), 10, 10,0);

            //resize
            //opencv_core.Mat img2 = cv2.resize(img,(int(width*1.0),int(height*1.0)),interpolation=cv2.INTER_CUBIC)
            opencv_core.Mat img2 = new opencv_core.Mat();
            opencv_imgproc.resize(srcImage, img2, new opencv_core.Size(450, 450));
            //在gray目录下生成resize图片
            opencv_imgcodecs.imwrite(path + "\\gray\\" + jpegFile.getName(), img2);

            opencv_core.Mat imageGrey = new opencv_core.Mat();

            //转化为灰度图
            opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.CV_RGB2GRAY);
            opencv_core.Mat imageLaplacian = new opencv_core.Mat();
            //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
            opencv_imgproc.Laplacian(img2, imageLaplacian, CV_16U);
            double meanValue = 0.0;
            //meanValue = mean(imageSobel)[0];
            meanValue = opencv_core.mean(imageLaplacian).blue();
            System.out.println(jpegFile.getName() + "     size:" + srcImage.cols() + "*" + srcImage.rows() + "    清晰度检测(Laplacian Method):" + meanValue);


        }
    }


    /**
     * https://www.cnblogs.com/niulang/p/11578328.html
     * Laplacian梯度方法
     */
    public static void LaplacianV2() {
        String path = "D:\\Users\\xiancheng\\Desktop\\pic\\downLoad1";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        for (File jpegFile : fs) {

            opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());
            //resize
            opencv_core.Mat img2 = new opencv_core.Mat();
            opencv_imgproc.resize(srcImage, img2, new opencv_core.Size(450, 450));

            opencv_core.Mat imageGrey = new opencv_core.Mat();
            //转化为灰度图
            opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.CV_RGB2GRAY);
            opencv_core.Mat imageLaplacian = new opencv_core.Mat();

            //梯度函数
            opencv_imgproc.Laplacian(img2, imageLaplacian, CV_16U);
            double meanValue = 0.0;
            meanValue = opencv_core.mean(imageLaplacian).blue();

            System.out.println(jpegFile.getName() + "     size:" + img2.cols() + "*" + img2.rows() + "  " +
                    "  清晰度检测(Laplacian Method):" + meanValue);
        }
    }


    private static class Temp {
        String name;
        Double score;
    }
}
