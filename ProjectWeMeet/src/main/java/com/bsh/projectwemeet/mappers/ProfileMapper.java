package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {
    int insertProfile(ProfileEntity profile);
    ProfileEntity selectProfileIndex(@Param(value = "index") int index);
    UserEntity selectUserByPassword(@Param(value = "email") String email);
}
