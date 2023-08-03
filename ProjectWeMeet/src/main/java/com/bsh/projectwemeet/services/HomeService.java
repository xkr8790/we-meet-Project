package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.mappers.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class HomeService {
    public final HomeMapper homeMapper;

    @Autowired
    public HomeService(HomeMapper homeMapper){
        this.homeMapper = homeMapper;
    }

    public ProfileEntity selectLoginProfile(HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        return  this.homeMapper.selectLoginProfile(loginUser.getEmail());
    }

}
