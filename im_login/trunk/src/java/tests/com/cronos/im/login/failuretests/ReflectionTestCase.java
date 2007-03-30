/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * A base <code>TestCase</code> for accessing protected methods.
 *
 * @author mittu
 * @version 1.0
 */
public class ReflectionTestCase extends TestCase {

    /**
     * Returns a class by its name.
     *
     * @param className
     *            The name of the class.
     * @return The class object.
     *
     * @throws ClassNotFoundException
     *             If the appropriate class is not found.
     */

    protected Class getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    /**
     * Invoke a method by reflection.
     *
     * @param obj
     *            the object to invoke on
     * @param type
     *            the type of obj
     * @param name
     *            the name of the method
     * @param parameterTypes
     *            the parameter types of the method
     * @param parameters
     *            the parameters
     * @return the return value, or null if the method is a void-return one
     * @throws Exception
     *             if any error happens
     */
    protected Object invokeMethod(Object obj, Class type, String name, Class[] parameterTypes, Object[] parameters)
        throws Exception {

        Method method = type.getDeclaredMethod(name, parameterTypes);
        if (!method.isAccessible()) {
            try {
                method.setAccessible(true);
                return method.invoke(obj, parameters);
            } finally {
                method.setAccessible(false);
            }
        } else {
            return method.invoke(obj, parameters);
        }
    }

    /**
     * Invoke a method in its super class by reflection.
     *
     * @param obj
     *            the object to invoke on
     * @param type
     *            the type of obj
     * @param name
     *            the name of the method
     * @param parameterTypes
     *            the parameter types of the method
     * @param parameters
     *            the parameters
     * @return the return value, or null if the method is a void-return one
     * @throws Exception
     *             if any error happens
     */
    protected Object invokeSuperMethod(Object obj, Class type, String name, Class[] parameterTypes, Object[] parameters)
        throws Exception {

        Method method = type.getSuperclass().getDeclaredMethod(name, parameterTypes);
        if (!method.isAccessible()) {
            try {
                method.setAccessible(true);
                return method.invoke(obj, parameters);
            } finally {
                method.setAccessible(false);
            }
        } else {
            return method.invoke(obj, parameters);
        }
    }


    /**
     * Invoke a method without no parameter.
     *
     * @param obj
     *            the object to invoke on
     * @param type
     *            the type of the object
     * @param name
     *            the method name
     * @return the return value
     * @throws Exception
     *             to invoker
     */
    protected Object invokeMethod(Object obj, Class type, String name) throws Exception {
        return this.invokeMethod(obj, type, name, new Class[0], new Object[0]);
    }
}
