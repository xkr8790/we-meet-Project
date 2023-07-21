package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ReviewEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "article")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "review", method = RequestMethod.GET)
    public ModelAndView getReview(@RequestParam(value = "index") int articleIndex) {
        System.out.println("???");
        ModelAndView modelAndView = new ModelAndView("home/review");
        ReviewEntity[] reviewEntities = this.reviewService.selectAll(articleIndex);

        modelAndView.addObject("reviews", reviewEntities);

        return modelAndView;
    }

    @RequestMapping(value = "/review/index", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postReview(HttpServletRequest request, ReviewEntity reviewEntity, @SessionAttribute(value = "user") UserEntity user) {
        reviewEntity.setNickname(user.getNickname());
        reviewEntity.setEmail(user.getEmail());
        boolean result = this.reviewService.reviewWrite(request, reviewEntity);
        return String.valueOf(result);
    }


    @RequestMapping(value = "review/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteReview(@RequestParam(value = "index") int index) {
        boolean result = this.reviewService.deleteByIndex(index);
        System.out.println(result);
        return String.valueOf(result);
    }


}
