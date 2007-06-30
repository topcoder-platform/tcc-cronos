/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import java.io.Serializable;

/**
 * <p>A simple structure providing the details for a particular browser plugin available for download. The purposes of
 * this class is just to display the information details for plugin, like, size, version, supported browser on a <code>
 * Plugin Download</code> page.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PluginInfo implements Serializable {

    /**
     * <p>A <code>int</code> corresponding to plugin for <code>Microsoft Internet Explorer</code> browser.</p>
     */
    public static int IE_PLUGIN = 1;

    /**
     * <p>A <code>int</code> corresponding to plugin for <code>FireForx</code> browser.</p>
     */
    public static int FIREFORX_PLUGIN = 2;

    /**
     * <p>A <code>String</code> providing the specification of size of plugin distribution.</p>
     */
    private String fileSize = null;

    /**
     * <p>A <code>String</code> providing the date of posting plugin for public access.</p>
     */
    private String datePosted = null;

    /**
     * <p>A <code>String</code> providing the plugin version.</p>
     */
    private String version = null;

    /**
     * <p>A <code>String</code> representing a language for plugin's user interface.</p>
     */
    private String language = null;

    /**
     * <p>A <code>String</code> providing the name of the browser supported by plugin.</p>
     */
    private String browser;

    /**
     * <p>Constructs new <code>PluginInfo</code> instance with specified parameters providing the plugin details to be
     * displayed to users.</p>
     *
     * @param fileSize a <code>String</code> providing the specification of size of plugin distribution.
     * @param datePosted a <code>String</code> providing the date of posting plugin for public access.
     * @param version a <code>String</code> providing the plugin version.
     * @param language a <code>String</code> representing a language for plugin's user interface.
     * @param browser a <code>String</code> referencing the target browser which the plugin is developed for.
     */
    public PluginInfo(String fileSize, String datePosted, String version, String language, String browser) {
        this.fileSize = fileSize;
        this.datePosted = datePosted;
        this.version = version;
        this.language = language;
        this.browser = browser;
    }

    /**
     * <p>Gets the specification of plugin size.</p>
     *
     * @return a <code>String</code> providing the specification of size of plugin distribution.
     */
    public String getFileSize() {
        return this.fileSize;
    }

    /**
     * <p>Gets the plugin posting date.</p>
     *
     * @return a <code>String</code> providing the date of posting plugin for public access.
     */
    public String getDatePosted() {
        return this.datePosted;
    }

    /**
     * <p>Gets the plugin verison.</p>
     *
     * @return a <code>String</code> providing the plugin version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * <p>Gets the language use for localizing plugin's user interface.</p>
     *
     * @return a <code>String</code> representing a language for plugin's user interface.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * <p>Gets the name of the browser supported by this plugin.</p>
     *
     * @return a <code>String</code> providing the name of the browser supported by plugin.
     */
    public String getBrowser() {
        return this.browser;
    }
}
