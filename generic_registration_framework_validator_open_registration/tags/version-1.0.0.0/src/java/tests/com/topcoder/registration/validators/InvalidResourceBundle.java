/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators;

import java.util.ListResourceBundle;

/**
 * <p>
 * A ResourceBundle that always returns a StringBuffer instead of String.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class InvalidResourceBundle extends ListResourceBundle {

    /**
     * <p>
     * The contests map.
     * </p>
     */
    private static final Object[][] CONTENTS = {
        {"validation.registration.open.registration.success", new StringBuffer("this")},
        {"validation.registration.open.unregistration.success", new StringBuffer("this")}};

    /**
     * <p>
     * Returns the content of this bundle. It will hold StringBuffer instead of Strings.
     * </p>
     *
     * @return the content of this bundle.
     */
    public Object[][] getContents() {
        return CONTENTS;
    }
}
