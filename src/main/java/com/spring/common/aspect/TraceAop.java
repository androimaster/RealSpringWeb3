package com.spring.common.aspect;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TraceAop {

    @Pointcut("execution(public * com.spring.controller..*(..))")
    public void pointCutMethod() {}
    
    @Around("pointCutMethod() && args(model, req, res)")
    public Object trace(ProceedingJoinPoint joinPoint, Model model, HttpServletRequest req, HttpServletResponse res) throws Throwable {
        log.info("===================================================================");
        log.info("profiler == Before  || " + joinPoint.getSignature().toShortString());
        log.info("== 이 시스템은 " + System.getProperty("spring.profiles.active") + "입니다.");
        String addr = req.getRemoteAddr();
        log.info("== 서비스 요청 ip는 " + addr + "입니다.");
        log.info("===================================================================");
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(joinPoint.getArgs()[0], Map.class);
        
        Object result = new Object();
        try {
            result = joinPoint.proceed();
            log.info("sserver :: " + System.getProperty("spring.profiles.active"));
            model.addAttribute("ServerInfo", System.getProperty("spring.profiles.active"));
            return result;
        }
        finally { }
    }
}

