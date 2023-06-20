package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.RecoverContactCodeEntity;
import com.bsh.projectwemeet.entities.RecoverEmailCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.*;
import com.bsh.projectwemeet.services.CheckService;
import com.bsh.projectwemeet.services.RecoverAccountService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

@Controller
@RequestMapping(value="/recoverAccount")
public class RecoverAccountController {

    private final RecoverAccountService recoverAccountService;
    private final CheckService checkService;

    @Autowired
    public RecoverAccountController(RecoverAccountService recoverAccountService, CheckService checkService){
        this.recoverAccountService = recoverAccountService;
        this.checkService = checkService;
    }


    // 연락처를 이용해 이메일 찾기 관련된 코드
    @RequestMapping(value="contactCodeRec", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getContactCodeRec(RecoverContactCodeEntity recoverContactCode){
        SendRecoverContactCodeResult result = this.recoverAccountService.sendRecoverContactCode(recoverContactCode);
        JSONObject responseObject = new JSONObject(){{
            put("result", result.name().toLowerCase());
        }};
//        이거 주석달기
        if(result == SendRecoverContactCodeResult.SUCCESS){
            responseObject.put("salt", recoverContactCode.getSalt());
        }
        return responseObject.toString();
    }

    // patch는 데이터 베이스의 많은 값들중 전체 값이 아닌 몇개의 값만 바꿀려고 할때 사용한다고 생각하면 된다.
    @RequestMapping(value="contactCodeRec", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchContactCodeRec(RecoverContactCodeEntity recoverContactCode){

        VeryfiRecoverContactCodeResult result = this.recoverAccountService.verifyRecoverContactCodeResult(recoverContactCode);
        JSONObject responseObject = new JSONObject(){{
            put("result", result.name().toLowerCase());
        }};
//        여기서 contact끼리의 값이 값으니 UserEntity안의 email값을 꺼낸다는 의미이다.
        if(result == VeryfiRecoverContactCodeResult.SUCCESS){
            UserEntity user = this.recoverAccountService.getUserByContact(recoverContactCode.getContact(), recoverContactCode.getName());
//            responseObject.put("email", user.getEmail());
//            responseObject.put("name", user.getName());
        }
        return responseObject.toString();
    }
    /*-----------------------------------------------------------------------------------------------------------------------------------*/

    @RequestMapping(value = "recoverPassword",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postRecoverPassword(RecoverEmailCodeEntity recoverEmailCode) throws MessagingException, MessagingException {
        SendRecoverEmailCodeResult result = this.checkService.sendRecoverEmailCode(recoverEmailCode);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        if (result == SendRecoverEmailCodeResult.SUCCESS) {
            responseObject.put("redirect", String.format("http://localhost:6795/recoverAccount/recoverPassword?email=%s&salt=%s",
                    recoverEmailCode.getEmail(),
                    recoverEmailCode.getSalt()));
        }
        return responseObject.toString();
    } //링크보내기

    @RequestMapping(value = "recoverPassword",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRecoverPassword(RecoverEmailCodeEntity recoverEmailCode) {
        VerifyRecoverEmailCodeResult result = this.checkService.verifyRecoverEmailCode(recoverEmailCode);
        ModelAndView modelAndView = new ModelAndView("_recoverEmail");
        modelAndView.addObject("result", result.name().toLowerCase());
        modelAndView.addObject("recoverEmailCode", recoverEmailCode);
        return modelAndView;
    } //링크타고 갈떄쓰는거


    @RequestMapping(value = "emailCodeRec",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postEmailCodeRec(RecoverEmailCodeEntity recoverEmailCode) {
        VerifyRecoverEmailCodeResult result = this.checkService.recoverEmailCodeResult(recoverEmailCode);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    } // 인증 판단

    @RequestMapping(value = "recoverPassword",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchRecoverPassword(RecoverEmailCodeEntity recoverEmailCode, UserEntity user) {
        RecoverPasswordResult result = this.checkService.recoverPassword(recoverEmailCode, user);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }


}
