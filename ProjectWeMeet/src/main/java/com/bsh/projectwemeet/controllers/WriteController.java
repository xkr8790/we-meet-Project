package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.enums.InsertArticleResult;
import com.bsh.projectwemeet.services.ArticleService;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class WriteController {

    public final ArticleService articleService;

    public WriteController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @RequestMapping(value = "write",method = RequestMethod.GET)
    public ModelAndView getWrite () {
        ModelAndView modelAndView = new ModelAndView("home/write");
        return modelAndView;
    } //게시판 주소로 가기

    @RequestMapping(value = "write",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String postWrite(HttpServletRequest request, ArticleEntity entity) {
        InsertArticleResult result = this.articleService.putArticle(request,entity);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }
}
