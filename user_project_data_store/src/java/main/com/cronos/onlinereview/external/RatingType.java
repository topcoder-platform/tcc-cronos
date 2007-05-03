/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.external;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Iterator;

import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This typesafe enum represents the various ratings types (phases) that the <b>User Project Data Store</b> component
 * supports, namely, <em>design</em> and <em>development</em>. The Config Manager is used to retrieve the id number
 * of each phase. The required namespace is defined in this class, along with a method to retrieve the appropriate id
 * number for the rating type.
 * </p>
 * <p>
 * The required configuration is in the given namespace. The keys are the two strings, DESIGN_NAME and DEVELOPMENT_NAME,
 * and the values are the id numbers of the respective phases (112, and 113 respectively).
 * </p>
 * <p>
 * As an example, the namespace would look like this:
 *
 * <pre>
 * &lt;Property name=&quot;Design&quot;&gt;&lt;Value&gt;112&lt;/Value&gt;&lt;/Property&gt;
 * &lt;Property name=&quot;Development&quot;&gt;&lt;Value&gt;113&lt;/Value&gt;&lt;/Property&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread-safe.
 * </p>
 *
 * @author dplass, oodinary
 * @author FireIce
 * @version 1.1
 * @since 1.0
 */
public class RatingType extends Enum implements Serializable {

    /**
     * <p>
     * The namespace which must store the name-to-id mapping.
     * </p>
     * <p>
     * If the namespace is empty or not found, it will default design to 112 and development to 113.
     * </p>
     */
    public static final String NAMESPACE = "com.cronos.onlinereview.external.RatingType";

    /**
     * <p>
     * The name of the design rating type, and the property name that stores the design phase number.
     * </p>
     */
    public static final String DESIGN_NAME = "Design";

    /**
     * <p>
     * The name of the development rating type, and the property name that stores the development phase number.
     * </p>
     */
    public static final String DEVELOPMENT_NAME = "Development";

    /**
     * <p>
     * The rating type that represents the design phase.
     * </p>
     */
    public static final RatingType DESIGN = getRatingType(DESIGN_NAME);

    /**
     * <p>
     * The rating type that represents the development phase.
     * </p>
     */
    public static final RatingType DEVELOPMENT = getRatingType(DEVELOPMENT_NAME);

    /**
     * <p>
     * The default integer code of the development phase.
     * </p>
     */
    private static final int DEFAULT_DEV_CODE = 113;

    /**
     * <p>
     * The default integer code of the design phase.
     * </p>
     */
    private static final int DEFAULT_DESIGN_CODE = 112;

    /**
     * <p>
     * The name of the rating type as set in the constructor and accessed by getName. Will never be null or empty after
     * trim.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * The id of this rating type/phase as set in the constructor and accessed by getId. Will never be negative.
     * </p>
     */
    private final int id;

    /**
     * <p>
     * Constructs this object with the given parameters.
     * </p>
     *
     * @param id
     *            the id number of this rating type/phase (e.g., 112 for design, 113 for development).
     * @param name
     *            the name of this phase (e.g., Design, Development).
     */
    private RatingType(int id, String name) {
        this.name = name;
        this.id = id;
    }

    /**
     * <p>
     * Builds or retrieves the named rating type from either the typesafe enum registry, or builds a new one with the id
     * named in the configuration file.
     * </p>
     *
     * @param typeName
     *            the name of the rating type (e.g., "Design", "Development").
     * @return a RatingType which corresponds to the given typeName.
     * @throws IllegalArgumentException
     *             if typeName is <code>null</code> or empty after trim.
     */
    public static RatingType getRatingType(String typeName) {
        UserProjectDataStoreHelper.validateStringEmptyNull(typeName, "typeName");

        // Configures from the config-file using ConfigManager.
        configure();

        // Gets Enum by the value of the typeName string.
        RatingType enumRatingType = (RatingType) Enum.getEnumByStringValue(typeName, RatingType.class);

        if (enumRatingType == null) {
            // Judges the type of the typeName, and creates the instance of RatingType due to the type.
            if (typeName.equals(DESIGN_NAME)) {
                enumRatingType = new RatingType(DEFAULT_DESIGN_CODE, typeName);
            } else if (typeName.equals(DEVELOPMENT_NAME)) {
                enumRatingType = new RatingType(DEFAULT_DEV_CODE, typeName);
            } else {
                enumRatingType = new RatingType(0, typeName);
            }
        }

        return enumRatingType;
    }

