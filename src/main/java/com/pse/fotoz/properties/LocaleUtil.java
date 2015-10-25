package com.pse.fotoz.properties;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 * Utility class deriving properties and locale specifications.
 * @author Robert
 */
public class LocaleUtil {
    
    /**
     * 
     * @param request
     * @return 
     */
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
    
    /**
     * Obtains properties for a given language.
     * @param lang One of the supported languages "en" or "nl"
     * @return Map containing key-value pairs as stored in the properties file.
     */
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
    
    /**
     * 
     * @param request
     * @return 
     */
    public static Map<String, String> getErrorProperties(
            HttpServletRequest request) 
    {
        try {
            String lang = request.getSession().getAttribute("lang").toString();
            return getErrorProperties(lang);
        } catch (IllegalArgumentException | NullPointerException e) {
            request.getSession().setAttribute("lang", "nl");
            String lang = request.getSession().getAttribute("lang").toString();
            return getErrorProperties(lang);
        }
    }
    
    /**
     * Obtains errormessage properties for a given language.
     * @param lang One of the supported languages "en" or "nl"
     * @return Map containing key-value pairs as stored in the properties file.
     */
    public static Map<String, String> getErrorProperties(String lang) {
        
        final String propertiesFile;
        final Locale locale = getLocale(lang);
        
        switch (lang.toLowerCase()) {
            case "en":
                propertiesFile = "properties.errormsg_en";
                break;
            case "nl":
                propertiesFile = "properties.errormsg_nl";
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
    
    /**
     * Builds the appropriate Locale from the given language.
     * @param lang One of the supported languages "en" or "nl"
     * @return Locale corresponding to the given language.
     */
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
