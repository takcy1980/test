/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.*;

/**
 *
 * @author René
 */
public class ConfigurationManager {

    private static final int reloadDelayMS = 30000;//herlaadtijd voor xml, zodat de xml niet telkens gelezen hoeft te worden
    public static XMLConfiguration config;

    static {
        initConfig();
    }

    public static void initConfig()  {
    
        try {
            config = new XMLConfiguration("application.cfg.xml");
        } catch (ConfigurationException ex) {
            Logger.getLogger(ConfigurationManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        strategy.setRefreshDelay(reloadDelayMS);
        config.setReloadingStrategy(strategy);
        config.setValidating(true);//valideer de xml

    }

}
