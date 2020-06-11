package com.cpack.mapper.pmapper;

import com.cpack.model.UserProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProviderMapper {
    String PROVIDER = "name,phone,idCard,sex,age,date,password,photo,status,nickname,address,comName";
    @Insert("insert into userprovider("+PROVIDER+") values (#{name},#{phone},#{idCard},#{sex},#{age},#{date},#{password},#{photo},2,#{nickname},#{address},#{comName})")
    void register(UserProvider provider);

    @Select("select * from userprovider where phone = #{phone} and password = #{password}")
    UserProvider login(@Param("phone")String phone, @Param("password")String password);

    @Select("select * from userprovider where idCard = #{idCard}")
    UserProvider findById(@Param("idCard") String idCard);

    @Select("select * from userprovider where phone = #{phone}")
    UserProvider findByPhone(@Param("phone") String phone);

    @Delete("delete from userprovider where idCard = #{idCard}")
    void deleteByIdCard(@Param("idCard") String idCard);

}
