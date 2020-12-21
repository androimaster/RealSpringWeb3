package com.spring.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.common.auth.UserAuth;
import com.spring.service.CommonService;
import com.spring.service.StudyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@RequestMapping("nosession")
@RequiredArgsConstructor
public class NosessionController {

    private final StudyService studyService;
    private final CommonService commonService;

    @GetMapping("nosession/registForm")
    public String registForm(Model model, HttpServletRequest req, HttpServletResponse res) { 
        return "home/registForm";
    }

    @RequestMapping("nosession/doReg")
    public String doReg(@RequestParam Map<String, String> model, HttpServletRequest req, HttpServletResponse res) {
        System.out.println(model.toString());
        return (String) studyService.doReg(model);
    }
    
    @RequestMapping("nosession/loginForm")
    public String loginForm(@RequestParam Map<String, String> map, Model model, HttpServletRequest req, HttpServletResponse res) {
        return "auth/loginForm";
    }
    
    @RequestMapping("nosession/login")
    public String login(@RequestParam Map<String, String> map, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
        String name = map.get("name");
        String password = map.get("password");
        log.info("1 name :: " + name);
        log.info("2 password :: " + password);
        String rtnPW = "";
        String page = "";
        try {
            //1. name 으로 회원 정보 조회
            Map param = new HashMap();
            param.put("NAME", name);
            Map<String, Object> userInfo = commonService.getUserInfo(param);
            if(userInfo == null) {
                log.info("존재하지 않는 사용자입니다.");
                model.addAttribute("error", "true");
                return "auth/loginForm";
            }
            log.info("1. 사용자 정보를 가져왔습니다.");
            String getPassword = (String)userInfo.get("PASSWORD");
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //해쉬값 업데이트
            digest.update(password.getBytes());
            //해쉬값(다이제스트) 얻기
            byte byteData[] = digest.digest();
            log.info("byteData[]:"+Arrays.toString(byteData));
            
            StringBuffer sb = new StringBuffer();
            //출력
            for(byte byteTmp : byteData) {
                sb.append(Integer.toString((byteTmp&0xff) + 0x100, 16).substring(1));
            }
            rtnPW = sb.toString();

            log.info("rtnPW       :: " + rtnPW);
            log.info("getPassword :: " + getPassword);
            log.info("req.getContextPath() :: " + req.getContextPath());
            if(rtnPW.equals(getPassword) ) {
                page = "home";
                log.info("로그인 성공 - " + page);
                UserAuth userAuth = new UserAuth((String)userInfo.get("NAME"), (String)userInfo.get("EMAIL"));
                session.setAttribute("authUser", userAuth);
                model.addAttribute("sserver", System.getProperty("spring.profiles.active"));
            }
            else {
                log.info("패스워드가 틀렸습니다.");
                model.addAttribute("error", "true");
                page = "auth/loginForm";
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        log.info("req.getContextPath() :: " + req.getContextPath());
        return page;
    }

    @RequestMapping("nosession/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index.jsp";
    }
}
