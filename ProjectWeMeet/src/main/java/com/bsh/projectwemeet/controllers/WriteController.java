package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.services.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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


    @RequestMapping(value = "write",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView postWrite(HttpServletRequest request,
                                  ArticleEntity article,
                                  @RequestParam(value = "dayStr") String dayStr,
                                  @RequestParam(value = "timeStr") String timeStr,
                                  @RequestParam(value = "limit") String limit,
                                  @RequestParam(value = "thumbnailMultipart") MultipartFile thumbnailMultipart,
                                  HttpSession session) throws IOException, ParseException {

        //TEXT_HTML_VALUE
        //해당 컨트롤러 메서드가 클라이언트에게 HTML 형식의 응답을 제공한다는 것을 나타냅니다.

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day = sdf.parse(dayStr);
        article.setAppointmentStartDate(day);

        SimpleDateFormat tsdf = new SimpleDateFormat("HH:mm");
        Date time = tsdf.parse(timeStr);
        article.setAppointmentStartTime(time);

        int limitPeople = Integer.parseInt(limit);
        article.setLimitPeople(limitPeople);

        article.setThumbnail(thumbnailMultipart.getBytes());
        article.setThumbnailMime(thumbnailMultipart.getContentType());

        //사진자체는 RGB로 이루어져있으므로 배열로 받아야됨

        boolean result = this.writeService.putArticle(request, article, session);

        ModelAndView modelAndView = new ModelAndView();

        System.out.println(result);
        System.out.println(article.getIndex());


        if (result) {
            // 게시글이 성공적으로 처리된 경우, redirect로 계시글 이동
            modelAndView.setViewName("redirect:/article/read?index="+article.getIndex());
        } else {
            // 처리에 실패한 경우
            modelAndView.setViewName("home/main");
            modelAndView.addObject("result", result);
        }
        return modelAndView;
    }//POST 방식으로 글쓰기



    @RequestMapping(value = "write", method = RequestMethod.GET)
    public ModelAndView getWrite() {
        ModelAndView modelAndView = new ModelAndView("home/write");
        return modelAndView;
    } //게시판 주소로 가기
   //@ResponseBody -> 반환된 데이터 JSON이나 기타형식으로 처리됨 이경우 redirect 제대로 안먹힐수도 있음


    @RequestMapping(value ="delete",method = RequestMethod.DELETE) //주소도 같고 메서드도 같으면 충돌이 일어난다.
    @ResponseBody
    public String deleteIndex(@RequestParam(value = "index")int index){
        boolean result = this.writeService.deleteByIndex(index);
        return String.valueOf(result);
    }



}