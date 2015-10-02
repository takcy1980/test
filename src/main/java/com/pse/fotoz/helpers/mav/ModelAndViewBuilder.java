package com.pse.fotoz.helpers.mav;

import com.pse.fotoz.properties.LocaleUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Robert
 */
public class ModelAndViewBuilder {
    private final ModelAndView blueprint = new ModelAndView();
    
    public ModelAndViewBuilder withProperties(
            HttpServletRequest request) {
        Map<String, String> labels;

        try {
            labels = LocaleUtil.getProperties(
                    request.getSession().getAttribute("lang").toString());
        } catch (IllegalArgumentException | NullPointerException e) {
            request.getSession().setAttribute("lang", "nl");
            labels = LocaleUtil.getProperties(
                    request.getSession().getAttribute("lang").toString());
        }
            
        blueprint.addObject("labels", labels);
        
        return this;
    }
    
    public ModelAndView build() {
        return blueprint;
    }
    
    public static ModelAndViewBuilder empty() {
        return new ModelAndViewBuilder();
    }
}
