/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * <p>
 * TestHelper class for the test.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Verify that given clazz is annotated by given annotationClass.
     * </p>
     *
     * @param clazz
     *            the clazz to verify
     * @param annotationClass
     *            the annotation class
     */
    public static void assertClassAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        TestCase.assertTrue("class named " + clazz.getName() + "should be annotated by "
            + annotationClass.getName(), clazz.isAnnotationPresent(annotationClass));
    }

    /**
     * <p>
     * Verify that given method is annotated by given annotationClass.
     * </p>
     *
     * @param method
     *            the method to verify
     * @param annotationClass
     *            the annotation class
     */
    @SuppressWarnings("unchecked")
    public static void assertMethodAnnotation(Method method, Class<? extends Annotation> annotationClass) {
        TestCase.assertTrue("Method named " + method.getName() + "should be annotated by "
            + annotationClass.getName(), method.isAnnotationPresent(annotationClass));
    }

    /**
     * <p>
     * Verify that given field is annotated by given annotationClass.
     * </p>
     *
     * @param field
     *            the field to verify
     * @param annotationClass
     *            the annotation class
     */
    @SuppressWarnings("unchecked")
    public static void assertFieldAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        TestCase.assertTrue("Field named " + field.getName() + "should be annotated by "
            + annotationClass.getName(), field.isAnnotationPresent(annotationClass));
    }

    /**
     * <p>
     * Sets the value of a private field in the given instance.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     */
    public static void setPrivateField(Object instance, String name, Object value) {

        try {
            // get the reflection of the field
            Field field = instance.getClass().getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given instance by given name.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to.
     * @param name
     *            the name of the private field to be retrieved.
     * @return the value of the private field.
     */
    public static Object getPrivateField(Object instance, String name) {
        Object obj = null;
        try {
            Field field = instance.getClass().getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
        return obj;
    }
}