package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.services.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/")
public class WriteController {

    private final WriteService writeService;

    @Autowired
    public WriteController(WriteService writeService) {
        this.writeService = writeService;
    }

    @RequestMapping(value = "write", method = RequestMethod.GET)
    public ModelAndView getWrite() {
        ModelAndView modelAndView = new ModelAndView("home/write");
        return modelAndView;
    } //게시판 주소로 가기



    @RequestMapping(value = "write",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView postWrite(HttpServletRequest request, ArticleEntity article,
                                  @RequestParam(value = "dayStr")String dayStr,
                                  @RequestParam(value = "timeStr")String timeStr)throws ParseException, NoSuchAlgorithmException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day = sdf.parse(dayStr);
        article.setDay(day);


        return null;
    }

}
