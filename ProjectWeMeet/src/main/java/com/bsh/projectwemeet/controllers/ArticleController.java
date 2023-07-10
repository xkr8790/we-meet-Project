package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.FinishResult;
import com.bsh.projectwemeet.enums.InsertParticipate;
import com.bsh.projectwemeet.enums.PatchArticleResult;
import com.bsh.projectwemeet.enums.SelectParticipantsResult;
import com.bsh.projectwemeet.services.ArticleService;
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

import javax.servlet.http.Cookie;
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

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "article",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView getArticle() {
        ModelAndView modelAndView = new ModelAndView("home/article"); //index.html 연결

        String movie = "영화";
        String game = "게임";
        String sports   = "운동";
        String walk = "산책";
        String eat = "식사";
        String meet = "만남";

        ArticleEntity[] articles = this.articleService.getAll();

        ArticleEntity[] articleMovie = this.articleService.getCategory(movie);
        ArticleEntity[] articleGame = this.articleService.getCategory(game);
        ArticleEntity[] articleSports = this.articleService.getCategory(sports);
        ArticleEntity[] articleWalk = this.articleService.getCategory(walk);
        ArticleEntity[] articleEat = this.articleService.getCategory(eat);
        ArticleEntity[] articleMeet = this.articleService.getCategory(meet);

        modelAndView.addObject("article", articles);
        modelAndView.addObject("articleMovie", articleMovie);
        modelAndView.addObject("articleGame", articleGame);
        modelAndView.addObject("articleSports", articleSports);
        modelAndView.addObject("articleWalk", articleWalk);
        modelAndView.addObject("articleEat", articleEat);
        modelAndView.addObject("articleMeet", articleMeet);

        return modelAndView;
    } //게시판 전부 가져오기


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
    public ModelAndView getRead(@RequestParam(value = "index") int index) {
        ModelAndView modelAndView = new ModelAndView("home/bulletin");

        // articleService를 통해 index에 해당하는 게시글을 가져옵니다.
        ArticleEntity article = this.articleService.readArticle(index);

        ArticleEntity[] articles = this.articleService.getMiniArticle();

        // ModelAndView에 "article"이라는 이름으로 가져온 게시글을 추가합니다.
        modelAndView.addObject("article", article);
        modelAndView.addObject("articles", articles);

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
        boolean result = this.articleService.Participate(index, participants, session);
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







}


