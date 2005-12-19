/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Selector 1.0
 */
package com.topcoder.buildutility;

import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.Template;

/**
 * <p>An interface specifying the contract the template selection algorithm. The main purpose of this interface is to
 * provide the clients of <code>Template Selector</code> component with a freedom to switch to different template
 * selection algorithms without affecting the code depending on the algorithm. So whenever a new implementation of
 * template selection algorithm is put to production only a logic instantiating the algorithm implementation may need
 * to be updated. All other code which makes use of template selection algorithm remains unaffected.</p>
 *
 * <p>This interface defines a single method which is used to locate all the templates corresponding to specified
 * component version using the supplied template hierarchy as a start point.</p>
 *
 *
 * @author isv
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public interface TemplateSelectionAlgorithm {

    /**
     * <p>Locates the templates matching the specified component version using the specified template hierarchy as a
     * start point.</p>
     *
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     *
     * @return a <code>Template</code> array providing the templates matching the specified component version. If no
     * matching templates are found then a zero-length array is returned. This method MUST never return <code>
     * null</code>.
     * @param componentVersion a <code>ComponentVersion</code> instance providing the details for the component to
     * find the matching templates for.
     * @param templateHierarchy a <code>TemplateHierarchy</code> providing the details for the template hierarchy to
     * perform a lookup for the requested templates in.
     */
    public Template[] selectTemplates(ComponentVersion componentVersion, TemplateHierarchy templateHierarchy);
}


