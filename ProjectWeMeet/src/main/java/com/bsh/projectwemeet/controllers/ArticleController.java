package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.services.ArticleService;
import org.apache.ibatis.annotations.Param;
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
import java.util.Objects;

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

    @RequestMapping(value ="article/read",
            method = RequestMethod.DELETE)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String deleteIndex(@RequestParam(value = "index")int index,HttpSession session,ArticleEntity article){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if(!Objects.equals(loginUser.getEmail(), article.getEmail())){
            return null;
        }
        boolean result = this.articleService.deleteByIndex(index);
        return String.valueOf(result);
    }

    @RequestMapping(value = "article/patch",
            method = RequestMethod.GET)
    public ModelAndView getWrite(@RequestParam(value = "index")int index, HttpSession session,ArticleEntity article) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if(loginUser.getEmail()!= article.getEmail()){
            return null;
        }
        article = articleService.getPatchIndexArticle(index);
        ModelAndView modelAndView = new ModelAndView("home/patchWrite");
        modelAndView.addObject("article",article);
        return modelAndView;
    } //게시판 수정하는 폼 받아오기


    //    @RequestMapping(value = "article/patch",
    //            method = RequestMethod.PATCH)
    //    @ResponseBody
    //    public String patchArticle(@Param(value = "index")int index,
    //                            @Param(value = "text") String text){
    //        boolean result = this.patchArticle(null);
    //        return String.valueOf(result); //참이면 결과 반환
    //    } //게시판 수정


}
