package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.RegisterResult;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class WriteService {

    private final ArticleMapper articleMapper;

    @Autowired
    public WriteService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }


    public boolean putArticle(HttpServletRequest request, ArticleEntity article, HttpSession session){

        UserEntity logineduser = (UserEntity) session.getAttribute("user");

        article.setEmail(logineduser.getEmail())
                .setView(0)
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setDeleted(false)
                .setFinished(false);

        return this.articleMapper.insertArticle(article)>0;

    }


}
