package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.enums.InsertArticleResult;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Service
public class ArticleService {

    private final ArticleMapper articleMapper;


    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public InsertArticleResult putArticle(HttpServletRequest request, ArticleEntity article){
        if (article.getTitle() == null || article.getCategory() < 0 || article.getContent() == null ||
                article.getAddressPrimary() == null || article.getAddressSecondary() == null ||
                article.getAppointmentStartDate() == null || article.getAppointmentStartTime() == null
                || article.getLimitPeople() < 0 || article.getLatitude() == 0 || article.getLongitude() == 0) {
            return InsertArticleResult.FAILURE;
        }
        article.setView(0)
                .setCreateAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setDeleted(false)
                .setFinished(true);

        return articleMapper.insertArticle(article) > 0
                ? InsertArticleResult.SUCCESS
                : InsertArticleResult.FAILURE;
    }


}
