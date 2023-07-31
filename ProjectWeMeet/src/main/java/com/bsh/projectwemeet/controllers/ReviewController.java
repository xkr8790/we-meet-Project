package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.ReviewEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.ReviewResult;
import com.bsh.projectwemeet.services.ReviewService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping(value = "article")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    @RequestMapping(value = "review", method = RequestMethod.GET)
//    public ModelAndView getReview(@RequestParam(value = "index") int articleIndex) {
//        System.out.println("???");
//        ModelAndView modelAndView = new ModelAndView("home/review");
//        ReviewEntity[] reviewEntities = this.reviewService.selectAll(articleIndex);
//
//        modelAndView.addObject("reviews", reviewEntities);
//
//        return modelAndView;
//    }

    @RequestMapping(value = "review/index", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postReview(@SessionAttribute(value = "user") UserEntity user,
                             HttpServletRequest request, ReviewEntity reviewEntity, HttpSession session) {
        reviewEntity.setNickname(user.getNickname());
        reviewEntity.setEmail(user.getEmail());

//        System.out.println(reviewEntity.getArticleIndex());
        ReviewResult result = this.reviewService.reviewWrite(request, reviewEntity, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }


    @RequestMapping(value = "review/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteReview(@RequestParam(value = "index") int index) {
        boolean result = this.reviewService.deleteByIndex(index);
        System.out.println(result);
        return String.valueOf(result);
    }

    @RequestMapping(value = "review/profiles", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getParticipantProfileThumbnail(@RequestParam(value = "index") int index) {
        ProfileEntity article = this.reviewService.readReviewProfile(index);

        ResponseEntity<byte[]> response;
        if (article == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(article.getProfileThumbnail().length);
            headers.setContentType(MediaType.parseMediaType(article.getProfileThumbnailMime()));
            response = new ResponseEntity<>(article.getProfileThumbnail(), headers, HttpStatus.OK);
        }
        return response;
    }


}
