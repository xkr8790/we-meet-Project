package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.RecoverEmailCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.RecoverPasswordResult;
import com.bsh.projectwemeet.enums.SendRecoverEmailCodeResult;
import com.bsh.projectwemeet.enums.VerifyRecoverEmailCodeResult;
import com.bsh.projectwemeet.mappers.CheckMapper;
import com.bsh.projectwemeet.utils.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class CheckService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final CheckMapper checkMapper;

    @Autowired
    public CheckService(JavaMailSender javaMailSender,
                        SpringTemplateEngine springTemplateEngine,
                        CheckMapper checkMapper) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
        this.checkMapper = checkMapper;
    }

    public SendRecoverEmailCodeResult sendRecoverEmailCode(RecoverEmailCodeEntity recoverEmailCode) throws MessagingException {
        if (recoverEmailCode == null ||
                recoverEmailCode.getEmail() == null ||
                !recoverEmailCode.getEmail().matches("^(?=.{10,50}$)([\\da-zA-Z\\-_\\.]{5,25})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$")) {
            return SendRecoverEmailCodeResult.FAILURE;
        }
        if (this.checkMapper.selectUserByEmail(recoverEmailCode.getEmail()) == null) {
            return SendRecoverEmailCodeResult.FAILURE;
        }
        recoverEmailCode
                .setCode(RandomStringUtils.randomAlphanumeric(6))
                .setSalt(CryptoUtil.hashSha512(String.format("%s%s%f%f",
                        recoverEmailCode.getCode(),
                        recoverEmailCode.getEmail(),
                        Math.random(),
                        Math.random())))
                .setCreatedAt(new Date())
                .setExpiresAt(DateUtils.addHours(recoverEmailCode.getCreatedAt(), 1))
                .setExpired(false);
        String url = String.format("http://localhost:6795/recoverAccount/recoverPassword?email=%s&salt=%s",
                recoverEmailCode.getEmail(),
                recoverEmailCode.getSalt());
        String code = recoverEmailCode.getCode();

        Context context = new Context();
        context.setVariable("url", url);
        context.setVariable("code", code);

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setSubject("[WE - MEET 비밀번호 재설정] 이메일 인증");
        mimeMessageHelper.setFrom("chngdch3@naver.com"); // 당신 이메일
        mimeMessageHelper.setTo(recoverEmailCode.getEmail());
        mimeMessageHelper.setText(this.springTemplateEngine.process("/user/SendEmailCode", context), true);
        this.javaMailSender.send(mimeMessage);

        return this.checkMapper.insertRecoverEmailCode(recoverEmailCode) > 0
                ? SendRecoverEmailCodeResult.SUCCESS
                : SendRecoverEmailCodeResult.FAILURE;
    }


    public VerifyRecoverEmailCodeResult verifyRecoverEmailCode(RecoverEmailCodeEntity recoverEmailCode) {
        if (recoverEmailCode == null ||
                recoverEmailCode.getEmail() == null ||
                recoverEmailCode.getCode() == null ||
                recoverEmailCode.getSalt() == null ||
                !recoverEmailCode.getEmail().matches("^(?=.{10,50}$)([\\da-zA-Z\\-_.]{5,25})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$") ||
                !recoverEmailCode.getCode().matches("^([\\da-zA-Z]{6})$") ||
                !recoverEmailCode.getSalt().matches("^([\\da-f]{128})$")) {
            return VerifyRecoverEmailCodeResult.FAILURE;
        }
        recoverEmailCode = this.checkMapper.selectRecoverEmailCodeByEmailCodeSalt(recoverEmailCode);
        if (recoverEmailCode == null) {
            return VerifyRecoverEmailCodeResult.FAILURE;
        }
        if (new Date().compareTo(recoverEmailCode.getExpiresAt()) > 0) {
            return VerifyRecoverEmailCodeResult.FAILURE_EXPIRED;
        }
        return this.checkMapper.updateRecoverEmailCode(recoverEmailCode) > 0
                ? VerifyRecoverEmailCodeResult.SUCCESS
                : VerifyRecoverEmailCodeResult.FAILURE;
    }


    public VerifyRecoverEmailCodeResult recoverEmailCodeResult(RecoverEmailCodeEntity recoverEmailCode) {
        if (recoverEmailCode == null ||
                recoverEmailCode.getEmail() == null ||
                recoverEmailCode.getCode() == null ||
                recoverEmailCode.getSalt() == null ||
                !recoverEmailCode.getEmail().matches("^(?=.{10,50}$)([\\da-zA-Z\\-_.]{5,25})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$") ||
                !recoverEmailCode.getCode().matches("^([\\da-zA-Z]{6})$") ||
                !recoverEmailCode.getSalt().matches("^([\\da-f]{128})$")) {
            return VerifyRecoverEmailCodeResult.FAILURE;
        }
        recoverEmailCode = this.checkMapper.selectRecoverEmailCodeByEmailCodeSalt(recoverEmailCode);
        if (recoverEmailCode == null) {
            return VerifyRecoverEmailCodeResult.FAILURE;
        }
        if (new Date().compareTo(recoverEmailCode.getExpiresAt()) > 0) {
            return VerifyRecoverEmailCodeResult.FAILURE_EXPIRED;
        }
        recoverEmailCode.setExpired(true);
        return this.checkMapper.updateRecoverEmailCode(recoverEmailCode) > 0
                ? VerifyRecoverEmailCodeResult.SUCCESS
                : VerifyRecoverEmailCodeResult.FAILURE_EXPIRED;
    }

    public RecoverPasswordResult recoverPassword(RecoverEmailCodeEntity recoverEmailCode, UserEntity user) {
        if (recoverEmailCode == null ||
                recoverEmailCode.getEmail() == null ||
                recoverEmailCode.getCode() == null ||
                recoverEmailCode.getSalt() == null ||
                user == null ||
                user.getPassword() == null) {
            return RecoverPasswordResult.FAILURE;
        }
        recoverEmailCode = this.checkMapper.selectRecoverEmailCodeByEmailCodeSalt(recoverEmailCode);
        if (recoverEmailCode == null || !recoverEmailCode.isExpired()) {
            return RecoverPasswordResult.FAILURE;
        }
        String newPassword = CryptoUtil.hashSha512(user.getPassword());
        user = this.checkMapper.selectUserByEmail(user.getEmail());
        if (user == null) {
            return RecoverPasswordResult.FAILURE;
        }
        user.setPassword(newPassword);
        user.setPassword(CryptoUtil.hashSha512(user.getPassword()));
        return this.checkMapper.updateUser(user) > 0 && this.checkMapper.deleteRecoverEmailCode(recoverEmailCode) > 0
                ? RecoverPasswordResult.SUCCESS
                : RecoverPasswordResult.FAILURE;
    }
}
