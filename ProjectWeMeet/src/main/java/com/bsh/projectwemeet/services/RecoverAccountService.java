package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.RecoverContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.SendRecoverContactCodeResult;
import com.bsh.projectwemeet.enums.VerifyRecovercontactCodeResult;
import com.bsh.projectwemeet.mappers.RecoverAccountMapper;
import com.bsh.projectwemeet.utils.CryptoUtil;
import com.bsh.projectwemeet.utils.NCloudUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecoverAccountService {
    private final RecoverAccountMapper recoverAccountMapper;

    @Autowired
    public RecoverAccountService(RecoverAccountMapper recoverAccountMapper) {
        this.recoverAccountMapper = recoverAccountMapper;
    }

    // 연락처 입력후 인증번호 보내기 클릭할때를 위한 코드
    public SendRecoverContactCodeResult sendRecoverContactCode(RecoverContactCodeEntity recoverContactCode) {
        if (recoverContactCode == null ||
                recoverContactCode.getContact() == null ||
                recoverContactCode.getContact().matches("^(010)(\\d{8})$")) {
            return SendRecoverContactCodeResult.FAILURE;
        }


        UserEntity existingUser = this.recoverAccountMapper.selectUserByContact(recoverContactCode.getContact(), recoverContactCode.getName());
        if (existingUser == null) {
            return SendRecoverContactCodeResult.FAILURE;
        }


        recoverContactCode.setCode(RandomStringUtils.randomNumeric(6))
                .setSalt(CryptoUtil.hashSha512(String.format("%s%s%f%f",
                        recoverContactCode.getCode(),
                        recoverContactCode.getContact(),
                        Math.random(),
                        Math.random())))
                .setCreatedAt(new Date())
                .setExpiresAt(DateUtils.addMinutes(recoverContactCode.getCreatedAt(), 5))
                .setExpired(false);
        NCloudUtil.sendSms(recoverContactCode.getContact(), String.format("[WeMeet 계정찾기] 이메일 찾기 인증번호 [%s]를 입력해 주세요.", recoverContactCode.getCode()));

        return this.recoverAccountMapper.insertRecoverContactCode(recoverContactCode) > 0
                ? SendRecoverContactCodeResult.SUCCESS
                : SendRecoverContactCodeResult.FAILURE;

    }

    //    인증번호를 보낸 이후 사용할 코드
    public VerifyRecovercontactCodeResult verifyRecoverContactCodeResult(RecoverContactCodeEntity recoverContactCode) {
        if (recoverContactCode == null ||
                recoverContactCode.getContact() == null ||
                recoverContactCode.getCode() == null ||
                recoverContactCode.getSalt() == null ||
                !recoverContactCode.getContact().matches("^(010\\d{8})$") ||
                !recoverContactCode.getCode().matches("^(\\d{6})$") ||
                !recoverContactCode.getSalt().matches("^([\\da-f]{128})$")) {
            return VerifyRecovercontactCodeResult.FAUILURE;
        }

        recoverContactCode = this.recoverAccountMapper.selectRecoverContactCodeByCodeByContactCodeSalt(recoverContactCode);

        if (recoverContactCode == null) {
            return VerifyRecovercontactCodeResult.FAUILURE;
        }
        if (new Date().compareTo(recoverContactCode.getExpiresAt()) > 0) {
            return VerifyRecovercontactCodeResult.FAILURE_EXPIRED;
        }
        recoverContactCode.setExpired(true);
        return this.recoverAccountMapper.updateRecoverContactCode(recoverContactCode) > 0
                ? VerifyRecovercontactCodeResult.SUCCESS
                : VerifyRecovercontactCodeResult.FAUILURE;
    }

    public UserEntity getUserByContact(String contact, String name) {
        return this.recoverAccountMapper.selectUserByContact(contact, name);
    }

}
