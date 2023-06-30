package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class WriteService {

    private final ArticleMapper articleMapper;

    @Autowired
    public WriteService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }


    public boolean putArticle(HttpServletRequest request, ArticleEntity article){

        return true;
    }


}
