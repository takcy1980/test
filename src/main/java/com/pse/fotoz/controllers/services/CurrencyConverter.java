package com.pse.fotoz.controllers.services;

import com.pse.fotoz.remote.ws.FixerIo;
import javax.servlet.http.HttpServletRequest;
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
            return ResponseEntity.ok().body(FixerIo.getRates().toString());
        } catch (FixerIo.ServiceUnavailableException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Service unavailable.");
        }
    }
}
