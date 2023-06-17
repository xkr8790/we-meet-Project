package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.RecoverContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecoverAccountMapper {

    UserEntity selectUserByContact(@Param(value = "contact") String contact,
                                   @Param(value= "name")String name);

//    UserEntity selectUserByName(@Param(value = "contact")String contact,
//            @Param(value="name")String name);


    int insertRecoverContactCode(RecoverContactCodeEntity recoverContactCode);

    RecoverContactCodeEntity selectRecoverContactCodeByCodeByContactCodeSalt(RecoverContactCodeEntity recoverContactCode);

    int updateRecoverContactCode(RecoverContactCodeEntity recoverContactCode);


    RecoverContactCodeEntity selectRecoverContactCodeByNameContactCodeSalt(RecoverContactCodeEntity recoverContactCode);

    int selectUserByContactName(@Param(value = "contact") String contact,
                            @Param(value= "name")String name);

    int deleteRecoverContactCode(RecoverContactCodeEntity recoverContactCode);
}
