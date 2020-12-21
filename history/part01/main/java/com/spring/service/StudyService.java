package com.spring.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.spring.repository.CommonDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyService  {

    public final CommonDao dao;

    @Transactional
    public String doReg(Map model) {
        String resultPage = "";
        //1. 중복 확인
        int cnt = dao.selectInt("study.existMembers", model);
        if(cnt > 0) {
            resultPage = "home/registFail";
        }
        else {
            //2. 등록
            String password = (String)model.get("password");
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                digest.update(password.getBytes());
                byte byteData[] = digest.digest();
                StringBuffer sb = new StringBuffer();
                for(byte byteTmp : byteData) {
                    sb.append(Integer.toString((byteTmp&0xff) + 0x100, 16).substring(1));
                }
                model.put("password", sb.toString());System.out.println("PW :: " + sb.toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            dao.insert("study.insertMember", model);
            resultPage = "home/registOK";
        }
        return resultPage;
    }

    @Transactional
    public Map testTransaction(ModelMap model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", "1");
        map.put("name", "아로미");
        //1. 데이터 확인
        Map<String, Object> resultMap = dao.selectOne("study.selectItems", map);
        if(resultMap == null) System.out.printf("id가 %s인 데이터는 존재하지 않습니다.\n", map.get("id"));
        else System.out.println("조회 결과 확인 :: " + resultMap.toString());
        map.put("id", "777");
        
        //2. 데이터 insert
        dao.insert("study.insertItems", map);
        
        //3. 데이터 확인
        resultMap = dao.selectOne("study.selectItems", map);
        if(resultMap == null) System.out.printf("id가 %s인 데이터는 존재하지 않습니다.\n", map.get("id"));
        else System.out.println("조회 결과 확인 :: " + resultMap.toString());
        
        System.out.println("" + 1/0);
        
        //4. 데이터 삭제
        System.out.println("입력된 데이터 삭제 " + map.toString());
        dao.delete("study.deleteItems", map);
        
        //5. 데이터 확인
        resultMap = dao.selectOne("study.selectItems", map);
        if(resultMap == null) System.out.printf("id가 %s인 데이터는 존재하지 않습니다.\n", map.get("id"));
        else System.out.println("조회 결과 확인 :: " + resultMap.toString());
        
        return resultMap;
    }
}
