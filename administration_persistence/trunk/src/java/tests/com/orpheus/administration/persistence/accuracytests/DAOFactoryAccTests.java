/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import junit.framework.TestCase;

import com.orpheus.administration.persistence.DAOFactory;

/**
 * 
 * @author waits
 * @version 1.0
 */
public class DAOFactoryAccTests extends TestCase {

	/**
     * create instance.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //add config file
        TestHelper.addConfigFile(TestHelper.ADMINISTRATION_PERSISTENCE_CONFIG_FILE);
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.SEARCH_BUNDLE_CONFIG_FILE);
    }
    
    /**
     * <p>Test getMessageDAO method.</p>
     * @throws Exception into Junit
     */
    public void testGetMessageDAO() throws Exception{
    	assertNotNull("The dao is not null.", DAOFactory.getMessageDAO());
    }
    /**
     * <p>Test getAdminDataDAO method.</p>
     * @throws Exception into Junit
     */
    public void testGetAdminDataDAO() throws Exception{
    	assertNotNull("The dao is not null.", DAOFactory.getAdminDataDAO());
    }

	/**
	 * clear the config.
	 */
	protected void tearDown() throws Exception {
		TestHelper.clearConfigManager();
	}

	
}
