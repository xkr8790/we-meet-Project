package com.bsh.projectwemeet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class WriteController {

    @RequestMapping(value = "write",method = RequestMethod.GET)
    public ModelAndView getWrite () {
        ModelAndView modelAndView = new ModelAndView("home/write");
        return modelAndView;
    } //게시판 주소로 가기

    @RequestMapping(value = "write",method = RequestMethod.POST)
    public ModelAndView postWrite () {
        return null;
    } //게시판 주소로 가기
}
