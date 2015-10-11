/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers;

import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.*;
import com.pse.fotoz.helpers.Configuration.ConfigurationHelper;
import com.pse.fotoz.properties.LocaleUtil;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author René
 */
@Controller
@RequestMapping(value = "/photographers/upload")
public class PhotographersFileUploadController {

    //hele vage deploymentexceptin: eens nakijken wtf dat is
//    private Shop loggedInShop;
//    private  PhotographersFileUploadController() {
//        loggedInShop = getCurrentSHop();
//    }
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
    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String returnMessage = "";
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath(ConfigurationHelper.getShopAssetLocation());//dit bepaald de folder waar opgeslagen wordt
        //String name = "";

        if (!file.isEmpty()) {
            try {
                name = file.getOriginalFilename();
                //@Issue vindt de juiste foto sessie
                PictureSession session = null;
                Shop shop = session.getShop();
                if (saveUpload(file, name, description, session)) {
                    File folder = new File(appPath + "\\" + shop.getLogin());
                    folder.mkdir(); //maak folder als niet bestaat
                    file.transferTo(new File(appPath + "\\" + shop.getLogin() + "\\" + name));
                    returnMessage = "Upload van " + name + " geslaagd!";
                }
            } catch (Exception e) {
                returnMessage = "Upload van " + name + " mislukt => " + e.getMessage();
            }
        } else {
            returnMessage = "Upload van " + name + " mislukt, bestand was leeg.";
        }

        return returnMessage;
    }

    private Shop getCurrentSHop() {
        //todo: linken aan inlogprocedure
        return Shop.getShopByLogin("mooiekiekjes-eindhoven");
    }

    //TODO: width en height afleiden
    private boolean saveUpload(MultipartFile file, String title, String description, PictureSession session) {
        boolean returnVal = false;
        try {

            Picture pic1 = new Picture();
            pic1.setSession(session);
            //pic1.setWidth(500);
            //pic1.setHeight(400);
            pic1.setFileName("weiland.jpg");
            pic1.setDescription(description);
            //pic1.setPrice(new BigDecimal(10.75));
            pic1.setHidden(false);
            pic1.setApproved(Picture.Approved.PENDING);
            pic1.setSubmissionDate(new Date());
            pic1.setTitle(title);
            pic1.persist();
            returnVal = true;
        } catch (HibernateException ex) {
            Logger.getLogger(PhotographersFileUploadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;
    }

    private boolean saveUploadToDisc(MultipartFile file) {
        boolean returnVal = false;

        return returnVal;
    }

}
