/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import com.cronos.im.persistence.ParameterHelpers;

/**
 * <p>An object encapsulating a category created by {@link RoleCategoryPersistence RoleCategoryPersistence}. The
 * members of this class correspond to the columns of the <i>category</i> table.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class Category {
    /**
     * The category ID, corresponding to the <i>category.category_id</i> column. This member is initialized at
     * construction, is immutable, and will never be negative.
     */
    private final long id;

    /**
     * The category name, corresponding to the <i>category.name</i> column. This member is initialized at
     * construction, is immutable, and will never be <code>null</code> or an empty string.
     */
    private final String name;

    /**
     * The category name, corresponding to the <i>category.description</i> column. This member is initialized at
     * construction, is immutable, and will never be <code>null</code> or an empty string.
     */
    private final String description;

    /**
     * The category chattable setting, corresponding to the <i>category.description</i> column. This member is
     * initialized at construction and is immutable.
     */
    private final boolean chattable;

    /**
     * Constructs a new <code>Category</code> with the specified fields.
     *
     * @param id the category ID
     * @param name the category name
     * @param description the category description
     * @param chattableFlag the category chattable flag
     * @throws IllegalArgumentException if <code>id</code> is negative or <code>name</code> or
     *   <code>description</code> is <code>null</code> or an empty string
     */
    public Category(long id, String name, String description, boolean chattableFlag) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must not be negative");
        }

        ParameterHelpers.checkParameter(name, "name");
        ParameterHelpers.checkParameter(description, "description");

        this.id = id;
        this.name = name;
        this.description = description;
        this.chattable = chattableFlag;
    }

    /**
     * Returns the non-negative category ID.
     *
     * @return the non-negative category ID
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the category name.
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the category description.
     *
     * @return the category description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the category chattable flag.
     *
     * @return the category chattable flag
     */
    public boolean getChattable() {
        return chattable;
    }
}
