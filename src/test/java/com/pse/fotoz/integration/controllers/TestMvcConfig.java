package com.pse.fotoz.integration.controllers;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.spring.PebbleViewResolver;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Mimic configuration of production environment.
 * Contains however only minimal required setup and does away with problematic,
 * unused beans.
 * @author Robert
 */
@Configuration
@ComponentScan(basePackages = {
    "com.pse.fotoz.controllers.producer",
    "com.pse.fotoz.controllers.customers",
    "com.pse.fotoz.controllers.photographers",
    "com.pse.fotoz.controllers.common"})
@EnableWebMvc
public class TestMvcConfig {
    
    @Autowired
    private ServletContext servletContext;
    
    @Bean
    public ViewResolver viewResolver() {
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix("");
        viewResolver.setPebbleEngine(pebbleEngine());
        return viewResolver;
    }
    
    @Bean
    public PebbleEngine pebbleEngine() {
        PebbleEngine engine = new PebbleEngine(templateLoader());

        /* @Issue
         Disables template caching, should be removed in a production environment
         */
        engine.setTemplateCache(null);
        
        return engine;
    }
    
    @Bean
    public Loader templateLoader() {
        return new ServletLoader(servletContext);
    }
}
