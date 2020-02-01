package com.sl.osmdemo.service.impl;

import com.sl.osmdemo.entity.Hotel;
import com.sl.osmdemo.entity.Photo;
import com.sl.osmdemo.mapper.HotelMapper;
import com.sl.osmdemo.mapper.PhotoMapper;
import com.sl.osmdemo.service.PhotoService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: sl
 * @Date: 2020/1/16
 * @Description:
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private HotelMapper hotelMapper;


    @Override
    public void PhotoCompare(Long beginHotelId, Long endHotelId) {
        Long currentHotelId = null;
        try {
            FileWriter fileWriter = new FileWriter("E:\\图片数据处理\\跑size文件\\hotel_photo_deleteId_test.txt");
            List<Hotel> hotelList = hotelMapper.selectHotelList(beginHotelId, endHotelId);
            while (!CollectionUtils.isEmpty(hotelList)) {
                List<Photo> deleteList = new ArrayList<>();
                for (Hotel hotel : hotelList) {
                    currentHotelId = hotel.getHotelId();
                    List<Photo> hotelPhotoList = photoMapper.getHotelPhotoByHotelId(hotel.getHotelId());
                    Set<String> sizeSet = new HashSet<>();
                    //酒店图片去重
                    for (Photo photo : hotelPhotoList) {
                        if (Strings.isEmpty(photo.getSize())) {
                            continue;
                        }
                        boolean flag = sizeSet.add(photo.getSize());
                        //重复
                        if (!flag) {
                            deleteList.add(photo);
                        }
                    }

                    //房型图片去重
                    List<Photo> roomPhotoList = photoMapper.getRoomPhotoByHotelId(hotel.getHotelId());
                    //分组
                    Map<Long, List<Photo>> photoMap = roomPhotoList.stream().collect(Collectors.groupingBy(Photo::getRoomId));
                    for (Map.Entry<Long, List<Photo>> entry : photoMap.entrySet()) {
                        Set<String> roomSetTemp = new HashSet<>();
                        List<Photo> rPhotoList = entry.getValue();
                        for (Photo photo : rPhotoList) {
                            if (Strings.isNotEmpty(photo.getSize())) {
                                boolean flag = roomSetTemp.add(photo.getSize());
                                //重复
                                if (!flag) {
                                    deleteList.add(photo);
                                }
                            }
                        }
                    }
                }

                //write to txt
                writeToTxt(deleteList, fileWriter);

                Long beginId = hotelList.get(hotelList.size() - 1).getHotelId();
                System.out.println("currrent begin HotelId:" + beginId);
                hotelList = hotelMapper.selectHotelList(beginId, endHotelId);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("异常,HotelId" + currentHotelId);
            e.printStackTrace();
        }
    }

    private void writeToTxt(List<Photo> deleteList, FileWriter fileWriter) throws IOException {

        for (Photo photo : deleteList) {
            fileWriter.write(photo.getPhotoId() + "\r\n");//写入 \r\n换行
        }
    }
}
