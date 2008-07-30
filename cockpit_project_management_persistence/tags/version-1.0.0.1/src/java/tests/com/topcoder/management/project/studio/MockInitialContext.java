/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * <p>
 * Mock subclass of <code>InitialContext</code> to be used in failure testing.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockInitialContext implements Context {
    /**
     * <p>
     * Map contains bindings of named object.
     * </p>
     */
    private final Map<String, Object> bindings = new HashMap<String, Object>();

    /**
     * Creates a new MockInitialContext object.
     * @throws NamingException
     *         if a naming exception is encountered.
     */
    public MockInitialContext() throws NamingException {
    }

    /**
     * Binds a name to an object.
     * @param name
     *        the name to bind
     * @param obj
     *        the object to bind; possibly null
     * @throws NamingException
     *         if a naming exception is encountered
     */
    public void bind(String name, Object obj) throws NamingException {
        bindings.put(name, obj);
    }

    /**
     * Retrieves the named object.
     * @param name
     *        the name of the object to look up
     * @return the object bound to name
     * @throws NamingException
     *         if a naming exception is encountered
     */
    public Object lookup(String name) throws NamingException {
        if (!bindings.containsKey(name)) {
            throw new NamingException("no named object with name '" + name + "'");
        }

        return bindings.get(name);
    }

    /**
     * Not used here.
     * @param propName
     *        The name
     * @param propVal
     *        The object
     * @return The object
     * @throws NamingException
     *         to JUnit
     */
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @param obj
     *        The object
     * @throws NamingException
     *         to JUnit
     */
    public void bind(Name name, Object obj) throws NamingException {
    }

    /**
     * Not used here.
     * @throws NamingException
     *         to JUnit
     */
    public void close() throws NamingException {
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @param prefix
     *        The prefix
     * @return The context
     * @throws NamingException
     *         to JUnit
     */
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @param prefix
     *        The prefix
     * @return The context
     * @throws NamingException
     *         to JUnit
     */
    public String composeName(String name, String prefix) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The context
     * @throws NamingException
     *         to JUnit
     */
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The context
     * @throws NamingException
     *         to JUnit
     */
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @throws NamingException
     *         to JUnit
     */
    public void destroySubcontext(Name name) throws NamingException {

    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @throws NamingException
     *         to JUnit
     */
    public void destroySubcontext(String name) throws NamingException {

    }

    /**
     * Not used here.
     * @return The environment
     * @throws NamingException
     *         to JUnit
     */
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return null;
    }

    /**
     * Gets the name.
     * @return The name.
     * @throws NamingException
     *         to JUnit
     */
    public String getNameInNamespace() throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The parser
     * @throws NamingException
     *         to JUnit
     */
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The parser
     * @throws NamingException
     *         to JUnit
     */
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The bindings
     * @throws NamingException
     *         to JUnit
     */
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The bindings
     * @throws NamingException
     *         to JUnit
     */
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The bindings
     * @throws NamingException
     *         to JUnit
     */
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return The bindings
     * @throws NamingException
     *         to JUnit
     */
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return An object
     * @throws NamingException
     *         to JUnit
     */
    public Object lookup(Name name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return An object
     * @throws NamingException
     *         to JUnit
     */
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @return An object
     * @throws NamingException
     *         to JUnit
     */
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @param obj
     *        The object
     * @throws NamingException
     *         to JUnit
     */
    public void rebind(Name name, Object obj) throws NamingException {

    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @param obj
     *        The object
     * @throws NamingException
     *         to JUnit
     */
    public void rebind(String name, Object obj) throws NamingException {

    }

    /**
     * Not used here.
     * @param propName
     *        The prop name
     * @return An object
     * @throws NamingException
     *         to JUnit
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
    }

    /**
     * Not used here.
     * @param oldName
     *        The old name
     * @param newName
     *        the new name
     * @throws NamingException
     *         to JUnit
     */
    public void rename(Name oldName, Name newName) throws NamingException {

    }

    /**
     * Not used here.
     * @param oldName
     *        The old name
     * @param newName
     *        the new name
     * @throws NamingException
     *         to JUnit
     */
    public void rename(String oldName, String newName) throws NamingException {

    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @throws NamingException
     *         to JUnit
     */
    public void unbind(Name name) throws NamingException {

    }

    /**
     * Not used here.
     * @param name
     *        The name
     * @throws NamingException
     *         to JUnit
     */
    public void unbind(String name) throws NamingException {

    }
}
