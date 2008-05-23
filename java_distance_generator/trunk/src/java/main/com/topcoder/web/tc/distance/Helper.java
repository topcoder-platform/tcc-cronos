/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * Helper class containing utility methods for this component.
 * </p>
 * <p>
 * Thread-safety: this class is thread safe by having no mutable state.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class Helper {

    /**
     * Private empty constructor.
     */
    private Helper() {
        // Does nothing
    }

    /**
     * Checks collections argument. The argument cannot be null or contain null elements. It cannot be empty
     * if the given <code>isEmptyAllowed</code> is <code>false</code>.
     *
     * @param collection the collection to be tested.
     * @param name the name of the argument being tested.
     * @param isEmptyAllowed if the collection can be empty.
     */
    public static void checkCollection(Collection<?> collection, String name, boolean isEmptyAllowed) {
        ExceptionUtils.checkNull(collection, null, null, "The [" + name + "] argument cannot be null.");

        if (!isEmptyAllowed && collection.size() == 0) {
            throw new IllegalArgumentException("The [" + name + "] argument cannot be empty.");
        }

        for (Object element : collection) {
            ExceptionUtils.checkNull(element, null, null, "The [" + name + "] argument cannot have null elements.");
        }
    }

    /**
     * Create a list of Double(-1) values.
     *
     * @param size the size of the list.
     * @return a list of <code>size</code> Double(-1) values.
     */
    public static List<Double> getUndefinedList(int size) {
        List<Double> rcList = new ArrayList<Double>(size);

        for (int i = 0; i < size; ++i) {
            rcList.add(-1.0);
        }
        return rcList;
    }
}
