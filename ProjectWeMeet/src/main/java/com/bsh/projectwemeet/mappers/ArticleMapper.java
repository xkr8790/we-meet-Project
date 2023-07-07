package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleEntity articles);

    int insertParticipants(ParticipantsEntity participants);

    ArticleEntity[] selectAll();

    ArticleEntity[] selectArticleMain();

    ArticleEntity[] selectDifferentArticle();

    ArticleEntity selectCategory(@Param(value = "category")String category);



    ArticleEntity selectArticleByIndex(@Param(value = "index") int index);

    ArticleEntity selectArticleByPatchIndex(@Param(value = "index")int index);

    ParticipantsEntity selectParticipants(@Param(value = "index") int index);

    int deleteByArticle(@Param(value = "index")int index); //Param 사용시 SQL문에 파라미터 타입을 안사용해도된다

    int deleteByParticipants(@Param(value = "index")int index); //Param 사용시 SQL문에 파라미터 타입을 안사용해도된다


    int updateArticle(ArticleEntity article);
    //게시글 수정

    int updateArticleContent(@Param(value = "index") int index,
                             String title,
                             String category,
                             String content,
                             String place,
                             String address,
                             Date appointmentStartDate,
                             Date appointmentStartTime,
                             double latitude,
                             double longitude,
                             MultipartFile thumbnailMultipart,
                             @Param(value = "thumbnailMime") String thumbnailMime); //게시판 수정


    int updateParticipate(ArticleEntity article);


}
