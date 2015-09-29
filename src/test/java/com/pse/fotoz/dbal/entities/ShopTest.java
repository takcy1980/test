/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.dbal.entities;

import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.HibernateSession;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author René van de Vorst
 */
public class ShopTest {

    private Session session;
    private Photographer photographer;
    private Shop shop;

    public ShopTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws HibernateException {
        photographer = new Photographer();
        photographer.setAddress("Molenaar 24");
        photographer.setCity("Eindhoven");
        photographer.setEmail("info@mooiekiekjes.nl");
        photographer.setName("Mooie Kiekjes Eindhoven");
        photographer.setPhone("040-9573238");

        photographer.persist();

        shop = new Shop();
        shop.setPhotographer(photographer);
        shop.setLogin("winkel1");
        shop.setPassword("123");

        shop.persist();
        session = HibernateSession.getInstance().newSession();
    }

    @After
    public void tearDown() {
        session.close();
    }

    @Test
    public void testPasswordHashing() {

        assertTrue(shop.validatePassword("123"));
        assertFalse(shop.validatePassword("random crap"));
    }

    @Test
    public void testPersistedPasswordHashing() {
        Shop shopFromDB = Shop.getShopByID(shop.getId());
        assertTrue(shopFromDB.validatePassword("123"));
        assertFalse(shopFromDB.validatePassword("random crap"));
    }
}
