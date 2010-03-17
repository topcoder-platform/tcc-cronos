/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.liquid.portal.service.bean.LiquidPortalServiceBean;

/**
 * <p>
 * Demo for this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Demo for the bean.
     * </p>
     *
     * @throws Exception
     *          to JUnit
     */
    public void testDemo() throws Exception {
        // in the JBoss enviroment, the configurationFileName and configurationNamespace will be injected,
        // also the initialize method be called auto.
        LiquidPortalServiceBean liquidPortalService = new LiquidPortalServiceBean();
        Field field = liquidPortalService.getClass().getDeclaredField("configurationFileName");
        field.setAccessible(true);
        field.set(liquidPortalService, "test_files/liquidPortalService.properties");
        field = liquidPortalService.getClass().getDeclaredField("configurationNamespace");
        field.setAccessible(true);
        field.set(liquidPortalService, "com.liquid.portal.service");
        Method method = liquidPortalService.getClass().getDeclaredMethod("initialize", new Class[] {});
        method.setAccessible(true);
        method.invoke(liquidPortalService, new Object[] {});

        // now, we can call the business method using liquidProtalService
        // ...
    }

}
