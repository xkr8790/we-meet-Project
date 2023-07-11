package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {
    int insertProfile(ProfileEntity profile);
    UserEntity selectNickname(@Param(value = "email") String email);
    UserEntity selectEmail(@Param(value = "email") String email);
    UserEntity selectContact(@Param(value = "email") String email);
    UserEntity selectAddressPrimary(@Param(value = "email") String email);
    UserEntity selectAddressSecondary(@Param(value = "email") String email);
    UserEntity selectAll(@Param(value = "email") String email);
    ProfileEntity selectProfileIndex(@Param(value = "index") int index);
    UserEntity selectUserByPassword(@Param(value = "email") String email);
}
