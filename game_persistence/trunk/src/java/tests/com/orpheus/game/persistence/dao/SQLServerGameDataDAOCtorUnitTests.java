/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.dao;

import com.orpheus.game.persistence.GameDataDAO;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.TestHelper;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test case for class <code>SQLServerGameDataDAO</code>.  This class test the ctor of the class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SQLServerGameDataDAOCtorUnitTests extends TestCase {
    /** SQLServerGameDataDAO instance to test against. */
    GameDataDAO dao = null;

    /**
     * create instance.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);

        dao = new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
    }

    /**
     * simply verify the creating.
     */
    public void testCtor() {
        assertNotNull("Fails to instantiate the SQLServerGameDataDAO.", dao);
    }

    /**
     * Test the ctor with namespace, the value is null ,iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new SQLServerGameDataDAO(null);
            fail("The namespace to instantiate the dao is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace, the value is empty ,iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new SQLServerGameDataDAO("  ");
            fail("The namespace to instantiate the dao is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The namespace does not exist,InstantiationException expected.
     */
    public void testCtor_notExistNamespace() {
        try {
            new SQLServerGameDataDAO("notExistNamespace");
            fail("The namespace to instantiate the dao does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop'sepcNamespace' is missing,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingSepcNamespaceProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_missingSepcNamespace.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'sepcNamespace' is missing.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop'sepcNamespace''s value does not exist,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_notExistSepcNamespaceProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_notExistSepcNamespace.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'sepcNamespace' 's value does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'factoryKey' is missing,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingFactoryKeyProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_missingFactoryKey.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'factoryKey' is missing.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'factoryKey' 's value does not exist in
     * objectfactory.xml,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidFactoryKeyProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_invalidFactoryKey.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'factoryKey' 's value is invalid.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'randomStringImageKey' is missing,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingStringRandomKeyProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_missingStringRandomKey.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'randomStringImageKey' is missing.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'randomStringImageKey' 's value does not exist in
     * objectfactory.xml,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidStringRandomKeyProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_notExistStringRandomKey.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'randomStringImageKey' 's value is invalid.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'KeyLength' is missing,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingKeyLengthProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_missingKeyLength.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'KeyLength' is missing.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'KeyLength' 's value does not exist in
     * objectfactory.xml,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidkeyLengthProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_invalidKeyLength.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'KeyLength' 's value is invalid.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'MediaType' is missing,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_missingMediaTypeProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_missingMediaType.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'MediaType' is missing.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the ctor with namespace.The prop 'connectionName' 's value does not exist in
     * objectfactory.xml,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidConnectionNameProp() throws Exception {
        ConfigManager.getInstance().removeNamespace(TestHelper.DAO_NAMESPACE);
        TestHelper.addConfigFile("invalidConfig/DAO_Config_notExistConnectionName.xml");

        try {
            new SQLServerGameDataDAO(TestHelper.DAO_NAMESPACE);
            fail("The prop'connectionName' 's value is invalid.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * clear the environement.
     *
     * @throws Exception into JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
    }
}
