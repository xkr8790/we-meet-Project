package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.*;
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

    public ArticleEntity getArticleByIndex(int index) {
        return this.articleMapper.selectArticleByIndex(index);
    }

    public ArticleEntity[] getMainArticle(boolean isFinished) {
        return this.articleMapper.selectArticleMain(isFinished);
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



    public boolean InsertParticipate(int index, ParticipantsEntity participants, HttpSession session) {

        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if(loginUser.getEmail() == article.getEmail()){
            return false;
        }

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

    public SelectParticipantsResult selectParticipants(int index,HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);

        if(user == null){
            return SelectParticipantsResult.FAILURE_LOGIN;
        }

        if(Objects.equals(user.getEmail(), article.getEmail())){
            return SelectParticipantsResult.FAILURE_MINE;
        }

        return this.articleMapper.selectCheckParticipants(index,user.getEmail()) != null
                ? SelectParticipantsResult.SUCCESS
                : SelectParticipantsResult.FAILURE;
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


    public InsertLikeAndReportResult InsertLike(int index, LikeReportEntity likeEntity, HttpSession session,boolean flag) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index); //인덱스로 게시판찾기
        flag = true;

        if (Objects.equals(user.getEmail(), article.getEmail())) {
            return InsertLikeAndReportResult.FAILURE;
        } //자기가 작성한 이메일에 좋아요를 못하게하는 if문


        if (articleMapper.selectLike(index, user.getEmail(),flag) != null) {
            return InsertLikeAndReportResult.FAILURE_LIKE;
        } //만약에 아이디와 게시판 인덱스를 검색해서 이미 좋아요를 했다면 select해서 중복실패가 나오게한다.


        if (articleMapper.selectLike(index, user.getEmail(),flag) == null) {
            article.setLikeCount(article.getLikeCount() + 1);
            likeEntity.setArticleIndex(index)
                    .setEmail(user.getEmail())
                    .setCreatedAt(new Date())
                    .setLikeFlag(true);
            articleMapper.updateLike(article);
            return articleMapper.insertLike(likeEntity) > 0
                    ? InsertLikeAndReportResult.SUCCESS_LIKE
                    : InsertLikeAndReportResult.FAILURE_LIKE;
        } //좋아요 인서트


      return InsertLikeAndReportResult.FAILURE;
    }

    public InsertLikeAndReportResult InsertReport(int index, LikeReportEntity likeEntity, HttpSession session,boolean flag) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index); //인덱스로 게시판찾기
        flag = true;

        if (Objects.equals(user.getEmail(), article.getEmail())) {
            return InsertLikeAndReportResult.FAILURE;
        } //자기가 작성한 이메일에 좋아요를 못하게하는 if문

        if (articleMapper.selectReport(index, user.getEmail(),flag) != null) {
            return InsertLikeAndReportResult.FAILURE_REPORT;
        } //만약에 아이디와 게시판 인덱스를 검색해서 이미 좋아요를 했다면 select해서 중복실패가 나오게한다.

        if (articleMapper.selectReport(index, user.getEmail(),flag) == null) {
            article.setReport(article.getReport() + 1);
            likeEntity.setArticleIndex(index)
                    .setEmail(user.getEmail())
                    .setCreatedAt(new Date())
                    .setReportFlag(true);
            articleMapper.updateReport(article);
            return articleMapper.insertReport(likeEntity) > 0
                    ? InsertLikeAndReportResult.SUCCESS_REPORT
                    : InsertLikeAndReportResult.FAILURE_REPORT;
        } //싫어요 인서트

        return InsertLikeAndReportResult.FAILURE;
    }

    public LikeReportEntity selectLike(int index,HttpSession session,boolean flag){
        UserEntity user = (UserEntity) session.getAttribute("user");
        flag = true;

        if (user == null) {
            // 예외 처리: user가 null인 경우에 대한 처리 로직 추가
            return null; // 또는 예외 처리에 맞는 반환값 설정
        }

        LikeReportEntity result = articleMapper.selectLike(index, user.getEmail(), flag);

        if(result != null){
            return result;
        }

       return null;
    }

    public LikeReportEntity selectReport(int index,HttpSession session,boolean flag){
        UserEntity user = (UserEntity) session.getAttribute("user");
        flag = true;

        if (user == null) {
            // 예외 처리: user가 null인 경우에 대한 처리 로직 추가
            return null; // 또는 예외 처리에 맞는 반환값 설정
        }

        LikeReportEntity result = articleMapper.selectReport(index, user.getEmail(), flag);

        if(result != null){
            return result;
        }

        return null;
    }

    public DeleteLikeReportResult deleteLike(int index,HttpSession session, boolean flag){
        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index); //인덱스로 게시판찾기
        flag = true;

        article.setLikeCount(article.getLikeCount() - 1);
        articleMapper.updateLike(article);

        return this.articleMapper.deleteByLike(index, user.getEmail(),flag) > 0
                ? DeleteLikeReportResult.SUCCESS_LIKE
                : DeleteLikeReportResult.FAILURE_LIKE;
    }

    public DeleteLikeReportResult deleteReport(int index,HttpSession session, boolean flag){
        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index); //인덱스로 게시판찾기
        flag = true;

        article.setReport(article.getReport() - 1); //신고 취소할경우 줄어들기
        articleMapper.updateReport(article);  //업데이트

        return this.articleMapper.deleteByReport(index, user.getEmail(),flag) > 0
                ? DeleteLikeReportResult.SUCCESS_REPORT
                : DeleteLikeReportResult.FAILURE_REPORT;
    }







    //-----------------------------------------------게시판 리뷰-------------------------------------------------------------
    public boolean patchFinish(int index, HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        ArticleEntity articles = this.articleMapper.selectArticleByIndexEmail(index);

        if (articles == null || loginUser == null || !loginUser.getEmail().equals(articles.getEmail())) {
            return false;
        }

        articles.setFinished(true);
        return this.articleMapper.updateFinished(articles) > 0;
    }


