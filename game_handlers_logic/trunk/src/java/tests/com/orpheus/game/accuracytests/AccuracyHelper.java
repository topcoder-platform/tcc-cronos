/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.orpheus.game.accuracytests.mock.MockBean;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class for test.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class AccuracyHelper {
    /**
     * The dir for accuracy test files.
     */
    private static String BASE_DIR = new File(".").getAbsolutePath() + File.separatorChar +
        "test_files" + File.separatorChar + "accuracy_tests" + File.separatorChar;
    
    /**
     * The ejb hase been deployed or not.
     */
    private static boolean deployed = false;
    
    /**
     * The user profile id used in session.
     */
    public static long USER_PROFILE_ID = 10000; 
    
    /**
     * Private ctor.
     */
    private AccuracyHelper() {
    }

    /**
     * Loads the configuration file.
     * 
     * @throws Exception propagated
     *
     * @param file
     *            the file to be loaded.
     */
    public static void loadConfig() throws Exception {
        removeAllNamespace();
        ConfigManager.getInstance().add("accuracy_tests" + File.separatorChar + "GameHandlersLogic.xml");
    }

    /**
     * Releases all the configurations from the manager.
     * 
     * @throws Exception propagated
     */
    public static void removeAllNamespace() throws Exception {
        ConfigManager CM = ConfigManager.getInstance();
        for (Iterator iterator = CM.getAllNamespaces(); iterator.hasNext();) {
            CM.removeNamespace((String) iterator.next());
        }
    }

    /**
     * Deploys the mock ejb for testing purpose.
     *
     * @throws Exception propagated
     */
    public static void deployEJB() throws Exception {
        if (!deployed) {
            MockContextFactory.setAsInitial();
            MockContainer mockContainer = new MockContainer(new InitialContext());
            SessionBeanDescriptor statefulSampleDescriptor = 
                new SessionBeanDescriptor("mock_game_data", GameDataHome.class, GameData.class, MockBean.class);
            statefulSampleDescriptor.setStateful(true);
            mockContainer.deploy(statefulSampleDescriptor);
            deployed = true;
        }
    }
    
    /**
     * Gets the field value.
     * 
     * @param clazz the class
     * @param fieldName the field name
     * @param obj the object
     * @return the field value
     * 
     * @throws Exception propagated
     */
    public static Object getFieldValue(Class clazz, String fieldName, Object obj) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
    
    /**
     * Gets the field value.
     * 
     * @param clazz the class
     * @param fieldName the field name
     * @param obj the object
     * @return the field value
     * 
     * @throws Exception propagated
     */
    public static int getFieldIntValue(Class clazz, String fieldName, Object obj) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getInt(obj);
    }

    /**
     * Loads the file, and parse it to document.
     *
     * @param filename
     *            the file name.
     * @return the Element. 
     * 
     * @throws Exception propagated
     */
    public static Element getDomElement(String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        Document document = factory.newDocumentBuilder().parse(BASE_DIR + filename);
        return document.getDocumentElement();
    }
}
