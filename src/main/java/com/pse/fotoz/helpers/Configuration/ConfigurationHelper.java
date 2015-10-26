/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author René
 */
public class ConfigurationHelper {

    //---------general------------- 

    public static String getUrlprefix() {
        return ConfigurationManager.config.getString("general.urlprefix");
    }

    //-----fileupload---------

    public static int getMaxfilesizeinkb() {
        return ConfigurationManager.config.getInt("fileupload.maxfilesizeinkb");
    }

    public static int getMaxmultipartsizeinkb() {
        return ConfigurationManager.config.
                getInt("fileupload.maxmultipartsizeinkb");
    }

    public static List<String> getExtensionwhitelist() {
        return Arrays.asList(ConfigurationManager.config.
                getStringArray("fileupload.extensionwhitelist"));
    }
    
    //------filelocations-------
    
    public static String getGeneralAssetLocation() {
        return ConfigurationManager.config.
                getString("filelocations.generalassetlocation");
    }
        
    public static String getShopAssetLocation() {
        return ConfigurationManager.config.
                getString("filelocations.savelocationshops");
    }
                
    public static String getProductTypeAssetLocation() {
        return ConfigurationManager.config.
                getString("filelocations.savelocationproducttype");
    }
}
