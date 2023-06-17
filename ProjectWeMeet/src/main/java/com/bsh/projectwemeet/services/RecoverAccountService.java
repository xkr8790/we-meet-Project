package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.RecoverContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.SendRecoverContactCodeResult;
import com.bsh.projectwemeet.enums.SendRecoverEmailNameResult;
import com.bsh.projectwemeet.enums.VeryfiRecoverContactCodeResult;
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
                !recoverContactCode.getContact().matches("^(010)(\\d{8})$") ||
                recoverContactCode.getName() == null) {
            return SendRecoverContactCodeResult.FAILURE;
        }


        UserEntity existingUser = this.recoverAccountMapper.selectUserByContact(recoverContactCode.getContact(), recoverContactCode.getName());
        if (existingUser == null ) {
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
                .setExpired(false)
                .setName(recoverContactCode.getName());

        NCloudUtil.sendSms(recoverContactCode.getContact(), String.format("[WeMeet 계정찾기] 이메일 찾기 인증번호 [%s]를 입력해 주세요.", recoverContactCode.getCode()));

        return this.recoverAccountMapper.insertRecoverContactCode(recoverContactCode) > 0
                ? SendRecoverContactCodeResult.SUCCESS
                : SendRecoverContactCodeResult.FAILURE;

    }

    //    인증번호를 보낸 이후 사용할 코드
    public VeryfiRecoverContactCodeResult verifyRecoverContactCodeResult(RecoverContactCodeEntity recoverContactCode) {
        if (recoverContactCode == null ||
                recoverContactCode.getContact() == null ||
                recoverContactCode.getCode() == null ||
                recoverContactCode.getSalt() == null ||
                !recoverContactCode.getContact().matches("^(010\\d{8})$") ||
                !recoverContactCode.getCode().matches("^(\\d{6})$") ||
                !recoverContactCode.getSalt().matches("^([\\da-f]{128})$")) {
            return VeryfiRecoverContactCodeResult.FAILURE;
        }

        recoverContactCode = this.recoverAccountMapper.selectRecoverContactCodeByCodeByContactCodeSalt(recoverContactCode);

        if (recoverContactCode == null) {
            return VeryfiRecoverContactCodeResult.FAILURE;
        }
        if (new Date().compareTo(recoverContactCode.getExpiresAt()) > 0) {
            return VeryfiRecoverContactCodeResult.FAILURE_EXPIRED;
        }
        recoverContactCode.setExpired(true);
        return this.recoverAccountMapper.updateRecoverContactCode(recoverContactCode) > 0
                ? VeryfiRecoverContactCodeResult.SUCCESS
                : VeryfiRecoverContactCodeResult.FAILURE;
    }

    public UserEntity getUserByContact(String contact, String name) {
        return this.recoverAccountMapper.selectUserByContact(contact, name);
    }


public SendRecoverEmailNameResult sendRecoverEmailNameResult(RecoverContactCodeEntity recoverContactCode, UserEntity user){
// 요청 했는 값에 대한 이야기
    if (recoverContactCode == null ||
            recoverContactCode.getContact() == null ||
            !recoverContactCode.getContact().matches("^(010)(\\d{8})$") ||
            recoverContactCode.getName() == null ||
     recoverContactCode.getCode() == null ||
    recoverContactCode.getSalt() == null) {
        return SendRecoverEmailNameResult.FAILURE;
    }
// recover 데이터 베이스에서 연락처 코드 솔트 이름까지 있는지 없는지 확인 코드 작성하기
 RecoverContactCodeEntity existingContact = this.recoverAccountMapper.selectRecoverContactCodeByNameContactCodeSalt(recoverContactCode);
    if(existingContact ==null ||
    existingContact.getSalt() ==null ||
    existingContact.getName() == null ||
    existingContact.getCode() == null ||
    existingContact.getContact() ==null){
        return SendRecoverEmailNameResult.FAILURE;
    }
//    시간 안에 완료가 되었는지 안되었는지 확인 코드 작성 이건 따로 필요가 없다
//     왜냐하면 시간에 대한 인증 관련된것은 다른 버튼의 종류이기 때문이다.

//   delete 확인하는 코드(중복 제거를 위해)

//     유저에서 select로 연락처 해서 이메일 가져오기
    user = this.recoverAccountMapper.selectUserByContact(user.getContact(), user.getName());
    if(user == null){
        return SendRecoverEmailNameResult.FAILURE;
    }

    return this.recoverAccountMapper.deleteRecoverContactCode(recoverContactCode) > 0
            ? SendRecoverEmailNameResult.SUCCESS
            : SendRecoverEmailNameResult.FAILURE;


}




}
