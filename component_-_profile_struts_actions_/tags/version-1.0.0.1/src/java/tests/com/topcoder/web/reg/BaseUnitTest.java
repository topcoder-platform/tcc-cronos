/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <p>
 * This is base class for Unit tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseUnitTest extends StrutsSpringTestCase {

    /**
     * <p>
     * Represents test files directory name.
     * </p>
     */
    private static final String TEST_FILES = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator;

    /**
     * <p>
     * Represents application context file name.
     * </p>
     */
    private static final String APPLICATION_CONTEXT_FILE_NAME = TEST_FILES + "applicationContext.xml";

    /**
     * <p>
     * Represents ApplicationContext instance for testing.
     * </p>
     */
    private ApplicationContext context;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        context = new FileSystemXmlApplicationContext(APPLICATION_CONTEXT_FILE_NAME);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        context = null;
    }

    /**
     * <p>
     * Retrieves bean for given name from context.
     * </p>
     * @param <T> the type of bean
     * @param beanName the bean name
     * @return retrieved bean for given name from context
     */
    @SuppressWarnings("unchecked")
    protected < T > T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    /**
     * <p>
     * Sets object field value via reflection.
     * </p>
     * @param fieldName the field name
     * @param fieldValue the field value
     * @param obj the object to set
     * @param clazz the object class
     */
    public static void setFieldValue(String fieldName, Object fieldValue, Object obj, Class<?> clazz) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (SecurityException e) {
            // never happened
        } catch (NoSuchFieldException e) {
            // never happened
        } catch (IllegalArgumentException e) {
            // never happened
        } catch (IllegalAccessException e) {
            // never happened
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }
}
