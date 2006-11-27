/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.failuretests;

import com.orpheus.game.GameDataManagerConfigurationException;
import com.orpheus.game.GameDataUtility;

import junit.framework.TestCase;

/**
 * <p>Failure test GameDataUtility class.</p>
 * @author waits
 * @version 1.0
 */
public class GameDataUtilityFailureTests extends TestCase {

	/**
	 * add config file.
	 */
	protected void setUp() throws Exception {
		TestHelper.addConfigFile(TestHelper.GAME_INTERFACE_LOGIC_CONFIG_FILE);
	}

	/**
	 * <p>Test the getConfiguredGameDataManagerInstance method, the key is null, iae expected.</p>
	 * @throws Exception into JUnit
	 */
	public void testGetConfiguredGameDataManagerInstance_nullKey() throws Exception{
		try{
			GameDataUtility.getConfiguredGameDataManagerInstance(null);
			fail("The key is null.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * <p>Test the getConfiguredGameDataManagerInstance method, the key is empty, iae expected.</p>
	 * @throws Exception into JUnit
	 */
	public void testGetConfiguredGameDataManagerInstance_emptyKey() throws Exception{
		try{
			GameDataUtility.getConfiguredGameDataManagerInstance("  ");
			fail("The key is empty.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * <p>Test the getConfiguredGameDataManagerInstance method, the namespace is null, iae expected.</p>
	 * @throws Exception into JUnit
	 */
	public void testGetConfiguredGameDataManagerInstance_nullNamespace() throws Exception{
		try{
			GameDataUtility.getConfiguredGameDataManagerInstance(null,"manager");
			fail("The namespace is null.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * <p>Test the getConfiguredGameDataManagerInstance method, the namespace is empty, iae expected.</p>
	 * @throws Exception into JUnit
	 */
	public void testGetConfiguredGameDataManagerInstance_emptyNamespace() throws Exception{
		try{
			GameDataUtility.getConfiguredGameDataManagerInstance("  ","manager");
			fail("The namespace is empty.");
		}catch(IllegalArgumentException e){
			//good
		}
	}
	/**
	 * <p>Test the getConfiguredGameDataManagerInstance method, the namespace does not exist, GameDataManagerConfigurationException expected.</p>
	 * @throws Exception into JUnit
	 */
	public void testGetConfiguredGameDataManagerInstance_NotExistNamespace() throws Exception{
		try{
			GameDataUtility.getConfiguredGameDataManagerInstance("notExistNamespace","manager");
			fail("The namespace does not exist.");
		}catch(GameDataManagerConfigurationException e){
			//good
		}
	}
	/**
	 * <p>Test the getConfiguredGameDataManagerInstance method, the key does not exist, GameDataManagerConfigurationException expected.</p>
	 * @throws Exception into JUnit
	 */
	public void testGetConfiguredGameDataManagerInstance_NotExistKey() throws Exception{
		try{
			GameDataUtility.getConfiguredGameDataManagerInstance(TestHelper.GAME_DATA_OBJECT_FACTORY_NAMESPACE,"notExistKey");
			fail("The key does not exist.");
		}catch(GameDataManagerConfigurationException e){
			//good
		}
	}
	/**
	 * clear the environment.
	 */
	protected void tearDown() throws Exception {
		TestHelper.clearConfigManager();
	}

}
