/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.stresstests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.liquid.portal.service.bean.LiquidPortalServiceBean;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author urtks
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Stress test of the creation of the bean.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCreation() throws Exception {
        long startMillis = System.currentTimeMillis();

        for (int i = 0; i < 1000; ++i) {
            LiquidPortalServiceBean liquidPortalService = new LiquidPortalServiceBean();

            Field field = liquidPortalService.getClass().getDeclaredField("configurationFileName");
            field.setAccessible(true);
            field.set(liquidPortalService, "test_files/StressTests/liquidProtalService.properties");

            field = liquidPortalService.getClass().getDeclaredField("configurationNamespace");
            field.setAccessible(true);
            field.set(liquidPortalService, "com.liquid.portal.service");

            Method method = liquidPortalService.getClass().getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(liquidPortalService, new Object[] {});
        }

        long endMillis = System.currentTimeMillis();

        System.out.println("It takes " + (endMillis - startMillis)
                + " millis to create 1000 instances of LiquidPortalServiceBean.");
    }
}