    /**
     * <p>
     * Returns a <code>{@link RatingType}</code> which corresponds to the given id.
     * </p>
     *
     * @param id
     *            the id (phase #) of the rating type to retrieve.
     * @return a RatingType which corresponds to the given id, or <code>null</code> if not found.
     * @throws IllegalArgumentException
     *             if id is not positive.
     */
    public static RatingType getRatingType(int id) {
        UserProjectDataStoreHelper.validateNotPositive(id, "id");

        // Configures from the config-file using ConfigManager.
        configure();

        // Gets the Enum list from the Enum.
        List enumRatingTypes = Enum.getEnumList(RatingType.class);

        // Searches through the List, and if the id of any RatingType item in the List matches the
        // given parameter, then the item would be returned.
        for (Iterator it = enumRatingTypes.iterator(); it.hasNext();) {
            RatingType ratingType = (RatingType) it.next();
            if (ratingType.getId() == id) {
                return ratingType;
            }
        }

        // Not found.
        return null;
    }

    /**
     * <p>
     * Returns a hash code for this rating type, based on the hashcode of the name.
     * </p>
     *
     * @return the hashCode of the name variable.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * <p>
     * Returns true if 'that' is also a RatingType, is not null, and has the same hashcode as
     * this object.
     * </p>
     *
     * @return <code>true</code> if 'that' is also a RatingType, is not null, and has the same
     *         hashcode as this object, else <code>false</code>.
     * @param that
     *            the object to compare with.
     */
    public boolean equals(Object that) {
        return that instanceof RatingType && that.hashCode() == this.hashCode();
    }

    /**
     * <p>
     * Returns the name of this rating type as set in the constructor.
     * </p>
     * <p>
     * This method is required by the <code>{@link Enum}</code> superclass.
     * </p>
     *
     * @return the name of this rating type. Will never be <code>null</code> or empty after trim.
     */
    public String toString() {
        return getName();
    }

    /**
     * <p>
     * Returns the name of this rating type as set in the constructor.
     * </p>
     *
     * @return the name of this rating type. Will never be <code>null</code> or empty after trim.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Returns the id of this rating type as set in the constructor.
     * </p>
     *
     * @return the id of this rating type. Will never be negative or zero.
     */
    public int getId() {
        return this.id;
    }

    /**
     * <p>
     * This private method retrieves the configuration from the default namespace and creates enumerated types for each
     * of the name/value pairs.
     * </p>
     */
    private static void configure() {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            Enumeration names = configManager.getPropertyNames(NAMESPACE);

            while (names.hasMoreElements()) {
                // Retrieves the configuration from the default namespace and creates enumerated
                // types for each of the name/value pairs.
                String name = (String) names.nextElement();
                RatingType enumRatingType = (RatingType) Enum.getEnumByStringValue(name, RatingType.class);

                if (enumRatingType == null) {
                    int id = Integer.parseInt((String) configManager.getProperty(NAMESPACE, name));

                    // Just creating the object is enough to "register" it with the Enum class.
                    // Throws away the actual object.
                    new RatingType(id, name);
                }
            }
        } catch (UnknownNamespaceException e) {
            // Does nothing here, just ignores the exception.
        } catch (NumberFormatException e) {
            // Does nothing here, just ignores the exception.
        }
    }
}
