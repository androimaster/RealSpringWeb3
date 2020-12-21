package com.spring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.service.BoardService;
import com.spring.service.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final CommonService commonService;
    private final BoardService service;

    @GetMapping("writeForm1")
    public String writeForm1(Model model, HttpServletRequest req, HttpServletResponse res) {
        return "board/writeForm1";
    }
    
    @PostMapping("upload")
    public String upload(Model model, MultipartHttpServletRequest req, HttpServletResponse res) {
        String result = service.upload(req);
        return result;
    }

    @GetMapping("writeForm2")
    public String writeForm2(Model model, HttpServletRequest req, HttpServletResponse res) {
        return "board/writeForm2";
    }
    
    @PostMapping("upload2")
    public String upload2(Model model, MultipartHttpServletRequest req, HttpServletResponse res) {
        List<MultipartFile> files = req.getFiles("files");

        for(int i=0; i < files.size(); i++) {
            MultipartFile mpf = files.get(i);
            String sPath = "";
            try {
                sPath = "c:" + File.separator + "NAS" + File.separator;

                //1. 지정된 위치가 존재하는지 확인하고 없으면 경로를 생성한다.
                File chkDir = new File(sPath);
                if(!chkDir.isDirectory()) {
                    chkDir.mkdirs();
                }
                
                //2. 지정된 위치에 파일을 복사한다.
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(sPath + mpf.getOriginalFilename()));
            }
            catch (IOException e) {
                System.out.println("error - " + e.getMessage());
                e.printStackTrace();
            }
            model.addAttribute(files.stream().collect(Collectors.toList()));
        }
        return "home/registOK";
    }

    @GetMapping("writeForm3")
    public String writeForm3(Model model, HttpServletRequest req, HttpServletResponse res) {
        return "board/writeForm3";
    }
    
    @ResponseBody
    @PostMapping("upload3")
    public Map<String, Object> upload3(Model model, MultipartHttpServletRequest req, HttpServletResponse res) {
        Map result = new HashMap();
        List<Map<String, Object>> resultList = service.upload3(req);
        result.put("files", resultList);
        log.info(">> " + result.toString());
        return result;
    }

    @RequestMapping("getFileDownload")
    public ModelAndView getFileDownload(@RequestParam Map<String, String> map) throws Exception {
        Map fileMap = commonService.getData("board.getFileInfo", map);
        String path = "";
        if(fileMap != null) {
            path = ""+ fileMap.get("FILE_PATH") + fileMap.get("FILE_NAME");
        }
        File downloadFile = new File(path);
        Map data = new HashMap();
        data.put("model", downloadFile);  // 실제 서버에 저장된 파일정보
        data.put("FILE_REALNAME", fileMap.get("FILE_REALNAME")); //다운로드 파일정보
        
        return new ModelAndView("downloadView", "downloadFile", data);
    }

}
