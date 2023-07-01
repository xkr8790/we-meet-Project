package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.RegisterResult;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class WriteService {

    private final ArticleMapper articleMapper;

    @Autowired
    public WriteService(ArticleMapper articleMapper) { this.articleMapper = articleMapper; }

    public boolean putArticle(HttpServletRequest request, ArticleEntity article, HttpSession session){

        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        article.setEmail(loginUser.getEmail())
                .setView(0)
                .setCreateAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setDeleted(false)
                .setFinished(false);

        return this.articleMapper.insertArticle(article)>0;
    }
}