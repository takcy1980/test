package com.pse.fotoz.controllers.photographers;


import com.pse.fotoz.dbal.entities.*;
import com.pse.fotoz.properties.LocaleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author René
 */

@Controller
@RequestMapping("/photographers/account/photos")
public class PhotographersPhotosController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, String> labels;
        mav.setViewName("/photographers/account/photos.twig");

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
        mav.addObject("pictures", getPictureList());
        return mav;
    }
    
    private  List<Picture> getPictureList(){
        List<Picture> returnList = new ArrayList<>();
//        for (int i=0; i<5; i++){
//            Picture pic = new Picture();
//            pic.setName("dddd");
//            returnList.add(pic);
//       
//    }
        Shop s  = Shop.getShopByID(2);
        for(Picture p: s.getPictures()){
            returnList.add(p);
        }
            
  
        return returnList;
    }
    
}

