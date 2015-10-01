package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Robert
 */
@Controller
@RequestMapping("/producer/dashboard/submissions")
public class ProducerPictureSubmissionController {

    @RequestMapping(method = RequestMethod.GET)
	public ModelAndView displaySubmissions(HttpServletRequest request) {
            ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
            
            List<Picture> pictures = HibernateEntityHelper.all(Picture.class);
            
            mav.addObject("pictures", pictures);
            
            mav.addObject("page", new Object() {
                public String lang = request.getSession().
                        getAttribute("lang").toString();
                public String uri = "/producer/dashboard/submissions";
                public String redirect = request.getRequestURL().toString();
            });
            
            mav.setViewName("producer/dashboard/submissions.twig"); 
            
            return mav;
	}
}
