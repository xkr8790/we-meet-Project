package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.models.PagingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleEntity articles);

    int insertParticipants(ParticipantsEntity participants);


    ArticleEntity[] selectAll();

    int selectCount( @Param(value = "searchCriterion")String searchCriterion,
                     @Param(value = "searchQuery")String searchQuery);
    //select문의 id와 같기때문에 셀렉트된 데이터의 개수 모두 가져오기

    ArticleEntity[] selectByPage(@Param(value = "pagingModel") PagingModel pagingModel);

    ArticleEntity[] selectByPageCategory(@Param(value = "category") String category,
                                         @Param(value = "pagingModel") PagingModel pagingModel);

    ArticleEntity[] selectArticleMain();

    ArticleEntity[] selectDifferentArticle();


    ArticleEntity selectArticleByIndex(@Param(value = "index") int index);

    ArticleEntity selectArticleByPatchIndex(@Param(value = "index") int index);

    ArticleEntity[] selectArticleByPatchHashTag(@Param(value = "index") int index);

    ParticipantsEntity selectParticipants(@Param(value = "index") int index);

    int deleteByArticle(@Param(value = "index")int index); //Param 사용시 SQL문에 파라미터 타입을 안사용해도된다

    int deleteByParticipants(@Param(value = "index")int index); //Param 사용시 SQL문에 파라미터 타입을 안사용해도된다

    int updateLike(ArticleEntity article);

    int updateReport(ArticleEntity article);


    int updateArticle(ArticleEntity article);
    //게시글 수정

    int updateArticleContent(ArticleEntity article); //게시판 수정


    int updateParticipate(ArticleEntity article);






    ArticleEntity selectArticleByIndexEmail(@Param(value="index") int index);

    int updateFinished(ArticleEntity article);


}
