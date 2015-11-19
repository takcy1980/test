/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments;

import com.pse.fotoz.payments.domain.PaymentRequest;
import com.pse.fotoz.payments.domain.PaymentResponse;
import com.pse.fotoz.payments.domain.enums.Locale;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author René
 */
public class PaymentFacadeTest {
    
    public PaymentFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreatePayment() {
        
        PaymentRequest payC = new PaymentRequest();
        payC.setAmount(10.12d);
        payC.setDescription("description test");
        payC.setRedirectUrl("http://www.test.abc/redirecturlvanons");
        //payC.setWebhookUrl("http://www.test.abc/");
        payC.setLocale(Locale.SPAIN);
        
        PaymentFacade pmf  = new PaymentFacade();
       // pmf.setIsDebug(true);
        //pmf.setUseProxy(true);
        PaymentResponse result = pmf.CreatePayment(payC).get();
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
