package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleEntity articles);

    int insertParticipants(ParticipantsEntity participants);


    ArticleEntity[] selectAll();

    ArticleEntity[] selectArticleMain();

    ArticleEntity selectArticleByIndex(@Param(value = "index") int index);

    ArticleEntity selectArticleByPatchIndex(@Param(value = "index") int index);

    int selectParticipants(@Param(value = "index") int index);

    int deleteByArticle(@Param(value = "index") int index); //Param 사용시 SQL문에 파라미터 타입을 안사용해도된다

    int deleteByParticipants(@Param(value = "index") int index); //Param 사용시 SQL문에 파라미터 타입을 안사용해도된다


    int updateArticle(ArticleEntity article);

    int updateParticipate(ArticleEntity article);



    ArticleEntity selectArticleByIndexEmail(@Param(value="index") int index);

    int updateFinished(ArticleEntity article);


}
