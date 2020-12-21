package com.spring.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.repository.CommonDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    public final CommonDao dao;

    @Transactional
    public String upload(MultipartHttpServletRequest request) {
        String result = "home/registOK";
        try {
            Iterator<String> itr = request.getFileNames();
            MultipartFile mpf = null;
            int cnt = 0;
            String sGroupKey = dao.selectStr("board.selectFileGroupKey", null);
            while (itr.hasNext()) {
                cnt++;
                mpf = request.getFile(itr.next());
                if(mpf.isEmpty()) continue;
                log.info(mpf.getOriginalFilename() + " uploaded!");

                String sPath = "c:" + File.separator + "NAS" + File.separator;

                //1. 파일관리 테이블에 데이터를 insert한다.
                Map<String, Object> param = new HashMap<>();
                param.put("GROUP_KEY"    , sGroupKey);
                param.put("FILE_KEY"     , cnt);
                param.put("FILE_REALNAME", mpf.getOriginalFilename());
                param.put("FILE_NAME"    , Calendar.getInstance().getTimeInMillis());
                param.put("FILE_PATH"    , sPath);
                param.put("FILE_LENGTH"  , mpf.getBytes().length);
                param.put("FILE_TYPE"    , mpf.getContentType());
                param.put("USER_ID"      , System.getProperty("spring.profiles.active"));

                //2. 지정된 위치가 존재하는지 확인하고 없으면 경로를 생성한다.
                File chkDir = new File(sPath);
                if(!chkDir.isDirectory()) {
                    chkDir.mkdirs();
                }

                //3. 지정된 위치에 파일을 복사한다.
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(sPath + param.get("FILE_NAME")));
                dao.insert("board.insertFileInfo", param);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "home/registFail";
        }
        return result;
    }

    @Transactional
    public List<Map<String, Object>> upload3(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("files");
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        String sPath = "c:" + File.separator + "NAS" + File.separator;
        String sGroupKey = dao.selectStr("board.selectFileGroupKey", null);
        int cnt = 0;
        
        for(int i=0; i < files.size(); i++) {
            MultipartFile mpf = files.get(i);
            cnt++;
            
            try {
                //1. 파일관리 테이블에 데이터를 insert한다.
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("GROUP_KEY", sGroupKey);
                param.put("FILE_KEY", cnt);
                param.put("FILE_REALNAME", mpf.getOriginalFilename());
                param.put("FILE_NAME", Calendar.getInstance().getTimeInMillis());
                param.put("FILE_PATH", sPath);
                param.put("FILE_LENGTH", mpf.getBytes().length);
                param.put("FILE_TYPE", mpf.getContentType());
                param.put("USER_ID", System.getProperty("spring.profiles.active"));
                returnList.add(param);
                dao.insert("board.insertFileInfo", param);
                
                //2. 지정된 위치가 존재하는지 확인하고 없으면 경로를 생성한다.
                File chkDir = new File(sPath);
                if(!chkDir.isDirectory()) {
                    chkDir.mkdirs();
                }
                
                //3. 지정된 위치에 파일을 복사한다.
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(sPath + param.get("FILE_NAME")));
            }
            catch (IOException e) {
                System.out.println("error - " + e.getMessage());
                e.printStackTrace();
            }
            
        }
        return returnList;
    }
}
