package com.bsh.projectwemeet.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class MainController {
    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getMain(){
        ModelAndView modelAndView = new ModelAndView("home/main");
        return modelAndView;
    }

    @RequestMapping(value="recoverAccount", method = RequestMethod.GET)
    public ModelAndView getRecoverAccount () {
        ModelAndView modelAndView = new ModelAndView("home/recoverAccount");
        return modelAndView;
    }

}
