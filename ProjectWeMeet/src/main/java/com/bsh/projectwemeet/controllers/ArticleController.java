package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.enums.InsertParticipate;
import com.bsh.projectwemeet.services.ArticleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
    public ModelAndView getArticle() {
        ModelAndView modelAndView = new ModelAndView("home/article"); //index.html 연결
        ArticleEntity[] articles = this.articleService.getAll();
        modelAndView.addObject("article", articles);
        return modelAndView;
    }

    @RequestMapping(value = "article/read",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index") int index) {
        ModelAndView modelAndView = new ModelAndView("home/bulletin");

        // articleService를 통해 index에 해당하는 게시글을 가져옵니다.
        ArticleEntity article = this.articleService.readArticle(index);

        // ModelAndView에 "article"이라는 이름으로 가져온 게시글을 추가합니다.

        modelAndView.addObject("article", article);

        return modelAndView;
    }

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
    } //인덱스 번호로 사진가져와서 인덱스에 해당하는 게시판에 사진 나타내기


    @RequestMapping(value = "article/read",
            method = RequestMethod.DELETE)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String deleteIndex(@RequestParam(value = "index") int index, ArticleEntity article, HttpSession session) {
        boolean result = this.articleService.deleteByIndex(index, article, session);
        System.out.println(String.valueOf(result));
        return String.valueOf(result);
    } //게시판 삭제

    @RequestMapping(value = "article/patch",
            method = RequestMethod.GET)
    public ModelAndView getWrite(@RequestParam(value = "index") int index, HttpSession session, ArticleEntity article) {
        article = articleService.getPatchIndexArticle(index);
        ModelAndView modelAndView = new ModelAndView("home/patchWrite");
        modelAndView.addObject("article", article);
        return modelAndView;
    } //게시판 수정하는 폼 받아오기

    @RequestMapping(value = "article/Participate",
            method = RequestMethod.POST)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String postParticipate(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        InsertParticipate result = this.articleService.Participate(index, participants, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    } //인원참가


    @RequestMapping(value = "article/Participate",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView patchArticle(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session){
        boolean result = this.articleService.SelectParticipants(index, participants, session);
        ModelAndView modelAndView = new ModelAndView("home/bulletin");
        modelAndView.addObject("result", result);
        return modelAndView;
    } //참가한 인원이 클릭시 Select
}
