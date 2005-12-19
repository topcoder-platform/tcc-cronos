/*
 * MockTemplateSelector.java    Created on 2005-7-7
 *
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 * 
 * @author qiucx0161
 *
 * @version 1.0
 */
package com.topcoder.buildutility.failuretests;

import com.topcoder.buildutility.TemplateSelector;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.TemplateHierarchy;


/**
 * The <code>MockTemplateSelector</code>class is the subclass of
 * <code>TemplateSelector</code>, it will show that no matching
 * <code>TemplateHierarchy</code> exists.
 */
final class MockTemplateSelector extends TemplateSelector {
    /**
     * It always return <code>null</code> for test.
     *
     * @param componentVersion The <code>ComponentVersion</code> instance.
     * @param templateHierarchy The <code>TemplateHierarchy</code>instance.
     *
     * @return null for test.
     */
    protected TemplateHierarchy findMatchingTemplateHierarchy(ComponentVersion version,
                                                              TemplateHierarchy hierarchy) {
        return null;
    }
}
