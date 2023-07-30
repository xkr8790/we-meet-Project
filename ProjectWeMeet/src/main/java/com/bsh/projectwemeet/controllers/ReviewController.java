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

    @RequestMapping(value = "article/review/profiles", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getParticipantProfileThumbnail(@RequestParam(value = "email") String email, HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        ProfileEntity article = this.reviewService.readReviewProfile(loginUser.getEmail());

        ResponseEntity<byte[]> response;
        if (article == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                // 원본 이미지를 BufferedImage로 변환

                BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(article.getProfileThumbnail()));

                // 새로운 크기로 이미지 조정
                int newWidth = 60;
                int newHeight = 60;
                Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // BufferedImage 생성
                BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                // Graphics2D를 사용하여 이미지 그리기
                Graphics2D graphics = outputImage.createGraphics();
                graphics.drawImage(resizedImage, 0, 0, null);
                graphics.dispose();

                // 조정된 이미지를 바이트 배열로 변환
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(outputImage, "jpg", baos);
                byte[] resizedImageBytes = baos.toByteArray();

                // HTTP 응답 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setContentLength(resizedImageBytes.length);
                headers.setContentType(MediaType.IMAGE_JPEG);

                response = new ResponseEntity<>(resizedImageBytes, headers, HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace();
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }


}
