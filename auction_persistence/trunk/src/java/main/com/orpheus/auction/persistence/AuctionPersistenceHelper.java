/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.util.Date;


/**
 * <p>
 * Defines utilities for persistence classes. Since this class will be accessed via multi-packages, this class is
 * declared in public scope.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class AuctionPersistenceHelper {
    /**
     * Private constructor to prevent this class be instantiated.
     */
    private AuctionPersistenceHelper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code> or is empty string.
     */
    public static void validateString(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty string.");
        }
    }

    /**
     * <p>
     * Validates the value of a double variable. The value cannot be negative.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is negative.
     */
    public static void validateNotNegative(double value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " should not be negative.");
        }
    }

    /**
     * <p>
     * Validates the value of a Object collection. The String collection can not be null, or contains any null element.
     * </p>
     *
     * @param value the Object collection to validate.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if value is null, or contains any null element.
     */
    public static void validateCollection(Object[] value, String name) {
        validateNotNull(value, name);

        for (int i = 0; i < value.length; i++) {
            if (value[i] == null) {
                throw new IllegalArgumentException(name + " can not contain null element.");
            }
        }
    }

    /**
     * Validates the value of an AuctionDTO variable. The valud cannot be null. auction.minimumbid, auction.itemCount
     * cannot be negative. The bidDTOs of auction cannot be null and contain null Bid elements. The bid.imageId,
     * bid.maxAmount, bid.effectiveAmount (if not null) canot be negative.
     *
     * @param auction the AuctionDTO to be Validated.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException If auction is null, or has null Bid elements, or if auction.minBid,
     *         auction.itemCount, bid.imageId, bid.maxAmount, or bid.effectiveAmount (if not null) are negative.
     */
    public static void validateAuctionDTO(AuctionDTO auction, String name) {
        AuctionPersistenceHelper.validateNotNull(auction, "auction");
        AuctionPersistenceHelper.validateNotNegative(auction.getMinimumBid(), "mininumBid");
        AuctionPersistenceHelper.validateNotNegative(auction.getItemCount(), "itemCount");

        validateBidDTOCollection(auction.getBids(), "bidDTOs of the auction");
    }

    /**
     * Validates the value of a BidDTO collection. The collection cannot be null or contain null element. The
     * bid.imageId, bid.maxAmount, bid.effectiveAmount (if not null) canot be negative.
     *
     * @param bidDTOs the BidDTO collection to validate.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException If bidDTOs is null, or has null Bid elements, or if bid.imageId, bid.maxAmount,
     *         or bid.effectiveAmount (if not null) are negative.
     */
    public static void validateBidDTOCollection(BidDTO[] bidDTOs, String name) {
        validateNotNull(bidDTOs, name);

        for (int i = 0; i < bidDTOs.length; i++) {
            validateBidDTO(bidDTOs[i], "bidDTO of bidDTOs");
        }
    }

    /**
     * Validates the value of a BidDTO. The BidDTO cannot be null. The bid.imageId, bid.maxAmount, bid.effectiveAmount
     * (if not null) canot be negative.
     *
     * @param bidDTO the BidDTO to validate.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException If bidDTO is null, or if bid.imageId, bid.maxAmount, or bid.effectiveAmount (if
     *         not null) are negative.
     */
    public static void validateBidDTO(BidDTO bidDTO, String name) {
        validateNotNull(bidDTO, name);
        AuctionPersistenceHelper.validateNotNegative(bidDTO.getImageId(), "imageId of bidDTO");
        AuctionPersistenceHelper.validateNotNegative(bidDTO.getMaxAmount(), "maxAmount");

        if (bidDTO.getEffectiveAmount() != null) {
            AuctionPersistenceHelper.validateNotNegative(bidDTO.getEffectiveAmount().intValue(), "effectionAmount");
        }
    }

    /**
     * Validates two date values. the startDate cannot later than the endDate(if all are not null).
     *
     * @param startDate the start date.
     * @param endDate the end date.
     *
     * @throws IllegalArgumentException If startDate > endDate(only if both not null).
     */
    public static void validateTwoDate(Date startDate, Date endDate) {
        if ((startDate != null) && (endDate != null) && (startDate.compareTo(endDate) > 0)) {
            throw new IllegalArgumentException("Start date must be less than or equal to end date.");
        }
    }

    /**
     * <p>
     * Create an object factory using given namespace.
     * </p>
     *
     * @param namespace the given namespace to create the object factory.
     *
     * @return the object factory created.
     *
     * @throws ObjectInstantiationException if any error happens when creating.
     */
    public static ObjectFactory createObjectFactory(String namespace) throws ObjectInstantiationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(namespace));
        } catch (SpecificationConfigurationException e) {
            throw new ObjectInstantiationException("Can't create the ObjectFactory.", e);
        } catch (IllegalReferenceException e) {
            throw new ObjectInstantiationException("Can't create the ObjectFactory.", e);
        }
    }

    /**
     * <p>
     * Create an object with the given objectFactory and classname.
     * </p>
     *
     * @param objectFactory the objectFactory to create the object.
     * @param key the key to fetch the object from objectFactory.
     * @param expected the expected type of the object to create.
     *
     * @return the created object.
     *
     * @throws ObjectInstantiationException if it fails to create the object or the object is not of expected type.
     */
    public static Object createObject(ObjectFactory objectFactory, String key, Class expected)
        throws ObjectInstantiationException {
        try {
            Object object = objectFactory.createObject(key);

            if (!expected.isInstance(object)) {
                throw new ObjectInstantiationException("The object is not instanceof " + expected.getName());
            }

            return object;
        } catch (InvalidClassSpecificationException e) {
            throw new ObjectInstantiationException("The " + expected.getName() + " instance "
                + "can't be created successfully.", e);
        }
    }

    /**
     * <p>
     * Get the string property value in <code>ConfigManager</code> with specified namespace and name.
     * </p>
     *
     * @param namespace the namespace of the config string property value .
     * @param name the name of the config string property value.
     * @param required whether the property value is required to get.
     *
     * @return the config string property value in <code>ConfigManager</code>.
     *
     * @throws ObjectInstantiationException if the namespace doesn't exist, or the parameter doesn't exist if it is
     *         required to get, or the parameter value is an empty string.
     */
    public static String getStringPropertyValue(String namespace, String name, boolean required)
        throws ObjectInstantiationException {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);

            if ((value == null)) {
                if (required) {
                    throw new ObjectInstantiationException("The required parameter " + name + " in namespace "
                        + namespace + " doesn't exist.");
                }

                return null;
            }

            if (required && (value.trim().length() == 0)) {
                throw new ObjectInstantiationException("The parameter value of " + name + " in namespace "
                        + namespace + " is an empty string.");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            throw new ObjectInstantiationException("The namespace with the name of " + namespace + " doesn't exist.",
                une);
        }
    }
}
