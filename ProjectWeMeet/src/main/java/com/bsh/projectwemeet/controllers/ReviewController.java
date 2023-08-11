package com.bsh.projectwemeet.controllers;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "article")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //    리뷰 insert
    @RequestMapping(value = "review/index", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postReview(@SessionAttribute(value = "user") UserEntity user,
                             HttpServletRequest request, ReviewEntity reviewEntity, HttpSession session) {
        reviewEntity.setNickname(user.getNickname());
        reviewEntity.setEmail(user.getEmail());
        ReviewResult result = this.reviewService.reviewWrite(request, reviewEntity, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    // 리뷰 delete
    @RequestMapping(value = "review/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteReview(@RequestParam(value = "index") int index) {
        boolean result = this.reviewService.deleteByIndex(index);
        return String.valueOf(result);
    }

    //    리뷰 작성자 프로필 select
    @RequestMapping(value = "/review/profiles", method = RequestMethod.GET)
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
