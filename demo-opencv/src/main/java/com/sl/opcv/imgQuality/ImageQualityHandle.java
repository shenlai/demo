package com.sl.opcv.imgQuality;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_16U;



/**
 * @author shenlai
 * @Description TODO
 * @create 2021/3/4 11:04
 */
public class ImageQualityHandle {


    /**
     * 42.jpg矩阵的标准差： 48.558186182021075
     * <p>
     *     https://blog.csdn.net/lx83350475/article/details/84228922
     * javacv 检测图片清晰度
     * 标准差越大说明图像质量越好
     */
    public static void clarityException() {


        String path = "D:\\Users\\xiancheng\\Desktop\\pic";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        //File jpegFile = fs[0];

        File jpegFile = new File(path + "\\42.jpg");
        clarityException(jpegFile);

    }

    public static void clarityException(File jpegFile) {

        opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());
        //System.out.println(JSON.toJSONString(srcImage));

        opencv_core.Mat imageGrey = new opencv_core.Mat();
        //转化为灰度图
        opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.COLOR_BGR2GRAY);
        //在gray目录下生成灰度图片
        //opencv_imgcodecs.imwrite(path + "\\gray-" + jpegFile.getName(), imageGrey);

        opencv_core.Mat laplacianDstImage = new opencv_core.Mat();

        //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
        opencv_imgproc.Laplacian(imageGrey, laplacianDstImage, opencv_core.CV_64F);

        //在laplacian目录下生成经过拉普拉斯掩模做卷积运算的图片
        //opencv_imgcodecs.imwrite(path + "\\laplacian-" + jpegFile.getName(), laplacianDstImage);

        //矩阵标准差
        opencv_core.Mat stddev = new opencv_core.Mat();

        //求矩阵的均值与标准差
        opencv_core.meanStdDev(laplacianDstImage, new opencv_core.Mat(), stddev);
        // ((全部元素的平方)的和)的平方根
        // double norm = Core.norm(laplacianDstImage);
        // System.out.println("\n矩阵的均值：\n" + mean.dump());
        System.out.println(jpegFile.getName() + "矩阵的标准差：" + stddev.createIndexer().getDouble());
        // System.out.println(jpegFile.getName()+"平方根：\n" + norm);
    }


    /**
     * Laplacian梯度方法
     * https://blog.csdn.net/u011555996/article/details/103864766
     */
    public static void clarityExceptionV2() {

        List<String> paths = Arrays.asList(
                "D:\\Users\\xiancheng\\Desktop\\pic\\test1\\01.jpg",
                "D:\\Users\\xiancheng\\Desktop\\pic\\test1\\02.jpg",
                "D:\\Users\\xiancheng\\Desktop\\pic\\test1\\03.jpg",
                "D:\\Users\\xiancheng\\Desktop\\pic\\test1\\1fa90d56dc764e9682e47fd54335c2601614852267349_original.jpg",
                "D:\\Users\\xiancheng\\Desktop\\pic\\test1\\1fa90d56dc764e9682e47fd54335c2601614852267349_750_750.jpg",
                "D:\\Users\\xiancheng\\Desktop\\pic\\test1\\1fa90d56dc764e9682e47fd54335c2601614852267349_450_450.jpg"
        );
        for (String path : paths) {
            File jpegFile = new File(path);
            opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());

            opencv_core.Mat imageGrey = new opencv_core.Mat();
            //转化为灰度图
            opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.CV_RGB2GRAY);
            opencv_core.Mat imageSobel = new opencv_core.Mat();
            //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
            opencv_imgproc.Laplacian(imageGrey, imageSobel, CV_16U);
            double meanValue = 0.0;
            //meanValue = mean(imageSobel)[0];
            meanValue = opencv_core.mean(imageSobel).blue();
            System.out.println(jpegFile.getName() + "清晰度检测(Laplacian Method):" + meanValue);

        }

    }

    /**
     * https://blog.csdn.net/u011555996/article/details/103864766
     * Tenengrad梯度方法
     */
    public static void tenengrad() {

        String path = "D:\\Users\\xiancheng\\Desktop\\pic\\download";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        for (File jpegFile : fs) {

            opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());

            opencv_core.Mat imageGrey = new opencv_core.Mat();
            //转化为灰度图
            opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.CV_BGR2GRAY);

            opencv_core.Mat imageSobel = new opencv_core.Mat();
            opencv_imgproc.Sobel(imageGrey, imageSobel, CV_16U, 1, 1);

            double meanValue = 0.0;
            //meanValue = mean(imageSobel)[0];
            meanValue = opencv_core.mean(imageSobel).blue();
            System.out.println(jpegFile.getName() + "     size:" + srcImage.cols() + "*" + srcImage.rows() + "    清晰度检测(Tenengrad Method):" + meanValue);

        }


    }


    /**
     * https://blog.csdn.net/u011555996/article/details/103864766
     * 方差方法
     */
    public static void fangcha() {

        String path = "D:\\Users\\xiancheng\\Desktop\\pic\\download";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        for (File jpegFile : fs) {

//            opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());
//
//            opencv_core.Mat imageGrey = new opencv_core.Mat();
//            //转化为灰度图
//            opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.CV_BGR2GRAY);
//
//            //矩阵标准差
//            opencv_core.Mat stddev = new opencv_core.Mat();
//
//            //求灰度图像的标准差
//            opencv_core.meanStdDev(imageGrey, new opencv_core.Mat(), stddev);
//
//            System.out.println(jpegFile.getName() + "矩阵的标准差：" + stddev.createIndexer().getDouble());



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

            //矩阵标准差
            opencv_core.Mat stddev = new opencv_core.Mat();

            //梯度图像的标准差
            //System.out.println(jpegFile.getName() + "     size:" + srcImage.cols() + "*" + srcImage.rows() + "    清晰度检测(Tenengrad Method):" + meanValue);

            opencv_core.meanStdDev(imageLaplacian, new opencv_core.Mat(), stddev);
            System.out.println(jpegFile.getName() + "     size:" + srcImage.cols() + "*" + srcImage.rows() + "矩阵的标准差:" + stddev.createIndexer().getDouble());

        }


    }

    /**
     * Laplacian梯度方法
     * https://blog.csdn.net/u011555996/article/details/103864766
     */
    public static void clarityExceptionV3() {

        String path = "D:\\Users\\xiancheng\\Desktop\\pic\\download1";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        for (File jpegFile : fs) {

            opencv_core.Mat srcImage = opencv_imgcodecs.imread(jpegFile.getAbsolutePath());

            opencv_core.Mat imageGrey = new opencv_core.Mat();
            //转化为灰度图
            opencv_imgproc.cvtColor(srcImage, imageGrey, opencv_imgproc.CV_RGB2GRAY);
            opencv_core.Mat imageLaplacian = new opencv_core.Mat();
            //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
            opencv_imgproc.Laplacian(imageGrey, imageLaplacian, CV_16U);
            double meanValue = 0.0;
            //meanValue = mean(imageSobel)[0];
            meanValue = opencv_core.mean(imageLaplacian).blue();
            System.out.println(jpegFile.getName() + "     size:" + srcImage.cols() + "*" + srcImage.rows() + "    清晰度检测(Laplacian Method):" + meanValue);

        }

    }


    public static void downLoadImg() throws IOException {
        //region
        List<String> urlStrs = Arrays.asList(
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-23/4e249ecbf493836555ea058c1f08e0c5.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-23/109ac6d260a675ec4d74ea9b728ba99b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-23/cfbb9b513fdadebb463959ed59b6c35a.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/09-04/7eb578245cfa93b6d6eb57dd798d28ae.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/08-31/d6532eafd7061b51b157c89372ee27c6.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-24/4684fd47a30a426e335427e251b316ab.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-24/fad0a332fe58458c7727201a16e3edb3.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-24/e2aa47438d2b1f12962709c8d3f04a5d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-24/966d253c29ebd417bfee075b87beb632.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-24/13703889f8374751477d626ccd77c519.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/07-22/b71994b14bf929e9339b45884789245c.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/01-09/295f06b03ae501dd90fb9a02e0202084.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-18/12a73fab299c90c8b7ac923286a441aa.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-18/cb76a6c409dd91ce1634c084a9338989.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-18/5a4ffe9c67495b7410cca27a67a1cc1a.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/03-28/5613369ba377049fa9eb59fc10ac2f97.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-13/b8c4975b9872dae938885467d111ef9d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-13/ddf803c333251348cab41ecd295893e0.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-13/84a471d474f4760c46706cebcab3d32b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-13/2e4d780a41aafddb587897368338351f.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-13/395e60cb7707d74bdf2f4fbb77b91ac3.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-14/ed7fdd45c03f4ac8871fe517b2a013ba1607936808859.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-14/c6b97cd642044570a7a2882c351a17ab1607937548717.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-14/266e460172a244ccb6e33a37caa172b81607937553653.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-14/15063994770b42eaa15b97a21049b5c21607937559419.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-14/eac63c4a9e6949eea3dc3344de0a4e211607937566561.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-16/4611d5b0b6bd4237b843680e179da8aa1608098510802.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-16/88d63bc3ac494e539c1cf35d2ab54c1d1608098519444.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-17/84ac09820dcf4fdda626b96c0890a5671608188941536.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-17/9d66c460d8d549c1b294c6a5eb93d3f71608188947667.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-17/77370d89adf34938a5fd7ffe815a1b501608188954224.jpg",
                "http://shopimg0.youxianbianli.com/2020/12-17/3af82c177a3a405cb45993a15928e6021608188978710.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-04/2b52015c679b9bf4ca22649abb54b4fe.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-04/b9edf1faca4f4707abe6f1870762f60d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-04/a67c44d5b3dc2573ab47c2f6bb68b5e4.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/02-09/aa88d3dd82447ab3f93dafa9e745589c.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/03-13/4df2294860dd0bf8926fae64ad13fe07.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/04-13/3c45805bb70f41c9e8d7065ef6e13d9b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/04-13/8d7911decf76203d7e5c36bc409b5a88.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/04-13/25977cc2ff7bea3fcb37c9e9799cd9dd.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/04-13/4620238d19174b0931a899296dfbc68a.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/04-13/e268310e528ff7f781d536741910ec1b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/02-11/50eb4355528a48b7bdac562610d9f01e.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-04/380cd19d9f638a1ca1ceb214d597a2b7.png",
                "http://img1.youxianbianli.com/public/upload/goods/2019/08-31/b920015738a550ae63acd35b328309f7.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/08-31/5156ed18ed1c9838a25f7e6db78ae923.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/08-31/a0a94e5925645958c4901108f34e4dcf.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/12-11/45e75a9020c446f12cb33ce446f9a4b3.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/12-11/8c1b234d46f738300a20c96498ba472c.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/12-11/117a5e971d111b79c7aec97cef2a4190.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-16/eed4b386966fce013bd7692f32e2d16c.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-16/91d59609a1c4627e10b36aee73956f1a.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-16/7c249c6d601514010c43a66812462775.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/07-29/13af059c9e026fc2a72cf85e8ab96767.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/7c54556ccdc07fcbee673e3b4d5d08c8.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/5a8da6fb675501ace11cc8afffe2889f.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/72df6f9826f5972afbf0becd5caa9ba2.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/ae53f53cd3400b306217943659055079.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/e3ee77e20fd3949b14ec4493fa1dcfaf.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/8b37db5763da40b293d5fdaa1068127f.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-11/a160c8f9c34db30479af983257a71d72.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-13/87220d736fc53f6c51735749f7251145.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-29/456770101b0724ad11f91bec0d91e8f3.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/03-30/3804300674dcd627c588b1894c4b9614.png",
                "http://img1.youxianbianli.com/public/upload/goods/2020/03-30/f4b6ef13912f8f68e59e33262352d54c.png",
                "http://img1.youxianbianli.com/public/upload/goods/2020/03-30/c525c459cf520fe7a1d50706a7373f0d.png",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-12/568410b90b3820c05bca1369932f0559.png",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-12/16bc8658e1bbac288ec0175094ee13fd.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/05-12/d89aaeb04be348b2d756dcb2b505cea3.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/03-05/3d61c4a632e418b0c5f63d798bad22d9.png",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-15/5890507cde1ab14d72acb0c5e0946c3e.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-15/2ee1a859367c4337c279b26cecfc439b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/05-15/c92b3baba182871c551033ad51b65f26.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2020/03-05/14988c53fcee527a3a4a11c55b7516ef.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/04-09/3a07223f245483caf50b440062721c0d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/12-24/6acfbc0f3f118e1eaf07510696c80fa1.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-22/b1710a834693debf0db8d3511a8a7a4b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-22/bb8686eb413e3c6e7e099001896c541a.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-22/e4c9e399849e798512fcf6f4530810ff.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-22/9315e03bdc2a11c69159f01aa277460a.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-25/92c61335926154d5f1f140b5d4587de7.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-25/7fc4cd8c6e41d0fa2a83296115444346.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-25/6990e21e3e00ab01efcc10dd0f41579d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-27/e1a1341e6b22a8deba6703c861a174f9.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-27/63f27aa6ecb1eab89f77f6f9a7919490.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-27/be74efe8c5f6fcb13e0f62f626f795f0.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/09-27/03226170da772f85e4669e4e8d40b40e.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/11-06/0f3a73f8fcc9aa2792fac9c56a4be746.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/11-06/f4c6d9d03079befd8c09fa7fdf36cf68.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/11-06/984cdfa8569a2df7ccf95e3b42885354.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/06-21/4ae31d0e19d101b49a54e5b0462edcd0.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/11-06/66c37dee96ce0c2f6315a8449d220ba9.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/11-06/b939c9700045f26a164cb8a0bad8127b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/11-06/ee0fedefb2e317bde891902703ea7a79.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-26/b1da28010068d573e2ecfefc79cc5ae3.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-26/335e400ee4da7d25a723d853634f1e1b.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-26/4f562d3645375b5c78ec798024d7cc3d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-26/8a63c1f1301cce8bff0d314eae427f0e.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-26/8e4986a4c68f4e267dc12e4a5e8ecc47.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-26/bc4039733ec09cc680947285a4947bfd.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/11-30/555b4a97eafb26550e5c1201c35aeb98.png",
                "http://img1.youxianbianli.com/public/upload/goods/2018/06-28/1c5cce62b8ed0427a12c3b541fb7d5a9.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/12-30/acbe9d50e0ce296c644c6fcee9826e15.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-11/551d3e05066c7e296501f57fb6d99260.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-11/3ccf6d6485f02cd6b29d8b3e394d5479.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/03-11/11363e3ca0742707f392807062bff00d.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/07-23/ec80119cb1bb902090174139b70abd02.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/07-23/0e51595817eb330c80620dab63192828.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2019/07-23/2090e74e66c00593d713c82ce3d5cd02.jpg",
                "http://img1.youxianbianli.com/public/upload/goods/2018/12-06/dadd752c54c6161e00914a4a5affe56b.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/5c4ce2f6595246efb2dbab04b1acff671615017119668.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/655fd4aaa4df4832b04dc5764fd7e3c91615017116006.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/763b1de5d0354d7994ece7b9e21cfacc1615017068125.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/03e23cf6d66a467099465139cde879f91615016885795.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/92fbd6de045d4b3cbd6fc6de6b256f8d1615016885770.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/826dbaff744d44cfb5abe67ef42ea98f1615016885682.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/4335bfad81f446a6b0585da17a83d5c01615016861002.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/cc0135e1123840e5a2edf803b33477661615016739247.png",
                "http://shopimg0.youxianbianli.com/2021/03-06/afffb639fc364d5ca04666d119ec40ef1615016736665.png",
                "http://shopimg0.youxianbianli.com/2021/03-06/abe1dd995bb548c495970871aedc4edf1615016733779.png",
                "http://shopimg0.youxianbianli.com/2021/03-06/f92d1aee695b448e9f41f830edd4552d1615016471036.png",
                "http://shopimg0.youxianbianli.com/2021/03-06/eb1c90192b71439d8160b2bc61ed335b1615016468019.png",
                "http://shopimg0.youxianbianli.com/2021/03-06/68d4d54fc1204d41b183e059936e61651615016465396.png",
                "http://shopimg0.youxianbianli.com/2021/03-06/40d15e3fac6345da91fbaad444eb30271615012917088.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/e2744008252d4a31a44f54f96e98aad51615012857910.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/882dc24839354a45b18539b397916e521615012857770.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/59613f8f234f42d6ba1dc95ac5c70c931615012857328.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/15e2acb6f81f4e7ab14e3f4739c32b471615012369677.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/5031b15c087a4e9d822c1e4c08f0760b1615012116912.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/4943cb089934482a9fa6802462c1b20e1615012116912.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/01174070d1634d80a68b24c2c2764bdd1615012116893.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/925cff88574f46e78ab63ebb0b284da21615011822677.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/090ad9de14214214a6eb34f4f51d3f4b1615011822711.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/cb5843231f454fe2870fd1d9290f6fd71615011822699.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/933ae7fa33724315bcf83d15b2d617221614997656107.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/2d38e5d2dce0499186bd36b317ce6ab41614996260146.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/aea16b475bd2431199f7a1042c137b781614995430529.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/9b3b0aa9a49e4910bb8e1a6735520a181614995355373.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/e30c0b3cfeac4ad286153dbb2d5b94f61614995351703.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/3d64a5d97b464f12af9fc4c112393eab1614995170218.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/aa4802a2a4a94646aa8c5e004edfa5ff1614995067798.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-06/97b13b44309a4bf2b31d2003f47a0cee1614995067770.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/6dcce752ff494b0ba1bc9fb8a83af8ed1614940554193.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/a11b8253e6584356bdb6d172f21ca6d81614940548095.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/440c8f5e15104f359db17223c21b590f1614940349215.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/d3080aa24e1d40908300a24efc0c717a1614940349190.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/4ff0d204c2b547cebfaca9ef62ecfb381614940349175.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/72f22952e1e04f2780cd50cec9f3c6471614940077461.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/e79d69f6ebbb47fabe7f224e1b90e07b1614940077258.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/7e4fc1cebdfd4597b913a229808018f31614940077235.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/abdc649444e241c2b6a8a06ceb59e9fc1614940115430.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/60c86a9e7b014a109b0dbd855d7921f11614938128332.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/2183d3839c95469a94006c6e7335b5e01614938119426.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/0c7bef3d8a5049518fd79f6f6407aebf1614938110573.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/3707f9ad063b4f4aa3ea898fa7434fa31614937953820.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/20ae5ba9e7cb4cb7806dc284b4c305b21614937563947.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/f05805089cb44f5b99c45a1a135bf0921614937563912.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-05/7cd9cd7da63e42e2a7eced7425619c7e1614937446555.png",
                "http://shopimg0.youxianbianli.com/2021/03-05/f1fd0e7d21c44ad691d87b8ecaa349131614937442537.png",
                "http://shopimg0.youxianbianli.com/2021/03-05/bca46c628f244e5ca63bded69fb6f8011614937438475.png",
                "http://shopimg0.youxianbianli.com/2021/03-05/7803b7e6dc7c47b3832d56915aa1e4e21614937220764.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/a19eff0ddf0b4c16abc1e8ebf6aa42ad1614821386081.png",
                "http://shopimg0.youxianbianli.com/2021/03-04/d4c3d9c916d3457daa6a2e2364bd648e1614821382353.png",
                "http://shopimg0.youxianbianli.com/2021/03-04/e789f57015274d1eb5d9d5c1935a16cb1614821378051.png",
                "http://shopimg0.youxianbianli.com/2021/03-04/a9f3c3470ffa4fbfbe74743d9c35388d1614821096278.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/f9923858ac0c45ec92e661b560f46d361614821078864.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/90342d216d744d61b0e543f96d38bafa1614821078347.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/5eeae452d35044dc90bb509c842c921a1614821077357.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/39fbdc53386a48bb804d65e77cbde2351614821075600.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/632dc27b71114aada8f674f2ac831df91614821075077.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-04/5654278c53244b7c9395ca776abd73ba1614787668201.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/db608f7a801d4889a9c82e56eb6710c81614768065742.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/14fcda4cde78463a99795c811df3b7661614767912193.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/448c670721fa44fca2e289e37a8324271614767908392.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/509ad896f95b4b2c8696135336f638c51614767904843.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/f152c42facf747d38ff294a15c10acf31614767672927.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/ff859a87597e4ec1b1a9702ee37438911614767672919.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/d6eaaeac579b415c917e548b437b4b1a1614767672936.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/70fbcd109aa04413a6d4aab21476e10e1614767672906.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/b5e923f3db424028a7c28fc2ebb1e8c81614767651644.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/bb16be1f506d4440b03cfa5646b4ab841614767648502.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/4d8be4ad7c654ef8ad70d88e5f5d5cd41614767645605.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/2e49abe927544c21a7a30a1cc6499b7e1614767656399.png",
                "http://shopimg0.youxianbianli.com/2021/03-03/37e36a4d947449e39a9ab3a74abbe9071614767653358.png",
                "http://shopimg0.youxianbianli.com/2021/03-03/fbdd82b174d541a1bd55d330a1ec1d0a1614767650203.png",
                "http://shopimg0.youxianbianli.com/2021/03-03/c1dce1113a98416182d5c176c7b7eea01614767647449.png",
                "http://shopimg0.youxianbianli.com/2021/03-03/b942f247804e41d286f70e2fda3a59181614767268153.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/b3db4fcde411479a95c6d0f061eb9d161614767264170.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/6e1c5092438a4697badeaad70c1836061614767258048.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/7a269b124439406ebcda4b7b5c0722041614767118003.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/ee50f1a9b06e47679522e03c1c4fd2b41614766988837.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/e3a76cdde0db4793b80a7d731290e07c1614766983939.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/ab5b60aba01948a4a84dbb8ff1310ab51614766975409.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/2892762c795b498e94fa2be561992fc21614766970703.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/f681b52fc56445d2aebaae5f132d4ce41614766966639.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/cbd46202fa584ff58e4edcd41b0a2ce41614766540216.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/20b6754d6f3440729706be163e3c7c271614766540179.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/4c5d2fe0a4094c9e833269f84a4547ba1614766536130.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/5d62952d9b2a4b9bbc074e9628e75edb1614766532131.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/f09b64f3980142d8b2f0e84a2548a6481614766090969.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/eb523bfc43214cd1b7ad33f82c2a4af61614766087130.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/33813bda18f148cabe1c6717fa67766d1614766083319.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/6dafa30e484e409085edb3569c11d54d1614765399064.png",
                "http://shopimg0.youxianbianli.com/2021/03-03/a39e0513c72e496892e945b9f1b518291614765399050.jpg",
                "http://img1.youxianbianli.com/2021/02-25/78aff5fdd6f34163acaf615b6c5f9b281614247721917.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/f3140da692f9449886ff844e848f92591614762474810.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/e72ac57853ad4e4b96d5a5cce76df0d21614762474805.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/7deb5382b99d48fcb7a700ac4362ec661614762474788.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/48486bf9b3704c1b8e6c621bd16aae6b1614762474778.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/aad51ba3db284753960351b208053ece1614762343688.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/84a9c2ed75f149929bb0c574bb6171981614762343667.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/3a7de43b089149c0aa55c701bf919ec11614762343653.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/c96a94710ec24d7592bf9ad63e69c4a11614762343648.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/0fe456ab090e40f89ef3f9e764c7940e1614762343531.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-03/14bbc945b76e48cf9f3b8007e3e82ba31614762343536.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/4d1e1f5c902e492da5bf262dadce85e81614583916525.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/cd9fa21945ca4039a4817023f6bd78351614583916525.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ab2031fba1854b06a23116900cdd85ad1614583524588.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ba49487748d24df99a1b784243fd081c1614583524577.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/94addf3ddd9745eab81b879390336ee21614583524556.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/e545df34550f4d2faf99cd14978adf1f1614583407779.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/02b25290809148af82ed8932891516cc1614583353621.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/d2925065ac384101b4ecab7f5944fb251614583353330.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ecd056dc3e934391bb222e6e91bb83861614583353324.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/75229c21e3714756a46d9dcfc2073a721614583353313.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/b9d7a83305ed459b8ed12da09bfbe2f51614583353309.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/987fc5c5278c430db0c0203afb30a1021614583318005.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/4d1e1f5c902e492da5bf262dadce85e81614583916525.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/cd9fa21945ca4039a4817023f6bd78351614583916525.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ab2031fba1854b06a23116900cdd85ad1614583524588.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ba49487748d24df99a1b784243fd081c1614583524577.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/94addf3ddd9745eab81b879390336ee21614583524556.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/e545df34550f4d2faf99cd14978adf1f1614583407779.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/02b25290809148af82ed8932891516cc1614583353621.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/d2925065ac384101b4ecab7f5944fb251614583353330.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ecd056dc3e934391bb222e6e91bb83861614583353324.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/75229c21e3714756a46d9dcfc2073a721614583353313.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/b9d7a83305ed459b8ed12da09bfbe2f51614583353309.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/987fc5c5278c430db0c0203afb30a1021614583318005.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/91f79e0f46dc4f91a1ce61736539d9801614583276135.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/dad3207e2dd94538acde6abf8eb2eeef1614583276097.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ed9fdfe5aae146f8b3f9e440a0e0574e1614583247051.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/0249585c811647269e2906f4055e2b3c1614583161058.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/96d01a25909a4a4e963a8cb11bfa93241614583182750.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/8fd1088a9e1b44baa8c29db07c83c9d61614583182744.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/befae5a4e4bf4b528cdf8e97c91e57911614583166511.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/34c40ee2c34f4615b8aa4b4dea5aeb1a1614583161707.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/0cdaf31839574e6a9194bdae64a3338e1614583113742.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/60668e3969fe47d68061195d11d6e7d21614583108525.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/502ade8fea0e4263b11e5fcd3ee6c9591614583097206.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/25fa4fa6fa934ab1b0fb1282e84275b31614582716781.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ecec94298f30461b86dd611e04bd67d81614582716720.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/8acea644668a4919b94fd86b7a3aed8c1614582103592.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/549f08f125394462862676bcf25614791614582103586.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c258a241ff7a491a8bf579b0bd607acb1614582103587.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/618c4161f67946a49066cef8fa6817181614582540000.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/5505ffd72369415bbe7a7b4f25eceda21614582539967.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/dc0e7f0df7df491e815444ffa9315da61614582539916.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/906d883a301b4096a65fb38847b8893b1614582539915.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/eb5e6eddb4494831aab8827cc1f92afe1614582539898.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/9bb3f9cd820f471a99f51274c33a12d21614582001884.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/24fad7f6461e495ba85dd2cad8723b131614581991666.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/7c8649edb55a4f2da34a948d55e333ea1614581983809.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/e84fa75634814b4990b9aa912196ff8c1614581956728.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/21349cc063ae41f8a67aacbfa415c7621614581956711.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/f98ee0a6a87e4c4293e8c45bcefcb2dc1614581956684.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c241a878116b40cfab9e45d44cb71e161614581956653.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/f174e85504c4451ab37d71a9d6653b891614581956656.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/e2453aa5ab91427eb6ef20bdc5d7b8171614581882748.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/463081e75dbd4d249f2b87e7590330ae1614581876599.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/d4db5e25f1924d57bf58caeb937cc4c01614581796310.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/4a87dc3679034981a610dd8b000599051614581791388.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/fc0421e096f247d2b15e7ad128ccbb011614581656862.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/a8292910c0e14b209113fd487c5c9fe01614581649763.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/450feee51a3b4025acf703f3c8f3ffb21614581643503.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/206680f1b88c439fb5f783ece8d16b2c1614581642615.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/0a8c7794b8834da5b39c40e144c9b4861614581642564.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/4f65901a96824abc9fb1987b2bd455c31614581641888.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/23f655087b334b5aba43ed0d842e19501614581551082.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/780800d1e33f48feb11444050dc494d31614581509607.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3a1bcabf453f4c5980dbaeabbd095dbd1614581474380.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/75b1ec6ff4e542c6841d218366f56f2b1614581461759.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/62d9032f617845f9b196e3ac2a004aa81614581504017.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/7a11b26d64bd439cbc28a45e9d50a07f1614581503987.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/d3be8d02488f44d18d421cfca8e041341614581503972.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/878fda7ef31f4198b707eac83c958e3b1614581503963.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/810a9ea635654fb4a59f57b162db34a31614581503864.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/6f7bee8f81964a16b08de9d8a39f75cc1614581495556.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3ab01fec7c07485a8c8c9de43260aa411614581442128.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3cefa4698f6c4aceb3df170984d0c4e41614581442108.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/7483467b46ca4af696f534c2c28ff7181614581442086.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/4a4888000e554b7e96e5804db8c9c1a21614581432426.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/668ca6517fa442bbbbea2994d2e904e41614581431294.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/6c40f645ead34831a6a32aed17ba79041614581428548.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/6579417e1c1c41cabb548958da55d8361614581295702.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/420251cff8f8427d86fff9410b086a531614580934392.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/02402c42c59b4e3f99a09ba9dad4b6141614580934045.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/4de408811a734e73b4118eb1e5f288ef1614580933985.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/1c02295dc3524a25b73fa6f1760da89c1614580139733.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/1c02295dc3524a25b73fa6f1760da89c1614580139733.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/106e31047199468aae9150c9a9ac01761614580133475.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/106e31047199468aae9150c9a9ac01761614580133475.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/47eac5bc3df14db7b69f87d1eb250ba91614580125095.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/47eac5bc3df14db7b69f87d1eb250ba91614580125095.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/a6fff9bce448431fa5770807545662611614580011977.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/f2d85bf2868e4266b24ca917305249e91614580011918.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3224a00f025c400b9eb74989782cc13f1614580011925.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/01230e44273b4ee5bae6aed2dcf232261614580011917.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/688f0d7ed2af45568a458b5f7b01d6a61614580011616.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/69b39cdbea5848928f436decf690cdc51614580004325.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/2237cf0c5c714c54981bb9a79da3f5971614580059199.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3d90820639084a1a91e1b678c7a2e35b1614580059200.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/36df60cc7dfe4103b70d803b2a8b8c641614580059176.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/780d603ddb13433ba02bf5986690c6131614580050977.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/eb5c8783338b426f80368efaaa3985fb1614580048371.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/161008e6f96241c988f5ba65394f4de81614580046035.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/7466c660c9c047f9b602d5a4403eb2ee1614580043568.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/f8ca8505aef84aefbad5bc18ebb156ee1614580041156.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/221af8f0c1bc4e1faf17d2ebe017b2dd1614580038149.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/44623cfb94884f448ee218f71ff6eba61614579484798.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/af707a2b201f4e69a764ddd5d0fd7c8c1614579481006.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c71938af39834768ac4917102137cb331614579477813.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/5593f3b8a9b545b9854c8541b9495f481614579436434.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/1826efab10524ba69f3d6f7b43bba6aa1614579436218.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/58cf363564304abf94911c56c986628c1614579435916.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/517b07bc7c7443a49b09e670302881ab1614579435577.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/f7223e3ab193468397be24fd08bd20c41614579302589.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/1cb5c87b522d4850936781fbc872ca6c1614579302467.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/44666440888143f38fd4ff3acaaf45d51614579302470.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/94b51c2040f0459e97658f84006703bd1614579302464.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/9ec9a5913aa04ce489c968902ff8bb9b1614579302464.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/636a3b8aec304e5c84c80bf6fb390a881614579127260.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/565acf626bf146fcbc8dc094b0b7feec1614579098173.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/1aa8538469e045958aaaf861a69533151614579098098.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3e2e4490380c4fc8bccac4c20be153d31614578831824.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/2d2014cce85941cca52bb54a637b4e231614578831812.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/825320e77b6e4aaa98072cdb9188fff71614578831779.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/0b67c024c04142329687c12fd18015e91614578288450.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/d753417cf74c480ba45b6667f86364ad1614578288430.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/734fb15e3fe64201b4f039cce99da1a71614578288364.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ada1d76e81544b82b454997637381a861614578051083.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/67b34ff9b5bb493fa9045fa68764cec61614578050944.png",
                "http://img1.youxianbianli.com/2021/02-27/788ef4f4eca64bcbb77bb1767df6738a1614416224774.jpg",
                "http://img1.youxianbianli.com/2021/02-27/74385e0373c14a79a347a4090be2f4271614416224753.jpg",
                "http://img1.youxianbianli.com/2021/02-27/3507c94c47a84669b5f887092511dc6a1614416224743.jpg",
                "http://img1.youxianbianli.com/2021/02-27/f9880a21985b4e9b80ed37ba06c201f31614416215302.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/2748982ccd0844f180670db3fa21c8cc1614577481015.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/22bbe37748064f05aa5df7a077aef0841614571016254.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/42d8dc039a564170a97a9146fd047cc31614569748416.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/a29a7f0d7830428d8e4795d5432165531614569777080.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/9237ccd7799245e5b4db4fbeaa2240bc1614569771626.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/7a77a959c55246c1a90e480ea0066aea1614569766408.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/e97ae8c92e68454f854b5598b26f19fb1614569762191.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/1beb1df59a1448beb7ec1f6025083ca41614569648222.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/d8c6ef0b39774fdb99abd7a7bb883b4b1614569648211.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/1ac20393dea44f628d9d54bf44395ae01614569648153.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c76d514c89924220a4145909f4b93d891614569493124.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/8d6fc1d6c4884d1eae32c6fe5ce5f39c1614569493043.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/efc20f00193c4a3aae0fa9aa2f8d390e1614569492907.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/ed50e3d41ad34d3da977928cfe47a9dc1614569480671.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/be2bb682e9b24392b1986504765f68361614569478312.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c40d9dd21432421fa69a636ea326e7511614569475859.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/b3548828be914b61a6cd0851454224dc1614569433815.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/cf958d506e294b3a81fcad98b547c88a1614569433796.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/b5cade096cb143b8a2d0a04dc304c6dc1614569433543.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/41ba30c7ab4449d4911f97fa6e52c3dc1614569341978.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/ee496b8f05654fd78d14010ab8118bc91614569337305.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/0cead0e6582948199e15c755b76723901614569334090.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/4f1d1aafa8b043d3a84fd388be5f118d1614569355105.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/91ba5521dc9c4f739fcb0e86a26aa18f1614569352224.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/99da20a3edcf46ff96337cf1e075b3ac1614569349957.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/5ddffef1d65e44dc8c5971a0d7ea0fb21614569242922.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/aab36b76201340dfa4331132b14a5bd51614569242926.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/46581d9d03e94653842135521ea5bc8d1614569242739.png",
                "http://shopimg0.youxianbianli.com/2021/03-01/def6c6705ec8469c8397aeeaa51596c21614569066002.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/8e018ef398f54ca9904c54126445a8181614569058483.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3176ce334042497bb8ac071d42a926971614569041561.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/3352c8cc801646939783bf58a4cfb6c41614569041539.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/6a4526de6c064258929fdf3bee270df51614569041537.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/e2d5fc80f3474aa3ae74e01a351f03991614569041458.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c11cbea7d89e416ea11062a15cba1ac31614569065308.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/c01e1275e4a64a318be033570f380a8d1614569065307.jpg",
                "http://shopimg0.youxianbianli.com/2021/03-01/37f09cf26d404860b53ead55e0844d961614569065273.jpg");
        //endregion

        List<String> urlStrs_extend = new ArrayList<>();
        urlStrs.stream().forEach(
                x -> {
                    urlStrs_extend.add(x);
                    urlStrs_extend.add(x + "?x-oss-process=image/resize,w_450,h_450");
                    urlStrs_extend.add(x + "?x-oss-process=image/resize,w_750,h_750");
                }
        );

        String savePath = "D:\\Users\\xiancheng\\Desktop\\pic\\downLoad";
        for (String urlString : urlStrs_extend) {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            //默认值
            String filename = "";
            if (urlString.contains("?")) {
                String originalUrl = urlString.substring(0, urlString.indexOf("?"));
                String extensionName = originalUrl.substring(originalUrl.lastIndexOf(".") + 1);
                filename = originalUrl.substring(originalUrl.lastIndexOf("/") + 1);
                String size = "_" + urlString.substring(urlString.length() - 3) + "_" + urlString.substring(urlString.length() - 3);
                filename = filename.substring(0, filename.lastIndexOf(".")) + size + "." + extensionName;
            } else {
                filename = urlString.substring(urlString.lastIndexOf("/") + 1);
            }

//        // 获取图片的扩展名
//        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
            OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }


    }

//    public static void main(String[] args) throws Exception {
//        // TODO Auto-generated method stub
//        download("http://avatar.csdn.net/1/3/B/1_li1325169021.jpg", "1_li1325169021.jpg", "d:\\image\\");
//    }

}
