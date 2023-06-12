package com.bsh.wemeetproject.services;

import com.bsh.wemeetproject.entities.UserEntity;
import com.bsh.wemeetproject.enums.LoginResult;
import com.bsh.wemeetproject.mappers.LoginMapper;
import com.bsh.wemeetproject.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginMapper loginMapper;

    @Autowired
    public LoginService(LoginMapper loginMapper){
        this.loginMapper = loginMapper;
    }

    public LoginResult login(UserEntity user){
//  웹 페이지에서 로그인 입력값이 없거나 이메일,비밀번호 양식이 정규식과 다를때 '실패'를 return한다.
        if(user.getEmail() == null ||
                user.getPassword() == null||
                !user.getEmail().matches("^(?=.{10,50}$)([\\da-zA-Z\\-_\\.]{5,25})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$") ||
                !user.getPassword().matches("^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?]{8,50})$")){
            return LoginResult.FAILURE;
        }

//        existingUser의 변수로 데이터 베이스안의 email값을 반환한다.
        UserEntity existingUser = this.loginMapper.selectUserByEmail(user.getEmail());
//        로그인 화면에서 입력한 email값이 데이터 베이스에 없는경우를 말한다.
        if(existingUser == null){
            return LoginResult.FAILURE;
        }

//        로그인 화면에서 입력한 비밀번호와 데이터베이스 안 비밀번호가 같지 않다면
        user.setPassword(CryptoUtil.hashSha512(user.getPassword())); // 비밀번호에 hash됨
        if (!user.getPassword().equals(existingUser.getPassword())) {
            return LoginResult.FAILURE;
        }

//        아래의 코드로 인해 로그인상태(쿠키)를 유지힌다.
        user.setNickname(existingUser.getNickname())
                .setContact(existingUser.getContact())
                .setAdmin(existingUser.isAdmin())
                .setRegisteredAt(existingUser.getRegisteredAt());


//        이건 물어보기
//        if (user.getStatus().equals("DELETED")) {
//            return LoginResult.FAILURE;
//        }
//        if (user.getStatus().equals("EMAIL_PENDING")) {
//            return LoginResult.FAILURE_EMAIL_NOT_VERIFIED;
//        }
//        if (user.getStatus().equals("SUSPENDED")) {
//            return LoginResult.FAILURE_SUSPENDED;
//        }



//        위의 조건이 아닌 이상 나머지는 성공했다고 생각해서 return값을 반환한다.

        return LoginResult.SUCCESS;
    }
}
