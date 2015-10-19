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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javassist.bytecode.Descriptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author René
 */
@Controller
@RequestMapping("/photographers/upload2")
public class PhotographerMultiFileUploadController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, String> labels;
        mav.setViewName("/photographers/account/uploadMulti.twig");

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
    public @ResponseBody
    String upload(MultipartHttpServletRequest request,
            HttpServletResponse response) throws IOException {

        // Getting uploaded files from the request object
        Map<String, MultipartFile> fileMap = request.getFileMap();
              //Logger.getLogger(fileMap.size())
        //for(Map.Entry<String, MultipartFile> entry : Map.

        //1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        //2. get each file
        while (itr.hasNext()) {
            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            Logger.getLogger(mpf.getOriginalFilename() + " uploaded! ");

            File folder = new File("C:\\test");
            folder.mkdir(); //maak folder als niet bestaat
            mpf.transferTo(new File("c:\\test\\" + mpf.getOriginalFilename()));

        }
        return "";
    }
}
