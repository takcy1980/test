package com.pse.fotoz.properties;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Robert
 */
public class LocaleUtil {
    
    public static Map<String, String> getProperties(HttpServletRequest request) 
    {
        try {
            String lang = request.getSession().getAttribute("lang").toString();
            return getProperties(lang);
        } catch (IllegalArgumentException | NullPointerException e) {
            request.getSession().setAttribute("lang", "nl");
            String lang = request.getSession().getAttribute("lang").toString();
            return getProperties(lang);
        }
    }
    
    public static Map<String, String> getProperties(String lang) {
        
        final String propertiesFile;
        final Locale locale = getLocale(lang);
        
        switch (lang.toLowerCase()) {
            case "en":
                propertiesFile = "properties.en_GB";
                break;
            case "nl":
                propertiesFile = "properties.nl_NL";
                break;                
            default:
                throw new IllegalArgumentException("Language is not "
                        + "supported.");
        }
        
        final ResourceBundle bundle = ResourceBundle.getBundle(
                propertiesFile, 
                locale);
        
        return bundle.keySet().stream().collect(
                Collectors.toMap(k -> k, k -> bundle.getString(k)));
    }
    
    public static Locale getLocale(String lang) {
        switch (lang.toLowerCase()) {
            case "en":
                return Locale.ENGLISH;
            case "nl":
                return new Locale.Builder().
                        setLanguage("nl").setRegion("NL").build();             
            default:
                throw new IllegalArgumentException("Language is not "
                        + "supported.");
        }
    }
}
