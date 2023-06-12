package com.bsh.wemeetproject.services;

import com.bsh.wemeetproject.entities.RecoverContactCodeEntity;
import com.bsh.wemeetproject.entities.UserEntity;
import com.bsh.wemeetproject.enums.SendRecoverContactCodeResult;
import com.bsh.wemeetproject.enums.VerifyRecovercontactCodeResult;
import com.bsh.wemeetproject.mappers.RecoverMapper;
import com.bsh.wemeetproject.utils.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecoverAccountService {
    private final RecoverMapper recoverMapper;

    @Autowired
    public RecoverAccountService (RecoverMapper recoverMapper){
        this.recoverMapper = recoverMapper;
    }

// 연락처 입력후 인증번호 보내기 클릭할때를 위한 코드
    public SendRecoverContactCodeResult sendRecoverContactCode(RecoverContactCodeEntity recoverContactCode){
        if(recoverContactCode == null ||
         recoverContactCode.getContact() == null||
        recoverContactCode.getContact().matches("^(010)(\\d{8})$")){
            return SendRecoverContactCodeResult.FAILURE;
        }


        UserEntity existingUser = this.recoverMapper.selectUserByContact(recoverContactCode.getContact()){
            if(existingUser == null){
                return SendRecoverContactCodeResult.FAILURE;
            }
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

        return this.recoverMapper.insertRecoverContactCode(recoverContactCode) > 0
                ? SendRecoverContactCodeResult.SUCCESS
                : SendRecoverContactCodeResult.FAILURE;

    }

//    인증번호를 보낸 이후 사용할 코드
    public VerifyRecovercontactCodeResult verifyRecoverContactCodeResult(RecoverContactCodeEntity recoverContactCode){
        if(recoverContactCode == null ||
                recoverContactCode.getContact() == null ||
                recoverContactCode.getCode() == null ||
                recoverContactCode.getSalt() == null ||
                !recoverContactCode.getContact().matches("^(010\\d{8})$") ||
                !recoverContactCode.getCode().matches("^(\\d{6})$") ||
                !recoverContactCode.getSalt().matches("^([\\da-f]{128})$")){
            return VerifyRecovercontactCodeResult.FAUILURE;
        }

        recoverContactCode = this.recoverMapper.selectRecoverContactCodeByCodeByContactCodeSalt(recoverContactCode);

        if(recoverContactCode == null){
            return VerifyRecovercontactCodeResult.FAUILURE;
        }
        if(new Date().compareTo(recoverContactCode.getExpiresAt()) >0){
            return VerifyRecovercontactCodeResult.FAILURE_EXPIRED;
        }
        recoverContactCode.setExpired(true);
        return this.recoverMapper.updateRecoverContactCode(recoverContactCode) >0
        ? VerifyRecovercontactCodeResult.SUCCESS
        : VerifyRecovercontactCodeResult.FAUILURE;
    }

    public UserEntity getUserByContact(String contact){
        return this.recoverMapper.selectUserByContact(contact);
    }

}
