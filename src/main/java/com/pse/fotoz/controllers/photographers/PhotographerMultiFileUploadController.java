/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers;

import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.Configuration.ConfigurationHelper;
import com.pse.fotoz.properties.LocaleUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author René
 */
@Controller
@RequestMapping("/photographers/upload")
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
            HttpServletResponse response) {

        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath(ConfigurationHelper.getShopAssetLocation());//dit bepaald de folder waar opgeslagen wordt
        String returnMessage = "";

        Iterator<String> itr = request.getFileNames();
        MultipartFile file = null;

        while (itr.hasNext()) {
            file = request.getFile(itr.next());
            Logger.getLogger(file.getOriginalFilename() + " uploaded! ");


            if (!file.isEmpty()) {
                try {
                    String name = file.getOriginalFilename();
                    
                    Shop shop = getCurrentSHop();
                    //todo: sessie dropdowntje oid
                    PictureSession session = (shop.getSessions().isEmpty()) ? null : shop.getSessions().iterator().next();
                    if (saveUpload(file, name, session)) {
                        File folder = new File(appPath + "\\" + shop.getLogin());
                        folder.mkdir(); //maak folder als niet bestaat
                        file.transferTo(new File(appPath + "\\" + shop.getLogin() + "\\" + name));
                        returnMessage = "Upload van " + name + " geslaagd!";
                    }
                } catch (Exception e) {
                    returnMessage = "Upload mislukt => " + e.getMessage();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                returnMessage = "Upload van mislukt, bestand was leeg.";
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        }
        return returnMessage;
    }

    private Shop getCurrentSHop() {
        //todo: linken aan inlogprocedure
        return Shop.getShopByLogin("mooie-kiekjes");
    }

    
    private boolean saveUpload(MultipartFile file, String title, PictureSession session) throws IOException {
        boolean returnVal = false;
        try {
            //dit weghalen indien dimensies niet nodig zijn
            BufferedImage image = ImageIO.read(file.getInputStream());

            Picture pic1 = new Picture();
            pic1.setSession(session);
            pic1.setWidth(image.getWidth());
            pic1.setHeight(image.getHeight());
            pic1.setFileName(file.getOriginalFilename());
            
            //pic1.setDescription(description);
            //pic1.setPrice(new BigDecimal(10.75));
            pic1.setHidden(false);
            pic1.setApproved(Picture.Approved.PENDING);
            pic1.setSubmissionDate(new Date());
            pic1.setTitle(title);
            pic1.persist();
            returnVal = true;
        } catch (HibernateException ex) {
            Logger.getLogger(PhotographerMultiFileUploadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;
    }
}
