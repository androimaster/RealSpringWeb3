package com.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.common.util.CommonUtil;
import com.spring.service.StudyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StudyService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest req, HttpServletResponse res) {
        Locale locale = new Locale("ko", "KR");
        log.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate );
        model.addAttribute("combo", CommonUtil.getSelect());
        return "home";
    }

    @GetMapping("home/commonCode")
    public String commonCode(Model model, HttpServletRequest req, HttpServletResponse res) {
        log.info(" :: Study.commonCode :: ");
        return "home/commonCode";
    }
}
