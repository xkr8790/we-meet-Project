package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.models.PagingModel;
import com.bsh.projectwemeet.services.ArticleService;
import com.bsh.projectwemeet.services.CompleteService;
import com.bsh.projectwemeet.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class CompleteCategoryController {

    private final CompleteService completeService;
    private final ReviewService reviewService;


    @Autowired
    public CompleteCategoryController(CompleteService completeService, ReviewService reviewService) {
        this.completeService = completeService;
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "complete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getComplete(@RequestParam(value = "p", defaultValue = "1", required = false) int requestPage,
                                    @RequestParam(value = "category", required = false) String category,
                                    @RequestParam(value = "c", defaultValue = "complete", required = false) String searchCriterion,
                                    @RequestParam(value = "q", defaultValue = "", required = false) String searchQuery) {


            ModelAndView modelAndView = new ModelAndView("home/completeCategory"); //index.html 연결
            PagingModel pagingCategory = new PagingModel(
                    ArticleService.PAGE_COUNT, //메모서비스의 읽기 전용 변수 접근
                    this.completeService.getCountCategory(searchCriterion, searchQuery,category),
                    requestPage); //객체화

            ArticleEntity[] articleCategory = this.completeService.getCountCategoryByPage(pagingCategory, searchCriterion, searchQuery, category);
            //페이징하면서 카테고리 관련 게시물 나타내기


            modelAndView.addObject("articleCategory", articleCategory);
            modelAndView.addObject("pagingCategory", pagingCategory);
            modelAndView.addObject("category", category);
        modelAndView.addObject("searchCriterion", searchCriterion);
        modelAndView.addObject("searchQuery", searchQuery);
            return modelAndView;

        }


        @RequestMapping(value = "complete/image",
                method = RequestMethod.GET)
        public ResponseEntity<byte[]> getThumbnail ( @RequestParam(value = "index") int index){

            ArticleEntity article = this.completeService.readArticle(index);

            ResponseEntity<byte[]> response;
            if (article == null) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentLength(article.getThumbnail().length);
                headers.setContentType(MediaType.parseMediaType(article.getThumbnailMime()));
                response = new ResponseEntity<>(article.getThumbnail(), headers, HttpStatus.OK);
            }
            return response;
        }


        @RequestMapping(value = "complete/category/image",
                method = RequestMethod.GET)
        public ResponseEntity<byte[]> getCategoryThumbnail ( @RequestParam(value = "index") int index){

            ArticleEntity article = this.completeService.readArticle(index);

            ResponseEntity<byte[]> response;
            if (article == null) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentLength(article.getThumbnail().length);
                headers.setContentType(MediaType.parseMediaType(article.getThumbnailMime()));
                response = new ResponseEntity<>(article.getThumbnail(), headers, HttpStatus.OK);
            }
            return response;
        }


    }
