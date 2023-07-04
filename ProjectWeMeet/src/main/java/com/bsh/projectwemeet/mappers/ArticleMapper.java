package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleEntity articles);

    ArticleEntity[] selectAll();

    ArticleEntity selectArticleByIndex(@Param(value = "index") int index);

    int updateArticle(ArticleEntity article);




}
