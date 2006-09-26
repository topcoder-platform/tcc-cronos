/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is a typesafe enum that provides all the possible Screening Status that
 * a Screening task can undergo.
 * </p>
 * <p>
 * It is possible to retrieve the different constants via their identifier
 * (through the static Enum.getEnumByOrdinal) or name (through the static
 * Enum.getEnumByStringValue)
 * </p>
 * <p>
 * Thread Safety: This class is immutable and therefore thread-safe.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class ScreeningStatus extends Enum {

    /**
     * <p>
     * This Enum value represents a Screening Task that has not yet been chosen
     * by any screener and is available for screening.
     * </p>
     */
    public static final ScreeningStatus PENDING = new ScreeningStatus(1, "Pending");

    /**
     * <p>
     * This Enum value represents a Screening Task that has already been chosen
     * for screening by a screener instance but has not yet completed screening.
     * </p>
     */
    public static final ScreeningStatus SCREENING = new ScreeningStatus(2, "Screening");

    /**
     * <p>
     * This Enum value represents a Screening Task that has already completed
     * screening but has failed because it has not satisfied the screening
     * logic.
     * </p>
     */
    public static final ScreeningStatus FAILED = new ScreeningStatus(3, "Failed");

    /**
     * <p>
     * This Enum value represents a Screening Task that has already completed
     * screening and has passed all the screening logic conditions.
     * </p>
     */
    public static final ScreeningStatus PASSED = new ScreeningStatus(4, "Passed");

    /**
     * <p>
     * This Enum value represents a Screening Task that has already completed
     * screening and has passed all the screening logic conditions, but has
     * generated a number of warnings.
     * </p>
     */
    public static final ScreeningStatus PASSED_WITH_WARNING = new ScreeningStatus(5,
        "Passed with Warning");

    /**
     * <p>
     * This is the unique identifier of the Status. It is also used as the
     * persisted identifier value where applicable.
     * </p>
     */
    private final int id;

    /**
     * <p>
     * This is the name and string representation of the ScreeningStatus.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Private constructor used to initialize the ScreeningStatus.
     * </p>
     * @param id
     *            The id of the ScreeningStatus.
     * @param name
     *            The name of the ScreeningStatus.
     * @throws IllegalArgumentException
     *             if name is null or an empty String.
     */
    private ScreeningStatus(int id, String name) {
        if (name == null) {
            throw new IllegalArgumentException("name should not be null.");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("name should not be empty (trimmed).");
        }

        this.id = id;
        this.name = name;
    }

    /**
     * <p>
     * Retrieves the String representation, which is equal to the name.
     * </p>
     * @return the String representation, which is equal to the name.
     */
    public String toString() {
        return name;
    }

    /**
     * <p>
     * Retrieves the unique identifier of the Status. It is also used as the
     * persisted identifier value where applicable.
     * </p>
     * @return the unique identifier of the Status. It is also used as the
     *         persisted identifier value where applicable.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>
     * Retrieves the name of the ScreeningStatus.
     * </p>
     * @return the name of the ScreeningStatus.
     */
    public String getName() {
        return name;
    }
}
