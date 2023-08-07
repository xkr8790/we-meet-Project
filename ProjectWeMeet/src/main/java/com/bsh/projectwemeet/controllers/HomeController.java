package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.selectParticipateProfile;
import com.bsh.projectwemeet.services.ArticleService;
import com.bsh.projectwemeet.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping(value="/")
public class HomeController {

    private final ArticleService articleService;
    private final HomeService homeService;

    @Autowired
    public HomeController(ArticleService articleService, HomeService homeService) {
        this.articleService = articleService;
        this.homeService =homeService;
    }

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getMain(boolean isFinished){
        ModelAndView modelAndView = new ModelAndView("home/main");
        ArticleEntity[] articles = this.articleService.getMainArticle(isFinished);
        modelAndView.addObject("article", articles);
        return modelAndView;
    } //메인 홈 주소



    @RequestMapping(value = "/Privacy-Policy",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getPrivacyPolicy(){
        ModelAndView modelAndView = new ModelAndView("home/Privacy-Policy/Privacy-Policy");
        return modelAndView;
    } //메인 홈 주소

    @RequestMapping(value = "TermsOfService",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getTermsOfService(){
        ModelAndView modelAndView = new ModelAndView("home/Privacy-Policy/Terms-of-Service");
        return modelAndView;
    } //메인 홈 주소

    @RequestMapping(value = "/image",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@RequestParam(value = "index")int index){

        ArticleEntity article = this.articleService.readArticle(index);

        ResponseEntity<byte[]> response;
        if (article == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(article.getThumbnail().length);
            headers.setContentType(MediaType.parseMediaType(article.getThumbnailMime()));
            response = new ResponseEntity<>(article.getThumbnail(),headers,HttpStatus.OK);
        }
        return response;
    }

    @RequestMapping(value = "/read",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index") int index) {
        ModelAndView modelAndView = new ModelAndView("home/bulletin");

        // articleService를 통해 index에 해당하는 게시글을 가져옵니다.
        ArticleEntity article = this.articleService.readArticle(index);

        // ModelAndView에 "article"이라는 이름으로 가져온 게시글을 추가합니다.
        modelAndView.addObject("article", article);

        return modelAndView;
    }


    @RequestMapping(value = "/profiles", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getParticipantProfileThumbnail(HttpSession session) {

        ProfileEntity loginProfiles = this.homeService.selectLoginProfile(session);

        ResponseEntity<byte[]> response;
        if (loginProfiles == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                // 원본 이미지를 BufferedImage로 변환

                BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(loginProfiles.getProfileThumbnail()));

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
