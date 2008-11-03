/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * <p>
 * Mock implementation of <code>Context</code>. It mocks the several possible behavior of
 * {@link Context#lookup(String)} method.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContext implements Context {

    /**
     * <p>
     * Mock implementation.
     * <ul>
     *     <li>
     *     If given name equals "ExceptionBean", <code>NamingException</code> will be thrown.
     *     </li>
     *     <li>
     *     If given name equals "NullBean", <code>null</code> will be returned.
     *     </li>
     *     <li>
     *     If given name equals "WrongTypeBean", <code>Object</code> will be returned.
     *     </li>
     *     <li>
     *     Otherwise {{@link TestHelper#CONTEST_MANAGER} will be returned.
     *     </li>
     * </ul>
     * </p>
     *
     * @param name of object to look up.
     * @return the mock instance of <code>ContestManager</code>.
     * @throws NamingException If given name equals "ExceptionBean".
     */
    public Object lookup(String name) throws NamingException {
        if ("ExceptionBean".equalsIgnoreCase(name)) {
            throw new NamingException("Mock NamingException");
        }
        if ("NullBean".equalsIgnoreCase(name)) {
            return null;
        }
        if ("WrongTypeBean".equalsIgnoreCase(name)) {
            return new Object();
        }
        return TestHelper.CONTEST_MANAGER;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param propName Useless parameter.
     * @param propVal Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @param obj Useless parameter.
     * @throws NamingException never
     */
    public void bind(Name name, Object obj) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @param obj Useless parameter.
     * @throws NamingException never
     */
    public void bind(String name, Object obj) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @throws NamingException never
     */
    public void close() throws NamingException {
        //empty
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @param prefix Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @param prefix Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public String composeName(String name, String prefix) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @throws NamingException never
     */
    public void destroySubcontext(Name name) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @throws NamingException never
     */
    public void destroySubcontext(String name) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @return null always.
     * @throws NamingException never
     */
    public Hashtable < ? , ? > getEnvironment() throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @return null always.
     * @throws NamingException never
     */
    public String getNameInNamespace() throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public NamingEnumeration < NameClassPair > list(Name name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public NamingEnumeration < NameClassPair > list(String name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public NamingEnumeration < Binding > listBindings(Name name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public NamingEnumeration < Binding > listBindings(String name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Object lookup(Name name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param name Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @param obj Useless parameter.
     * @throws NamingException never
     */
    public void rebind(Name name, Object obj) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @param obj Useless parameter.
     * @throws NamingException never
     */
    public void rebind(String name, Object obj) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Return null always.
     * </p>
     *
     * @param propName Useless parameter.
     * @return null always.
     * @throws NamingException never
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param oldName Useless parameter.
     * @param newName Useless parameter.
     * @throws NamingException never
     */
    public void rename(Name oldName, Name newName) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param oldName Useless parameter.
     * @param newName Useless parameter.
     * @throws NamingException never
     */
    public void rename(String oldName, String newName) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @throws NamingException never
     */
    public void unbind(Name name) throws NamingException {
        //empty
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param name Useless parameter.
     * @throws NamingException never
     */
    public void unbind(String name) throws NamingException {
        //empty
    }

}
