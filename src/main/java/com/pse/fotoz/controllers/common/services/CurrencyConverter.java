package com.pse.fotoz.controllers.common.services;

import com.pse.fotoz.remote.ws.FixerIo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Robert
 */
@Controller
@RequestMapping("/services/currency")
public class CurrencyConverter {
    
    @RequestMapping(method = RequestMethod.GET, value = "/rates")
    public ResponseEntity<String> getRates(HttpServletRequest request) {
        try {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json");
            return new ResponseEntity<>(FixerIo.getRates().toString(), 
                    responseHeaders, HttpStatus.OK);
        } catch (FixerIo.ServiceUnavailableException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Service unavailable.");
        }
    }
}
