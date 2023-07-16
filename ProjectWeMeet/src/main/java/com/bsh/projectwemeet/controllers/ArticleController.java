package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.*;
import com.bsh.projectwemeet.enums.*;
import com.bsh.projectwemeet.models.PagingModel;
import com.bsh.projectwemeet.enums.CreateCommentResult;
import com.bsh.projectwemeet.enums.DeleteCommentResult;
import com.bsh.projectwemeet.services.ArticleService;
import com.bsh.projectwemeet.services.ReviewService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/")
public class ArticleController {

    private final ArticleService articleService;
    private final ReviewService reviewService;

    @Autowired
    public ArticleController(ArticleService articleService, ReviewService reviewService) {
        this.articleService = articleService;
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "article",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getArticle(@RequestParam(value = "p", defaultValue = "1", required = false) int requestPage,
                                   @RequestParam(value = "category", required = false) String category,
                                   boolean isFinished) {
        ModelAndView modelAndView = new ModelAndView("home/article"); //index.html 연결

        PagingModel pagingCategory = new PagingModel(
                ArticleService.PAGE_COUNT, //메모서비스의 읽기 전용 변수 접근
                this.articleService.getCountCategory(category),
                requestPage); //객체화


        ArticleEntity[] articleCategory = this.articleService.getCountCategoryByPage(pagingCategory, category);
        //페이징하면서 카테고리 관련 게시물 나타내기

        modelAndView.addObject("articleCategory", articleCategory);
        modelAndView.addObject("pagingCategory", pagingCategory);
        modelAndView.addObject("category", category);

        return modelAndView;

    } //게시판 카테고리별//


    @RequestMapping(value = "article/image",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@RequestParam(value = "index") int index) {

        ArticleEntity article = this.articleService.readArticle(index);

        ResponseEntity<byte[]> response;
        if (article == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(article.getThumbnail().length);
            headers.setContentType(MediaType.parseMediaType(article.getThumbnailMime()));
            response = new ResponseEntity<>(article.getThumbnail(), headers, HttpStatus.OK);
        }
        return response;
    }


    @RequestMapping(value = "article/category/image",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCategoryThumbnail(@RequestParam(value = "index") int index) {

        ArticleEntity article = this.articleService.readArticle(index);

        ResponseEntity<byte[]> response;
        if (article == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(article.getThumbnail().length);
            headers.setContentType(MediaType.parseMediaType(article.getThumbnailMime()));
            response = new ResponseEntity<>(article.getThumbnail(), headers, HttpStatus.OK);
        }
        return response;
    }


    @RequestMapping(value = "article/read",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index") int index,HttpSession session,boolean flag) {
        ModelAndView modelAndView = new ModelAndView("home/bulletin");

        ArticleEntity article = this.articleService.readArticle(index);
        ArticleEntity[] articles = this.articleService.getMiniArticle();
        LikeReportEntity LikeResult = this.articleService.selectLike(index,session,flag);
        LikeReportEntity ReportResult = this.articleService.selectReport(index,session,flag);
        ParticipantsEntity ParticipantsResult = this.articleService.selectParticipants(index,session);
        // articleService를 통해 index에 해당하는 게시글을 가져옵니다.


        // ModelAndView에 "article"이라는 이름으로 가져온 게시글을 추가합니다.
        modelAndView.addObject("article", article);
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("LikeResult",LikeResult);
        modelAndView.addObject("ReportResult",ReportResult);
        modelAndView.addObject("ParticipantsResult",ParticipantsResult);

        return modelAndView;
    }//인덱스번호로 각 게시판 값 나타내기

    //인덱스 번호로 사진가져와서 인덱스에 해당하는 게시판에 사진 나타내기

    @RequestMapping(value = "article/read",
            method = RequestMethod.DELETE)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String deleteIndex(@RequestParam(value = "index") int index, ArticleEntity article, HttpSession session) {
        boolean result = this.articleService.deleteByIndex(index, article, session);
        return String.valueOf(result);
    }
    //게시판 삭제

    @RequestMapping(value = "article/patch",
            method = RequestMethod.GET)
    public ModelAndView getWrite(@RequestParam(value = "index") int index, HttpSession session) {
        ArticleEntity article = articleService.getPatchIndexArticle(index,session);
        ArticleEntity[] articleTag = articleService.getPatchIndexArticleHashTag(index);
        ModelAndView modelAndView = new ModelAndView("home/patchWrite");
        modelAndView.addObject("article", article);
        modelAndView.addObject("articleTag",articleTag);
        return modelAndView;
    }
    //게시판 수정 폼 받아오기

    @RequestMapping(value = "article/patch",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String PatchWrite(@RequestParam(value = "dayStr") String dayStr,
                             @RequestParam(value = "timeStr") String timeStr,
                             @RequestParam(value = "limit") String limit,
                             @RequestParam(value = "thumbnailMultipart", required = false) MultipartFile thumbnailMultipart,
                             ArticleEntity article,
                             HttpSession session) throws IOException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day = sdf.parse(dayStr);
        article.setAppointmentStartDate(day);

        SimpleDateFormat tsdf = new SimpleDateFormat("HH:mm");
        Date time = tsdf.parse(timeStr);
        article.setAppointmentStartTime(time);

        int limitPeople = Integer.parseInt(limit);
        article.setLimitPeople(limitPeople);


        if (thumbnailMultipart != null && !thumbnailMultipart.isEmpty()) {
            article.setThumbnail(thumbnailMultipart.getBytes())
                    .setThumbnailMime(thumbnailMultipart.getContentType());
        }


        PatchArticleResult result = this.articleService.UpdateArticle(article, session);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());
        return responseObject.toString();

    }


    @RequestMapping(value = "article/Participate",
            method = RequestMethod.POST)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String postParticipate(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        boolean result = this.articleService.InsertParticipate(index, participants, session);
        return String.valueOf(result);
    } //인원참가시 1증가하는 POST 방식 컨트롤러 (중복 체크 불가)

    @RequestMapping(value = "article/Participate",
            method = RequestMethod.GET)
    public ModelAndView getArticle(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        boolean result = this.articleService.checkParticipationStatus(index, participants, session);
        ModelAndView modelAndView = new ModelAndView("home/bulletin");
        modelAndView.addObject("result",result);
        return modelAndView;
    } //인원의 참가여부 select로 표현


    @RequestMapping(value = "article/Participate",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody //있어야지 JSON 사용가능
    public String patchParticipate(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        SelectParticipantsResult result = this.articleService.SelectParticipants(index, participants, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    } //인원이 참가 했을시 취소 가능하게

    @RequestMapping(value = "article/like",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postLike(@RequestParam(value = "index") int index, LikeReportEntity likeEntity, HttpSession session,boolean flag) {
        InsertLikeAndReportResult result = this.articleService.InsertLike(index,likeEntity,session,flag);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }
    //좋아요를 누르면 좋아요가 오르고 새로고침됨 - 중복시 좋아요 못함 - 게시판 작성자와 로그인 사용자 이메일 같으면 실패

    @RequestMapping(value = "article/report",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postReport(@RequestParam(value = "index") int index, LikeReportEntity likeEntity, HttpSession session,boolean flag) {
        InsertLikeAndReportResult result = this.articleService.InsertReport(index,likeEntity,session,flag);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }
    //를 누르면 좋아요가 오르고 새로고침됨 - 중복시 좋아요 못함 - 게시판 작성자와 로그인 사용자 이메일 같으면 실패

    @RequestMapping(value = "article/like",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteLike(@RequestParam(value = "index") int index,HttpSession session,boolean flag) {
        DeleteLikeReportResult result = this.articleService.deleteLike(index,session,flag);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }
    //좋아요 취소하는 컨트롤러

    @RequestMapping(value = "article/report",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteReport(@RequestParam(value = "index") int index,HttpSession session,boolean flag) {
        DeleteLikeReportResult result = this.articleService.deleteReport(index,session,flag);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }
    //신고를 취소하는 컨트롤러





    @RequestMapping(value="article/review", method = RequestMethod.GET)
    public ModelAndView getFinish(int index, HttpSession session){
        boolean result = this.articleService.patchFinish(index ,session);
        ModelAndView modelAndView = new ModelAndView("home/review");
        modelAndView.addObject("result", result);
        return modelAndView;
   }
//   게시물 작성자와 로그인된 아이디가 같은지 다른지에 대한 여부를 통해 페이지 넘어가게 하기

//    @RequestMapping(value="article/review", method = RequestMethod.PATCH)
//    @ResponseBody
//    public String patchFinished(ArticleEntity article, HttpSession session){
//        FinishResult result = this.articleService.patchFinished(article, session);
//        JSONObject responseObject = new JSONObject() {{
//            put("result", result.name().toLowerCase());
//        }};
//        return responseObject.toString();
//    }







//    댓글

    @RequestMapping(value = "comment",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommentEntity[] getComment(@RequestParam(value = "articleIndex")int articleIndex){
        return this.articleService.getCommentsOf(articleIndex);
    }

//    @RequestMapping(value = "comment",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public String postComment(HttpServletRequest request,
//                              CommentEntity comment,
//                              HttpSession session){
//        boolean result = this.articleService.putComment(request, comment,session);
//
//
//        return String.valueOf(result);
//    }

    @RequestMapping(value = "comment",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postComment(HttpServletRequest request,
                              CommentEntity comment,
                              HttpSession session,
                              ArticleEntity article){
        CreateCommentResult result = this.articleService.putComment(request, comment, session, article);
        JSONObject responseObject = new JSONObject(){{
            put("result",result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "comment",
            method = RequestMethod.DELETE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String deleteComment(@RequestParam(value = "index") int commentIndex, HttpSession session) {
        CommentEntity comment = new CommentEntity();
        comment.setIndex(commentIndex);

        DeleteCommentResult result = this.articleService.deleteComment(comment, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }


//    완료 후 다음으로 보내기

    @RequestMapping(value="article/review", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUpdateCategory(@RequestParam(value="index")int index, String category){
        ModelAndView modelAndView = new ModelAndView("home/review");
        ArticleEntity article = this.articleService.getUpdateCategoryByIndex(index);
        ReviewEntity[] reviewEntities = this.reviewService.getAll();
        modelAndView.addObject("article", article);
        modelAndView.addObject("reviews", reviewEntities);
        return modelAndView;
    }



    @RequestMapping(value="article/review", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateCategory(int index, HttpSession session){
        UpdateCategoryResult result = this.articleService.updateCategory(index, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }






}
