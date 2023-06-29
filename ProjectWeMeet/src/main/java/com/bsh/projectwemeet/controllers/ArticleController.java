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
public class ArticleController {


    @RequestMapping(value = "article",
            method = RequestMethod.GET)
    public ModelAndView getArticle() {
        ModelAndView modelAndView = new ModelAndView("home/article");
        return modelAndView;
    }

}
