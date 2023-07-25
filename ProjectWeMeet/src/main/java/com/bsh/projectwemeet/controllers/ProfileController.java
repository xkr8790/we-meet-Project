package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.*;
import com.bsh.projectwemeet.enums.*;
import com.bsh.projectwemeet.services.ProfileService;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
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
        int article = this.profileService.getArticleIndexCountByEmail(session);
        modelAndView.addObject("profile", userEntities);
        modelAndView.addObject("article",article);
        return modelAndView;
    }

    @RequestMapping(value = "checkPassword",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCheckPassword() {
        return new ModelAndView("home/profile");
    }
    @RequestMapping(value = "checkPassword",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postCheckPassword(HttpSession session, @RequestParam("checkPassword") String password) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        LoginResult result = this.profileService.checkPassword(user);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "/modifyPassword",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModifyPassword(){
        return new ModelAndView("home/profile");
    }

    @RequestMapping(value = "/modifyPassword",
            method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String modifyPassword(HttpSession session, @RequestParam("infoPassword") String password) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModifyPasswordResult result = this.profileService.modifyPassword(password, user, session);

        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "/resetContact",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getResetContact() {
        return new ModelAndView("home/profile");
    }

    @RequestMapping(value = "/resetContact",
    method = RequestMethod.PATCH,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchResetContact(HttpSession session, @RequestParam("infoContact") String contact) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModifyPasswordResult result = this.profileService.resetContact(contact, user, session);

        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "/resetAddress",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getResetAddress(){
        return new ModelAndView("home/profile");
    }


    @RequestMapping(value = "/resetAddress",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchResetAddress(HttpSession session, @RequestParam("infoAddressPostal") String postal,
                                    @RequestParam("infoAddressPrimary") String primary, @RequestParam("infoAddressSecondary") String secondary) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModifyPasswordResult result = this.profileService.resetAddress(postal, primary, secondary, user, session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "/profileImage",
    method = RequestMethod.POST,
    produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String postIndex(HttpServletRequest request,
                                  ProfileEntity profile,
                                  @RequestParam(value = "thumbnailMultipart")MultipartFile thumbnailMultipart,
                                  HttpSession session) throws IOException {
        profile.setProfileThumbnail(thumbnailMultipart.getBytes());
        profile.setProfileThumbnailMime(thumbnailMultipart.getContentType());
        boolean result = this.profileService.putProfile(request, profile, session);
//        if (result) {
//            modelAndView.setViewName("redirect:/profile");
//        }
        return String.valueOf(result);
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

    @RequestMapping(value = "/deleteUser",
    method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteUser(HttpSession session) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");
        session.setAttribute("user", loggedInUser);
        DeleteUserResult result = this.profileService.deleteUserResult(session);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};

        if (result == DeleteUserResult.SUCCESS) {
            session.invalidate();
        }
        return responseObject.toString();
    }
}
