/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;

import java.util.Date;


/**
 * Helper class for the whole component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
    /**
     * Private empty constructor.
     */
    private Helper() {
    }
    /**
     * Get Property using propertyName and namespace.
     *
     * @param namespace the namespace
     * @param propertyName the property name
     * @return property
     * @throws GameDataManagerConfigurationException if property miss
     */
    public static final Property getProperty(String namespace,
            String propertyName) throws GameDataManagerConfigurationException {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            Property property = (Property) cm.getPropertyObject(namespace,
                    propertyName);
            if (property == null) {
                throw new GameDataManagerConfigurationException("The property:'"
                        + propertyName + "' does not exist.");
            }
            return property;
        } catch (UnknownNamespaceException e) {
            throw new GameDataManagerConfigurationException("The namespace:'" + namespace
                    + "' does not exist.");
        }
    }

    /**
     * Get sub-property value(int).
     *
     * @param jigsawProperty the jigsaw Property
     * @param propertyName the property name
     * @return int value
     * @throws GameDataManagerConfigurationException if value is incorrect.
     */
    public static final int getSubPropertyInt(Property jigsawProperty,
            String propertyName) throws GameDataManagerConfigurationException {
        try {
            String value = getSubPropertyString(jigsawProperty, propertyName);
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new GameDataManagerConfigurationException("The property:'" + propertyName
                    + "' can not be parsed as a integer.");
        }

    }
    /**
     * get sub-property String using propertyName.
     *
     * @param property the property
     * @param propertyName the property name
     * @return the String value
     * @throws GameDataManagerConfigurationException if the value is invalid
     */
    public static final String getSubPropertyString(Property property,
            String propertyName) throws GameDataManagerConfigurationException {
        Property subProperty = property.getProperty(propertyName);
        if (subProperty == null) {
            throw new GameDataManagerConfigurationException(propertyName
                    + " is missing in configuration.");
        }
        String value = subProperty.getValue();
        // Check the missing and empty
        if (((value == null) || (value.trim().length() == 0))) {
            throw new GameDataManagerConfigurationException(propertyName
                    + " is missing in configuration.");
        }

        return value;
    }
    /**
     * This method gets property value as String.
     * The String value is required.
     *
     * @param namespace the namespace
     * @param propertyName property name
     * @return the configed String value
     * @throws GameDataManagerConfigurationException if property is missed or empty
     */
    public static final String getPropertyString(String namespace,
            String propertyName) throws GameDataManagerConfigurationException {
        String value;

        try {
            value = ConfigManager.getInstance().getString(namespace,
                    propertyName);
        } catch (UnknownNamespaceException e) {
            throw new GameDataManagerConfigurationException("The namespace '" + namespace
                    + "' is missing in configuration.", e);
        }

        // Check the missing and empty
        if (((value == null) || (value.trim().length() == 0))) {
            throw new GameDataManagerConfigurationException(propertyName
                    + " is missing in configuration.");
        }

        return value;
    }

    /**
     * This method gets property value as int.
     * The configed int value should be positive and is required.
     *
     * @param namespace the namespace
     * @param propertyName the property name
     * @return configed int value
     * @throws GameDataManagerConfigurationException if property is missed, empty or not positive integer
     */
    public static final int getPropertyInt(String namespace, String propertyName)
        throws GameDataManagerConfigurationException {
        String value = getPropertyString(namespace, propertyName);
        int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new GameDataManagerConfigurationException("The '" + propertyName
                    + "' can not be paresed as a integer.", e);
        }
        if (intValue <= 0) {
            throw new GameDataManagerConfigurationException("The '" + propertyName
                    + "' must be positive.");
        }
        return intValue;
    }
    /**
     * Check if the object is <code>null</code>.
     * @param obj
     *            the object to check.
     * @param name
     *            the object name
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>.
     */
    static void checkObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("the object " + name + " should not be null.");
        }
    }

    /**
     * Check if the string is <code>null</code> or empty.
     * @param str
     *            the string to check.
     * @param name
     *            the parameter name.
     * @throws IllegalArgumentException
     *             if the string is <code>null</code> or empty.
     */
    static void checkStringNotNullOrEmpty(String str, String name) {
        checkObjectNotNull(str, name);

        if ((str != null) && (str.trim().length() == 0)) {
            throw new IllegalArgumentException("the string " + name + " should not be empty.");
        }
    }

    /**
     * Gets the string property value of the property name, it must exist in the
     * configuration manager, the string property value is mandatory.
     *
     * @param namespace the namespace where the configuration is read.
     * @param propertyName the property name of the property to be read.
     * @return the string value of the property in the configuration.
     * @throws GameDataManagerConfigurationException if <code>namespace</code> is missing in configuration;
     * or the property is
     *             missing in the configuration and the property is mandatory; or accessing the configuration manager
     *             fails.
     */
    static String getMandatoryProperty(String namespace, String propertyName)
        throws GameDataManagerConfigurationException {
        String value;

        try {
            value = ConfigManager.getInstance().getString(namespace,
                    propertyName);
        } catch (UnknownNamespaceException e) {
            throw new GameDataManagerConfigurationException("The namespace '"
                + namespace + "' is missing in configuration.", e);
        }

        // Check the missing and empty
        if (((value == null) || (value.trim().length() == 0))) {
            throw new GameDataManagerConfigurationException(propertyName
                + " is missing in configuration.");
        }

        return value;
    }

    /**
     * Gets the string property value of the property name, it must exist in the
     * configuration manager, the string property value is mandatory.
     *
     * @param namespace the namespace where the configuration is read.
     * @param propertyName the property name of the property to be read.
     * @return the string value of the property in the configuration.
     * @throws GameDataManagerConfigurationException if <code>namespace</code> is missing in configuration;
     * or the property is
     *             missing in the configuration and the property is mandatory; or accessing the configuration manager
     *             fails.
     */
    static String[] getMandatoryPropertyArray(String namespace,
        String propertyName) throws GameDataManagerConfigurationException {
        String[] values;

        try {
            values = ConfigManager.getInstance().getStringArray(namespace,
                    propertyName);
        } catch (UnknownNamespaceException e) {
            throw new GameDataManagerConfigurationException("The namespace '"
                + namespace + "' is missing in configuration.", e);
        }

        // Check the missing and empty
        if (values == null) {
            throw new GameDataManagerConfigurationException(propertyName
                + " is missing in configuration.");
        }

        return values;
    }

    /**
     * <p>
     * Copy a hosting slot to a <code>HostingSlot</code> instance,
     * and set the start date to the given date instance.
     * </p>
     *
     * @param slot the hosting slot to be set.
     * @param date the start date to set into the slot.
     * @return a copied instance.
     */
    static HostingSlot copyToSetStartDate(HostingSlot slot, Date date) {
        HostingSlotImpl copy = doCopy(slot);
        copy.setHostingStart(date);

        return copy;
    }

    /**
     * <p>
     * Copy a hosting slot to a <code>HostingSlot</code> instance,
     * and set the end date to the given date instance.
     * </p>
     *
     * @param slot the hosting slot to be set.
     * @param date the start date to set into the slot.
     * @return a copied instance.
     */
    static HostingSlot copyToSetEndDate(HostingSlot slot, Date date) {
        HostingSlotImpl copy = doCopy(slot);
        copy.setHostingEnd(date);

        return copy;
    }

    /**
     * <p>
     * Copy a hosting slot to a <code>HostingSlot</code> instance,
     * and set the Sequence number to the sequence number instance.
     * </p>
     *
     * @param slot the hosting slot to be set.
     * @param number the Sequence number to set into the slot.
     * @return a copied instance.
     */
    static HostingSlotImpl copyToSequenceNumber(HostingSlot slot, int number) {
        HostingSlotImpl copy = doCopy(slot);
        copy.setSequenceNumber(number);

        return copy;
    }
    /**
     * <p>
     * Copy a hosting slot to a <code>HostingSlot</code> instance,
     * and set the Sequence number to the sequence number instance.
     * </p>
     *
     * @param slot the hosting slot to be set.
     * @param number the Sequence number to set into the slot.
     * @return a copied instance.
     */
    public static HostingSlotImpl copySlot(HostingSlot slot, Date startDate, Date endDate, DomainTarget [] targets) {
        HostingSlotImpl copy = doCopy(slot);
        copy.setHostingStart(startDate);
        copy.setHostingEnd(endDate);
        copy.setDomainTargets(targets);
        return copy;
    }
    /**
     * <p>
     * Copy a hosting slot to a <code>HostingSlot</code> instance.
     * </p>
     * @param slot the slot to be copied
     * @return a copied hosting slot
     */
    public static HostingSlotImpl doCopy(HostingSlot slot) {
        HostingSlotImpl copy = new HostingSlotImpl();

        //copy all the fields
        copy.setId(slot.getId());
        copy.setDomain(slot.getDomain());
        copy.setImageId(slot.getImageId());
        copy.setBrainTeaserIds(slot.getBrainTeaserIds());
        copy.setPuzzleId(slot.getPuzzleId());
        copy.setSequenceNumber(slot.getSequenceNumber());
        copy.setDomainTargets(slot.getDomainTargets());
        copy.setWinningBid(slot.getWinningBid());
        copy.setHostingStart(slot.getHostingStart());
        copy.setHostingEnd(slot.getHostingEnd());
        copy.setHostingBlockId(slot.getHostingBlockId());
        return copy;
    }
}
