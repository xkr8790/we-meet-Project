package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.CreateArticleResult;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public ArticleEntity[] getAll(ArticleEntity article) {
        if(article == null){
//            return CreateArticleResult.FAILURE;
        }

        return null;
    }

    public CreateArticleResult result(ArticleEntity articles, UserEntity user) {
        return null;
    }


}
