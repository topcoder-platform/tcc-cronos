/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import junit.framework.TestCase;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class TestsHelper extends TestCase {
    /**
     * <p>
     * Integer value for testing.
     * </p>
     */
    private static final int INTEGER_VALUE = 10;

    /**
     * <p>
     * Float value for testing.
     * </p>
     */
    private static final float FLOAT = 3.14159F;

    /**
     * <p>
     * Double value for testing.
     * </p>
     */
    private static final double DOUBLE = 3.14159;

    /**
     * <p>
     * Type arguments class variable.
     * </p>
     */
    private static final Map<Class<?>, Object> TYPE_ARGUMENTS = new HashMap<Class<?>, Object>();

    static {
        TYPE_ARGUMENTS.put(Collection.class, new ArrayList<Object>());
        TYPE_ARGUMENTS.put(List.class, new ArrayList<Object>());
        TYPE_ARGUMENTS.put(Set.class, new HashSet<Object>());
        TYPE_ARGUMENTS.put(SortedSet.class, new TreeSet<Object>());
        TYPE_ARGUMENTS.put(Map.class, new HashMap<Object, Object>());
        TYPE_ARGUMENTS.put(SortedMap.class, new TreeMap<Object, Object>());
        TYPE_ARGUMENTS.put(Boolean.class, true);
        TYPE_ARGUMENTS.put(Boolean.TYPE, true);
        TYPE_ARGUMENTS.put(Character.class, 'Z');
        TYPE_ARGUMENTS.put(Character.TYPE, 'Z');
        TYPE_ARGUMENTS.put(Byte.class, (byte) INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Byte.TYPE, (byte) INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Short.class, (short) INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Short.TYPE, (short) INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Integer.class, INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Integer.TYPE, INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Long.class, (long) INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Long.TYPE, (long) INTEGER_VALUE);
        TYPE_ARGUMENTS.put(Float.class, FLOAT);
        TYPE_ARGUMENTS.put(Float.TYPE, FLOAT);
        TYPE_ARGUMENTS.put(Double.class, DOUBLE);
        TYPE_ARGUMENTS.put(Double.TYPE, DOUBLE);
        TYPE_ARGUMENTS.put(java.sql.Date.class, new java.sql.Date(new Date().getTime()));
        TYPE_ARGUMENTS.put(Timestamp.class, new Timestamp(new Date().getTime()));
        TYPE_ARGUMENTS.put(Calendar.class, Calendar.getInstance());
        TYPE_ARGUMENTS.put(StatisticsType.class, StatisticsType.AVERAGE);
    }

    /**
     * <p>
     * Size of array for testing.
     * </p>
     */
    private static final int TEST_ARRAY_SIZE = 10;

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestsHelper() {
        // empty
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj the given object.
     * @param field the field name.
     * @param useClass the field owner.
     *
     * @throws Exception for JUnit.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field, Class<?> useClass) throws Exception {
        Object value = null;

        Field declaredField = useClass.getDeclaredField(field);
        declaredField.setAccessible(true);

        value = declaredField.get(obj);

        return value;
    }

    /**
     * <p>
     * Tests that the getter and setter methods for <code>property</code> work in a basic fashion, which is that the
     * getter returns the exact same object as set by the setter method.
     * </p>
     *
     * <p>
     * Uses a default argument for basic collection types, primitive types, Dates, java.sql.Dates, and Timestamps. See
     * {@link TestsHelper#TYPE_ARGUMENTS}.
     * </p>
     *
     * @param target the object on which to invoke the getter and setter
     * @param targetClass the object's class
     * @param property the property name, e.g. "firstName"
     *
     * @throws Exception for JUnit
     */
    private static void assertBasicGetterSetterBehavior(Object target, Class<?> targetClass, String property)
        throws Exception {
        PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
        Class<?> type = descriptor.getPropertyType();

        Method writeMethod = descriptor.getWriteMethod();
        Method readMethod = descriptor.getReadMethod();

        if (writeMethod.getDeclaringClass() != targetClass) {
            return;
        }

        Object arg = null;
        if (type.isArray()) {
            arg = Array.newInstance(type.getComponentType(), new int[] {TEST_ARRAY_SIZE});
        } else if (type.isEnum()) {
            arg = type.getEnumConstants()[0];
        } else if (TYPE_ARGUMENTS.containsKey(type)) {
            arg = TYPE_ARGUMENTS.get(type);
        } else {
            arg = invokeDefaultConstructorEvenIfPrivate(type);
        }

        writeMethod.invoke(target, arg);
        Object propertyValue = readMethod.invoke(target);
        if (type.isPrimitive()) {
            assertEquals(property + " getter/setter failed test", arg, propertyValue);
        } else {
            assertEquals(property + " getter/setter failed test", arg, propertyValue);
        }
    }

    /**
     * <p>
     * Invoke default constructor.
     * </p>
     *
     * @param type the class type
     *
     * @throws Exception to JUnit
     *
     * @return the object instance
     */
    private static Object invokeDefaultConstructorEvenIfPrivate(Class<?> type) throws Exception {
        Constructor<?> ctor = type.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    /**
     * <p>
     * See {@link #assertBasicGetterSetterBehavior(Object, String[])} method. No items are blacklisted.
     * </p>
     *
     * @param target the object on which to invoke the getter and setter
     * @param targetClass the object's class
     *
     * @throws Exception for JUnit.
     */
    public static void assertBasicGetterSetterBehavior(Object target, Class<?> targetClass) throws Exception {
        assertBasicGetterSetterBehaviorWithBlacklist(target, targetClass);
    }

    /**
     * <p>
     * See {@link #assertBasicGetterSetterBehavior(Object,String)} method. Big difference here is that we try to
     * automatically introspect the target object, finding read/write properties, and automatically testing the getter
     * and setter. Note specifically that read-only properties are ignored, as there is no way for us to know how to
     * set the value (since there isn't a public setter).
     * </p>
     *
     * <p>
     * Any property names contained in the blacklist will be skipped.
     * </p>
     * <p/>
     *
     * @param target the object on which to invoke the getter and setter
     * @param targetClass the object's class
     * @param propertyNames the list of property names that should not be tested
     *
     * @throws Exception for JUnit.
     */
    private static void assertBasicGetterSetterBehaviorWithBlacklist(Object target, Class<?> targetClass,
        String... propertyNames) throws Exception {
        List<String> blacklist = Arrays.asList(propertyNames);
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getWriteMethod() == null || descriptor.getReadMethod() == null) {
                continue;
            }
            if (!blacklist.contains(descriptor.getDisplayName())) {
                assertBasicGetterSetterBehavior(invokeDefaultConstructorEvenIfPrivate(target.getClass()),
                    targetClass, descriptor.getDisplayName());
            }
        }
    }
}
