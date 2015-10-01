/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.dbal.entities;

import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.HibernateSession;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.bytecode.stackmap.TypeData.ClassName;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 *
 * @author René
 */
public class PictureTest {
    private Session session;
    private static final Logger log = Logger.getLogger(ClassName.class.getName());

    public PictureTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws HibernateException {
         session = HibernateSession.getInstance().newSession();
    }

    @After
    public void tearDown() {
        session.close();
    }

    @Test
    public void TestPersistence() throws HibernateException {
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
        pic1.setName("plaatje1.jpg");
        pic1.setDescription("kei mooi");
        pic1.setPrice(new BigDecimal(10.75));
        pic1.setHidden(false);
        pic1.setCensored(false);

        pic1.persist();

        Picture pic2 = new Picture();
        pic2.setShop(shop);
        pic2.setWidth(600);
        pic2.setHeight(400);
        pic2.setName("plaatje2.jpg");
        pic2.setDescription("kei mooi");
        pic2.setPrice(new BigDecimal(10.75));
        pic2.setHidden(false);
        pic2.setCensored(false);

        pic2.persist();
       
         //session.beginTransaction();

       // Shop shopFromDB = (Shop) session.load(Shop.class, shop.getId());
        Shop shopFromDB = Shop.getShopByID(shop.getId());
        Assert.notNull(shopFromDB.getPictures());
        Assert.isTrue(shopFromDB.getPictures().size() == 2);

        Picture picFromDB = (Picture) shopFromDB.getPictures().iterator().next();
        log.log(Level.INFO, picFromDB.getName());
        Assert.isTrue(picFromDB.getName().startsWith("plaatje"));
        
    }

}
