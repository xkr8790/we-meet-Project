package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.LoginResult;
import com.bsh.projectwemeet.services.LoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView("home/login");
        return modelAndView;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postLogin(HttpSession session, UserEntity user){
        LoginResult result = this.loginService.login(user);
        if(result == LoginResult.SUCCESS){
            session.setAttribute("user", user);
        }
        JSONObject responseObject = new JSONObject(){{
           put("result", result.name().toLowerCase());
        }};

        return responseObject.toString();
}


}
