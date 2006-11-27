/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.io.File;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.game.GameDataManager;
import com.orpheus.game.GameDataManagerImpl;
import com.orpheus.game.GameDataUtility;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for GameDataUtility class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataUtilityTests extends TestCase {
	/**
	 * Represents the accuracytest folder.
	 */
	private static String PATH = "accuracytest";

	/**
	 * Represents the config file name of the GameDataManagerImpl class.
	 */
	private static final String CONFIG_FILE = PATH + File.separator + "Config.xml";

	/**
	 * Represents the object factory config file name.
	 */
	private static final String OBJECT_FACTORY_FILE = PATH + File.separator + "ObjectFactory.xml";

	/**
     * The context used to lookup EJB.
     */
    private Context context;

	/**
     *  Sets up the environments. Loads the configuration files to ConfigManager.
     * @throws Exception
     *             to JUnit
     */
	protected void setUp() throws Exception {
//		 We need to set MockContextFactory as our JNDI provider.
        // This method sets the necessary system properties.
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        SessionBeanDescriptor sampleServiceDescriptor
            = new SessionBeanDescriptor("GameDataLocal",
                    GameDataLocalHome.class, GameDataLocal.class, new GameDataBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // 
        sampleServiceDescriptor
            = new SessionBeanDescriptor("GameDataRemote",
                GameDataHome.class, GameData.class, new GameDataBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // load it to the ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        cm.add(CONFIG_FILE);
        cm.add(OBJECT_FACTORY_FILE);
	}

	/**
     * Clears all the test environments.
     * @throws Exception
     *             to JUnit
     */
	protected void tearDown() throws Exception {
		MockContextFactory.revertSetAsInitial();
		clearNamespaces();
	}

	/**
     * Clears all the namespaces in the ConfigManager.
     * @throws Exception
     *             to JUnit
     */
    private static void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        // iterator through to clear the namespace
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();
            if (cm.existsNamespace(namespace)) {
                // removes the namespace that exists
                cm.removeNamespace(namespace);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for the method 
     *     <code>getConfiguredGameDataManagerInstance(String key)</code>.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetConfiguredGameDataManagerInstanceAccuracy1() throws Exception {
        GameDataManager manager = GameDataUtility.getConfiguredGameDataManagerInstance("GameDataManagerImpl");
    	assertNotNull("Can not get a manager instance from getConfiguredGameDataManagerInstance.",
                manager);
        ((GameDataManagerImpl)manager).stopManager();
    }

    /**
     * <p>
     * Accuracy test for the method 
     *         <code>getConfiguredGameDataManagerInstance(String namespace, String key)</code>.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetConfiguredGameDataManagerInstanceAccuracy2() throws Exception {
        GameDataManager manager =
            GameDataUtility.getConfiguredGameDataManagerInstance("com.orpheus.game.GameDataManager",
                "GameDataManager");
    	assertNotNull("Can not get a manager instance from getConfiguredGameDataManagerInstance.", manager);
        ((GameDataManagerImpl)manager).stopManager();
    }
}
