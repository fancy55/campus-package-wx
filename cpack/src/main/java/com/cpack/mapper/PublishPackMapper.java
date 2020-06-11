package com.cpack.mapper;

import com.cpack.model.Packages;
import com.cpack.model.PublishPack;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PublishPackMapper {
  //  String PUBLISKPACK = "orderId,sNickname,qNickname,price,sPos,qPos,date,sPhone,qPhone,description,sIdCard,qIdCard,category,sPhoto";
    String PACKAGES = "orderId,pDate,sPhone,ewm,qjm,gDate,zIdCard,zphone,qPos,status,sStatus,qStatus";
  //  String PARAM = "#{orderId},#{sNickname},#{qNickname},#{price},#{sPos},#{qPos},#{date},#{sPhone},#{qPhone},#{description},#{sIdCard},#{qIdCard},#{category},#{sPhoto},#{pDate},#{phone},#{ewm},#{qjm},#{gDate},#{sStatus},#{qStatus},#{zIdCard},#{zphone}";
    String PARAM_ = "#{orderId},#{pDate},#{sPhone},#{ewm},#{qjm},#{gDate},#{zIdCard},#{zphone},#{qPos},0,-1,-1";
    String UPARAM = "endDate=#{endDate},sPhoto=#{sPhoto},sNickname=#{sNickname},qNickname=#{qNickname},price=#{price},sPos=#{sPos},date=#{date},qPhone=#{qPhone},description=#{description},sIdCard=#{sIdCard},qIdCard=#{qIdCard},category=#{category}";

    @Insert("insert into publishpack("+PACKAGES+") values ("+PARAM_+")")
    int insertPackages(Packages packages);

    @Update("update publishpack set "+UPARAM+" where orderId=#{orderId}")
    int alterPublishPack(PublishPack publishPack);

    @Select("select * from publishpack where orderId = #{orderId}")
    PublishPack[] findById(@Param("orderId") String orderId);

    @Select("select * from publishpack where price = #{price}")
    PublishPack[] findByPrice(@Param("price") String price);

    @Select("select * from publishpack where date = #{date}")
    PublishPack[] findByDate(@Param("date") String date);

  @Select("select * from publishpack where pDate = #{pDate}")
  Packages[] findByPDate(@Param("pDate") String pDate);

    @Select("select * from publishpack where qPos = #{qPos}")
    PublishPack[] findByQPos(@Param("qPos") String qPos);

    @Update("update publishpack set qNickname = #{qNickname}, qIdCard = #{qIdCard}, qPhone = #{qPhone} ,status = '1' where orderId = #{orderId}")
    int updatePublishPack(@Param("qNickname")String qNickname,@Param("qIdCard")String qIdCard,@Param("qPhone")String qPhone, @Param("orderId")String orderId);

    @Select("select * from publishpack where status = '0'")
    PublishPack[] findAll();

    @Select("select * from publishpack where sIdCard = #{sIdCard}")
    PublishPack[] findBySIdCard(@Param("sIdCard") String sIdCard);

    @Select("select * from publishpack where sIdCard = #{sIdCard} and sStatus='-1' and status='-1'")
    Packages[] findBySIdCardStatus(@Param("sIdCard") String sIdCard);

    @Select("select * from publishpack where qIdCard = #{qIdCard}")
    PublishPack[] findByQIdCard(@Param("qIdCard") String qIdCard);

    @Select("select * from publishpack where qIdCard = #{qIdCard} and sStatus='-1' and status = '0'")
    PublishPack[] findByQIdCardStatus(@Param("qIdCard") String qIdCard);

    @Update("update publishpack set photo = #{photo} where sIdCard = #{sIdCard}")
    void alterPhoto(@Param("photo") String photo,@Param("sIdCard") String sIdCard);

    @Select("select ewm,qjm from publishpack where orderId = #{orderId}")
    String findEwmAndQJMByOrderId(@Param("orderId") String orderId);

    @Select("select ewm from publishpack where orderId = #{orderId}")
    String findEwmByOrderId(@Param("orderId") String orderId);

    @Select("select * from publishpack where orderId = #{orderId}")
    PublishPack findAllByOrderId(@Param("orderId") String orderId);

    @Select("select * from publishpack where orderId = #{orderId}")
    Packages findByOrderId(@Param("orderId") String orderId);

    @Select("select * from  publishpack where sIdCard=#{sIdCard} and sPhone=#{sPhone}")
    PublishPack[] findByIdCardAndPhone(@Param("sIdCard") String realMaster,@Param("sPhone") String sPhone);

    @Update("update publishpack set pDate=#{pDate},sStatus = '1' where orderId = #{orderId}")
    int updateSStatus(@Param("pDate")String pDate, @Param("orderId")String orderId);

  @Update("update publishpack set pDate=#{pDate},sStatus = '1',status = '1' where orderId = #{orderId}")
  int updateSStatusWithStatus(@Param("pDate")String pDate, @Param("orderId")String orderId);

    @Update("update publishpack set qStatus = '1' where orderId = #{orderId}")
    int updateQStatus(@Param("orderId")String orderId);

  @Update("update publishpack set status = '-1' where orderId = #{orderId}")
  int upDateStatus(@Param("orderId")String orderId);

    @Update("update publishpack set qjm = #{qjm} where orderId = #{orderId}")
    void buildQjm(@Param("qjm")String qjm, @Param("orderId")String orderId);

    @Select("select * from publishpack where ewm = #{ewm}")
    Packages findByEwm(@Param("ewm")String ewm);

    @Select("select * from publishpack where zIdCard = #{zIdCard}")
    Packages[] findAllByZIdCard(@Param("zIdCard")String zIdCard);

    @Select("select * from publishpack where sPhone = #{sPhone}")
    Packages[] findAllBySPhone(@Param("sPhone")String sPhone);

    @Select("select * from publishpack where sStatus = '-1' and sPhone = #{sPhone}")
    Packages[] findAllBySPhoneWait(@Param("sPhone")String sPhone);

    @Select("select * from publishpack where zIdCard = #{zIdCard}")
    Packages[] findAllBySIdCard(@Param("zIdCard")String zIdCard);
}
