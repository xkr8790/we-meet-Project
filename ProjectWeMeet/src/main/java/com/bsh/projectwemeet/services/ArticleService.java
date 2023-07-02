package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticlesEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.CreateArticleResult;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class ArticleService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public ArticlesEntity[] getAll(ArticlesEntity article) {
        if(article == null){
//            return CreateArticleResult.FAILURE;
        }

        return null;
    }

    public CreateArticleResult result(ArticlesEntity articles, UserEntity user) {
        return null;
    }


}
