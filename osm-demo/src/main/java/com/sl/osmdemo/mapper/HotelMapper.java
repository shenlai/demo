package com.sl.osmdemo.mapper;

import com.sl.osmdemo.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HotelMapper {

    @Select("select * from hotel where hotel_id=#{id}")
    Hotel getHotelById(Long id);

    @Select("select * from hotel where city_id=#{cityId}")
    List<Hotel> getHotelByCityId(Long cityId);


}
