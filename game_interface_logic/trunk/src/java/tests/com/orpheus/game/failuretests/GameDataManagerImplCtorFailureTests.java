/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.failuretests;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManagerConfigurationException;
import com.orpheus.game.GameDataManagerImpl;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>Failure test the ctors of the GameDataManagerImpl class.</p>
 * @author waits
 * @version 1.0
 */
public class GameDataManagerImplCtorFailureTests extends TestCase {

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** jndiNames array. */
    private String[] jndiNames = null;

    /** jndiDesignations array. */
    private String[] jndiDesignations = null;

    /** new_game_poll_interval. */
    private long newGameDiscoveryPollInterval;

    /** game_started_poll_interval. */
    private long gameStartedPollInterval;

    /**
     * create instance.
     */
    protected void setUp() throws Exception {
        //add config file
        TestHelper.addConfigFile(TestHelper.GAME_INTERFACE_LOGIC_CONFIG_FILE);

        jndiDesignations = new String[] { "Local", "Remote" };
        jndiNames = new String[] { "game/GameDataBean", "java:comp/env/ejb/game/GameDataEJB" };
        this.newGameDiscoveryPollInterval = 1000L;
        this.gameStartedPollInterval = 1000L;
    }

    /**
     * test the ctor of GameDataManagerImpl, the namespace is null, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new GameDataManagerImpl(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the namespace is empty, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new GameDataManagerImpl("");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the namespace does not exist, GameDataManagerConfigurationException
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_notExistNamespace() throws Exception {
        try {
            new GameDataManagerImpl("notExistNamespace");
            fail("The namespace does not exist.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'jndi_names' property is missing,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingJNDINamesProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_missingJNDINames.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'jndi_names' property is missing.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'new_game_poll_interval' property is missing,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingIntervalProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_missingInterval.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'new_game_poll_interval' property is missing.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'game_started_poll_interval' property is missing,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingIntervalProp2() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_missingInterval2.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'game_started_poll_interval' property is missing.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'new_game_poll_interval' property is not number,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidIntervalProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_invalidInterval2.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'new_game_poll_interval' property is not number.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'game_started_poll_interval' property is not number,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidIntervalProp2() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_invalidInterval.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'game_started_poll_interval' property is not number.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'jndi_names' property is invalid,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidJNDINamesProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_invalidJNDINames.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'jndi_names' property is not number.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'jndi_names' property is invalid,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidJNDINamesProp3() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_invalidJNDINames3.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'jndi_names' property is not number.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * test the ctor of GameDataManagerImpl, the 'jndi_names' property is invalid,
     * GameDataManagerConfigurationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidJNDINamesProp2() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.GAME_DATA_MANAGER_NAMESPACE);
        TestHelper.addConfigFile("failure_tests/Game_Interface_Logic_Config_invalidJNDINames2.xml");

        try {
            new GameDataManagerImpl();
            fail(" the 'jndi_names' property is not number.");
        } catch (GameDataManagerConfigurationException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiNames is null, iae expected.
     */
    public void testCtor_nullJNDINames() throws Exception {
        try {
            new GameDataManagerImpl(null, this.jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiNames array is null, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiNames contains null element, iae expected.
     */
    public void testCtor_nullJNDINamesElement() throws Exception {
        try {
            jndiNames = new String[] { "game/GameDataBean", null };
            new GameDataManagerImpl(jndiNames, this.jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiNames array contains null element, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiNames contains empty element, iae expected.
     */
    public void testCtor_emptyJNDINamesElement() throws Exception {
        try {
            jndiNames = new String[] { "game/GameDataBean", " " };
            new GameDataManagerImpl(jndiNames, this.jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiNames array contains empty element, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiDesignations is null, iae expected.
     */
    public void testCtor_nullJndiDesignations() throws Exception {
        try {
            new GameDataManagerImpl(this.jndiNames, null, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiDesignations array is null, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiDesignations contains null element, iae expected.
     */
    public void testCtor_nullJndiDesignationsElement() throws Exception {
        try {
            jndiDesignations = new String[] { "Local", null };
            new GameDataManagerImpl(this.jndiNames, jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiDesignations array is null, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiDesignations contains empty string, iae expected.
     */
    public void testCtor_emptyJndiDesignationsElement() throws Exception {
        try {
            jndiDesignations = new String[] { "Local", " " };
            new GameDataManagerImpl(this.jndiNames, jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiDesignations array is null, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiDesignations contains invalid String, iae expected.
     */
    public void testCtor_invalidJndiDesignationsElement() throws Exception {
        try {
            jndiDesignations = new String[] { "Local", "notLocal" };
            new GameDataManagerImpl(this.jndiNames, jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiDesignations array is null, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndiDesignations contains invalid String, iae expected.
     */
    public void testCtor_notSameLength() throws Exception {
        try {
            jndiDesignations = new String[] { "Local" };
            jndiNames = new String[] { "game/GameDataBean", "java:comp/env/ejb/game/GameDataEJB" };
            new GameDataManagerImpl(this.jndiNames, jndiDesignations, this.newGameDiscoveryPollInterval,
                this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndiDesignations array is null, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The newGameDiscoveryPollInterval is not positive, iae expected.
     */
    public void testCtor_notPostitiveInterval() throws Exception {
        try {
            new GameDataManagerImpl(jndiNames, jndiDesignations, 0, this.gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The newGameDiscoveryPollInterval is not positive, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The gameStartedPollInterval is not positive, iae expected.
     */
    public void testCtor_notPostitiveInterval2() throws Exception {
        try {
            new GameDataManagerImpl(jndiNames, jndiDesignations, this.newGameDiscoveryPollInterval,
                    0, 1000, (float)0.5, "namespace", "c");
            fail("The gameStartedPollInterval is not positive, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test  GameDataManagerImpl(String[] jndiNames, String[] jndiDesignations, long newGameDiscoveryPollInterval,
     * long gameStartedPollInterval).  The jndi does not bind, iae expected.
     */
    public void testCtor_notBindJNDI() throws Exception {
    	
        try {
            jndiNames = new String[] { "game/GameDataBeanNotExist", "game/GameDataBeanNotExist" };

            new GameDataManagerImpl(jndiNames, jndiDesignations, newGameDiscoveryPollInterval,
                    gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
            fail("The jndi does not bind, iae expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    
    /**
     * Fails to call ejb, GameDataException expected.
     * @throws Exception into Junit
     */
    public void testCtor_gameDataManagerException() throws Exception{
    	//bind jndi
    	bindBothJNDI_Invalid();
    	jndiNames = new String[]{"java:comp/env/ejb/game/GameDataEJBInvalid"};
    	jndiDesignations = new String[]{"Remote"};
    	try{ 
    		new GameDataManagerImpl(jndiNames, jndiDesignations, newGameDiscoveryPollInterval,
                    gameStartedPollInterval, 1000, (float)0.5, "namespace", "c");
    		fail("Fails to get games.");
    	} catch(GameDataException e){
    		//good
    	}
    }

    /**
     * clear the environment.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
    }

    /**
     * Bind both the local and remote jndi ejb for testing.
     *
     * @throws Exception into Junit
     */
    private void bindBothJNDI_Invalid() throws Exception {
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor gameDataLocalDescriptor = new SessionBeanDescriptor("game/GameDataBeanInvalid",
                GameDataLocal.class, GameDataLocal.class, new GameDataBeanInValid());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(gameDataLocalDescriptor);

        
        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor gameDataRemoteDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/game/GameDataEJBInvalid",
                GameDataHome.class, GameData.class, new GameDataBeanInValid());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(gameDataRemoteDescriptor);
    }
}
