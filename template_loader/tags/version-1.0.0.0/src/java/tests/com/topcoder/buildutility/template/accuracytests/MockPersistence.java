/**
*
* Copyright (c) 2005, TopCoder, Inc. All rights reserved
*/
package com.topcoder.buildutility.template.accuracytests;

import com.topcoder.buildutility.template.TemplateHierarchyPersistence;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.PersistenceException;

/**
 * Mock class for accuracy tests.
 * @author zjq
 * @version 1.0
 */
class MockPersistence implements TemplateHierarchyPersistence {

    /**
     * <p>The id used to create the TemplateHierarchy instance.</p>
     * <p>Initial value is 0, and it will be increased in getTemplateHierarchy method</p>
     */
    private long id = 0;

    /**
     * <p>Constructs new <code>MockPersistence</code> instance.</p>
     */
    public MockPersistence() {
        // empty constructor
    }

    /**
     * <p>Gets the top-level template hierarchy corresponding to specified name.</p>
     *
     * <p>This mock implementaion will return a new template hierarchy with no child.</p>
     *
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws IllegalArgumentException if specified argument is an empty <code>String</code> being trimmed.
     * @throws PersistenceException never thrown in this implementaion.
     *
     * @return a <code>TemplateHierarchy</code> corresponding to specified name.
     * @param name a <code>String</code> specifying the name of the requested top-level template hierarchy.
     */
    public TemplateHierarchy getTemplateHierarchy(String name) throws PersistenceException {
        ++id;

        // create the TemplateHierarchy instance with id and the given name
        TemplateHierarchy result = new TemplateHierarchy(id, name, id);

        // add a template to this TemplateHierarchy
        result.addTemplate(new Template(id, "name", null, "file_name", "acc_temp.tmp"));

        return result;
    }

}
