/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import com.topcoder.util.file.templatesource.TemplateSource;

/**
 * <p>
 * This class implements <code>TemplateSource</code> interface for stress tests.
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
public class MockTemplateSourceForStress implements TemplateSource {

    /**
     * <p>
     * Empty constructor for stress tests.
     * </p>
     *
     * @param namespace Useless
     * @param sourceId Useless
     */
    public MockTemplateSourceForStress(String namespace, String sourceId) {
        // empty
    }

    /**
     * <p>
     * Empty for stress tests.
     * </p>
     *
     * @param templateName not used
     * @param templateText not used
     */
    public void addTemplate(String templateName, String templateText) {
        // empty
    }

    /**
     * <p>
     * Empty for stress tests.
     * </p>
     *
     * @param templateName any template name will get same result.
     * @return always return same template for any template name.
     */
    public String getTemplate(String templateName) {
        String msg = "stress author littleken";

        return msg;
    }

    /**
     * <p>
     * Empty for stress tests.
     * </p>
     *
     * @param templateName not used
     */
    public void removeTemplate(String templateName) {
        // empty
    }

}
