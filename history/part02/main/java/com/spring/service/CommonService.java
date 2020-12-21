package com.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.repository.CommonDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    public final CommonDao dao;

    @Transactional
    public List<Map<String, Object>> getCodeList(Map params) {
        log.debug(">>" + params.toString());
        List<Map<String, Object>> resultList = dao.selectList("common.selectItems", params);
        return resultList;
    }

    public Map<String, Object> getData(String statement, Map condition) {
        Map<String, Object> result = dao.selectOne(statement, condition);
        return result;
    }

    public List<Map<String, Object>> getDataList(String statement, Map condition) {
        List<Map<String, Object>> resultList = dao.selectList(statement, condition);
        return resultList;
    }

    public Map<String, Object> getUserInfo(Map params) {
        Map<String, Object> result = dao.selectOne("common.getUserInfo", params);
        return result;
    }
}
