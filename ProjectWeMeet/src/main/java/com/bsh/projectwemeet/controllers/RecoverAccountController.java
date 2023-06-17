package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.RecoverContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.SendRecoverContactCodeResult;
import com.bsh.projectwemeet.enums.SendRecoverEmailNameResult;
import com.bsh.projectwemeet.enums.VeryfiRecoverContactCodeResult;
import com.bsh.projectwemeet.services.RecoverAccountService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value="/recoverAccount")
public class RecoverAccountController {

    private final RecoverAccountService recoverAccountService;

    @Autowired
    public RecoverAccountController(RecoverAccountService recoverAccountService){
        this.recoverAccountService = recoverAccountService;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getRecoverAccount () {
        ModelAndView modelAndView = new ModelAndView("home/recoverAccount");
        return modelAndView;
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


// 다음 버튼을 눌렀을때 일어날 일들에 대해서 적은 코드


    @RequestMapping(value = "confrimEmail", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView postConfrimEmail(RecoverContactCodeEntity recoverContactCode, UserEntity user) {

        boolean result = this.recoverAccountService.sendRecoverEmailNameResult(recoverContactCode, user);

        ModelAndView modelAndView = new ModelAndView("home/confirmEmail");
//이 부분만 알면 끝
        if (result) {
            modelAndView.setViewName("home/confirmEmail");
        } else {
            modelAndView.setViewName("/recoverAccount/");
            modelAndView.addObject("result", result);
        }
        return modelAndView;
    }






}
