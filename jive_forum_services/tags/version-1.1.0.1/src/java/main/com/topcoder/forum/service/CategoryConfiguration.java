/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import java.io.Serializable;


/**
 * <p>
 * This class contains configuration properties to be used in <code>JiveForumManager</code>
 * to create the category. It contains the following properties:<br/>
 * Category Name<br/>
 * Category Description<br/>
 * Root category id<br/>
 * Component Id<br/>
 * Version Text<br/>
 * Version Id<br/>
 * Whether the forum is public or not<br/>
 * Optional: Template Category Id<br/>
 * Optional: Moderator user id<br/>
 * Optional: Template Category Type<br/>
 * These properties are used to create the category and set its properties and then create
 * groups and set permissions. If a <code>Template Category Id</code> is specified, all the
 * forums, threads and announcements must be copied from that category to the created category,
 * maintaining the structure. If a <code>Template Category Type</code> is specified, it will
 * be used to get the preconfigured <code>Template category id</code>, and then do the same as
 * above. If a <code>Moderator User Id</code> is specified, that <code>user id</code> must be
 * assigned to the moderators group for the category, and a watch for the user must be created
 * for the category.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class CategoryConfiguration implements Serializable {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = 7060813318435474115L;

    /**
     * <p>
     * This field represents the category name. It's null initially, and must be non-null, non-empty
     * after set.
     * </p>
     */
    private String name;

    /**
     * <p>
     * This field represents the category description. It's null initially, and must be non-null,
     * non-empty after set.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This field represents the root category id. It's -1 initially, and must be above 0 after set.
     * </p>
     */
    private long rootCategoryId = -1;

    /**
     * <p>
     * This field represents the component id. It's -1 initially, and must be above 0 after set.
     * </p>
     */
    private long componentId = -1;

    /**
     * <p>
     * This field represents the version text. It's null initially, and must be non-null, non-empty
     * after set.
     * </p>
     */
    private String versionText;

    /**
     * <p>
     * This field represents the version id. It's -1 initially, and must be above 0 after set.
     * </p>
     */
    private long versionId = -1;

    /**
     * <p>
     * This field represents the category is public or not. It's true initially.
     * </p>
     */
    private boolean isPublic = true;

    /**
     * <p>
     * This field represents the template category id. It's -1 initially, and must be above 0 or just -1 after set.
     * </p>
     */
    private long templateCategoryId = -1;

    /**
     * <p>
     * This field represents the moderator user id. It's -1 initially, and must be above 0 or just -1 after set.
     * </p>
     */
    private long moderatorUserId = -1;

    /**
     * <p>
     * This field represents the template category type. It's null initially, and must be the value defined
     * in <code>CategoryType</code> enum or null after set.
     * </p>
     */
    private CategoryType templateCategoryType;

    /**
     * <p>Creates an instance. It does nothing else.</p>
     */
    public CategoryConfiguration() {
    }

    /**
     * <p>Gets the category name.</p>
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the category name to the specified value.</p>
     *
     * @param name the new category name
     * @throws IllegalArgumentException if the name is null or empty string
     */
    public void setName(String name) {
        Helper.checkString(name, "name");

        this.name = name;
    }

    /**
     * <p>Gets the category description.</p>
     *
     * @return the category description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the category description to the specified value.</p>
     *
     * @param description the new category description
     * @throws IllegalArgumentException if description is null or empty string
     */
    public void setDescription(String description) {
        Helper.checkString(description, "description");

        this.description = description;
    }

    /**
     * <p>Gets the root category id.</p>
     *
     * @return the root category id
     */
    public long getRootCategoryId() {
        return rootCategoryId;
    }

    /**
     * <p>Sets the root category id to the specified value.</p>
     *
     * @param rootCategoryId the new root Category Id
     * @throws IllegalArgumentException if the rootCategoryId is not above 0
     */
    public void setRootCategoryId(long rootCategoryId) {
        Helper.checkId(rootCategoryId, "rootCategoryId");

        this.rootCategoryId = rootCategoryId;
    }

    /**
     * <p>Gets the component's id.</p>
     *
     * @return the component's id
     */
    public long getComponentId() {
        return componentId;
    }

    /**
     * <p>Sets the component's id to the specified value.</p>
     *
     * @param componentId the new component id
     * @throws IllegalArgumentException if the componentId is not above 0
     */
    public void setComponentId(long componentId) {
        Helper.checkId(componentId, "componentId");

        this.componentId = componentId;
    }

    /**
     * <p>Gets the version text.</p>
     *
     *  @return the version text
     */
    public String getVersionText() {
        return versionText;
    }

    /**
     * <p>Sets the version text to the specified value.</p>
     *
     * @param versionText the new version text
     * @throws IllegalArgumentException if the versionText is null or empty string
     */
    public void setVersionText(String versionText) {
        Helper.checkString(versionText, "versionText");

        this.versionText = versionText;
    }

    /**
     * <p>Gets the version id.</p>
     *
     * @return the version id
     */
    public long getVersionId() {
        return versionId;
    }

    /**
     * <p>Sets the version id to the specified value.</p>
     *
     * @param versionId the new version id
     * @throws IllegalArgumentException if the versionId is not above 0
     */
    public void setVersionId(long versionId) {
        Helper.checkId(versionId, "versionId");

        this.versionId = versionId;
    }

    /**
     * <p>Indicates whether the category is public or not.</p>
     *
     * @return true if the category is public, otherwise returns false
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * <p>Sets the new public attribute of the category.</p>
     *
     * @param isPublic indicates whether the category is public or not
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * <p>Gets the template category's id.</p>
     *
     * @return the template category's id
     */
    public long getTemplateCategoryId() {
        return templateCategoryId;
    }

    /**
     * <p>Sets the template category's id to the specified value.</p>
     *
     * @param templateCategoryId the new template category id
     * @throws IllegalArgumentException if the templateCategoryId is not above 0 neither -1
     */
    public void setTemplateCategoryId(long templateCategoryId) {
        Helper.checkOptionId(templateCategoryId, "templateCategoryId");

        this.templateCategoryId = templateCategoryId;
    }

    /**
     * <p>Gets the moderator user's id.</p>
     *
     * @return the moderator user's id
     */
    public long getModeratorUserId() {
        return moderatorUserId;
    }

    /**
     * <p>Sets the moderator user's id to the new specified value.</p>
     *
     * @param moderatorUserId the new moderator user id
     * @throws IllegalArgumentException if the moderatorUserId is not above 0 neither -1
     */
    public void setModeratorUserId(long moderatorUserId) {
        Helper.checkOptionId(moderatorUserId, "moderatorUserId");

        this.moderatorUserId = moderatorUserId;
    }

    /**
     * <p>Gets the template category's type.</p>
     *
     * @return the template category type
     */
    public CategoryType getTemplateCategoryType() {
        return templateCategoryType;
    }

    /**
     * <p>Sets the template category type to the new specified value.</p>
     *
     * @param templateCategoryType the new template category type
     * @throws IllegalArgumentException if templateCategoryType is null
     */
    public void setTemplateCategoryType(CategoryType templateCategoryType) {
        Helper.checkNull(templateCategoryType, "templateCategoryType");

        this.templateCategoryType = templateCategoryType;
    }
}
