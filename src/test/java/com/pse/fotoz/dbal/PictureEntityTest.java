/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.dbal;

import com.pse.fotoz.dbal.entities.Customers;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.Shop;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 *
 * @author Robert
 */
public class PictureEntityTest {
    private static Session session;
    
    public PictureEntityTest() {        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() throws HibernateException {
        session = HibernateSession.getInstance().newSession();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void TestPersistence() throws HibernateException {
        Customers customer = new Customers();
        customer.setAddress("dorpstraat 1");
        customer.setCity("Tilburg");
        customer.setEmail("henk@henk.nl");
        customer.setName("Henk Henken");
        customer.setPhone("077-455998");
        customer.persist();
        
        Photographer photographer = new Photographer();
        photographer.setAddress("Molenaar 24");
        photographer.setCity("Eindhoven");
        photographer.setEmail("info@mooiekiekjes.nl");
        photographer.setName("Mooie Kiekjes Eindhoven");
        photographer.setPhone("040-9573238");

        photographer.persist();

        Shop shop = new Shop();
        shop.setPhotographer(photographer);
        shop.setLogin("winkel1");
        shop.setPasswordHash("123");

        shop.persist();

        Picture pic1 = new Picture();
        pic1.setShop(shop);
        pic1.setWidth(500);
        pic1.setHeight(400);
        pic1.setFileName("weiland.jpg");
        pic1.setDescription("Weiland gelegen in Zuidlimburg. Zonnig plaatje "
                + "met veel vee.");
        pic1.setPrice(new BigDecimal(10.75));
        pic1.setHidden(false);
        pic1.setApproved(Picture.Approved.PENDING);
        pic1.setSubmissionDate(new Date());
        pic1.setTitle("Weiland in mei.");

        pic1.persist();

        Shop shopFromDB = HibernateEntityHelper.all(Shop.class).
                stream().
                filter(s -> s.getLogin().equals("winkel1")).
                findAny().
                get();

        Assert.notNull(shopFromDB.getPictures());
        Assert.isTrue(shopFromDB.getPictures().size() == 1);

        Picture picFromDB = (Picture) shopFromDB.getPictures().iterator().next();
        
        Assert.isTrue(picFromDB.getFileName().equals("weiland.jpg"));
    }
}
