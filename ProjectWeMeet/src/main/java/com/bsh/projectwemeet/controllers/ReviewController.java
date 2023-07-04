package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ReviewEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.mappers.ReviewMapper;
import com.bsh.projectwemeet.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/")
public class ReviewController {

    private final ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService  reviewService){
        this.reviewService = reviewService;
    }

    @RequestMapping(value="review", method = RequestMethod.GET)
    public ModelAndView getReview(ReviewEntity reviewEntity){
        ModelAndView modelAndView = new ModelAndView("home/review");
//        modelAndView.addObject("reviews",reviewEntity);
        return modelAndView;
    }

    @RequestMapping(value="review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postReview(HttpServletRequest request, ReviewEntity reviewEntity, @SessionAttribute(value="user") UserEntity user){
        reviewEntity.setEmail(user.getEmail());
        boolean result = this.reviewService.reviewWrite(request, reviewEntity);
        return  String.valueOf(result);
    }


    @RequestMapping(value="review", method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String deleteReview(int index){
        boolean result = this.reviewService.deleteByIndex(index);
        return String.valueOf(result);
    }



}
