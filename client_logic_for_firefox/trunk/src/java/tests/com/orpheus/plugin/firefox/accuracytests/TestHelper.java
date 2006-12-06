/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import java.lang.reflect.Field;

import netscape.javascript.JSObject;

/**
 * Defines utilities used in test cases.
 * 
 * @author visualage
 * @version 1.0
 */
public final class TestHelper {
    /**
     * Creates a new instance of <code>TestHelper</code> class. This private constructor prevents the creation of a
     * new instance.
     */
    private TestHelper() {
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance. If the instance is <code>null</code>, the field is a static field. If any error occurs,
     * <code>null</code> is returned.
     * 
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     * @return the value of the private field.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field
            field = type.getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
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
     * Sets the value of a private field in the given class. The field has the given name. The value is set into the
     * given instance. If the instance is <code>null</code>, the field is a static field.
     * 
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be set.
     * @param value the value of the private field.
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field
            field = type.getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Set the value
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
     * Clears the cookie associated with the given Javascript window object.
     * 
     * @param window the Javascript window object whose cookie is cleared.
     */
    public static void clearCookie(JSObject window) {
        String cookie = ((JSObject) window.getMember("document")).getMember("cookie").toString();
        String[] cookies = cookie.split(";");

        for (int i = 0; i < cookies.length; ++i) {
            ((JSObject) window.getMember("document")).setMember("cookie", cookies[i]
                + ";path=/;expires=1 Jan 1970 00:00:00 GMT;");
        }
    }
}
