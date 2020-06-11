package com.cpack.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedbackClientMapper {
    @Insert("insert into feedbackclient(phone,content) values (#{phone},#{content})")
    int insert(@Param("phone") String phone, @Param("content") String content);
}