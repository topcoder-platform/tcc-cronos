/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.failuretests;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.orpheus.game.persistence.entities.HostingSlotImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Defines helper methods used in unit tests of this component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

	/** config file for the component. */
	public static final String GAME_INTERFACE_LOGIC_CONFIG_FILE = "failure_tests/Game_Interface_Logic_Config.xml";

	/** namespace for GameDataManager. */
	public static final String GAME_DATA_MANAGER_NAMESPACE = "com.orpheus.game.GameDataManagerImpl";

	/**
	 * namespace for the GameDataUtility to create manager instance.
	 */
	public static final String GAME_DATA_OBJECT_FACTORY_NAMESPACE = "com.orpheus.game.GameDataManager_ObjectFactory";

	/**
	 * 
	 * /** This private constructor prevents the creation of a new instance.
	 */
	private TestHelper() {
	}

	/**
	 * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
	 * the given instance. If the instance is null, the field is a static field. If any error occurs, null is returned.
	 * 
	 * @param type
	 *            the class which the private field belongs to
	 * @param instance
	 *            the instance which the private field belongs to
	 * @param name
	 *            the name of the private field to be retrieved
	 * 
	 * @return the value of the private field
	 */
	public static Object getPrivateField(Class type, Object instance, String name) {
		Field field = null;
		Object obj = null;

		try {
			// get the reflection of the field
			field = type.getDeclaredField(name);

			// set the field accessible.
			field.setAccessible(true);

			// get the value
			obj = field.get(instance);
		} catch (NoSuchFieldException e) {
			// ignore
		} catch (IllegalAccessException e) {
			// ignore
		} finally {
			if (field != null) {
				// reset the accessibility
				field.setAccessible(false);
			}
		}

		return obj;
	}

	/**
	 * Sets the value of a private field in the given class.
	 * 
	 * @param type
	 *            the class which the private field belongs to
	 * @param instance
	 *            the instance which the private field belongs to
	 * @param name
	 *            the name of the private field to be retrieved
	 * @param value
	 *            the value to set
	 */
	public static void setPrivateField(Class type, Object instance, String name, Object value) {
		Field field = null;

		try {
			// get the reflection of the field
			field = type.getDeclaredField(name);

			// set the field accessible.
			field.setAccessible(true);

			// set the value
			field.set(instance, value);
		} catch (NoSuchFieldException e) {
			// ignore
		} catch (IllegalAccessException e) {
			// ignore
		} finally {
			if (field != null) {
				// reset the accessibility
				field.setAccessible(false);
			}
		}
	}

	/**
	 * <p>
	 * Add the config file.
	 * </p>
	 * 
	 * @param fileName
	 *            the config file to add
	 * 
	 * @throws Exception
	 *             into JUnit
	 */
	public static void addConfigFile(String fileName) throws Exception {
		ConfigManager.getInstance().add(fileName);
	}

	/**
	 * This method clears all the namespaces from ConfigManager.
	 * 
	 * @throws Exception
	 *             if any error occurs when clearing ConfigManager
	 */
	public static void clearConfigManager() throws Exception {
		ConfigManager manager = ConfigManager.getInstance();

		for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
			manager.removeNamespace((String) iter.next());
		}
	}

	/**
	 * build a HostingSlot instance for testing.
	 * 
	 */
	public static HostingSlot buildHostingSlot() {
	    ImageInfo[] images = new ImageInfo[1];
		images[0] = new ImageInfoImpl(new Long(1), 1000L, "desc", new Boolean(true));

		// create Domain instance
		Domain domain = new DomainImpl(new Long(1), 1000l, "www.topcoder.com", new Boolean(true), images);

		// create brainTeaserIds array
		long[] brainTeaserIds = new long[2];
		brainTeaserIds[0] = 1L;
		brainTeaserIds[1] = 2;
		// create DomainTarget array
		DomainTarget[] domainTarget = new DomainTarget[1];
		domainTarget[0] = new DomainTargetImpl(new Long(1), 1000, "/tc", "text", "hash", 1000l);
		// create the date
		Date hostingStart = new Date(1000);
		Date hostingEnd = new Date(2000);

		HostingSlot slot = new HostingSlotImpl(new Long(1), domain, 1000l, brainTeaserIds, new Long(2), 1000,
				domainTarget, 100, hostingStart, hostingEnd);
		return slot;
	}
}
