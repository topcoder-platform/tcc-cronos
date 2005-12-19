/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Selector 1.0
 */
package com.topcoder.buildutility;

import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.component.TechnologyType;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import java.util.Set;
import java.util.HashSet;


/**
 * <p>This class is a default implementation of <code>TemplateSelectionAlgorithm</code> interface which implements a
 * template selection algorithm as specified by the <code>Requirements Specification</code> document.</p>
 *
 * <p>This class also serves as an extension point for possible future implementations of <code>
 * TemplateSelectionAlgorithm</code> interface. It provides a protected <code>
 * findMatchingTemplateHierarchy(ComponentVersion, TemplateHierarchy)</code> method which could be overridden by the
 * subclasses in accordance with a specific logic used to locate the template hierarchy matching the specified component
 * version. So this class may be interpreted as implementation of <code>Template Method</code> design pattern. The
 * difference is that the template method is not abstract and this class provides a default implementation of template
 * method.</p>
 *
 * <p>Thread safety: this class is thread safe. The instances of this class do not maintain any private state. However
 * the subclasses may choose to add a private state to be used in template selection. In such a case the subclasses are
 * responsible for addressing the thread safety properly.</p>
 *
 *
 * @author isv
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class TemplateSelector implements TemplateSelectionAlgorithm {

    /**
     * <p>Constructs new <code>TemplateSelector</code>. This constructor does nothing since the instances of <code>
     * TemplateSelector</code> class do not maintain any private state.</p>
     */
    public TemplateSelector() {
        // Empty constructor
    }

    /**
     * <p>Locates the templates matching the specified component version using the specified template hierarchy as a
     * start point.</p>
     *
     *
     * @return a <code>Template</code> array providing the templates matching the specified component version. If no
     * matching templates are found then a zero-length array is returned.
     * @param componentVersion a <code>ComponentVersion</code> instance providing the details for the component to
     * find the matching templates for.
     * @param templateHierarchy a <code>TemplateHierarchy</code> providing the details for the template hierarchy to
     * perform a lookup for the requested templates in.
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     */
    public Template[] selectTemplates(ComponentVersion componentVersion, TemplateHierarchy templateHierarchy) {

        // check the arguments
        if (componentVersion == null) {
            throw new NullPointerException("componentVersion should not be null");
        }
        if (templateHierarchy == null) {
            throw new NullPointerException("templateHierarchy should not be null");
        }

        // use findMatchingTemplateHierarchy method to find the TemplateHierarchy instance containing
        // the required Templates
        TemplateHierarchy hierarchy = findMatchingTemplateHierarchy(componentVersion, templateHierarchy);

        // if the hierarchy does not exist, return an empty array
        // otherwise return the Templates of the retrieved TemplateHierarchy
        if (hierarchy == null) {
            // NOTE: As the clarification on the forum, current implementation of findMatchingTemplateHierarchy
            //       will never return null, but the future subclass of TemplateSelector MAY override the
            //       findMatchingTemplateHierarchy method and return NULL to explicitly indicate that there is
            //       no matching template hierarchy
            return new Template[0];
        } else {
            return hierarchy.getTemplates();
        }
    }

    /**
     * <p>Locates the template hierarchy matching the specified component version. This implementation lookups for the
     * template hierarchy using the algorithm as requested by the <code>Component Specification</code> document.
     * However the subclasses may override this method if a different algorithm must be used.</p>
     *
     * @return a <code>TemplateHierarchy</code> providing the details for template hierarchy matching the specified
     * component version. If no matching template hierarchy is found then the method MUST return <code>null </code>.
     * @param componentVersion a <code>ComponentVersion</code> instance providing the details for the component to
     * find the matching templates for.
     * @param templateHierarchy a <code>TemplateHierarchy</code> providing the details for the template hierarchy to
     * perform a lookup for the requested templates in.
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     */
    protected TemplateHierarchy findMatchingTemplateHierarchy(
        ComponentVersion componentVersion, TemplateHierarchy templateHierarchy) {

        // check the arguments
        if (componentVersion == null) {
            throw new NullPointerException("componentVersion should not be null");
        }
        if (templateHierarchy == null) {
            throw new NullPointerException("templateHierarchy should not be null");
        }

        // create a set to store all attribute names and values of the given component version
        Set attributes = new HashSet();

        // get all attribute names from the ComponentVersion instance
        String[] attributeNames = componentVersion.getAttributeNames();

        // put all attribute names and values to the set
        for (int i = 0; i < attributeNames.length; ++i) {
            attributes.add(attributeNames[i]);
            attributes.add(componentVersion.getAttribute(attributeNames[i]));
        }

        // create a set to store all technology types of the given component version
        Set technologyTypes = new HashSet();

        // get all technology types from the ComponentVersion instance
        TechnologyType[] types = componentVersion.getTechnologyTypes();

        // put all technology type names to the set
        for (int i = 0; i < types.length; ++i) {
            technologyTypes.add(types[i].getName());
        }

        TemplateHierarchy currentNode = templateHierarchy;

        while (true) {

            // get all child nodes of current node
            TemplateHierarchy[] childNodes = currentNode.getChildren();

            // try to get the first child node which matches the attributes of component version
            TemplateHierarchy matchedNode = getMatchedNode(attributes, childNodes);
            if (matchedNode == null) {
                // if there is no matched node, we try to get the first child node
                // which matches the technology types of component version
                matchedNode = getMatchedNode(technologyTypes, childNodes);
            }

            if (matchedNode == null) {
                // if there is still no matched node, we need to break the loop
                break;
            }

            // update the currentNode
            currentNode = matchedNode;
        }
        return currentNode;
    }

    /**
     * Returns the first child node which matches the given set
     * in the given child nodes array. If there is no matched node, null will be returned.
     *
     * @param matchSet a set containing all names to match.
     * @param childNodes the child node array to check
     * @return the first matched child node or null if no one found.
     */
    private TemplateHierarchy getMatchedNode(Set matchSet, TemplateHierarchy[] childNodes) {

        // iterate through the child nodes
        for (int i = 0; i < childNodes.length; ++i) {
            // return the first child node which matches the attributes of the given component version
            if (matchSet.contains(childNodes[i].getName())) {
                return childNodes[i];
            }
        }

        // return null if there is no such node
        return null;
    }

}
