/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.integration.controllers.customers.shops;

import com.pse.fotoz.controllers.customers.shops.CustomerCartController;
import com.pse.fotoz.integration.controllers.TestMvcConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Robert
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration  
@ContextConfiguration(classes=TestMvcConfig.class)  
public class CustomerCartControllerTest {
    
    @Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc;
    
    CustomerCartController instance = new CustomerCartController();
    
    public CustomerCartControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of displayCart method, of class CustomerCartController.
     */
    @Test
    @Ignore
    public void testDisplayCart() {
        fail("The test case is a prototype.");
    }

    /**
     * Test of addItemToCart method, of class CustomerCartController.
     */
    @Test
    public void testAddItemToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                post("/customers/cart/ajax/add").content("")).
                andExpect(status().isBadRequest());
    }
    
}
