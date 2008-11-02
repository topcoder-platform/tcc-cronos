/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.topcoder.confluence.Helper;

/**
 * <p>
 * This class represents the page created or retrieved from Confluence. It contains parameters that describe the
 * page and also its content. When page is retrieved from Confluence, it will be transformed in this object to
 * provide more convenient access and relevant information to user.
 * </p>
 * <p>
 * <b>Thread Safety:</b> this class is mutable so not thread-safe. If instances of this class won't be changed
 * during the processing, they can be used in thread-safe manner.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"basePageUrl", "versionUrl", "assetName", "version", "assetType", "assetName", "catalog",
    "applicationCode", "componentType", "content"})
@XmlRootElement(name = "page")
public class Page {

    /**
     * <p>
     * This field stores url of the base page for current page. Base page is page named in the form
     * 'Catalog+Type+Name' for components and 'ApplicationCode+Name' for applications.
     * </p>
     * <p>
     * Cannot be set to null or empty. should be valid url.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private String basePageUrl;

    /**
     * <p>
     * This field stores url of the version page for current page. Version page is page named in the form
     * 'Catalog+Type+Name+Version' for components and 'ApplicationCode+Name+Version' for applications.
     * </p>
     * <p>
     * Cannot be set to null or empty.should be valid url.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private String versionUrl;

    /**
     * <p>
     * Contains the full title, without version, for the requested page (the version is not part of this field
     * value). Format: 'Component_Type Component_Name' for components, 'CodeName Application_Name' for
     * applications.
     * </p>
     * <p>
     * Cannot be null or empty
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private String assetName;

    /**
     * <p>
     * Stores the page version.
     * </p>
     * <p>
     * Cannot be null or empty. should be valid integer when parsed.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private String version;

    /**
     * <p>
     * Represents the asset type of the current page. Please refer to ConfluenceAssetType to see possible values.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private ConfluenceAssetType assetType;

    /**
     * <p>
     * Represents the current page's catalog (.net/java).
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private ConfluenceCatalog catalog;

    /**
     * <p>
     * Represents the application code. This field is required only when assetType is set to
     * ConfluenceAssetType.APPLICATION_SPECIFICATION.
     * </p>
     * <p>
     * If optional can contain any value, if required cannot be null or empty.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private String applicationCode;

    /**
     * <p>
     * This field represents the component type of the current page (custom/generic).
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private ComponentType componentType;

    /**
     * <p>
     * This field represents the content of the current page in the string form.
     * </p>
     * <p>
     * Cannot be set to null but can be empty.
     * </p>
     * <p>
     * Accessed through corresponding getter/setter.
     * </p>
     */
    private String content;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public Page() {
        // does nothing
    }

    /**
     * <p>
     * Creates Page and initializes corresponding fields.
     * </p>
     *
     * @param basePageUrl
     *            the base page url. no restrictions at this level, they will be verified like in setter
     * @param versionUrl
     *            the version url. no restrictions at this level, they will be verified like in setter
     * @param assetName
     *            the asset name. no restrictions at this level, they will be verified like in setter
     * @param version
     *            page version. no restrictions at this level, they will be verified like in setter
     * @param assetType
     *            the asset type. no restrictions at this level, they will be verified like in setter
     * @param catalog
     *            the catalog. no restrictions at this level, they will be verified like in setter
     * @param applicationCode
     *            the application code. no restrictions at this level, they will be verified like in setter
     * @param componentType
     *            the component type. no restrictions at this level, they will be verified like in setter
     * @param content
     *            the page content. no restrictions at this level, they will be verified like in setter
     * @throws if any arguments is null or any string arguments is empty except content
     */
    public Page(String basePageUrl, String versionUrl, String assetName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode,
        ComponentType componentType, String content) {
        setBasePageUrl(basePageUrl);
        setVersionUrl(versionUrl);
        setAssetName(assetName);
        setVersion(version);
        setAssetType(assetType);
        setCatalog(catalog);
        setApplicationCode(applicationCode);
        setComponentType(componentType);
        setContent(content);
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the base url of the page
     */
    public String getBasePageUrl() {
        return basePageUrl;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param basePageUrl
     *            the base url of the page. cannot be null or empty. should be valid url
     * @throws IllegalArgumentException
     *             if basePageUrl is null or empty
     */
    public void setBasePageUrl(String basePageUrl) {
        Helper.checkStringNullOrEmpty(basePageUrl, "basePageUrl");
        this.basePageUrl = basePageUrl;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the version url of the page
     */
    public String getVersionUrl() {
        return versionUrl;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param versionUrl
     *            the version url of the page. cannot be null or empty. should be valid url
     * @throws IllegalArgumentException
     *             if versionUrl is null or empty
     */
    public void setVersionUrl(String versionUrl) {
        Helper.checkStringNullOrEmpty(versionUrl, "versionUrl");
        this.versionUrl = versionUrl;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the asset name of the page
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param assetName
     *            the asset name of the page. cannot be null or empty
     * @throws IllegalArgumentException
     *             if assetName is null or empty
     */
    public void setAssetName(String assetName) {
        Helper.checkStringNullOrEmpty(assetName, "assetName");
        this.assetName = assetName;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the version of the page
     */
    public String getVersion() {
        return version;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param version
     *            the version of the page. cannot be null or empty. should be valid integer when parsed
     * @throws IllegalArgumentException
     *             if version is null or empty
     */
    public void setVersion(String version) {
        Helper.checkStringNullOrEmpty(version, "version");
        this.version = version;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the asset type of the page
     */
    public ConfluenceAssetType getAssetType() {
        return assetType;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param assetType
     *            the asset type of the page. cannot be null
     * @throws IllegalArgumentException
     *             if assetType is null
     */
    public void setAssetType(ConfluenceAssetType assetType) {
        Helper.checkNull(assetType, "assetType");
        this.assetType = assetType;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the catalog of the page
     */
    public ConfluenceCatalog getCatalog() {
        return catalog;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param catalog
     *            the catalog of the page. cannot be null
     * @throws IllegalArgumentException
     *             if catalog is null
     */
    public void setCatalog(ConfluenceCatalog catalog) {
        Helper.checkNull(catalog, "catalog");
        this.catalog = catalog;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the application code of the page
     */
    public String getApplicationCode() {
        return applicationCode;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param applicationCode
     *            the application code of the page. cannot be null or empty
     * @throws IllegalArgumentException
     *             if applicationCode is null or empty
     */
    public void setApplicationCode(String applicationCode) {
        Helper.checkStringNullOrEmpty(applicationCode, "applicationCode");
        this.applicationCode = applicationCode;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the component type of the page
     */
    public ComponentType getComponentType() {
        return componentType;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param componentType
     *            the component type of the page. cannot be null
     * @throws IllegalArgumentException
     *             if componentType is null
     */
    public void setComponentType(ComponentType componentType) {
        Helper.checkNull(componentType, "componentType");
        this.componentType = componentType;
    }

    /**
     * <p>
     * Simple getter for corresponding field.
     * </p>
     *
     * @return the content of the page
     */
    public String getContent() {
        return content;
    }

    /**
     * <p>
     * Simple setter for corresponding field.
     * </p>
     *
     * @param content
     *            the content of the page. cannot be null, can be empty
     * @throws IllegalArgumentException
     *             if content is null or empty
     */
    public void setContent(String content) {
        Helper.checkNull(content, "content");
        this.content = content;
    }
}