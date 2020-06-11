package com.cpack.mapper;

import com.cpack.model.UserClient;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserClientMapper {
    String USERCLIENT = "name,phone,idCard,sex,age,date,password,photo,status,nickname,college,grade,major,address";
    @Insert("insert into userclient("+USERCLIENT+") values (#{name},#{phone},#{idCard},#{sex},#{age},#{date},#{password},#{photo},0,#{nickname},#{college},#{grade},#{major},#{address})")
    void register(UserClient user);

    @Select("select * from userclient where phone = #{phone} and password = #{password}")
    UserClient login(@Param("phone")String phone, @Param("password")String password);

    @Select("select * from userclient where idCard = #{idCard}")
    UserClient findById(@Param("idCard") String idCard);

    @Select("select * from userclient where idCard = #{idCard} and phone #{phone}")
    UserClient checkById(@Param("idCard") String idCard,@Param("phone") String phone);

    @Select("select * from userclient where phone = #{phone}")
    UserClient findByPhone(@Param("phone") String phone);

    @Delete("delete from userclient where idCard = #{idCard}")
    int deleteByIdCard(@Param("idCard") String idCard);

    @Update("update userclient set phone = #{phone} where idCard = #{idCard}")
    int updateByIdCard(@Param("phone") String phone,@Param("idCard") String idCard);
}
