package com.cpack.mapper;

import com.cpack.model.CheckT;
import com.cpack.model.Report;
import com.cpack.model.UserClient;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CheckTMapper {
    String CHECKT = "idCard,file1,date,status,cnt";
    @Insert("insert into checkT("+CHECKT+") values (#{idCard},#{file1},#{date},0,1)")
    void postCheck(@Param("idCard")String idCard,@Param("file1")String file1,@Param("date")String date);

    @Update("update checkT set file1 = #{file1},cnt=cnt+1,date=#{date} where idCard = #{idCard}")
    void alterf1(@Param("file1") String file1,@Param("date") String date,@Param("idCard") String idCard);

    @Update("update checkT set file2 = #{file2} where idCard = #{idCard}")
    void alterf2(@Param("file2") String file2,@Param("idCard") String idCard);

    @Update("update checkT set file3 = #{file3} where idCard = #{idCard}")
    void alterf3(@Param("file3") String file3,@Param("idCard") String idCard);

    @Update("update checkT set file4 = #{file4} where idCard = #{idCard}")
    void alterf4(@Param("file4") String file4,@Param("idCard") String idCard);

    @Select("select * from report  where reportIdCard=#{reportIdCard}")
    Report[] getMy(@Param("reportIdCard") String reportIdCard);

    @Select("select status from checkT  where idCard=#{idCard}")
    String getMy1(@Param("idCard") String idCard);

    @Select("select * from checkT where status=1")
    CheckT[] getStatus1();

    @Select("select * from checkT where status=0")
    CheckT[] getStatus0();

    @Select("select * from checkT order by date")
    CheckT[] getAll();

    @Update("update checkT set status = 1,pdate=#{pdate} where idCard = #{idCard}")
    int passCheck(@Param("pdate")String pdate,@Param("idCard")String idCard);

    @Update("update checkT set status = -1,pdate=#{pdate} where idCard = #{idCard}")
    int notPassCheck(@Param("pdate")String pdate, @Param("idCard")String idCard);
}
