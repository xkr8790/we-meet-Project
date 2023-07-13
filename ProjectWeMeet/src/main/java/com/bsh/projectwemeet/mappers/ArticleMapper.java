package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.models.PagingModel;
import com.bsh.projectwemeet.entities.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Mapper
public interface ArticleMapper {

    int insertArticle(ArticleEntity articles);

    int insertParticipants(ParticipantsEntity participants);


    int selectCountCategory(@Param(value = "category")String category);
    //param을 이용해 관련 카테고리만 조회해서 가져온다


    ArticleEntity[] selectCountCategoryByPage(@Param(value = "pagingModel") PagingModel pagingModel,
                                              @Param(value = "category") String category);
    //카테고리 관련 페이징을 위한 매퍼


    ArticleEntity[] selectArticleMain();
    //메인에 게시물 6개 나타내기 위한 매퍼

    ArticleEntity[] selectDifferentArticle();
    //이런만남은 어때요?를 나타내기 위한 매퍼


    ArticleEntity selectArticleByIndex(@Param(value = "index") int index);
    //클릭시 해당게시물을 가기위해 index값으로 찾기위해 사용되는 매퍼

    ArticleEntity selectArticleByPatchIndex(@Param(value = "index") int index);
    //게시물 수정 인덱스 찾기

    ArticleEntity[] selectArticleByPatchHashTag(@Param(value = "index") int index);
    //게시물 해쉬태그 select

    ParticipantsEntity selectParticipants(@Param(value = "index") int index);
    // 참여인원 중복되있는지 select

    int updateLike(ArticleEntity article);
    //좋아요를 업데이트 하기위한 매퍼

    int updateReport(ArticleEntity article);
    //신고를 업데이트 하기위한 매퍼

    int updateArticle(ArticleEntity article);
    //조회수 수정

    int updateArticleContent(ArticleEntity article);
    //게시판 수정

    int updateParticipate(ArticleEntity article);
    //참여자의 업데이트를 위한 매퍼

    int deleteByArticle(@Param(value = "index")int index);
    //게시물의 인덱스로 삭제하기 위해 사용되는 매퍼

    int deleteByParticipants(@Param(value = "index")int index);
    //참여취소를 위해 Delete하기 위한 매퍼






    ArticleEntity selectArticleByIndexEmail(@Param(value="index") int index);

    int updateFinished(ArticleEntity article);
//    댓글
    CommentEntity[] selectCommentByArticleIndex(@Param(value = "articleIndex")int articleIndex);
    CommentEntity selectComment(@Param(value = "index") int index);

    CommentEntity selectCommentByEmail(CommentEntity comment);

    ArticleEntity selectArticleByEmail(ArticleEntity article);

    ArticleEntity selectArticleByArticleIndex(@Param("articleIndex") int articleIndex);

    int insertComment(CommentEntity comment);

    int updateComment(CommentEntity comment);




}
