package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.NoticeWriterArticleEntity;
import com.bsh.projectwemeet.entities.NoticeWriterImagesEntity;
import com.bsh.projectwemeet.mappers.NoticeWriterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Service
public class NoticeWriterService {

    private final NoticeWriterMapper noticeWriterMapper;

    @Autowired
    public NoticeWriterService(NoticeWriterMapper noticeWriterMapper) {
        this.noticeWriterMapper = noticeWriterMapper;
    }

    public boolean putNoticeWriter(HttpServletRequest request, NoticeWriterArticleEntity noticeWriterArticle) {
        noticeWriterArticle.setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"));


        return this.noticeWriterMapper.insertArticle(noticeWriterArticle) > 0
                ? true
                : false;
    }

    // 내용에 이미지 파일이 있을때 하는 코드
    public NoticeWriterArticleEntity readArticle(int index) {
        NoticeWriterArticleEntity article = this.noticeWriterMapper.selectArticleByIndex(index);

        return article;
    }





    public NoticeWriterImagesEntity putImage(HttpServletRequest request, MultipartFile file) throws IOException {
        NoticeWriterImagesEntity image = new NoticeWriterImagesEntity()
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setName(file.getOriginalFilename())
                .setSize((file.getSize()))
                .setContentType(file.getContentType())
                .setData(file.getBytes());
        this.noticeWriterMapper.insertImage(image);
        return image;

    }


    public NoticeWriterImagesEntity getImage(int index) {
        return this.noticeWriterMapper.selectImage(index);
    }


    public boolean deleteNoticeView(int index) {
        return this.noticeWriterMapper.deleteArticleByIndex(index) >0;

    }


}
