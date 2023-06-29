package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticlesEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insert(ArticlesEntity articles);



}
