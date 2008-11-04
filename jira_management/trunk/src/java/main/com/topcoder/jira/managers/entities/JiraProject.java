/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This java bean is a basic data holder that represents
 * <code>com.atlassian.jira.rpc.soap.beans.RemoteProject</code> entity from JIRA RPC plugin:
 * <p>
 * <table border>
 * <tr>
 * <th>JiraProject property</th>
 * <th>RemoteProject property</th>
 * </tr>
 * <tr>
 * <td>description</td>
 * <td>description</td>
 * </tr>
 * <tr>
 * <td>key</td>
 * <td>key</td>
 * </tr>
 * <tr>
 * <td>lead</td>
 * <td>lead</td>
 * </tr>
 * <tr>
 * <td>projectUrl</td>
 * <td>projectUrl</td>
 * </tr>
 * <tr>
 * <td>url</td>
 * <td>url</td>
 * </tr>
 * <tr>
 * <td>id</td>
 * <td>id</td>
 * </tr>
 * <tr>
 * <td>name</td>
 * <td>name</td>
 * </tr>
 * </table>
 * <p>
 * Any attribute in the bean is optional and no validation is performed here.
 * <p>
 * This entity can be serialized to XML. It is done through the use of class annotations:
 * <ol>
 * <li>XmlAccessorType(XmlAccessType.FIELD)</li>
 * <li>XmlType(name = "jiraProject", propOrder = {"description, key, lead, projectUrl, url, id,
 * name"})</li>
 * </ol>
 * <p>
 * Thread-safety. This class is mutable and not thread-safe.
 *
 * @author Mafy, agh
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jiraProject", propOrder = {"description", "key", "lead", "projectUrl", "url", "id", "name"})
public class JiraProject implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = -5189029413997504526L;

    /**
     * This field represents the 'description' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String description;

    /**
     * This field represents the 'key' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String key;

    /**
     * This field represents the 'lead' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String lead;

    /**
     * This field represents the 'projectUrl' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String projectUrl;

    /**
     * This field represents the 'url' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String url;

    /**
     * This field represents the 'id' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String id;

    /**
     * This field represents the 'name' property of the <code>JiraProject</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String name;

    /**
     * Default no-arg constructor.
     */
    public JiraProject() {
        // do nothing
    }

    /**
     * Creates a new <code>JiraProject</code> instance with the given description, key, lead,
     * projectUrl and url properties.
     *
     * @param description value of the 'description' property. It can be any value.
     * @param key value of the 'key' property. It can be any value.
     * @param lead value of the 'lead' property. It can be any value.
     * @param projectUrl value of the 'projectUrl' property. It can be any value.
     * @param url value of the 'url' property. It can be any value.
     */
    public JiraProject(String description, String key, String lead, String projectUrl, String url) {
        this.description = description;
        this.key = key;
        this.lead = lead;
        this.projectUrl = projectUrl;
        this.url = url;
    }

    /**
     * Getter for the 'description' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'description' property. It can be any value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the 'description' property.
     *
     * @param description new value of the 'description' property. It can be any value.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the 'key' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'key' property. It can be any value.
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter for the 'key' property.
     *
     * @param key new value of the 'key' property. It can be any value.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter for the 'lead' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'lead' property. It can be any value.
     */
    public String getLead() {
        return lead;
    }

    /**
     * Setter for the 'lead' property.
     *
     * @param lead new value of the 'lead' property. It can be any value.
     */
    public void setLead(String lead) {
        this.lead = lead;
    }

    /**
     * Getter for the 'projectUrl' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'projectUrl' property. It can be any value.
     */
    public String getProjectUrl() {
        return projectUrl;
    }

    /**
     * Setter for the 'projectUrl' property.
     *
     * @param projectUrl new value of the 'projectUrl' property. It can be any value.
     */
    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    /**
     * Getter for the 'url' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'url' property. It can be any value.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for the 'url' property.
     *
     * @param url new value of the 'url' property. It can be any value.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for the 'id' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'id' property. It can be any value.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the 'id' property.
     *
     * @param id new value of the 'id' property. It can be any value.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for the 'name' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'name' property. It can be any value.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the 'name' property.
     *
     * @param name new value of the 'name' property. It can be any value.
     */
    public void setName(String name) {
        this.name = name;
    }
}
