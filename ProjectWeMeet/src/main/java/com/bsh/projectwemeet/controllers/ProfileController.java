package com.bsh.projectwemeet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/profile")
public class ProfileController {

    @RequestMapping(value="profile", method = RequestMethod.GET)
    public ModelAndView getProfile(){
        ModelAndView modelAndView = new ModelAndView("home/profile");
        return modelAndView;
    }
}
