package com.cpack.mapper;

import com.cpack.model.LostPack;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LostPackMapper {
    @Insert("insert into lostpack(orderId,name,phone,date) values (#{orderId},#{name},#{phone},#{date});")
    int insertLostPack(LostPack lostPack);

    @Select("select * from lostpack")
    LostPack[] find();

}
