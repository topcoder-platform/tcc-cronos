/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

/**
 * <p>
 * This class defines three key constants used to get the AuctionManager, GameDataManager and
 * AdministrationManager from the ServletContext attributes.
 * </p>
 * <p>
 * The constants are loaded in static initializer and can be configured. If error occurs during
 * loading preconfigured values the AuctionLogicConfigException will be thrown. Here is an example
 * of configuration file:
 *
 * <pre>
 * &lt;Config name=&quot;com.orheus.auction.KeyConstants&quot;&gt;
 * &nbsp;&nbsp;&lt;Property name=&quot;auction_manager&quot;&gt;
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;Value&gt;auction_manager&lt;/Value&gt;
 * &nbsp;&nbsp;&lt;/Property&gt;
 * &nbsp;&nbsp;&lt;Property name=&quot;game_data_manager&quot;&gt;
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;Value&gt;game_data_manager&lt;/Value&gt;
 * &nbsp;&nbsp;&lt;/Property&gt;
 * &nbsp;&nbsp;&lt;Property name=&quot;administration_manager&quot;&gt;
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;Value&gt;administration_manager&lt;/Value&gt;
 * &nbsp;&nbsp;&lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread safety: This class is immutable and thread-safe.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class KeyConstants {

    /**
     * <p>
     * The configuration namespace to load constants from.
     * </p>
     */
    private static final String NAMESPACE = KeyConstants.class.getName();

    /**
     * <p>
     * The key used to get AuctionManager from the SevletContext attribute. It's loaded from
     * configuration inplace and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    public static final String AUCTION_MANAGER_KEY = Helper.getString(NAMESPACE, "auction_manager");

    /**
     * <p>
     * The key used to get GameDataManager from the SevletContext attribute. It's loaded from
     * configuration inplace and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    public static final String GAME_DATA_MANAGER_KEY = Helper.getString(NAMESPACE,
            "game_data_manager");

    /**
     * <p>
     * The key used to get AdministrationManager from the SevletContext attribute. It's loaded from
     * configuration inplace and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    public static final String ADMINISTRATION_MANAGER_KEY = Helper.getString(NAMESPACE,
            "administration_manager");

    /**
     * <p>
     * This class couldn't be instantiated.
     * </p>
     */
    private KeyConstants() {
        // do nothing
    }
}
