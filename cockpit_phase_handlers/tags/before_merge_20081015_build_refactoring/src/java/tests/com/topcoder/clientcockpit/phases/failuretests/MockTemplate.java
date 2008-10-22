/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateData;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.TemplateFields;

/**
 * <p>
 * Mock implementation of <code>Template</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTemplate implements Template {

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param data Useless parameter.
     * @return null always.
     * @throws TemplateFormatException never.
     * @throws TemplateDataFormatException never.
     */
    public String applyTemplate(TemplateData data)
        throws TemplateFormatException, TemplateDataFormatException {
        return null;
    }

    /**
     * <p>
     * Return a <code>TemplateFields</code> with empty nodes.
     * </p>
     *
     * @return <code>TemplateFields</code> with empty nodes.
     * @throws TemplateFormatException never.
     */
    public TemplateFields getFields() throws TemplateFormatException {
        return new TemplateFields(new Node[0], this);
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null always.
     */
    public String getTemplate() {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param template Useless parameter.
     * @throws TemplateFormatException never.
     */
    public void setTemplate(String template) throws TemplateFormatException {
        // empty
    }
}
