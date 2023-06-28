package com.bsh.projectwemeet.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class ArticleController {
    @RequestMapping(value = "article",
            method = RequestMethod.GET)
    public ModelAndView getArticle() {
        ModelAndView modelAndView = new ModelAndView("home/article");
        return modelAndView;
    }

    @RequestMapping(value = "article",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView postWrite() {
        return null;
    }

}
