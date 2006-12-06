/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.orpheus.plugin.firefox.eventlisteners.JavascriptUIEventListener;

import com.topcoder.util.config.ConfigManager;

import java.io.FileInputStream;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class UnitTestHelper {
    /** Represents the login click function name list. */
    public static final List LOGIN_CLICK_FUN = new ArrayList();

    /** Represents the login successful function name list. */
    public static final List SUCCESSFUL_LOGIN_FUN = new ArrayList();

    /** Represents the login successful function name list. */
    public static final List LOGOUT_CLICK_FUN = new ArrayList();

    /** Represents the logout successful function name list. */
    public static final List SUCCESSFUL_LOGOUT_FUN = new ArrayList();

    /** Represents the Show Active Games function name list. */
    public static final List SHOW_ACTIVE_GAMES_CLICK_FUN = new ArrayList();

    /** Represents the Show My Games function name list. */
    public static final List SHOW_MY_GAMES_CLICK_FUN = new ArrayList();

    /** Represents the Show Unlocked Domains function name list. */
    public static final List SHOW_UNLOCKED_DOMAINS_CLICK_FUN = new ArrayList();

    /** Represents the Show Upcoming Games function name list. */
    public static final List SHOW_UPCOMING_GAMES_CLICK_FUN = new ArrayList();

    /** Represents the Show Leaders function name list. */
    public static final List SHOW_LEADERS_CLICK_FUN = new ArrayList();

    /** Represents the Show Latest Clue function name list. */
    public static final List SHOW_LATEST_CLUE_CLICK_FUN = new ArrayList();

    /** Represents the page change function name list. */
    public static final List PAGE_CHANGED_FUN = new ArrayList();

    /** Represents the wording game id update click function name list. */
    public static final List WORKING_GAME_ID_UPDATE_FUN = new ArrayList();

    /** Represents the base domain for testing. It is read from the file test_files/domain.properties. */
    private static String domain = null;

    /** Represents the <code>UIEventListener</code> instance used for testing. */
    private static JavascriptUIEventListener listener;

    /**
     * <p>
     * Creates a new instance of <code>UnitTestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private UnitTestHelper() {
    }

    /**
     * <p>
     * Gets the base domain for testing. It is read from the file test_files/domain.properties.
     * </p>
     *
     * @return The base domain for testing.
     *
     * @throws Exception if error happens when try to read this value from the config files.
     */
    public static String getDomain() throws Exception {
        if (domain == null) {
            InputStream configFile = null;

            try {
                configFile = new FileInputStream(ClientLogicForFirefoxHelper.class.getClassLoader()
                                                                                  .getResource("domain.properties")
                                                                                  .getFile());

                Properties properties = new Properties();
                properties.load(configFile);

                domain = properties.getProperty("domain");
            } finally {
                if (configFile != null) {
                    configFile.close();
                }
            }
        }

        return domain;
    }

    /**
     * <p>
     * add the config of given config file.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void addConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("Client_Logic_for_Firefox_Config.xml");
    }

    /**
     * <p>
     * clear the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
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
     * Sets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be setted.
     * @param value the value be given to the field.
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Builds the <code>UIEventListener</code> instance for testing.
     * </p>
     *
     * @return the <code>UIEventListener</code> instance for testing.
     */
    public static JavascriptUIEventListener BuildListener() {
        if (listener == null) {
            listener = new JavascriptUIEventListener();

            LOGIN_CLICK_FUN.add("LOGIN_CLICK_FUN1");
            LOGIN_CLICK_FUN.add("LOGIN_CLICK_FUN0");
            listener.addFunctionName(UIEventType.LOGIN_CLICK, (String) LOGIN_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.LOGIN_CLICK, (String) LOGIN_CLICK_FUN.get(1));

            SUCCESSFUL_LOGIN_FUN.add("SUCCESSFUL_LOGIN_FUN1");
            SUCCESSFUL_LOGIN_FUN.add("SUCCESSFUL_LOGIN_FUN0");
            listener.addFunctionName(UIEventType.SUCCESSFUL_LOGIN, (String) SUCCESSFUL_LOGIN_FUN.get(0));
            listener.addFunctionName(UIEventType.SUCCESSFUL_LOGIN, (String) SUCCESSFUL_LOGIN_FUN.get(1));

            LOGOUT_CLICK_FUN.add("LOGOUT_CLICK_FUN1");
            LOGOUT_CLICK_FUN.add("LOGOUT_CLICK_FUN0");
            listener.addFunctionName(UIEventType.LOGOUT_CLICK, (String) LOGOUT_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.LOGOUT_CLICK, (String) LOGOUT_CLICK_FUN.get(1));

            SUCCESSFUL_LOGOUT_FUN.add("SUCCESSFUL_LOGOUT_FUN1");
            SUCCESSFUL_LOGOUT_FUN.add("SUCCESSFUL_LOGOUT_FUN0");
            listener.addFunctionName(UIEventType.SUCCESSFUL_LOGOUT, (String) SUCCESSFUL_LOGOUT_FUN.get(0));
            listener.addFunctionName(UIEventType.SUCCESSFUL_LOGOUT, (String) SUCCESSFUL_LOGOUT_FUN.get(1));

            SHOW_ACTIVE_GAMES_CLICK_FUN.add("SHOW_ACTIVE_GAMES_CLICK_FUN1");
            SHOW_ACTIVE_GAMES_CLICK_FUN.add("SHOW_ACTIVE_GAMES_CLICK_FUN0");
            listener.addFunctionName(UIEventType.SHOW_ACTIVE_GAMES_CLICK, (String) SHOW_ACTIVE_GAMES_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.SHOW_ACTIVE_GAMES_CLICK, (String) SHOW_ACTIVE_GAMES_CLICK_FUN.get(1));

            SHOW_MY_GAMES_CLICK_FUN.add("SHOW_MY_GAMES_CLICK_FUN1");
            SHOW_MY_GAMES_CLICK_FUN.add("SHOW_MY_GAMES_CLICK_FUN0");
            listener.addFunctionName(UIEventType.SHOW_MY_GAMES_CLICK, (String) SHOW_MY_GAMES_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.SHOW_MY_GAMES_CLICK, (String) SHOW_MY_GAMES_CLICK_FUN.get(1));

            SHOW_UNLOCKED_DOMAINS_CLICK_FUN.add("SHOW_UNLOCKED_DOMAINS_CLICK_FUN1");
            SHOW_UNLOCKED_DOMAINS_CLICK_FUN.add("SHOW_UNLOCKED_DOMAINS_CLICK_FUN0");
            listener.addFunctionName(UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK,
                (String) SHOW_UNLOCKED_DOMAINS_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK,
                (String) SHOW_UNLOCKED_DOMAINS_CLICK_FUN.get(1));

            SHOW_UPCOMING_GAMES_CLICK_FUN.add("SHOW_UPCOMING_GAMES_CLICK_FUN1");
            SHOW_UPCOMING_GAMES_CLICK_FUN.add("SHOW_UPCOMING_GAMES_CLICK_FUN0");
            listener.addFunctionName(UIEventType.SHOW_UPCOMING_GAMES_CLICK,
                (String) SHOW_UPCOMING_GAMES_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.SHOW_UPCOMING_GAMES_CLICK,
                (String) SHOW_UPCOMING_GAMES_CLICK_FUN.get(1));

            SHOW_LEADERS_CLICK_FUN.add("SHOW_LEADERS_CLICK_FUN1");
            SHOW_LEADERS_CLICK_FUN.add("SHOW_LEADERS_CLICK_FUN0");
            listener.addFunctionName(UIEventType.SHOW_LEADERS_CLICK, (String) SHOW_LEADERS_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.SHOW_LEADERS_CLICK, (String) SHOW_LEADERS_CLICK_FUN.get(1));

            SHOW_LATEST_CLUE_CLICK_FUN.add("SHOW_LATEST_CLUE_CLICK_FUN1");
            SHOW_LATEST_CLUE_CLICK_FUN.add("SHOW_LATEST_CLUE_CLICK_FUN0");
            listener.addFunctionName(UIEventType.SHOW_LATEST_CLUE_CLICK, (String) SHOW_LATEST_CLUE_CLICK_FUN.get(0));
            listener.addFunctionName(UIEventType.SHOW_LATEST_CLUE_CLICK, (String) SHOW_LATEST_CLUE_CLICK_FUN.get(1));

            PAGE_CHANGED_FUN.add("PAGE_CHANGED_FUN1");
            PAGE_CHANGED_FUN.add("PAGE_CHANGED_FUN0");
            listener.addFunctionName(UIEventType.PAGE_CHANGED, (String) PAGE_CHANGED_FUN.get(0));
            listener.addFunctionName(UIEventType.PAGE_CHANGED, (String) PAGE_CHANGED_FUN.get(1));

            WORKING_GAME_ID_UPDATE_FUN.add("WORKING_GAME_ID_UPDATE_FUN1");
            WORKING_GAME_ID_UPDATE_FUN.add("WORKING_GAME_ID_UPDATE_FUN0");
            listener.addFunctionName(UIEventType.WORKING_GAME_ID_UPDATE, (String) WORKING_GAME_ID_UPDATE_FUN.get(0));
            listener.addFunctionName(UIEventType.WORKING_GAME_ID_UPDATE, (String) WORKING_GAME_ID_UPDATE_FUN.get(1));
        }

        return listener;
    }
}
