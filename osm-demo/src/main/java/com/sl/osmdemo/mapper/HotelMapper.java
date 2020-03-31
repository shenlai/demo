package com.sl.osmdemo.mapper;

import com.sl.osmdemo.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HotelMapper {

    @Select("select * from hotel where hotel_id=#{id}")
    Hotel getHotelById(Long id);

    @Select("select * from hotel where city_id=#{cityId}")
    List<Hotel> getHotelByCityId(Long cityId);

    //@Select("select * from hotel_20191227 where is_master_hotel = 1 and hotel_id >#{idBegin} and hotel_id &lt;= #{idEnd} order by hotel_id limit 1000")
    @Select("select hotel_id as hotelId from hotel_20191227 where hotel_id >#{idBegin} and hotel_id <= #{idEnd} order by hotel_id limit 1000")
    List<Hotel> selectHotelList(@Param("idBegin") Long idBegin, @Param("idEnd") Long idEnd);
}
