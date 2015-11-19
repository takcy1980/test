package com.pse.fotoz.controllers.customers.shops;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.ProductOption;
import com.pse.fotoz.dbal.entities.ProductOption.ColorOption;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.helpers.forms.ColorOptionWrapper;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import com.pse.fotoz.properties.LocaleUtil;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Robert
 */
@Controller
@RequestMapping("/customers/pictures/")
public class CustomerPictureController {
    @RequestMapping(value = "/{picture}", method = RequestMethod.GET)
    public ModelAndView displayPictureDetail(
            @PathVariable("picture") String pictureid,
            HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/picture/" + pictureid + "/";
            public String redirect = request.getRequestURL().toString();
        });
        
        final Integer userid = Users.currentUserAccount()
                .map(a -> a.getId())
                .orElse(Integer.MIN_VALUE);
        
        int pictureidi = Parser.parseInt(pictureid)
                .orElse(Integer.MIN_VALUE);
        
        Optional<Picture> picture = HibernateEntityHelper.
                byId(Picture.class, pictureidi);
        
        boolean hasAccess = picture.map(p -> !p.isHidden()).orElse(false) ||
                picture.map(p -> p.getSession().getPermittedAccounts().stream()
                        .anyMatch(c -> c.getId() == userid))
                        .orElse(false);
        
        if (hasAccess) {
            mav.addObject("picture", picture.get());
            mav.setViewName("customers/pictures/picture_detail.twig");
            
            return mav;
        } else {
            return new ModelAndView("redirect:/app/customers/shops/");
        }
    }
    
    @RequestMapping(value = "/{picture}/order", method = RequestMethod.GET)
        public ModelAndView displayPictureOrder(
            @PathVariable("picture") String pictureid,
            final HttpServletRequest request) {
        ModelAndView mav = displayPictureDetail(pictureid, request);
        
        List<ProductType> types = HibernateEntityHelper.
                all(ProductType.class);
        
        List<ColorOptionWrapper> colorOptions = Stream.of(ColorOption.values()).
                map(o -> ColorOptionWrapper.of(o, 
                        LocaleUtil.getProperties(request))).
                collect(toList());
        
        mav.addObject("types", types);
        mav.addObject("colors", colorOptions);
        
        if (!mav.getViewName().startsWith("redirect:")) {
            mav.setViewName("customers/pictures/picture_order.twig");
        }
        
        return mav;
    }
}
