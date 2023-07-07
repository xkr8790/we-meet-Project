package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.enums.SelectParticipantsResult;
import com.bsh.projectwemeet.services.ArticleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "article",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getArticle() {
        ModelAndView modelAndView = new ModelAndView("home/article"); //index.html 연결
        ArticleEntity[] articles = this.articleService.getAll();
        modelAndView.addObject("article", articles);
        return modelAndView;
    } //게시판 전부 가져오기

    @RequestMapping(value = "article/category",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getArticleCategory(@RequestParam(value = "category")String category) {
        ArticleEntity article = articleService.getCategory(category);

        ModelAndView modelAndView = new ModelAndView("home/article");
        modelAndView.addObject("articles",article);

       return modelAndView;
    } //카테고리 관련인데 진짜 모르겠다 이게 무슨일인가? OMG


    @RequestMapping(value = "article/read",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index") int index) {
        ModelAndView modelAndView = new ModelAndView("home/bulletin");

        // articleService를 통해 index에 해당하는 게시글을 가져옵니다.
        ArticleEntity article = this.articleService.readArticle(index);

        // ModelAndView에 "article"이라는 이름으로 가져온 게시글을 추가합니다.
        modelAndView.addObject("article", article);

        return modelAndView;
    }//인덱스번호로 각 게시판 값 나타내기

    @RequestMapping(value = "article/read/different",
            method = RequestMethod.GET)
    public ModelAndView getDifferentArticle(@RequestParam(value = "insert") int insert) {
        ModelAndView modelAndView = new ModelAndView("home/bulletin");
        // articleService를 통해 index에 해당하는 게시글을 가져옵니다.
        ArticleEntity article = this.articleService.getMiniArticle(insert);

        modelAndView.addObject("articles", article);

        return modelAndView;
    }//인덱스번호로 각 게시판 값 나타내기


    @RequestMapping(value = "article/image",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@RequestParam(value = "index") int index) {

        ArticleEntity article = this.articleService.readArticle(index);

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

    //인덱스 번호로 사진가져와서 인덱스에 해당하는 게시판에 사진 나타내기

    @RequestMapping(value = "article/read",
            method = RequestMethod.DELETE)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String deleteIndex(@RequestParam(value = "index") int index, ArticleEntity article, HttpSession session) {
        boolean result = this.articleService.deleteByIndex(index, article, session);
        System.out.println(String.valueOf(result));
        return String.valueOf(result);
    }
    //게시판 삭제

    @RequestMapping(value = "article/patch",
            method = RequestMethod.GET)
    public ModelAndView getWrite(@RequestParam(value = "index") int index, HttpSession session) {
        ArticleEntity article = articleService.getPatchIndexArticle(index,session);
        ModelAndView modelAndView = new ModelAndView("home/patchWrite");
        modelAndView.addObject("article", article);
        return modelAndView;
    }
    //게시판 수정 폼 받아오기

    @RequestMapping(value = "article/patch",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String PatchWrite(@RequestParam(value = "index") int index,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "category") String category,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "place") String place,
                             @RequestParam(value = "address") String address,
                             @RequestParam(value = "dayStr") String day,
                             @RequestParam(value = "timeStr") String time,
                             @RequestParam(value = "latitude")String latitude,
                             @RequestParam(value = "longitude") String longitude,
                             @RequestParam(value = "thumbnailMultipart") MultipartFile thumbnailMultipart,
                             @RequestParam(value = "thumbnailMime") String thumbnailMime
                             ) throws ParseException, IOException {
        
        ArticleEntity article = new ArticleEntity();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date daystr = sdf.parse(day); //날짜


        SimpleDateFormat tsdf = new SimpleDateFormat("HH:mm");
        Date timestr = tsdf.parse(time); //시간

        double lat = Double.parseDouble(latitude); //위도
        double lng = Double.parseDouble(longitude); //경도


        boolean result = this.articleService.UpdateArticle(index, title, category, content, place, address,
                                                            daystr,timestr,lat,lng,thumbnailMultipart,thumbnailMime);
        return String.valueOf(result);
    }


    @RequestMapping(value = "article/Participate",
            method = RequestMethod.POST)
    @ResponseBody //주소도 같고 메서드도 같으면 충돌이 일어난다.
    public String postParticipate(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        boolean result = this.articleService.Participate(index, participants, session);
        return String.valueOf(result);
    } //인원참가시 1증가하는 POST 방식 컨트롤러 (중복 체크 불가)

    @RequestMapping(value = "article/Participate",
            method = RequestMethod.GET)
    public ModelAndView getArticle(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        boolean result = this.articleService.checkParticipationStatus(index, participants, session);
        ModelAndView modelAndView = new ModelAndView("home/bulletin");
        modelAndView.addObject("result",result);
        return modelAndView;
    } //인원의 참가여부 select로 표현


    @RequestMapping(value = "article/Participate",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody //있어야지 JSON 사용가능
    public String patchParticipate(@RequestParam(value = "index") int index, ParticipantsEntity participants, HttpSession session) {
        SelectParticipantsResult result = this.articleService.SelectParticipants(index, participants, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    } //인원이 참가 했을시 취소 가능하게
}
