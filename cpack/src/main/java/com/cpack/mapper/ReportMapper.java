package com.cpack.mapper;

import com.cpack.model.Report;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ReportMapper {
    @Insert("insert into report(reportIdCard,publishIdCard,orderId,content,status,date) values(#{reportIdCard},#{publishIdCard},#{orderId},#{content},-1,#{date})")
    int insert(Report report);

    @Select("select * from report")
    Report[] findAll();

    @Update("update report set status = '1' where reportIdCard=#{reportIdCard} and date=#{date} and orderId=#{orderId}")
    int dealReport(@Param("reportIdCard") String reportIdCard,@Param("date") String date,@Param("orderId") String orderId);
}
