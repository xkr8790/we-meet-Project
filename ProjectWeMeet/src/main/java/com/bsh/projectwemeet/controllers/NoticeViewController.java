package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.services.NoticeViewService;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value="/")
public class NoticeViewController {

    private final NoticeViewService noticeViewService;
    @Autowired
    public NoticeViewController(NoticeViewService noticeViewService){
        this.noticeViewService = noticeViewService;
    }

    @RequestMapping(value="delete", method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String deleteNoticeView(@RequestParam(value="index")int index){
        boolean result = this.noticeViewService.deleteNoticeView(index);
        return String.valueOf(result);
    }
}
