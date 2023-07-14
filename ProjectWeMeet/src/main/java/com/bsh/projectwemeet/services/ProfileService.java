package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.RegisterContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.*;
import com.bsh.projectwemeet.mappers.LoginMapper;
import com.bsh.projectwemeet.mappers.ProfileMapper;
import com.bsh.projectwemeet.utils.CryptoUtil;
import com.bsh.projectwemeet.utils.NCloudUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
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

    public UserEntity getAll(HttpSession session) {

        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        return this.profileMapper.selectAll(loginUser.getEmail());
    }

    public LoginResult checkPassword(UserEntity user) {
        if (user.getEmail() == null ||
                user.getPassword() == null) {
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

    //연락처 코드
    public SendRegisterContactCodeResult sendContactCodeResult(RegisterContactCodeEntity registerContactCode) {

        if (registerContactCode == null ||
        registerContactCode.getContact() == null ||
        !registerContactCode.getContact().matches("^(010)(\\d{8})$")) {
            return SendRegisterContactCodeResult.FAILURE;
        }


        if (registerContactCode.isExpired()) {
            return SendRegisterContactCodeResult.FAILURE;
        }

        if (this.profileMapper.selectUserByContact(registerContactCode.getContact()) != null) {
            return SendRegisterContactCodeResult.FAILURE;
        }
        String code = RandomStringUtils.randomNumeric(6);
        String salt = CryptoUtil.hashSha512(String.format("%s%s%f%f",
                registerContactCode.getCode(),
                code,
                Math.random(),
                Math.random()));
        Date createdAt = new Date();
        Date expiresAt = DateUtils.addMinutes(createdAt, 5);
        registerContactCode.setCode(code).setSalt(salt).setCreatedAt(createdAt).setExpiresAt(expiresAt).setExpired(false);

        NCloudUtil.sendSms(registerContactCode.getContact(), String.format("[WeMeet 연락처 변경] 연락처 변경 인증번호 [%s]를 입력해 주세요.", registerContactCode.getCode()));

        return this.profileMapper.insertContactCode(registerContactCode) > 0
                ? SendRegisterContactCodeResult.SUCCESS
                : SendRegisterContactCodeResult.FAILURE;
    }

    public VerifyRegisterContactCodeResult verifyRegisterContactCodeResult(RegisterContactCodeEntity registerContactCode) {
        registerContactCode = this.profileMapper.selectContactCodeByContactCodeSalt(registerContactCode);
        if (registerContactCode == null) {
            return VerifyRegisterContactCodeResult.FAILURE;
        }
        if (new Date().compareTo(registerContactCode.getExpiresAt()) > 0) {
            return VerifyRegisterContactCodeResult.FAILURE_EXPIRED;
        }
        registerContactCode.setExpired(true);
        return this.profileMapper.updateContactCode(registerContactCode) > 0
                ? VerifyRegisterContactCodeResult.SUCCESS
                : VerifyRegisterContactCodeResult.FAILURE;
    }


}
