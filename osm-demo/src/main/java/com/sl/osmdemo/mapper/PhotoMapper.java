package com.sl.osmdemo.mapper;

import com.sl.osmdemo.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PhotoMapper {




    @Update("update hotel_photo_20200116 set size=#{size} WHERE photo_id=#{photoId}")
    int updatePhoto(Photo photo);

    @Select("select photo_id as photoId,hotel_id as hotelId,size from hotel_photo_20200116 where display=1 and hotel_id=#{hotelId} and room_id is null")
    List<Photo> getHotelPhotoByHotelId(Long hotelId);

    @Select("select photo_id as photoId,hotel_id as hotelId,size,room_id as roomId from hotel_photo_20200116 where display=1 and hotel_id=#{hotelId} and room_id>0")
    List<Photo> getRoomPhotoByHotelId(Long hotelId);

}
