package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.CommentEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Service
public class ArticleService {

    public final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public ArticleEntity[] getAll() {
        return this.articleMapper.selectAll();
    }//게시판은 많으니 배열

    public ArticleEntity[] getMainArticle() {
        return this.articleMapper.selectArticleMain();
    }//메인에 나타낼 게시판 개수는 XML에서 LIMIT6으로 제한

    public ArticleEntity readArticle(int index) {

        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        // 주어진 index를 사용하여 articleMapper에서 해당 index에 해당하는 게시글을 조회합니다.

        if (article != null && !article.isDeleted()) {
            // 게시글이 존재하고 삭제되지 않은 경우에만 처리합니다.

            article.setView(article.getView() + 1);
            // 게시글의 조회수(view)를 1 증가시킵니다.

            this.articleMapper.updateArticle(article);
            // 조회수를 업데이트합니다.
        }
        return article;
        //결과적으로 삭제되지않거나
        //결과적으로 삭제되지않거나
    }

//    public boolean Participate(int index) {
//
//        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
//
//        if (article != null && !article.isDeleted()) {
//            // 게시글이 존재하고 삭제되지 않은 경우에만 처리합니다.
//            if (article.getParticipation() <= article.getLimitPeople()) {
//                article.setParticipation(article.getParticipation() + 1);
//                // 게시글의 조회수(view)를 1 증가시킵니다.
//                this.articleMapper.updateArticle(article);
//            }
//        } else {
//            return false;
//        }
//        return this.articleMapper.patchArticle(article)>0;
//    }

    public boolean deleteByIndex(int index, ArticleEntity article, HttpSession session) {

        article = this.articleMapper.selectArticleByIndex(index);
        UserEntity user = (UserEntity) session.getAttribute("user");

        boolean isAdmin = user.isAdmin(); // isAdmin()은 관리자 여부를 판단하는 메서드로 가정하였습니다

        if (isAdmin || Objects.equals(article.getEmail(), user.getEmail())) {
            return this.articleMapper.deleteByArticle(index) > 0;
        } else {
            return false;
        }
    }





    public ArticleEntity getPatchIndexArticle(int index) {
        return this.articleMapper.selectArticleByPatchIndex(index);
    }

    public ArticleEntity patchArticle(int index) {
        return null;
    }


    //    댓글
    public CommentEntity[] getCommentsOf(int articleIndex) {

        return this.articleMapper.selectCommentByArticleIndex(articleIndex);
    }

    public boolean putComment(HttpServletRequest request, CommentEntity comment,HttpSession session){

        UserEntity loginUser = (UserEntity) session.getAttribute("user");


        comment.setEmail(loginUser.getEmail())
                .setDeleted(false)
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"));
        return this.articleMapper.insertComment(comment)>0;
    }


    public boolean deleteComment(CommentEntity comment) {
        comment = this.articleMapper.selectComment(comment.getIndex());
        if (comment == null) {
            return false;
        }
        comment.setDeleted(true);
        return this.articleMapper.updateComment(comment) > 0;
    }


}
