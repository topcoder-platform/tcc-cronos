/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.xerces.parsers.DOMParser;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.topcoder.util.config.ConfigManager;

/**
 * This is a helper class used in tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Helper {
    /**
     * Represents the admin data home.
     */
    public static final String ADMIN_DATA_JNDI_NAME = "AdminDataHome";

    /**
     * Represents the game data home.
     */
    public static final String GAME_DATA_JNDI_NAME = "GameDataHome";

    /**
     * Represents the date format.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm";

    /**
     * Represent the file name of insert data sql scripts.
     */
    public static final String PROFILE_SESSION_KEY = "user_profile_session_key";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. This should
     * never be called.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the
     * given name. The value is retrieved from the given instance.
     * </p>
     *
     * @param type
     *            the class which the private field belongs to.
     * @param target
     *            the instance which the private field belongs to.
     * @param name
     *            the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any
     *         error occurs.
     * @throws Exception
     *             to JUnit
     */
    public static Object getPrivateField(Class type, Object target, String name)
        throws Exception {

        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(target);
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * <p>
     * Load all namespaces and setup database for testing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public static void prepareTest() throws Exception {
        ConfigManager cfg = ConfigManager.getInstance();
        releaseNamespace();
        // load namespace
        cfg.add("accuracytests/Config.xml");
        cfg.add("accuracytests/ObjectFactory.xml");

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(new SessionBeanDescriptor("GameDataHome",
                GameDataHome.class, GameData.class, new DummyGameDataBean()));
        mockContainer.deploy(new SessionBeanDescriptor("AdminDataHome",
                AdminDataHome.class, AdminData.class, new DummyAdminDataBean()));
    }

    /**
     * <p>
     * Release all test namespaces and clear database data from ConfigManager.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public static void clearTestEnvironment() throws Exception {
        releaseNamespace();
    }

    /**
     * <p>
     * Release all test namespaces from ConfigManager.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    static void releaseNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Gets the document element from the given xml string.
     *
     * @param xml the xml string
     *
     * @return the document element of the given xml string
     *
     * @throws IOException if failed to read xml
     * @throws SAXException if failed to parse the given xml
     */
    public static Element loadXmlString(String xml) throws SAXException,
        IOException {
        // parse the xml source
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new StringReader(xml)));

        // return the document element
        return parser.getDocument().getDocumentElement();
    }
}
