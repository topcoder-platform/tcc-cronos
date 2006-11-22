/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.naming.NamingException;

import junit.framework.TestCase;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy test case for GameOperationLogicUtility.
 * 
 * @author Zulander
 * @version 1.0
 */
public class GameOperationLogicUtilityAccuracyTest extends TestCase {

	/**
	 * Default GameOperationLogicUtility instance used in the tests.
	 */
	private GameOperationLogicUtility utility;

	/**
	 * Number of times of calling
	 * {@link GameOperationLogicUtility#getInstance()}.
	 */
	private static final int GET_INSTANCE_TIMES = 10;

	/**
	 * Loads configuration from files. Gets instance of
	 * GameOperationLogicUtility used in the tests.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.clearConfig();
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
		configManager.add("GameOperationLogicUtilityTest.xml");
		utility = GameOperationLogicUtility.getInstance();
	}

	/**
	 * Clears test environment.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
	protected void tearDown() throws Exception {
		super.tearDown();

		AccuracyTestHelper.clearConfig();
	}

	/**
	 * Test for {@link GameOperationLogicUtility#getInstance()}. It should
	 * return the same instance every time.
	 */
	public void testGameOperationLogicUtility_GetInstance() {
		GameOperationLogicUtility lastUtility = GameOperationLogicUtility
				.getInstance();
		GameOperationLogicUtility currentUtility = null;

		for (int i = 0; i < GET_INSTANCE_TIMES; ++i) {
			currentUtility = GameOperationLogicUtility.getInstance();
			assertEquals("getInstance failed.", lastUtility, currentUtility);
			currentUtility = lastUtility;
		}
	}

	/**
	 * Test for {@link GameOperationLogicUtility#getDataStoreKey()}. It should
	 * return "Data_store".
	 */
	public void testGameOperationLogicUtility_GetDataStoreKey() {
		assertEquals("getDataStoreKey() should return", "Data_store", utility
				.getDataStoreKey());
	}

	/**
	 * Test for {@link GameOperationLogicUtility#getGameDataLocalHome()}. It
	 * should return an instance of GameDataLocalHome.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
	public void testGameOperationLogicUtility_GetGameDataLocalHome()
			throws Exception, NamingException {
		assertTrue(
				"getGameDataLocalHome() should return instance of GameDataLocalHome",
				utility.getGameDataLocalHome() instanceof GameDataLocalHome);
	}

	/**
	 * Test for {@link GameOperationLogicUtility#getGameDataRemoteHome()}. It
	 * should return an instance of GameDataHome.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
	public void testGameOperationLogicUtility_GetGameDataRemoteHome()
			throws Exception, NamingException {
		assertTrue(
				"getGameDataRemoteHome() should return instance of GameDataHome",
				utility.getGameDataRemoteHome() instanceof GameDataHome);
	}

	/**
	 * Test for {@link GameOperationLogicUtility#getGameManagerKey()}. It
	 * should return "GameDataManager".
	 */
	public void testGameOperationLogicUtility_GetGameManagerKey() {
		assertEquals("getGameManagerKey() should return", "GameDataManager",
				utility.getGameManagerKey());
	}

	/**
	 * Test for {@link GameOperationLogicUtility#getPuzzleTypeSourceKey()}. It
	 * should return "PuzzleTypeSource".
	 */
	public void testGameOperationLogicUtility_GetPuzzleTypeSourceKey() {
		assertEquals("getPuzzleTypeSourceKey() should return",
				"PuzzleTypeSource", utility.getPuzzleTypeSourceKey());
	}

	/**
	 * Test for {@link GameOperationLogicUtility#isUseLocalInterface()}. It
	 * should return true.
	 */
	public void testGameOperationLogicUtility_IsUseLocalInterface() {
		assertEquals("isUseLocalInterface() should return", true, utility
				.isUseLocalInterface());
	}

}
