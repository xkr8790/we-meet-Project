package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.mappers.NoticeViewMapper;
import com.bsh.projectwemeet.mappers.NoticeWriterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeViewService {

    private final NoticeViewMapper noticeViewMapper;
    @Autowired
    public NoticeViewService( NoticeViewMapper noticeViewMapper) {
        this.noticeViewMapper = noticeViewMapper;
    }


    public boolean deleteNoticeView(int index) {
        return this.noticeViewMapper.deleteArticleByIndex(index) >0;

    }
}
