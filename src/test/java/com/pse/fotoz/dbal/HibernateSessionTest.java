package com.pse.fotoz.dbal;

import com.pse.fotoz.dbal.entities.Photographer;
import org.hibernate.Session;
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
public class HibernateSessionTest {
    
    public HibernateSessionTest() {
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
    public void testHibernateSetup() throws HibernateException {        
        Photographer photographer = new Photographer();
        photographer.setAddress("Molenaar 24");
        photographer.setCity("Eindhoven");
        photographer.setEmail("info@mooiekiekjes.nl");
        photographer.setName("Mooie Kiekjes Eindhoven");
        photographer.setPhone("040-9573238");
        
        photographer.persist();
    }
    
}
