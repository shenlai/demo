package com.javalanguage;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {

    /**
     * 快速排序https://blog.csdn.net/Yexiaofen/article/details/78018204
     */

    public void quickSort(int[] args, int low, int high) {
        if (low > high)
            return;

        int i = low;
        int j = high;
        int key = args[i];

        while (i < j) {

            //从右向左寻找<key的数,找到则退出此while
            while (i < j & args[j] > key) {
                j--;
            }
            //从左向右寻找>key的数
            while (i < j & args[i] <= key) {
                i++;
            }

            //交换i 和 j数值
            if (i < j) {
                int temp = args[i];
                args[i] = args[j];
                args[j] = temp;
            }
        }

        //i==j
        //交换key和args[i] 位置 --key位置确定
        int lowValue = args[i];
        args[i] = key;
        args[low] = lowValue;

        //递归遍历key左边数组
        quickSort(args, low, i - 1);

        //递归遍历key左边数组
        quickSort(args, i + 1, high);

    }


    @Test
    public void test() {
        int[] args = {1, 4, 6, 2, 75, 23, 7, 16, 30};

        //quickSort(args,0,args.length-1);
        //bubbleSort(args);
        System.out.println(Arrays.toString(args));

        //List<Integer> list = Ints.asList(args).sort((x, y)->{return x-y;});
        List<Integer> list = Ints.asList(args);

        list.sort((x, y) -> {
            return x - y;
        });

        System.out.println(list.toString());



        /*
        for (int value:args
             ) {
            System.out.println(value);
        }*/

    }


    /**
     * 冒泡排序
     *
     * @param args
     */
    public void bubbleSort(int[] args) {

        if (args == null || args.length == 0)
            return;

        for (int i = 0; i < args.length - 1; i++) {

            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j + 1]) {
                    int temp = args[j];
                    args[j] = args[j + 1];
                    args[j + 1] = temp;
                }
            }
        }
    }


}
