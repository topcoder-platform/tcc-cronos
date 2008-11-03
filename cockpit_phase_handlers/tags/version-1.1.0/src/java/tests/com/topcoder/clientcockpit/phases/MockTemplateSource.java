/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.util.file.templatesource.TemplateSource;

/**
 * <p>
 * This class implements <code>TemplateSource</code> interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTemplateSource implements TemplateSource {

    /**
     * <p>
     * Empty constructor.
     * </p>
     *
     * @param namespace Useless
     * @param sourceId Useless
     */
    public MockTemplateSource(String namespace, String sourceId) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
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
     * For any template name, always returns the same template:
     * <pre>
     * Date is %date%, operator is %operator%, cost is %cost%$.
     * </pre>.
     * </p>
     *
     * @param templateName any template name will get same result.
     * @return always return same template for any template name.
     */
    public String getTemplate(String templateName) {
        return "Date is %date%, operator is %operator%, cost is %cost%$.";
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param templateName not used
     */
    public void removeTemplate(String templateName) {
        // empty
    }

}
