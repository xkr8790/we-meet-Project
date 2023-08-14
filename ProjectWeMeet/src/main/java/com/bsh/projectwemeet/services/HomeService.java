package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ParticipantsEntity;
import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.mappers.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class HomeService {
    public final HomeMapper homeMapper;

    @Autowired
    public HomeService(HomeMapper homeMapper){
        this.homeMapper = homeMapper;
    }

//    로그인한 유저의 프로필 나타내기 위해 session과 DB의 usr와 비교하기
    public ProfileEntity selectLoginProfile(HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        return  this.homeMapper.selectLoginProfile(loginUser.getEmail());
    }


}
