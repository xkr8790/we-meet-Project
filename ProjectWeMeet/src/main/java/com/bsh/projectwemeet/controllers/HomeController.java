package com.bsh.projectwemeet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class HomeController {
    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    public ModelAndView getMain(){
        ModelAndView modelAndView = new ModelAndView("home/main");
        return modelAndView;
    } //메인 홈 주소

}
