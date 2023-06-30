package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteService {

    private final ArticleMapper articleMapper;

    @Autowired
    public WriteService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }





}