//    public FinishResult patchFinished(ArticleEntity article, HttpSession session){
//        UserEntity loginUser = (UserEntity) session.getAttribute("user");
//        ArticleEntity articles = this.articleMapper.selectArticleByIndexEmail(article);
//
//        if (articles == null || loginUser == null || !loginUser.getEmail().equals(articles.getEmail())) {
//            return FinishResult.FAILURE;
//        }
//        articles.setFinished(true);
//        return this.articleMapper.updateFinished(articles) > 0
//                ? FinishResult.SUCCESS
//                : FinishResult.SUCCESS;
//    }


//-------------------------------------- 댓글 리뷰 --------------------------------------------------------------

    //    댓글
    public CommentEntity[] getCommentsOf(int articleIndex) {

        return this.articleMapper.selectCommentByArticleIndex(articleIndex);
    }




    public CreateCommentResult putComment(HttpServletRequest request, CommentEntity comment, HttpSession session, String articleEmail,String nickname) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if (loginUser == null) {
            return CreateCommentResult.FAILURE_NOT_LOGIN; // 로그인 상태가 아닐 때
        }

        comment.setEmail(loginUser.getEmail())
                .setDeleted(false)
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setNickname(loginUser.getNickname());

        // 게시글 작성자와 댓글 작성자가 동일한지 확인
        if (articleEmail != null && loginUser.getEmail().equals(articleEmail)) {
            // Insert the comment for the 'SUCCESS_SAME' case
            int rowsAffected = articleMapper.insertComment(comment);
            if (rowsAffected > 0) {
                return CreateCommentResult.SUCCESS_SAME;
            } else {
                return CreateCommentResult.FAILURE;
            }
        } else {
            // Insert the comment for the 'SUCCESS' case
            int rowsAffected = articleMapper.insertComment(comment);
            if (rowsAffected > 0) {
                return CreateCommentResult.SUCCESS;
            } else {
                return CreateCommentResult.FAILURE;
            }
        }
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
            return DeleteCommentResult.FAILURE_DELETED; // 이미 삭제된 댓글 일 경우
        }

        existingComment.setDeleted(true);


        int updateCount = articleMapper.updateComment(existingComment);
        if (updateCount > 0) {
            return DeleteCommentResult.SUCCESS; // 성공
        } else {
            return DeleteCommentResult.FAILURE; // 실패
        }
    }

    public UserEntity userEmail(HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        if(articleMapper.selectUser(loginUser.getEmail())!=null){
            return  this.articleMapper.selectUser(loginUser.getEmail());
        }

        return null;
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
            articles.setFinished(true);
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
