package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

    UserEntity selectUserByEmail(@Param(value = "email") String email);

}
