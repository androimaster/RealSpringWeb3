package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.common.util.CommonUtil;
import com.spring.service.StudyService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("study")
@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyService service;
    
    @GetMapping("testTransaction")
    public String testTransaction(Model model, HttpServletRequest req, HttpServletResponse res) {
        Map map = service.testTransaction(null);
        return "showMessage";
    }

    @GetMapping("getMessage1")
    public User getMessage1(Model model, HttpServletRequest req, HttpServletResponse res) {
        log.info(" :: Study.getMessage1 :: ");
        List list = new ArrayList();
        User user = new User("빵형", "1", list);
        return user;
    }

    @GetMapping("getMessage2")
    public Map getMessage2(Model model, HttpServletRequest req, HttpServletResponse res) {
        log.info(" :: Study.getMessage2 :: ");
        Map map = new HashMap();
        map.put("name", CommonUtil.getSelect());
        return map;
    }

    @GetMapping("exceptionByZero")
    public Map exceptionByZero(Model model, HttpServletRequest req, HttpServletResponse res) {
        log.info(" :: Study.exceptionByZero :: ");
        System.out.println(1/0);
        return new HashMap();
    }

}

@Data
@AllArgsConstructor
class User {
    private String name;
    private String no;
    List list;
}