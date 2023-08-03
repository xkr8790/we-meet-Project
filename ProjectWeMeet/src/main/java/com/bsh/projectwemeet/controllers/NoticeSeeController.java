package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.services.NoticeSeeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class NoticeSeeController {

    private final NoticeSeeService noticeSeeService;

    public NoticeSeeController(NoticeSeeService noticeSeeService) {
        this.noticeSeeService = noticeSeeService;
    }

    @RequestMapping(value = "notice",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getNotice(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/Privacy-Policy/notice");
        UserEntity user = this.noticeSeeService.CheckUser(session);
        modelAndView.addObject("user",user);
        return modelAndView;
    } //메인 홈 주소
}
