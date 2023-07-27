package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.RegisterContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {
    int insertProfile(ProfileEntity profile);

    int insertContactCode(RegisterContactCodeEntity registerContactCode);

    UserEntity selectAll(@Param(value = "email") String email);

    UserEntity selectUserByContact(@Param(value = "contact") String contact);

    RegisterContactCodeEntity selectContactCodeByContactCodeSalt(RegisterContactCodeEntity registerContactCode);

    UserEntity selectPasswordByEmail(@Param(value = "email") String email);

    ProfileEntity selectThumbnail(@Param(value = "email") String email);

    int getArticleIndexCountByEmail(@Param(value = "email")String email);

    int updatePassword(UserEntity user);

    int updateContactCode(RegisterContactCodeEntity registerContactCode);

    int updateContact(UserEntity user);

    int updateAddress(UserEntity user);
    int updateThumbnail(ProfileEntity profile);

    int deleteUser(@Param(value = "email") String email);
    int deleteThumbnail(ProfileEntity profile);
}
