package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.CommentEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.CreateCommentResult;
import com.bsh.projectwemeet.enums.DeleteCommentResult;
import com.bsh.projectwemeet.enums.*;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import com.bsh.projectwemeet.models.PagingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Service
public class ArticleService {

    public final ArticleMapper articleMapper;

    public static final int PAGE_COUNT=6; //페이지에 나타낼 게시물 갯수

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }


    public int getCountCategory(String category){
        return this.articleMapper.selectCountCategory(category);
    }

    public ArticleEntity[] getCountCategoryByPage(PagingModel pagingModel,String category){
        return this.articleMapper.selectCountCategoryByPage(pagingModel,category);
    }


    public ArticleEntity[] getMainArticle() {
        return this.articleMapper.selectArticleMain();
    }//메인에 나타낼 게시판 개수는 XML에서 LIMIT 6으로 제한

    public ArticleEntity[] getMiniArticle(){
        return this.articleMapper.selectDifferentArticle();
    }



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
    } //게시판 나타내기



    public boolean Participate(int index, ParticipantsEntity participants, HttpSession session) {

        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        UserEntity loginUser = (UserEntity) session.getAttribute("user");


        if (loginUser.getEmail() == null) {
            return false;
        } //로그인한유저가 없을시 실패

        if (article.getParticipation() >= article.getLimitPeople()) {
            return false;
        } //제한인원을 초과해서 입력시 실패



        participants.setArticleIndex(article.getIndex())
                .setCreatedAt(new Date())
                .setEmail(loginUser.getEmail())
                .setCheckParticipationStatus(true);


        if (this.articleMapper.insertParticipants(participants) > 0) {
            article.setParticipation(article.getParticipation() + 1);
            this.articleMapper.updateParticipate(article);
            return true; //insert 성공시 참가수 1명 증가
        } else {
            return false; //insert 실패시 바로 실패 반환
        }
    } //인원 참가 클릭시 참가 할수있음 (중복 불가) -> DB 유니크 제약조건


    public boolean checkParticipationStatus(int index, ParticipantsEntity participants, HttpSession session) {
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        participants = this.articleMapper.selectParticipants(article.getIndex());
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if (loginUser != null && participants != null && loginUser.getEmail().equals(participants.getEmail())) {
            return participants.isCheckParticipationStatus();
        }

        return false; // 기본값은 참여하지 않은 것으로 설정
    }


    public SelectParticipantsResult SelectParticipants(int index, ParticipantsEntity participants, HttpSession session) {

        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);

        participants = this.articleMapper.selectParticipants(article.getIndex());

        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if (participants == null) {
            return SelectParticipantsResult.FAILURE;
        }


        if(!Objects.equals(loginUser.getEmail(), participants.getEmail())){
            return SelectParticipantsResult.FAILURE;
        } //참여자의 이메일과 지금 로그인한 이메일의 사용자가 같지않다면 실패 반환


        if(article.getParticipation() <= article.getLimitPeople()){
            article.setParticipation(article.getParticipation() - 1);
            this.articleMapper.updateParticipate(article);
        }    // 게시글의 참가 인원을 1명 줄입니다


        return this.articleMapper.deleteByParticipants(participants.getIndex()) > 0
                ? SelectParticipantsResult.SUCCESS
                : SelectParticipantsResult.FAILURE;
    } //참여 인원 삭제

    public boolean deleteByIndex(int index,ArticleEntity article,HttpSession session){

        article = this.articleMapper.selectArticleByIndex(index);
        UserEntity user = (UserEntity) session.getAttribute("user");

        if(user.isAdmin() || Objects.equals(article.getEmail(), user.getEmail())){
            return this.articleMapper.deleteByArticle(index) > 0;
        }else{
            return false;
        }

    }

    public ArticleEntity getPatchIndexArticle(int index,HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);

        if(!Objects.equals(loginUser.getEmail(), article.getEmail())){
            return null; //사용자의 이메일과 작성자의 이메일이 같지 않다면 수정못함
        }

        return this.articleMapper.selectArticleByPatchIndex(index);
    }

    public ArticleEntity[] getPatchIndexArticleHashTag(int index){
        return this.articleMapper.selectArticleByPatchHashTag(index);
    }



    public PatchArticleResult UpdateArticle(ArticleEntity article,HttpSession session) {

        UserEntity user = (UserEntity) session.getAttribute("user");

        if(user == null){
            return PatchArticleResult.FAILURE;
        }

        if (article.getThumbnail() == null || article.getThumbnailMime() == null) {
            article.setThumbnail(article.getThumbnail())
                    .setThumbnailMime(article.getThumbnailMime());
        }

        return this.articleMapper.updateArticleContent(article) > 0
                ? PatchArticleResult.SUCCESS
                : PatchArticleResult.FAILURE;
    }

    public LIkeAndReportResult UpdateLikeResult(int index, HttpSession session) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);

        if(Objects.equals(user.getEmail(), article.getEmail())){
            return LIkeAndReportResult.FAILURE; //7사용자의 이메일과 작성자의 이메일이 같다면 실패 / 좋아요 싫어요 실패
        }

        article.setLikeCount(article.getLikeCount() + 1);

        return this.articleMapper.updateLike(article) > 0
                ? LIkeAndReportResult.SUCCESS
                : LIkeAndReportResult.FAILURE;
    }

    public LIkeAndReportResult UpdateReportResult(int index, HttpSession session) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);

        if(Objects.equals(user.getEmail(), article.getEmail())){
            return LIkeAndReportResult.FAILURE; //사용자의 이메일과 작성자의 이메일이 같다면 실패 / 좋아요 싫어요 실패
        }

        article.setReport(article.getReport() + 1);

        return this.articleMapper.updateReport(article) > 0
                ? LIkeAndReportResult.SUCCESS
                : LIkeAndReportResult.FAILURE;
    }



    //    댓글
    public CommentEntity[] getCommentsOf(int articleIndex) {

        return this.articleMapper.selectCommentByArticleIndex(articleIndex);
    }



    public CreateCommentResult putComment(HttpServletRequest request, CommentEntity comment, HttpSession session, ArticleEntity article) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if (loginUser == null) {
            return CreateCommentResult.FAILURE_NOT_LOGIN; // 로그인 상태가 아닐 때
        }

        comment.setEmail(loginUser.getEmail())
                .setDeleted(false)
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"));

        // 게시글 작성자와 댓글 작성자가 동일한지 확인
        if (article != null && Objects.equals(loginUser.getEmail(), article.getEmail())) {
            return CreateCommentResult.SUCCESS_SAME; // 로그인한 유저와 게시글을 작성한 유저가 동일할 때
        }
        System.out.println(article.getEmail());

        // CommentEntity를 DB에 저장하고 결과에 따라 CreateCommentResult 반환
        return this.articleMapper.insertComment(comment) > 0
                ? CreateCommentResult.SUCCESS
                : CreateCommentResult.FAILURE;

    }



    public DeleteCommentResult deleteComment(CommentEntity comment, HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if (loginUser == null) {
            return DeleteCommentResult.FAILURE_NOT_LOGIN; // 로그인하지 않았을 때
        }

        CommentEntity existingComment = articleMapper.selectComment(comment.getIndex()); //댓글의 주인
        if (existingComment == null) {
            System.out.println(comment.getIndex());
            return DeleteCommentResult.FAILURE; // 존재하지 않는 댓글
        }

        boolean isAdmin = loginUser.isAdmin(); // 로그인한 유저가 관리자 인지 확인
        boolean isCommentOwner = existingComment.getEmail().equals(loginUser.getEmail()); // 댓글의 주인과 로그인한 유저가 동일

        if (!isCommentOwner && !isAdmin) {
            return DeleteCommentResult.FAILURE_NO_AUTHORITY; // 삭제 권한이 없는 경우
        }

        if (existingComment.isDeleted()) {
            return DeleteCommentResult.FAILURE_DELETED; // 이미 삭제된 댓글 일 경우,현재는 삭제 삭제버튼이 없기 때문에 작동할 경우는 없음
        }

        existingComment.setDeleted(true);


        int updateCount = articleMapper.updateComment(existingComment);
        if (updateCount > 0) {
            return DeleteCommentResult.SUCCESS; // 성공
        } else {
            return DeleteCommentResult.FAILURE; // 실패
        }
    }


// 완료페이지로 넘기기
    public UpdateCategoryResult updateCategory(int index, HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        ArticleEntity articles = this.articleMapper.selectArticleByCompleteIndex(index);
        if(loginUser == null){
            System.out.println("서비스1");
            return  UpdateCategoryResult.FAILURE;
        }
        if(articles ==null){
            System.out.println("서비스2");
            return UpdateCategoryResult.FAILURE;
        }
        if(loginUser.getEmail().equals(articles.getEmail())){
            System.out.println("서비스3");
            articles.setCategory("완료");
        }else{
            System.out.println("서비스4");
            return UpdateCategoryResult.FAILURE;
        }
        System.out.println("서비스5");
        return this.articleMapper.updateCategory(articles) > 0
                ? UpdateCategoryResult.SUCCESS
                : UpdateCategoryResult.FAILURE;

    }

    public ArticleEntity[] selectCategory(String category) {

        return this.articleMapper.selectCategory(category);
    }// 완료된 게시판은 전부 나타내기

    public ArticleEntity getUpdateCategoryByIndex(int index){
        return this.articleMapper.selectUpdateCategoryByIndex(index);
    }



}
