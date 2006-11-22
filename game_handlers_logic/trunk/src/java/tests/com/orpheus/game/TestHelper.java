/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.orpheus.game.accuracytests.mock.MockBean;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;


/**
 * Test Helper class.
 *
 * @author mittu
 * @version 1.0
 */
public class TestHelper {
    /**
     * Represents the user directory.
     */
    public static final String USER_DIR = System.getProperty("user.dir") + File.separator;

    /**
     * Represents the accuracy directory.
     */
    public static final String ACCURACY_DIR = USER_DIR + "test_files" + File.separator + "accuracy" + File.separator;

    /**
     * Represents the accuracy directory.
     */
    public static final String FAIL_DIR = USER_DIR + "test_files" + File.separator + "failure" + File.separator;

    /**
     * Represents the config file.
     */
    public static final String CONFIG = USER_DIR + "test_files" + File.separator + "GameHandlersLogic.xml";

    /**
     * Represents a config with "game_data_jndi_name" empty.
     */
    public static final String FAIL_CONF1 = FAIL_DIR + File.separator + "GameHandlersLogic1.xml";

    /**
     * Represents a config with "game_data_jndi_name" missing.
     */
    public static final String FAIL_CONF2 = FAIL_DIR + File.separator + "GameHandlersLogic2.xml";

    /**
     * Represents a config with out jndi utility namespace.
     */
    public static final String FAIL_CONF3 = FAIL_DIR + File.separator + "GameHandlersLogic3.xml";

    /**
     * Represents a player id for test purpose.
     */
    public static final long PLAYER_ID = 10001;

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * No instances allowed.
     */
    private TestHelper() {
        // empty.
    }

    /**
     * Loads the configuration file.
     *
     * @param file
     *            the file to be loaded.
     */
    static void loadConfig(String file) {
        releaseConfigs();
        ConfigManager manager = ConfigManager.getInstance();
        try {
            manager.add(file);
        } catch (ConfigManagerException e) {
            // ignore
        }
    }

    /**
     * Releases all the configurations from the manager.
     */
    static void releaseConfigs() {
        ConfigManager manager = ConfigManager.getInstance();
        for (Iterator iterator = manager.getAllNamespaces(); iterator.hasNext();) {
            try {
                manager.removeNamespace((String) iterator.next());
            } catch (UnknownNamespaceException e) {
                // ignore
            }
        }
    }

    /**
     * Parses the file from the specified location and returns a dom element.
     *
     * @param filename
     *            the file name.
     * @param fail
     *            failure fails.
     * @return a dom element.
     */
    static Element getDomElement(String filename, boolean fail) {
        try {
            // Create a builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);

            if (fail) {
                filename = FAIL_DIR + filename;
            } else {
                filename = ACCURACY_DIR + filename;
            }

            // Create the builder and parse the file
            Document document = factory.newDocumentBuilder().parse(new File(filename));
            return document.getDocumentElement();
        } catch (SAXException e) {
            // ignore
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // ignore
            e.printStackTrace();
        } catch (IOException e) {
            // ignore
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deploys the mock ejb for testing purpose.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    static void deployEJB() throws Exception {
        if (!deployed) {
            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one EJB are
             * deployed by the app server, so we don't need to do it.
             */

            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(new InitialContext());

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor statefulSampleDescriptor = new SessionBeanDescriptor("mock_game_data",
                    GameDataHome.class, GameData.class, MockBean.class);
            // Mark this bean as stateful. Stateless is the default.
            statefulSampleDescriptor.setStateful(true);

            mockContainer.deploy(statefulSampleDescriptor);

            // All EJBs are now deployed
            deployed = true;
        }
    }

    /**
     * Sets the flag to the deployed.
     *
     * @param flag
     *            flag to set
     */
    static void setDeployed(boolean flag) {
        deployed = flag;
    }
}
