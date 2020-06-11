package com.cpack.mapper.pmapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedbackMapper {
    @Insert("insert into feedback(phone,content) values (#{phone},#{content})")
    void insert(@Param("phone") String phone, @Param("content") String content);
}