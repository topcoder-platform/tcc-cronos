/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * <p>
 * This is base class for Unit tests.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class BaseUnitTest extends StrutsSpringTestCase {

    /**
     * <p>
     * Represents complete profile action bean name.
     * </p>
     */
    private static final String COMPLETE_PROFILE_ACTION_BEAN_NAME = "completeProfileAction";

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
    protected < T > T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    /**
     * <p>
     * Retrieves the CompleteProfileAction instance from context.
     * </p>
     * @return the CompleteProfileAction instance from context
     */
    protected CompleteProfileAction getCompleteProfileAction() {
        return getBean(COMPLETE_PROFILE_ACTION_BEAN_NAME);
    }

    /**
     * <p>
     * Inserts keys value into session.
     * </p>
     * @param key the session key
     * @param value the session key value
     */
    public static void putKeyValueToSession(String key, Object value) {
        Map < String, Object > session = ActionContext.getContext().getSession();
        if (session == null) {
            session = new HashMap < String, Object >();
        }
        session.put(key, value);
        ActionContext.getContext().setSession(session);
    }

    /**
     * <p>
     * Retrieves object field value via reflection.
     * </p>
     * @param fieldName the field name
     * @param obj the object to set
     * @param clazz the object class
     * @return object's field value
     */
    public static Object getFieldValue(String fieldName, Object obj, Class < ? > clazz) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
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
        return null;
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
    public static void setFieldValue(String fieldName, Object fieldValue, Object obj, Class < ? > clazz) {
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

    /**
     * <p>
     * Sets given request session attribute.
     * </p>
     * @param request the request to set session attribute
     * @param key the attribute key
     * @param value the attribute value
     */
    public static void setSessionAttribute(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }
}
