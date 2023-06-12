package com.bsh.wemeetproject.mappers;

import com.bsh.wemeetproject.entities.RecoverContactCodeEntity;
import com.bsh.wemeetproject.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecoverMapper {

    UserEntity selectUserByContact(@Param(value="contact")String contact);

    int insertRecoverContactCode(RecoverContactCodeEntity recoverContactCode);

    RecoverContactCodeEntity selectRecoverContactCodeByCodeByContactCodeSalt(RecoverContactCodeEntity recoverContactCode);

    int updateRecoverContactCode(RecoverContactCodeEntity recoverContactCode);
}
