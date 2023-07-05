package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.LoginResult;
import com.bsh.projectwemeet.mappers.LoginMapper;
import com.bsh.projectwemeet.mappers.ProfileMapper;
import com.bsh.projectwemeet.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class ProfileService {
    private final ProfileMapper profileMapper;
    private final LoginMapper loginMapper;

    @Autowired
    public ProfileService(ProfileMapper profileMapper, LoginMapper loginMapper) {
        this.profileMapper = profileMapper;
        this.loginMapper = loginMapper;
    }

    public LoginResult checkPassword(UserEntity user) {
        if (user.getPassword() == null) {
            return LoginResult.FAILURE;
        }
        UserEntity existingUser = this.loginMapper.selectUserByEmail(user.getEmail());
        user.setPassword(CryptoUtil.hashSha512(user.getPassword()));
        if (!user.getPassword().equals(existingUser.getPassword())) {
            return LoginResult.FAILURE;
        }
        return LoginResult.SUCCESS;
    }



    @Transactional
    public boolean putProfile(HttpServletRequest request, ProfileEntity profile, HttpSession session) {

        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        profile.setEmail(loginUser.getEmail())
                .setCreatedAt(new Date());
        return this.profileMapper.insertProfile(profile) > 0;
    }

}
