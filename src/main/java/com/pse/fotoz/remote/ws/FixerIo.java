package com.pse.fotoz.remote.ws;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author Robert
 */
public class FixerIo {
    
    private static final String endpoint = "https://api.fixer.io/latest";
    
    private static JSONObject latestRates;
    
    public static JSONObject getRates() throws ServiceUnavailableException {
        if (latestRates == null) {
            refresh();
        } else {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").
                        parse(latestRates.getString("date"));
                if ( (new Date().getTime() - date.getTime() ) / 
                        (24 * 60 * 60 * 1000) > 1) {
                    refresh();
                }
            } catch (ParseException ex) {
                throw new ServiceUnavailableException("Service is "
                        + "unavailable.");
            }
        }
        
        return latestRates.getJSONObject("rates");
    }

    private static void refresh() throws ServiceUnavailableException {
        try {
            latestRates = new JSONObject(new URL(endpoint).
                    getContent().toString());
        } catch (IOException ex) {
            throw new ServiceUnavailableException("Service is "
                    + "unavailable.");
        }
    }

    public static class ServiceUnavailableException extends Exception {

        public ServiceUnavailableException(String message) {
            super(message);
        }
    }
    
}
