package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.services.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class ArticleController {
    @RequestMapping(value = "article",method = RequestMethod.GET)
    public ModelAndView getArticle () {
        ModelAndView modelAndView = new ModelAndView("home/Article-Write");
        return modelAndView;
    } //게시판 주소로 가기
}