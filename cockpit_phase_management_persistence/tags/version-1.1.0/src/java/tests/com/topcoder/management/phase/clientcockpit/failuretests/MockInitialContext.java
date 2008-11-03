/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * <p>
 * Mock subclass of <code>InitialContext</code> to be used in failure testing.
 * </p>
 *
 * @author hfx
 * @version 1.0
 */
public class MockInitialContext extends InitialContext {
    /**
     * <p>
     * Map contains bindings of named object.
     * </p>
     */
    private final Map<String, Object> bindings = new HashMap<String, Object>();

    /**
     * Creates a new MockInitialContext object.
     *
     * @throws NamingException if a naming exception is encountered.
     */
    public MockInitialContext() throws NamingException {
    }

    /**
     * Binds a name to an object.
     *
     * @param name the name to bind
     * @param obj the object to bind; possibly null
     * @throws NamingException if a naming exception is encountered
     */
    public void bind(String name, Object obj) throws NamingException {
        bindings.put(name, obj);
    }

    /**
     * Unbinds a name from an object.
     *
     * @param name the name to unbind.
     * @throws NamingException if a naming exception is encountered.
     */
    public void unbind(String name) throws NamingException {
        bindings.remove(name);
    }

    /**
     * Retrieves the named object.
     *
     * @param name the name of the object to look up
     * @return the object bound to name
     * @throws NamingException if a naming exception is encountered
     */
    public Object lookup(String name) throws NamingException {
        if (!bindings.containsKey(name)) {
            throw new NamingException("no named object with name '" + name + "'");
        }

        return bindings.get(name);
    }
}
