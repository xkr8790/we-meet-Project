package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.RecoverContactCodeEntity;
import com.bsh.projectwemeet.entities.RegisterContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.*;
import com.bsh.projectwemeet.services.ProfileService;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping(value="profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(value="profile",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getProfile(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/profile");
        UserEntity userEntities = this.profileService.getAll(session);
        modelAndView.addObject("profile", userEntities);
        return modelAndView;
    }

    @RequestMapping(value = "checkPassword",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postCheckPassword(HttpSession session, UserEntity user) {
        LoginResult result = this.profileService.checkPassword(user);
        if (result == LoginResult.SUCCESS) {
            session.setAttribute("user", user);
        }
        JSONObject responseObject = new JSONObject() {{
            put("result",result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "index",
    method = RequestMethod.POST,
    produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public ModelAndView postIndex(HttpServletRequest request,
                                  ProfileEntity profile,
                                  @RequestParam(value = "thumbnailMultipart")MultipartFile thumbnailMultipart,
                                  HttpSession session) throws IOException {
        profile.setProfileThumbnail(thumbnailMultipart.getBytes());
        profile.setProfileThumbnailMime(thumbnailMultipart.getContentType());
        boolean result = this.profileService.putProfile(request, profile, session);
        ModelAndView modelAndView = new ModelAndView();
        if (result) {
            modelAndView.setViewName("redirect:/profile");
        }
        return modelAndView;
    }

    //인증번호 전송 코드
    @RequestMapping(value = "contactCodeRec",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getContactCodeRec(RegisterContactCodeEntity registerContactCode) {
        SendRegisterContactCodeResult result = this.profileService.sendContactCodeResult(registerContactCode);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        if (result == SendRegisterContactCodeResult.SUCCESS) {
            responseObject.put("salt", registerContactCode.getSalt());
        }
        return responseObject.toString();
    }

    //인증번호 6자리 확인 코드
    @RequestMapping(value = "contactCodeRec",
    method = RequestMethod.PATCH,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchContactCodeRec(RegisterContactCodeEntity registerContactCode) {
        VerifyRegisterContactCodeResult result = this.profileService.verifyRegisterContactCodeResult(registerContactCode);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};

        return responseObject.toString();
    }
}
