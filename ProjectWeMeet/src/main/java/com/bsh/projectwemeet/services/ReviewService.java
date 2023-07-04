package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ReviewEntity;
import com.bsh.projectwemeet.mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }


    public boolean reviewWrite(HttpServletRequest request, ReviewEntity reviewEntity) {

        reviewEntity.setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"));

        return this.reviewMapper.insertReview(reviewEntity) > 0;
    }

    public boolean deleteByIndex(int index){

        return this.reviewMapper.deleteByReview(index) >0 ;
    }


}
