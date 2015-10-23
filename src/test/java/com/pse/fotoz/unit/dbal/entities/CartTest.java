/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.unit.dbal.entities;

import com.pse.fotoz.dbal.entities.Cart;
import com.pse.fotoz.dbal.entities.Order;
import com.pse.fotoz.dbal.entities.OrderEntry;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert
 */
public class CartTest {
    
    private Cart cart;
    private final Supplier<OrderEntry> orders = () -> new OrderEntry();
    
    public CartTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        cart = new Cart();
        cart.getOrder().setEntries(new HashSet<>());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addOrderEntry method, of class Cart.
     */
    @Test
    public void testAddOrderEntry() {
        int amount = 10;
        
        Stream.generate(orders).limit(amount).
                forEach(e -> cart.addOrderEntry(e));
        
        Assert.assertEquals(amount, cart.getOrder().getEntries().size());
    }

    /**
     * Test of removeOrderEntry method, of class Cart.
     */
    @Test
    public void testRemoveOrderEntry() {
        int amount = 10;
        OrderEntry subject = new OrderEntry();
        
        Stream.generate(orders).limit(amount).
                forEach(e -> cart.addOrderEntry(e));
        cart.addOrderEntry(subject);        
        
        Assert.assertEquals(amount + 1, cart.getOrder().getEntries().size());
        Assert.assertTrue(cart.getOrder().getEntries().contains(subject));
        
        cart.removeOrderEntry(subject);
        
        Assert.assertEquals(amount, cart.getOrder().getEntries().size());
        
        Assert.assertFalse(cart.getOrder().getEntries().contains(subject));
    }
    
}
