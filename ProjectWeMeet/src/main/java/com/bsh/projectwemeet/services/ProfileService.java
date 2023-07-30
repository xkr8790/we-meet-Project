package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
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

        UserEntity user = (UserEntity) session.getAttribute("user");

        return this.profileMapper.selectAll(user.getEmail());
    }

    public ProfileEntity getThumbnail(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ProfileEntity profile = profileMapper.selectThumbnail(user.getEmail());


        return profile == null
                ? null
                : profile;
    }

    public DeleteUserResult deleteThumbnailResult(HttpSession session, ProfileEntity profile) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return DeleteUserResult.FAILURE;
        }

        profile.setProfileThumbnail(null)
                .setProfileThumbnailMime(null);

        return this.profileMapper.deleteThumbnail(profile) > 0
                ? DeleteUserResult.SUCCESS
                : DeleteUserResult.FAILURE;
    }

    //비밀번호 확인
//    public LoginResult checkPassword(UserEntity user) {
//        if (user.getPassword() == null) {
//            System.out.println("1");
//            return LoginResult.FAILURE;
//        }
//
//        UserEntity existingUser = this.profileMapper.selectPasswordByEmail(user.getEmail());
//
//        user.setPassword(CryptoUtil.hashSha512(user.getPassword()));
//
//        if (!user.getPassword().equals(existingUser.getPassword())) {
//            System.out.println(user.getPassword());
//            System.out.println(existingUser.getPassword());
//            return LoginResult.FAILURE;
//        }
//
//        return LoginResult.SUCCESS;
//    }

    public LoginResult checkPassword(UserEntity user) {
        if (user.getPassword() == null) {
            return LoginResult.FAILURE;
        }

        // 데이터베이스에서 해당 이메일에 해당하는 사용자 정보를 가져옴
        UserEntity existingUser = this.profileMapper.selectPasswordByEmail(user.getEmail());

        // 입력받은 비밀번호를 해시화하여 사용자의 비밀번호와 비교
        String hashedPassword = CryptoUtil.hashSha512(user.getPassword());
        if (existingUser != null && existingUser.getPassword().equals(hashedPassword)) {
            return LoginResult.SUCCESS; // 비밀번호 일치로 인증 성공
        } else {
            System.out.println(user.getPassword());
            System.out.println(existingUser.getPassword());
            return LoginResult.FAILURE; // 비밀번호 불일치로 인증 실패
        }
    }



    @Transactional
    public boolean putProfile(ProfileEntity profile) {
        return this.profileMapper.updateThumbnail(profile) > 0;
    }

    public int getArticleIndexCountByEmail(HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        Integer articleIndexCount = profileMapper.getArticleIndexCountByEmail(loginUser.getEmail());

        // articleIndexCount가 null인 경우 0으로 처리합니다.
        return articleIndexCount != null ? articleIndexCount : 0;
    }


    //연락처 코드 전송
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

    //인증코드 확인
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


    //비밀번호 변경
    public ModifyPasswordResult modifyPassword(String password, UserEntity user, HttpSession session) {

        if (!(session.getAttribute("user") instanceof UserEntity)) {
            return ModifyPasswordResult.FAILURE; //세션에서 user를 가져옴, 로그인 안 함
        }
        UserEntity signedUser = (UserEntity) session.getAttribute("user");
        password = CryptoUtil.hashSha512(password);
        if (password.equals(signedUser.getPassword())) {
            return ModifyPasswordResult.FAILURE_PASSWORD_MISMATCH; // 입력한 기존 비밀번호가 현재 비밀번호랑 다름
        }
        user.setPassword(password);
        return this.profileMapper.updatePassword(user) > 0
                ? ModifyPasswordResult.SUCCESS : ModifyPasswordResult.FAILURE;
    }

    // 연락처 변경
    public ModifyPasswordResult resetContact(String contact, UserEntity user, HttpSession session) {
        if (!(session.getAttribute("user") instanceof UserEntity)) {
            return ModifyPasswordResult.FAILURE;
        }
        UserEntity signedUser = (UserEntity) session.getAttribute("user");

        if (contact.equals(signedUser.getContact())) {
            return ModifyPasswordResult.FAILURE_PASSWORD_MISMATCH;
        }
        user.setContact(contact);
        return this.profileMapper.updateContact(user) > 0
                ? ModifyPasswordResult.SUCCESS : ModifyPasswordResult.FAILURE;
    }

    // 주소 변경
    public ModifyPasswordResult resetAddress(String postal, String primary, String secondary, UserEntity user, HttpSession session) {
        if (!(session.getAttribute("user") instanceof UserEntity)) {
            return ModifyPasswordResult.FAILURE;
        }
        user.setAddressPostal(postal)
                .setAddressPrimary(primary)
                .setAddressSecondary(secondary);
        return this.profileMapper.updateAddress(user) > 0
                ? ModifyPasswordResult.SUCCESS : ModifyPasswordResult.FAILURE;
    }

    //프로필 이미지 삭제

    // 회원탈퇴
    public DeleteUserResult deleteUserResult(HttpSession session) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");
        if (loggedInUser == null) {
            return DeleteUserResult.FAILURE;
        }
        return this.profileMapper.deleteUser(loggedInUser.getEmail()) > 0
                ? DeleteUserResult.SUCCESS
                : DeleteUserResult.FAILURE;
    }
}
