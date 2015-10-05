/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers;

import com.pse.fotoz.helpers.Configuration.ConfigurationHelper;
import com.pse.fotoz.properties.LocaleUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author René
 */
@Controller
@RequestMapping(value = "/photographers/upload")
public class PhotographersFileUploadController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, String> labels;
        mav.setViewName("/photographers/account/upload.twig");

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
    
    //TODO: extensie checken, checken voor dubbel, localisatie
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String returnMessage;
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath(ConfigurationHelper.getShopAssetLocation());
        String name = "";

        if (!file.isEmpty()) {
            try {
                name = file.getOriginalFilename();

                file.transferTo(new File(appPath + "\\" + name));
                returnMessage = "Upload van " + name + " geslaagd!";
            } catch (Exception e) {
                returnMessage = "Upload van " + name + " mislukt => " + e.getMessage();
            }
        } else {
            returnMessage = "Upload van " + name + " mislukt, bestand was leeg.";
        }

        return returnMessage;
    }

}
