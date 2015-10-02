/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.Configuration;

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
        return ConfigurationManager.config.getInt("fileupload.maxmultipartsizeinkb");
    }

    public static String[] getExtensionwhitelist() {
        return ConfigurationManager.config.getStringArray("fileupload.extensionwhitelist");
    }
}
