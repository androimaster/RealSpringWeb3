package com.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.service.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("common")
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService service;

    @PostMapping("getCodeList")
    public Map getCodeList(@RequestParam Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) {
        log.debug("common.getCodeList == " + model.toString());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("codeList", service.getCodeList(model));
        return result;
    }
}
