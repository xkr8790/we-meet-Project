package com.bsh.projectwemeet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class BulletinController {

    @RequestMapping(value="bulletin", method = RequestMethod.GET)
    public ModelAndView getBulletin(){
        ModelAndView modelAndView = new ModelAndView("home/bulletin");
        return modelAndView;
    }
}
