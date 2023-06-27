package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticlesEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    int insertArticleInformation(ArticlesEntity articles);
}
