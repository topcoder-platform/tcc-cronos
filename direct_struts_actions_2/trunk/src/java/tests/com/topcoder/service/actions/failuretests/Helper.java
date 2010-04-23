/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionProxy;

/**
 * Helper class for this component.
 *
 * @author moon.river
 * @version 1.0
 */
final class Helper {

    /**
     * Constructor.
     */
    private Helper() {
        // does nothing
    }

    /**
     * Sets up the proxy.
     * @param proxy the proxy to set up
     */
    public static void setUpSession(ActionProxy proxy) {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("USER_ID_KEY", "topcoder");
        proxy.getInvocation().getInvocationContext().setSession(session);
    }

    /**
     * Sets a field.
     * @param clazz class
     * @param instance instance
     * @param name name of the field
     * @param value value to set
     * @throws Exception to invoker
     */
    public static void setField(Class clazz, Object instance, String name, Object value) throws Exception, NoSuchFieldException {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(instance, value);
    }
}
