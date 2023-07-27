package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.enums.selectParticipateProfile;
import com.bsh.projectwemeet.services.ArticleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class HomeController {

    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getMain(boolean isFinished){
        ModelAndView modelAndView = new ModelAndView("home/main");
        ArticleEntity[] articles = this.articleService.getMainArticle(isFinished);
        modelAndView.addObject("article", articles);
        return modelAndView;
    } //메인 홈 주소

    @RequestMapping(value = "/profile/{index}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView getMainArticle(@PathVariable(value = "index")int index){
        System.out.println("Received index: " + index); // index 값을 출력
        ModelAndView modelAndView = new ModelAndView("home/main");
        ParticipantsEntity[] participantsArray = this.articleService.selectParticipantsProfile(index);
        modelAndView.addObject("participantsArray",participantsArray);
        return modelAndView;
    } //메인 홈 주소

    @RequestMapping(value = "/Privacy-Policy",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getPrivacyPolicy(){
        ModelAndView modelAndView = new ModelAndView("home/Privacy-Policy/Privacy-Policy");
        return modelAndView;
    } //메인 홈 주소

    @RequestMapping(value = "/TermsOfService",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getTermsOfService(){
        ModelAndView modelAndView = new ModelAndView("home/Privacy-Policy/Terms-of-Service");
        return modelAndView;
    } //메인 홈 주소


    @RequestMapping(value = "/image",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@RequestParam(value = "index")int index){

        ArticleEntity article = this.articleService.readArticle(index);

        ResponseEntity<byte[]> response;
        if (article == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(article.getThumbnail().length);
            headers.setContentType(MediaType.parseMediaType(article.getThumbnailMime()));
            response = new ResponseEntity<>(article.getThumbnail(),headers,HttpStatus.OK);
        }
        return response;
    }

    @RequestMapping(value = "/read",
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

}
