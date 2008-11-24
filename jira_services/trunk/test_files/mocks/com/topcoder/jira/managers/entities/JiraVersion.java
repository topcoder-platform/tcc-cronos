/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers.entities;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This java bean is a basic data holder that represents
 * <code>com.atlassian.jira.rpc.soap.beans.RemoteVersion</code> entity from JIRA RPC plugin:
 * <p>
 * <table border>
 * <tr>
 * <th>JiraVersion property</th>
 * <th>RemoteVersion property</th>
 * </tr>
 * <tr>
 * <td>releaseDate</td>
 * <td>releaseDate</td>
 * </tr>
 * <tr>
 * <td>sequence</td>
 * <td>sequence</td>
 * </tr>
 * <tr>
 * <td>archived</td>
 * <td>archived</td>
 * </tr>
 * <tr>
 * <td>released</td>
 * <td>released</td>
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
 * <li>XmlType(name = "jiraProject", propOrder = {"releaseDate, sequence, archived, released, id,
 * name"})</li>
 * </ol>
 * <p>
 * Thread-safety. This class is mutable and not thread-safe.
 *
 * @author Mafy, agh
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jiraVersion", propOrder = {"releaseDate", "sequence", "archived", "released", "id", "name"})
public class JiraVersion implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = 5503053656504000927L;

    /**
     * This field represents the 'releaseDate' property of the <code>JiraVersion</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private Date releaseDate;

    /**
     * This field represents the 'sequence' property of the <code>JiraVersion</code> entity.
     * <p>
     * Default value is zero. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private long sequence;

    /**
     * This field represents the 'archived' property of the <code>JiraVersion</code> entity.
     * <p>
     * Default value is false. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private boolean archived;

    /**
     * This field represents the 'released' property of the <code>JiraVersion</code> entity.
     * <p>
     * Default value is false. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private boolean released;

    /**
     * This field represents the 'id' property of the <code>JiraVersion</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String id;

    /**
     * This field represents the 'name' property of the <code>JiraVersion</code> entity.
     * <p>
     * Default value is null. Can be accessed/modified through the corresponding getter/setter.
     * <p>
     * Can take any value.
     */
    private String name;

    /**
     * Default no-arg constructor.
     */
    public JiraVersion() {
        // do nothing
    }

    /**
     * Creates a new <code>JiraProject</code> instance with the given releaseDate, sequence,
     * archived and released properties.
     *
     * @param releaseDate value of the 'releaseDate' property. It can be any value.
     * @param sequence value of the 'sequence' property. It can be any value.
     * @param archived value of the 'archived' property. It can be any value.
     * @param released value of the 'released' property. It can be any value.
     */
    public JiraVersion(Date releaseDate, long sequence, boolean archived, boolean released) {
        this.releaseDate = releaseDate;
        this.sequence = sequence;
        this.archived = archived;
        this.released = released;
    }

    /**
     * Getter for the 'releaseDate' property.
     * <p>
     * Initial value is null.
     *
     * @return value of the 'releaseDate' property. It can be any value.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Setter for the 'releaseDate' property.
     *
     * @param releaseDate new value of the 'releaseDate' property. It can be any value.
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Getter for the 'sequence' property.
     * <p>
     * Initial value is zero.
     *
     * @return value of the 'sequence' property. It can be any value.
     */
    public long getSequence() {
        return sequence;
    }

    /**
     * Setter for the 'sequence' property.
     *
     * @param sequence new value of the 'sequence' property. It can be any value.
     */
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    /**
     * Getter for the 'archived' property.
     * <p>
     * Initial value is false.
     *
     * @return value of the 'archived' property. It can be any value.
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Setter for the 'archived' property.
     *
     * @param archived new value of the 'archived' property. It can be any value.
     */
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    /**
     * Getter for the 'released' property.
     * <p>
     * Initial value is false.
     *
     * @return value of the 'released' property. It can be any value.
     */
    public boolean isReleased() {
        return released;
    }

    /**
     * Setter for the 'released' property.
     *
     * @param released new value of the 'released' property. It can be any value.
     */
    public void setReleased(boolean released) {
        this.released = released;
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
