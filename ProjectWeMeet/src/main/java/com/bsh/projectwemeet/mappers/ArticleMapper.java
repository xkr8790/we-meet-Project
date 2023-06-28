package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity articles);

}
