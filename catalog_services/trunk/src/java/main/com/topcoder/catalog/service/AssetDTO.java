/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.Technology;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>This class is a simple DTO which provides a representation of a component that is closer to business requirements
 * than persistent entities are.</p>
 * <p>In other words <code>AssetDTO</code> is a <code>Component</code> entity combined, as a rule with its current
 * or its latest <code>CompVersion</code>, or  with any other <code>Component</code>'s version.</p>
 * This is the mapping of the DTO fields:
 * <ul>
 * <li>name   &lt;-&gt;   Component.name</li>
 * <li>clientIds   &lt;-&gt;   List&lt;Component.clients.clientId&gt; [optional]</li>
 * <li>versionText   &lt;-&gt;   CompVersion.versionText</li>
 * <li>shortDescription   &lt;-&gt;   Component.shortDesc</li>
 * <li>detailedDescription   &lt;-&gt;   Component.description</li>
 * <li>functionalDescription   &lt;-&gt;   Component.functionalDesc</li>
 * <li>rootCategory   &lt;-&gt;   Component.rootCategory</li>
 * <li>categories   &lt;-&gt;   Component.categories</li>
 * <li>technologies   &lt;-&gt;   CompVersion.technologies</li>
 * <li>productionDate   &lt;-&gt;   CompVersion.versionDates(phase.id).productionDate [optional]</li>
 * <li>link   &lt;-&gt;   CompVersion.link [optional]</li>
 * <li>forum   &lt;-&gt;   CompVersion.forum [optional]</li>
 * <li>currentLatest   &lt;-&gt;   whether the currentVersion is also the latest [optional]</li>
 * <li>id   &lt;-&gt;   Component.id [optional]</li>
 * <li>versionId   &lt;-&gt;   CompVersion.id [optional]</li>
 * <li>userIds   &lt;-&gt;   List&lt;Component.users.userId&gt; [optional]</li>
 * <li>informationComplete:boolean   &lt;-&gt;   whether the information in the DTO is complete or partial
 *  [optional]</li>
 * </ul>
 * Example of creating an AssetDTO:
 * <pre>
 *      AssetDTO newAsset = new AssetDTO();
 *      newAsset.setName("Catalog Services");
 *      newAsset.setVersionText("1.0");
 *      newAsset.setShortDescription("short");
 *      newAsset.setDetailedDescription("detailed");
 *      newAsset.setFunctionalDescription("functional");
 *      // set the root category
 *      newAsset.setRootCategory(javaCategory);
 *
 *      // assign categories which this asset belongs to
 *      newAsset.setCategories(Arrays.asList(ejb3Category));
 *
 *      newAsset.setTechnologies(Arrays.asList(
 *        java15Technology,
 *        informixTechnology
 *      ));
 * </pre>
 * Updating an asset works in a similar way, except that it doesn't create a new version,
 * but just updates the version referred by DTO.
 * <p/>
 * Creating a new version:
 * <pre>
 *      // retrieve asset and with current version
 *      AssetDTO asset = remote.getAssetById(assetId, true);
 *      asset.setName("Catalog Service"); // update asset name
 *      asset.setVersionText("1.1"); // sent new text version
 *      asset.setVersionId(null); // reset versionís ID
 *      asset.setProductionDate(parseDate("2008/01/10")); // set new production date
 *      remote.createVersion(asset);
 * </pre>
 *
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class AssetDTO implements Serializable {
    /**
     * <p>This field represents the id of the asset.
     * Should be null when creating new asset.
     * Should not be null when updating an asset or creating a new version.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the name of the asset.
     * Should not be null or empty when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private String name;
    /**
     * <p>This field represents ids of the clients of this asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     */
    private List<Long> clientIds;
    /**
     * <p>This field represents the text of the version.
     * Should not be null or empty when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private String versionText;
    /**
     * <p>This field represents the number of the version.
     * This property used only on fetching data.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long versionNumber;
    /**
     * <p>This field represents the short description of the asset.
     * Should not be null or empty when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private String shortDescription;
    /**
     * <p>This field represents the detailed description of the asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private String detailedDescription;
    /**
     * <p>This field represents the functional description of the asset.
     * Should not be null or empty when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private String functionalDescription;
    /**
     * <p>This field represents the root category of the asset.
     * Should not be null when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Category</code> value or <code>null</code>.</p>
     */
    private Category rootCategory;
    /**
     * <p>This field represents the categories this asset belongs to.
     * Should not be null, empty or containing nulls when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     */
    private List<Category> categories;
    /**
     * <p>This field represents the technologies of this version of the asset.
     * Should not be null, empty or containing nulls when updating or creating an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     */
    private List<Technology> technologies;
    /**
     * <p>This field represents the production date of this version of the asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     */
    private Date productionDate;
    /**
     * <p>This field represents the link of this version of the asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     */
    private CompLink link;
    /**
     * <p>This field represents the forum for this version of the asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     */
    private CompForum forum;
    /**
     * <p>This field indicates if the current version is also the latest one.</p>
     * <p>The initial value is <tt>false</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>boolean</code> value.</p>
     */
    private boolean isCurrentVersionAlsoLatestVersion;
    /**
     * <p>This field represents the id of the asset's version.
     * Should be null when creating a new version or an asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long versionId;
    /**
     * <p>This field represents the users of the asset.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     */
    private List<Long> userIds;
    /**
     * <p>This field indicates if this DTO is complete or not.</p>
     * <p>The initial value is <tt>false</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>boolean</code> value.</p>
     */
    private boolean informationComplete;


    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public AssetDTO() {
    }

    /**
     * <p>Sets the id of the asset.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param id the id of the asset.
     * Should be null when creating new asset.
     * Should not be null when updating an asset or creating a new version.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Retrieves the id of the asset.</p>
     *
     * @return the id of the asset.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Sets the name of the asset.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     * @param name the name of the asset.
     * Should not be null or empty when updating or creating an asset.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Retrieves the name of the asset.</p>
     *
     * @return the name of the asset.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets ids of the clients of this asset.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     * @param clientIds ids of the clients of this asset.
     */
    public void setClientIds(List<Long> clientIds) {
        this.clientIds = clientIds;
    }

    /**
     * <p>Retrieves ids of the clients of this asset.</p>
     *
     * @return ids of the clients of this asset.
     */
    public List<Long> getClientIds() {
        return clientIds;
    }

    /**
     * <p>Sets the text of the version.</p>
     * <p>If the value is not <code>null</code>, then sets a trimmed string, as long as
     * database column has type <tt>CHAR(20)</tt> and trailing zeros are appended (but they are not desirable).</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     * @param versionText the text of the version.
     * Should not be null or empty when updating or creating an asset.
     */
    public void setVersionText(String versionText) {
        this.versionText = versionText == null ? null : versionText.trim();
    }

    /**
     * <p>Retrieves the text of the version.</p>
     *
     * @return the text of the version.
     */
    public String getVersionText() {
        return versionText;
    }

    /**
     * <p>Sets the number of the version.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param versionNumber the number of the version.
     */
    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * <p>Retrieves the number of the version.</p>
     *
     * @return the number of the version.
     */
    public Long getVersionNumber() {
        return versionNumber;
    }

    /**
     * <p>Sets the short description of the asset.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     * @param shortDescription the short description of the asset.
     * Should not be null or empty when updating or creating an asset.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * <p>Retrieves the short description of the asset.</p>
     *
     * @return the short description of the asset.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * <p>Sets the detailed description of the asset.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     * @param detailedDescription the detailed description of the asset.
     */
    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    /**
     * <p>Retrieves the detailed description of the asset.</p>
     *
     * @return the detailed description of the asset.
     */
    public String getDetailedDescription() {
        return detailedDescription;
    }

    /**
     * <p>Sets the functional description of the asset.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     * @param functionalDescription the functional description of the asset.
     * Should not be null or empty when updating or creating an asset.
     */
    public void setFunctionalDescription(String functionalDescription) {
        this.functionalDescription = functionalDescription;
    }

    /**
     * <p>Retrieves the functional description of the asset.</p>
     *
     * @return the functional description of the asset.
     */
    public String getFunctionalDescription() {
        return functionalDescription;
    }

    /**
     * <p>Sets the root category of the asset.</p>
     * <p>The acceptance region: any <code>Category</code> value or <code>null</code>.</p>
     * @param rootCategory the root category of the asset.
     * Should not be null when updating or creating an asset.
     */
    public void setRootCategory(Category rootCategory) {
        this.rootCategory = rootCategory;
    }

    /**
     * <p>Retrieves the root category of the asset.</p>
     *
     * @return the root category of the asset.
     */
    public Category getRootCategory() {
        return rootCategory;
    }

    /**
     * <p>Sets the categories this asset belongs to.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     * @param categories the categories this asset belongs to.
     * Should not be null, empty or containing nulls when updating or creating an asset.
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * <p>Retrieves the categories this asset belongs to.</p>
     *
     * @return the categories this asset belongs to.
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * <p>Sets the technologies of this version of the asset.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     * @param technologies the technologies of this version of the asset.
     * Should not be null, empty or containing nulls when updating or creating an asset.
     */
    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    /**
     * <p>Retrieves the technologies of this version of the asset.</p>
     *
     * @return the technologies of this version of the asset.
     */
    public List<Technology> getTechnologies() {
        return technologies;
    }

    /**
     * <p>Sets the production date of this version of the asset.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @param productionDate the production date of this version of the asset.
     */
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    /**
     * <p>Retrieves the production date of this version of the asset.</p>
     *
     * @return the production date of this version of the asset.
     */
    public Date getProductionDate() {
        return productionDate;
    }

    /**
     * <p>Sets the link of this version of the asset.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @param link the link of this version of the asset.
     */
    public void setLink(CompLink link) {
        this.link = link;
    }

    /**
     * <p>Retrieves the link of this version of the asset.</p>
     *
     * @return the link of this version of the asset.
     */
    public CompLink getLink() {
        return link;
    }

    /**
     * <p>Sets the forum for this version of the asset.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @param forum the forum for this version of the asset.
     */
    public void setForum(CompForum forum) {
        this.forum = forum;
    }

    /**
     * <p>Retrieves the forum for this version of the asset.</p>
     *
     * @return the forum for this version of the asset.
     */
    public CompForum getForum() {
        return forum;
    }

    /**
     * <p>Sets sign if the current version of the asset is the latest one.</p>
     * <p>The acceptance region: any <code>boolean</code> value.</p>
     * @param isCurrentVersionAlsoLatestVersion <code>true</code> if the current version is also the latest version,
     *                                           <code>false</code> otherwise.
     */
    public void setCurrentVersionAlsoLatestVersion(boolean isCurrentVersionAlsoLatestVersion) {
        this.isCurrentVersionAlsoLatestVersion = isCurrentVersionAlsoLatestVersion;
    }

    /**
     * <p>Indicates if the current version is also the latest one (or not).</p>
     *
     * @return <code>true</code> if the current version is also the latest version,
     *         <code>false</code> otherwise.
     */
    public boolean isCurrentVersionAlsoLatestVersion() {
        return isCurrentVersionAlsoLatestVersion;
    }

    /**
     * <p>Sets the id of the asset's version.
     * Should be null when creating a new version or an asset.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param versionId the id of the asset's version.
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * <p>Retrieves the id of the asset's version.</p>
     *
     * @return the id of the asset's version.
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * <p>Sets the users of the asset.</p>
     * <p>The acceptance region: any <code>List</code> value or <code>null</code>.</p>
     * @param userIds the users of the asset.
     */
    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    /**
     * <p>Retrieves the users of the asset.</p>
     *
     * @return the users of the asset.
     */
    public List<Long> getUserIds() {
        return userIds;
    }

    /**
     * <p>Sets sign if the information on the asset is complete or not.</p>
     * <p>The acceptance region: any <code>boolean</code> value.</p>
     * @param informationComplete <code>true</code> if this DTO is complete or <code>false</code> otherwise.
     */
    public void setInformationComplete(boolean informationComplete) {
        this.informationComplete = informationComplete;
    }

    /**
     * <p>Indicates if this DTO is complete or not.</p>
     *
     * @return <code>true</code> if this DTO is complete or <code>false</code> otherwise.
     */
    public boolean isInformationComplete() {
        return informationComplete;
    }
}



