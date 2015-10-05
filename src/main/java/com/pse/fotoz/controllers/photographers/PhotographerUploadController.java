/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers;

import com.pse.fotoz.properties.LocaleUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author René
 */
@Controller
@RequestMapping("/photographers/upload")
public class PhotographerUploadController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, String> labels;
        mav.setViewName("/photographers/account/upload2.twig");

        try {
            labels = LocaleUtil.getProperties(
                    request.getSession().getAttribute("lang").toString());
        } catch (IllegalArgumentException | NullPointerException e) {
            request.getSession().setAttribute("lang", "nl");
            labels = LocaleUtil.getProperties(
                    request.getSession().getAttribute("lang").toString());
        }

        mav.addObject("labels", labels);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String redirect = request.getRequestURL().toString();
        });
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveFiles(
            @ModelAttribute("uploadForm") FileUpload uploadForm,
            Model map) throws IllegalStateException, IOException {
        String saveDirectory = "c:/fileUpload/";

        List<MultipartFile> fileList = uploadForm.getFiles();

        List<String> fileNames = new ArrayList<>();

        if (null != fileList && fileList.size() > 0) {
            for (MultipartFile multipartFile : fileList) {

                String fileName = multipartFile.getOriginalFilename();
                if (!"".equalsIgnoreCase(fileName)) {
                    // Handle file content - multipartFile.getInputStream()
                    multipartFile
                            .transferTo(new File(saveDirectory + fileName));
                    fileNames.add(fileName);
                }
            }
        }

        map.addAttribute("files", fileNames);
        return "";
    }
}
