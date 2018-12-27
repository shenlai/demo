package com.sl.springbootdemo.IdGenerate;

/**
 * 生成30位唯一数: yyyyMMddHHmmssSSS(17位)+服务器IP转16进制(8位)+递增序号(5位)
 * 如果将系统部署到集群上面，情况有会有不同了，不同的服务器集群生成的这个数字，是有重合的概率的，
 * 因此，一般情况是，将集群中的每个机器进行IP编码，然后将机器编码放在这个标识中以示区分
 *
 * @author Dabria_ly 2017年8月24日
 */

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @Author: sl
 * @Date: 2018/12/27
 * @Description: TODO
 */
public class MyDefineUidUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MyDefineUidUtil.class);
    private static String hexIp = "";
    private static final int sequenceMax = 99999;

    private static int sequence = 0;
    private static String lastDateValue = "";

    public synchronized static String nextId() throws UnknownHostException {
        if (Strings.isBlank(hexIp)) {
            String ipStr = InetAddress.getLocalHost().getHostAddress();// 获取本机IP
            hexIp = hexIp(ipStr);
        }
        String dateStr = timeGen();
        //如果是同一时间生成的，则进行毫秒内序列
        if ((String.valueOf(dateStr)).equals(lastDateValue)) {
            sequence = sequence + 1;
            //毫秒内序列溢出
            if (sequence > sequenceMax) {
                //阻塞到下一个毫秒,获得新的时间
                dateStr = tilNextMillis();
                sequence = 0;
            }
        } else {
            //不是同一个时段，毫秒内序列重置
            sequence = 0;
        }
        //上次生成uid的时间
        lastDateValue = dateStr;
        String sequenceStr = String.format("%05d", sequence);
        String uId = new StringBuilder().append(dateStr).append(hexIp).append(sequenceStr).toString();
        return uId;
    }

    private static String hexIp(String ip) {
        StringBuilder sb = new StringBuilder();
        for (String seg : ip.split("\\.")) {
            String h = Integer.toHexString(Integer.parseInt(seg));
            if (h.length() == 1) sb.append("0");
            sb.append(h);
        }
        return sb.toString();
    }

    /**
     * 重复判断
     * @param list:被判断的集合
     * @return false:有重复元素或list为null; true:没有重复元素
     */
    public static boolean hasSame(List<? extends Object> list) {
        if (null == list)
            return false;
        System.out.println("list size:"+list.size());
        HashSet<Object> set = new HashSet<Object>(list);
        System.out.println("set size:"+set.size());
        return list.size() == set.size();
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间
     *
     * @return 当前时间戳
     */
    private static String tilNextMillis() {
        String timeStr = timeGen();
        while (timeStr.equals(lastDateValue)) {
            timeStr = timeGen();
        }
        return timeStr;
    }

    /**
     * 返回以毫秒为单位的当前时间yyyyMMddHHmmssSSS
     *
     * @return 当前时间(毫秒)
     */
    private static String timeGen() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

}
