package com.orpheus.plugin.firefox.failuretests;

import com.orpheus.plugin.firefox.eventlisteners.JavascriptUIEventListener;
import com.orpheus.plugin.firefox.UIEventType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

public class TestHelper {
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

    /** Represents the <code>UIEventListener</code> instance used for testing. */
    private static JavascriptUIEventListener listener;

    private TestHelper() {
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
