package com.bsh.projectwemeet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class confirmEmailController {

    @RequestMapping(value="confirmEmail", method = RequestMethod.GET)
    public ModelAndView getConfirmEmail(){
        ModelAndView modelAndView = new ModelAndView("home/confirmEmail");
        return modelAndView;
    }

}
