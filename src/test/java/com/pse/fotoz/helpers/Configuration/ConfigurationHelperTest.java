/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.Configuration;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author René van de Vorst
 */
public class ConfigurationHelperTest {

    public ConfigurationHelperTest() {
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
    public void testConfig() {
        //todo:later weghalen, vrij nutteloos verder
        assertTrue(ConfigurationHelper.getUrlprefix().equals("/app"));
        assertTrue(ConfigurationHelper.getMaxfilesizeinkb() == 10000);
        assertTrue(ConfigurationHelper.getExtensionwhitelist()[0].equals("jpg"));
    }

}
