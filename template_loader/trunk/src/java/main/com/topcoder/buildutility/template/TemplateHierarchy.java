/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;



/**
 * <p>This class is an object representation of a template hierarchies which are used as groups of the template
 * hierarchies and templates. This class does not implement any complex logic other than storing the values of the
 * attributes of the template hierarchy, returning them via simple accessor methods and maintaining the lists of
 * template hierarchies and templates directly nested within template hierarchy.</p>
 *
 * <p>Thread safety: this class is thread safe. The private state of the instances of this class is never changed after
 * instantiation. The mutator methods affecting the state of lists of templates and template hierarchies are
 * synchronized.</p>
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public class TemplateHierarchy {

    /**
     * <p>A <code>long</code> providing the ID of the template hierarchy uniquely distinguishing it among other
     * hierarchies. This instance field is initialized within the constructor and is never changed during the
     * lifetime of <code>TemplateHierarchy</code> instance.</p>
     */
    private long id = 0;

    /**
     * <p>A <code>String</code> providing the name of the template hierarchy uniquely distinguishing it among other
     * template hierarchies. This instance field is initialized within the constructor and is never changed during
     * the lifetime of <code>TemplateHierarchy</code> instance. This field must never be <code>null</code>.</p>
     */
    private String name = null;

    /**
     * <p>A <code>Map</code> mapping the <code>Long</code> objects (IDs) to <code>Template</code> objects representing
     * the templates which are directly nested within this template hierarchy. This instance field is initialized
     * within the constructor with an empty map and is populated through appropriate mutator method.</p>
     */
    private Map templates = null;

    /**
     * <p>A <code>Map</code> mapping the <code>Long</code> objects (IDs) to <code>TemplateHierarchy</code> objects
     * representing the template hierarchies which are directly nested within this template hierarchy. This instance
     * field is initialized within the constructor with an empty map and is populated through appropriate mutator
     * method.</p>
     */
    private Map childHierarchies = null;

    /**
     * <p>A <code>long</code> providing the ID of a parent template hierarchy which this template hierarchy is
     * directly nested within. For top-level hierarchies the value of this field must be equal to the value of 'id'
     * field. This instance field is initialized within the constructor and is never changed during the lifetime of
     * <code>TemplateHierarchy</code> instance.</p>
     */
    private long parentHierarchyId = 0;


    /**
     * <p>Constructs new <code>TemplateHierarchy</code> instance with specified ID, name and ID of a parent template
     * hierarchy. The lists of nested templates and template hierarchies are empty and must be populated through
     * appropriate mutator methods.</p>
     *
     *
     * @throws NullPointerException if specified <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if any of specified arguments is an empty <code>String</code> being trimmed.
     *
     * @param id a <code>long</code> providing the ID of this template hierarchy.
     * @param name a <code>String</code> providing the name of this template hierarchy.
     * @param parentHierarchyId a <code>long</code> providing the ID of parent template hierarchy which this template
     * hierarchy is directly nested within. For top-level hierarchies this parameter must be the same as <code>
     * id</code> parameter.
     */
    public TemplateHierarchy(long id, String name, long parentHierarchyId) {
        // validate the argument
        TemplateLoaderHelper.checkString(name, "name");

        // initialize the inner fields
        this.id = id;
        this.name = name;
        this.parentHierarchyId = parentHierarchyId;

        this.templates = new HashMap();
        this.childHierarchies = new HashMap();
    }

    /**
     * <p>Gets the template hierarchy node's name. The name of the node in the template hierarchy is used as part of
     * the template selection process. The name corresponds to either technology types or component attributes.</p>
     *
     * @return a <code>String</code> providing the name of the node in the template hierarchy is used as part of the
     * template selection process. The name corresponds to either technology types or component attributes.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Gets the templates which are directly nested within this template hierarchy.</p>
     *
     * <p>Each node in the template hierarchy can have zero to many template associations.</p>
     *
     * @return a <code>Template</code> array providing the templates which are directly nested within this template
     * hierarchy. If no templates are directly nested within this template hierarchy then an empty array is
     * returned.
     */
    public synchronized Template[] getTemplates() {
        return (Template[]) templates.values().toArray(new Template[0]);
    }

    /**
     * <p>Gets all of the template hierarchy nodes directly below the current node in the hierarchy.</p>
     *
     * @return a <code>TemplateHierarchy</code> array providing the template hierarchies which are directly nested
     * within this template hierarchy. If no template hierarchies are directly nested within this template
     * hierarchy then an empty array is returned.
     */
    public synchronized TemplateHierarchy[] getChildren() {
        return (TemplateHierarchy[]) childHierarchies.values().toArray(new TemplateHierarchy[0]);
    }

    /**
     * <p>Adds specified template to the list of templates directly nested within this template hierarchy.</p>
     *
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws DuplicateObjectException if the template with the same ID is already added to this template hierarchy.
     *
     * @param template a <code>Template</code> to be added to this template hierarchy.
     */
    public synchronized void addTemplate(Template template) {

        TemplateLoaderHelper.checkNull(template, "template");

        // crate the Long id instance
        Long identify = new Long(template.getId());

        // if the template with the same ID is already added, throw an exception
        if (templates.containsKey(identify)) {
            throw new DuplicateObjectException("the template is already added", identify.longValue());
        }

        // the template is valid, put it to the inner map
        templates.put(identify, template);
    }

    /**
     * <p>Adds specified template hierarchy to the list of template hierarchies directly nested within this template
     * hierarchy.</p>
     *
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws DuplicateObjectException if the template hierarchy with the same ID is already added to this template
     * hierarchy.
     * @throws IllegalArgumentException if the templateHierarchy is the top level hierarchy or templateHierarchy's
     * parent id should equal to the ID of a hierarchy which the nested hierarchy is added to.
     *
     * @param templateHierarchy a <code>TemplateHierarchy</code> to be added to this template hierarchy.
     */
    public void addNestedHierarchy(TemplateHierarchy templateHierarchy) {
        TemplateLoaderHelper.checkNull(templateHierarchy, "templateHierarchy");

        // throws IllegalArgumentException if the templateHierarchy is the top level hierarchy or templateHierarchy's
        // parent id should equal to the ID of a hierarchy which the nested hierarchy is added to.
        if (templateHierarchy.getId() == templateHierarchy.getParentId()) {
            throw new IllegalArgumentException("templateHierarchy should not be top level hierarchy");
        }
        if (templateHierarchy.getParentId() != this.id) {
            throw new IllegalArgumentException("templateHierarchy's parent id should equal to the ID of"
                + "a hierarchy which the nested hierarchy is added to.");
        }

        Long identify = new Long(templateHierarchy.getId());

        synchronized (this) {
            // if the template hierarchy with the same ID is already added, throw an exception
            if (childHierarchies.containsKey(identify)) {
                throw new DuplicateObjectException("the template hierarchy is already added", identify.longValue());
            }
        }

        // release the lock here to avoid deadlock
        // NOTE: this does not break thread safety, since the following code does not uses any inner field

        // CHECK LOOP
        // use BFS to visit all children, if one child is the root node, there is a loop.
        //
        // NOTE: I use the reference rather than the id to determine whether 2 TemplateHierarchy instances are same

        // a Stack used to store all visited node
        Stack visited = new Stack();
        visited.push(templateHierarchy);

        // use BFS to visit all children
        while (visited.size() > 0) {
            // get a TemplateHierarchy from visited nodes
            TemplateHierarchy current = (TemplateHierarchy) visited.pop();

            // get all children of current node
            TemplateHierarchy[] children = current.getChildren();
            for (int i = 0; i < children.length; ++i) {
                // if a child is the root node, loop exist
                if (children[i] == this) {
                    throw new IllegalArgumentException("the TemplateHierarchy causes a loop");
                }
                // add the child to visited nodes
                visited.push(children[i]);
            }
        }

        // obtain the lock again
        synchronized (this) {
            // the template hierarchy is valid, put it to the inner map
            childHierarchies.put(identify, templateHierarchy);
        }
    }

    /**
     * <p>Sets the file server root URI which must be used to resolve the URI of a file providing the content of all
     * templates which are directly or indirectly nested within this template hierarchy. This method will be called
     * immediately after instantiation of this <code>TemplateHierarchy</code> instance. Only <code>TemplateLoader
     * </code> is allowed to call this method. Therefore this method is declared with package private access.</p>
     *
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     * @throws IllegalArgumentException if specified <code>fileServerUri</code> is an empty <code>String</code> being
     * trimmed.
     *
     * @param fileServerUri a <code>String</code> providing the URI of a file server root.
     */
    synchronized void setFileServerUri(String fileServerUri) {
        // throw exception immediately, if the argument is invalid
        TemplateLoaderHelper.checkString(fileServerUri, "fileServerUri");

        // notify all templates that belong to this hierarchy
        for (Iterator iterator = templates.values().iterator(); iterator.hasNext();) {
            ((Template) iterator.next()).setFileServerUri(fileServerUri);
        }

        // notify all child templates hierarchies
        for (Iterator iterator = childHierarchies.values().iterator(); iterator.hasNext();) {
            ((TemplateHierarchy) iterator.next()).setFileServerUri(fileServerUri);
        }
    }

    /**
     * <p>Gets the ID of this template hierarchy.</p>
     *
     * @return a <code>long</code> providing the ID of this template hierarchy.
     */
    public long getId() {
        return id;
    }


    /**
     * <p>Gets the ID of a parent template hierarchy which this template hierarchy is directly
     * nested within. For top-level hierarchies this method will return the same value as returned
     * by 'getId()' method.</p>
     *
     * @return a <code>long</code> providing the ID of the parent template hierarchy.
     */
    public long getParentId() {
        return parentHierarchyId;
    }

    /**
     * <p>Checks whether this template hierarchy has any subordinate template hierarchy nested within it or not.</p>
     *
     * @return <code>true</code> if this template hierarchy has at least one hierarchy nested within it;
     * <code>false</code> otherwise.
     */
    public synchronized boolean hasChildren() {
        return childHierarchies.size() > 0;
    }

    /**
     * <p>Checks whether this template hierarchy has any subordinate templates directly nested within it or not.</p>
     *
     * @return <code>true</code> if this template hierarchy has at least one template directly nested within it;
     * <code>false</code> otherwise.
     */
    public synchronized boolean hasTemplates() {
        return templates.size() > 0;
    }
}
