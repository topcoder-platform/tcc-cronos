/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.administration.persistence.OrpheusMessengerPlugin;
import com.orpheus.administration.persistence.RemoteOrpheusMessengerPlugin;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataLocal;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.administration.persistence.AdminDataLocalHome;
import com.orpheus.administration.persistence.impl.AdminMessage;
import com.orpheus.auction.KeyConstants;
import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.GameDataUtility;
import com.orpheus.game.GameDataManagerConfigurationException;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.OrpheusFunctions;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.server.util.AdminDataEJBAdapter;
import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.MessengerPlugin;
import com.topcoder.message.messenger.Messenger;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.generator.guid.UUIDUtility;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.web.sitestatistics.TextStatistics;
import com.topcoder.util.web.sitestatistics.SiteStatistics;
import com.topcoder.util.web.sitestatistics.StatisticsException;
import com.topcoder.util.net.httputility.HttpUtility;
import com.topcoder.util.net.httputility.HttpException;
import com.topcoder.util.algorithm.hash.HashException;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.randomstringimg.Configuration;
import com.topcoder.randomstringimg.ObfuscationAlgorithm;
import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.ObfuscationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * <p>An abstract base class for custom {@link Handler} implementations provided by <code>Orpheus Game Server</code>
 * module. Provides the useful functionality for parsing the configuration parameters from provided <code>XML
 * </code> element and the candidate names for various configuration parameters.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AbstractGameServerHandler {

    /**
     * <p>A <code>String</code> providing the ID of a template source to be passed to <code>Document Generator</code>
     * for locating the template. </p>
     */
    public static final String TEMPLATE_SOURCE_ID = "classpath";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the game ID from.</p>
     */
    protected static final String GAME_ID_PARAM_NAME_CONFIG = "game_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user profile ID from.</p>
     */
    protected static final String PROFILE_ID_PARAM_NAME_CONFIG = "profile_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the puzzle ID from.</p>
     */
    protected static final String PUZZLE_ID_PARAM_NAME_CONFIG = "puzzle_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the slot ID from.</p>
     */
    protected static final String SLOT_ID_PARAM_NAME_CONFIG = "slot_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain target sequence number from.</p>
     */
    protected static final String DOMAIN_TARGET_PARAM_NAME_CONFIG = "target_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain from.</p>
     */
    protected static final String DOMAIN_PARAM_NAME_CONFIG = "domain_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain target text from.</p>
     */
    protected static final String TEXT_PARAM_NAME_CONFIG = "text_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain target URL from.</p>
     */
    protected static final String URL_PARAM_NAME_CONFIG = "url_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the sequence number from.</p>
     */
    protected static final String SEQ_NUMBER_PARAM_NAME_CONFIG = "sequence_number_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * <code>JNDI</code> name to be used for locating the <code>Home</code> interface for <code>GameData</code> EJB.</p>
     */
    protected static final String GAME_EJB_JNDI_NAME_CONFIG = "game_data_jndi_name";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * <code>JNDI</code> name to be used for locating the <code>Home</code> interface for <code>AdminData</code> EJB.
     * </p>
     */
    protected static final String ADMIN_EJB_JNDI_NAME_CONFIG = "admin_data_jndi_name";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * flag indicating whether a remote interface should be used for accessing the EJBs or not.</p>
     */
    protected static final String USER_REMOTE_INTERFACE_CONFIG = "use_remote_interface";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * value of media type to render the puzzle for.</p>
     */
    protected static final String MEDIA_TYPE_VALUE_CONFIG = "media_type";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * configuration namespace for object factory.</p>
     */
    protected static final String OBJECT_FACTORY_NS_VALUE_CONFIG = "object_factory_ns";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * configuration namespace for random string image factory.</p>
     */
    protected static final String RANDOM_STRING_IMAGE_NS_VALUE_CONFIG = "random_string_image_ns";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * value of puzzle solution tester base name.</p>
     */
    protected static final String SOLUTION_TESTER_BASE_NAME_VALUE_CONFIG = "solutiontester_base_name";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name for <code>JNDI</code> context to be used for obtaining the <code>JNDI</code> context from <code>JNDI Utility
     * </code>.</p>
     */
    protected static final String JNDI_CONTEXT_NAME_CONFIG = "jndi_context_name";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of games.</p>
     */
    protected static final String GAMES_ATTR_NAME_CONFIG = "games_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the hosting slot.</p>
     */
    protected static final String HOSTING_SLOT_ATTR_NAME_CONFIG = "slot_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the puzzle type source.</p>
     */
    protected static final String PUZZLE_TYPE_SOURCE_ATTR_NAME_CONFIG = "puzzle_type_source_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of user profiles.</p>
     */
    protected static final String PROFILES_ATTR_NAME_CONFIG = "profiles_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of details for slot sponsors.</p>
     */
    protected static final String SLOT_SPONSORS_ATTR_NAME_CONFIG = "slot_sponsors_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of slot completions.</p>
     */
    protected static final String SLOT_COMPLETIONS_ATTR_NAME_CONFIG = "slot_completions_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of pending winners.</p>
     */
    protected static final String PENDING_WINNERS_ATTR_NAME_CONFIG = "pending_winners_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of ball colors.</p>
     */
    protected static final String BALL_COLORS_ATTR_NAME_CONFIG = "ball_colors_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for a single game.</p>
     */
    protected static final String GAME_DETIALS_ATTR_NAME_CONFIG = "game_detail_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for a user profile.</p>
     */
    protected static final String PROFILE_ATTR_NAME_CONFIG = "profile_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of details for slots completed by player.</p>
     */
    protected static final String PLAYER_COMPLETED_SLOTS_ATTR_NAME_CONFIG = "player_completed_slots_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of details for domains.</p>
     */
    protected static final String DOMAINS_ATTR_NAME_CONFIG = "domains_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the game play history.</p>
     */
    protected static final String GAME_PLAY_ATTR_NAME_CONFIG = "game_play_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of details for unlocked domains.</p>
     */
    protected static final String UNLOCKED_DOMAINS_DETAILS_ATTR_NAME_CONFIG = "unlocked_domains_details_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the puzzle media type.</p>
     */
    protected static final String MEDIA_TYPE_ATTR_NAME_CONFIG = "media_type_request_attribute_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the puzzle ID.</p>
     */
    protected static final String PUZZLE_ID_ATTR_NAME_CONFIG = "puzzle_id_request_attribute_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * list of recipients for the email to be sent.</p>
     */
    protected static final String EMAIL_RECIPIENTS_CONFIG = "email_recipients";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * subject for the email to be sent.</p>
     */
    protected static final String EMAIL_SUBJECT_CONFIG = "email_subject";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * puzzle name (e.g., "Sliding Tile Puzzle", "Jigsaw Puzzle").</p>
     */
    protected static final String PUZZLE_NAME = "puzzle_name";
    
    /**
     * <p>A <code>String</code> providing the puzzle name as stored in the "puzzle" table of the database.)</p>
     */
    protected static final String PUZZLE_DB_NAME = "puzzle_db_name";
    
    /**
     * <p>A <code>String</code> providing the url-pattern suffix of a handler XML file. (Note: This can be removed if the 
     * url-pattern can be obtained in another fashion.)</p>
     */
    protected static final String URL_PATTERN_SUFFIX = "url_pattern_suffix";
    
    /**
     * <p>A <code>Map</code> mapping the <code>String</code> name of a configuration parameter to respective <code>
     * Object</code> providing the value for configuration parameter.</p>
     */
    private final Map config;

    /**
     * <p>Constructs new <code>AbstractGameServerHandler</code> instance. The list of cofniguration parameters is
     * initially empty and may be populated by calling to various <code>readAs</code> methods provided by this class.
     * </p>
     */
    protected AbstractGameServerHandler() {
        this.config = new HashMap();
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>String</code> and saves it for further use.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsString(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        if (value != null) {
            this.config.put(parameterName, value);
        }
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>Long</code> and saves it for further user.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsLong(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        if (value != null) {
            this.config.put(parameterName, new Long(value));
        }
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>Integer</code> and saves it for further use.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsInteger(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        if (value != null) {
            this.config.put(parameterName, new Integer(value));
        }
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>Float</code> and saves it for further use.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsFloat(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        if (value != null) {
            this.config.put(parameterName, new Float(value));
        }
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>Boolean</code> and saves it for further use.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsBoolean(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        if (value != null) {
            this.config.put(parameterName, Boolean.valueOf(value));
        }
    }

    /**
     * <p>Gets the value of specified configuration parameter as string.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>String</code> providing the value of specified configuration parameter or <code>null</code> if
     *         such parameter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>String
     *         </code> type.
     */
    protected final String getString(String parameterName) {
        return (String) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified configuration parameter as <code>Long</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>Long</code> providing the value of specified configuration parameter or <code>null</code> if such
     *         parammeter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>Long</code>
     *         type.
     */
    protected final Long getLong(String parameterName) {
        return (Long) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified configuration parameter as <code>Integer</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>Integer</code> providing the value of specified configuration parameter or <code>null</code> if
     *         such parammeter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>Integer
     *         </code> type.
     */
    protected final Integer getInteger(String parameterName) {
        return (Integer) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified configuration parameter as <code>Float</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>Float</code> providing the value of specified configuration parameter or <code>null</code> if
     *         such parammeter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>Float
     *         </code> type.
     */
    protected final Float getFloat(String parameterName) {
        return (Float) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified configuration parameter as <code>Boolean</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>Boolean</code> providing the value of specified configuration parameter or <code>null</code> if
     *         such parammeter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>Boolean
     *         </code> type.
     */
    protected final Boolean getBoolean(String parameterName) {
        return (Boolean) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified request parameter as <code>Long</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the configuration property referencing the name
     *        of requested request parameter.
     * @param request a <code>HttpServletRequest</code> representing the incoming request from the client. 
     * @return a <code>lLong</code> providing the value of specified request parameter.
     * @throws HandlerExecutionException if value of specified request parameter is not compatible with <code>Long
     *         </code> type.
     */
    protected final long getLong(String parameterName, HttpServletRequest request) throws HandlerExecutionException {
        String value = request.getParameter(getString(parameterName));
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Parameter [" + parameterName + "] does not provide a long value ["
                                                + value + "]", e);
        }
    }

    /**
     * <p>Gets the value of specified request parameter as <code>Integer</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the configuration property referencing the name
     *        of requested request parameter.
     * @param request a <code>HttpServletRequest</code> representing the incoming request from the client.
     * @return a <code>int</code> providing the value of specified request parameter.
     * @throws HandlerExecutionException if value of specified request parameter is not compatible with <code>Integer
     *         </code> type.
     */
    protected final int getInteger(String parameterName, HttpServletRequest request) throws HandlerExecutionException {
        String value = request.getParameter(getString(parameterName));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Parameter [" + parameterName + "] does not provide an int value ["
                                                + value + "]", e);
        }
    }
    
    /**
     * <p>Gets the value of specified request parameter as <code>Boolean</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the configuration property referencing the name
     *        of requested request parameter.
     * @param request a <code>HttpServletRequest</code> representing the incoming request from the client.
     * @return a <code>boolean</code> providing the value of specified request parameter.
     * @throws HandlerExecutionException if value of specified request parameter is not compatible with <code>Boolean
     *         </code> type.
     */
    protected final boolean getBoolean(String parameterName, HttpServletRequest request) throws HandlerExecutionException {
        String value = request.getParameter(getString(parameterName));
        return "true".equals(value);
    }

    /**
     * <p>Gets the text value of the specified node (key) from the given element.</p>
     *
     * @param element an <code>Element</code> providing the element to be used.
     * @param key a <code>String</code> providing the tag name for element to be extracted.
     * @param required <code>true</code> if specified configuration parameter is required; <code>false</code> otherwise.
     * @return a <code>String</code> providing the text value of the given tag or <code>null</code> if given tag is
     *         missing and it is not required.
     * @throws IllegalArgumentException if the given <code>key</code> is not present or has empty value.
     */
    protected static String getElement(Element element, String key, boolean required) {
        NodeList nodeList;
        nodeList = element.getElementsByTagName(key);
        if (required && (nodeList.getLength() == 0)) {
            throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
        }
        if (nodeList.getLength() > 1) {
            throw new IllegalArgumentException("Key '" + key + "' is occurring more than once in the element");
        }
        if (!required && nodeList.getLength() == 0) {
            return null;
        }
        Node node = nodeList.item(0).getFirstChild();
        if (required && (node == null)) {
            throw new IllegalArgumentException("The [" + key + "] element is not found");
        }
        String value = node.getNodeValue();
        if (required && ((value == null) || (value.trim().length() == 0))) {
            throw new IllegalArgumentException("The [" + key + "] element is empty");
        }
        return value;
    }

    /**
     * <p>Obtains a <code>JNDI</code> context initialized based on configuration parameters provided by the specified
     * element.</p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @return a <code>Context</code> representing a <code>JNDI</code> context to be used for locating the desired
     *         resources.
     * @throws GameDataConfigurationException if context could not be instantiated.
     */
    protected Context getJNDIContext(Element element) {
        // Instantiate JNDI context
        String jndiContextName = getElement(element, JNDI_CONTEXT_NAME_CONFIG, true);
        try {
            return JNDIUtils.getContext(jndiContextName);
        } catch (NamingException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        } catch (ConfigManagerException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        }
    }

    /**
     * <p>Obtains a <code>JNDI</code> context initialized based on configuration parameters provided by the specified
     * element.</p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @return a <code>Context</code> representing a <code>JNDI</code> context to be used for locating the desired
     *         resources.
     * @throws GameDataConfigurationException if context could not be instantiated.
     */
    protected UserProfileManager getUserProfileManager(Element element) {
        try {
            String objectFactoryNs = getElement(element, OBJECT_FACTORY_NS_VALUE_CONFIG, true);
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(objectFactoryNs),
                                                      ObjectFactory.BOTH);
            return (UserProfileManager) factory.createObject(UserProfileManager.class);
        } catch (Exception e) {
            throw new GameDataConfigurationException("Could not instantiate User Profile Manager", e);
        }
    }

    /**
     * <p>Gets the reference to <code>Game Data EJB</code> based on current configuration settings.</p>
     *
     * @param jndiContext a <code>Context</code> representing a <code>JNDI</code> context 
     * @return an <code>Object</code> providing either remote or local interface to <code>Game Data EJB</code>. 
     * @throws RemoteException if an error is reported while creating a remote <code>EJB</code> instance. 
     * @throws CreateException if an error is reported while creating an <code>EJB</code> instance.
     * @throws NamingException if an error is reported while accessing the <code>JNDI</code> service. 
     */
    protected final Object getGameDataEJB(Context jndiContext) throws RemoteException, CreateException,
                                                                      NamingException {
        String gameDataJndiName = getString(GAME_EJB_JNDI_NAME_CONFIG);
        if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
            GameDataHome gameDataHome = (GameDataHome) JNDIUtils.getObject(jndiContext, gameDataJndiName,
                                                                           GameDataHome.class);
            return gameDataHome.create();
        } else {
            GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) JNDIUtils.getObject(jndiContext,
                                                                                          gameDataJndiName,
                                                                                          GameDataLocalHome.class);
            return gameDataLocalHome.create();
        }
    }

    /**
     * <p>Gets the reference to <code>Admin Data EJB</code> based on current configuration settings.</p>
     *
     * @param jndiContext a <code>Context</code> representing a <code>JNDI</code> context
     * @return an <code>Object</code> providing either remote or local interface to <code>Admin Data EJB</code>.
     * @throws RemoteException if an error is reported while creating a remote <code>EJB</code> instance.
     * @throws CreateException if an error is reported while creating an <code>EJB</code> instance.
     * @throws NamingException if an error is reported while accessing the <code>JNDI</code> service.
     */
    protected final Object getAdminDataEJB(Context jndiContext) throws RemoteException, CreateException,
                                                                       NamingException {
        String gameDataJndiName = getString(ADMIN_EJB_JNDI_NAME_CONFIG);
        if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
            AdminDataHome adminDataHome = (AdminDataHome) JNDIUtils.getObject(jndiContext, gameDataJndiName,
                                                                              AdminDataHome.class);
            return adminDataHome.create();
        } else {
            AdminDataLocalHome adminDataLocalHome = (AdminDataLocalHome) JNDIUtils.getObject(jndiContext,
                                                                                             gameDataJndiName,
                                                                                             AdminDataLocalHome.class);
            return adminDataLocalHome.create();
        }
    }

    /**
     * <p>Gets the ID of an authenticated user associated with an incoming request.</p>
     *
     * @param context an <code>ActionContext</code> providing the details for context surrounding the incoming request. 
     * @return a <code>long</code> providing the ID of authenticated user.
     * @throws HandlerExecutionException if user is not logged to server currently.
     */
    protected long getUserId(ActionContext context) throws HandlerExecutionException {
        HttpSession session = context.getRequest().getSession(false);
        if (session != null) {
            UserProfile authenticatedUser = LoginHandler.getAuthenticatedUser(session);
            if (authenticatedUser != null) {
                return ((Long) authenticatedUser.getIdentifier()).longValue();
            }
        }
        throw new HandlerExecutionException("The user is not logged to Game Server");
    }

    /**
     * <p>Gets the value of specified attribute from the session associated with incoming request.</p>
     *
     * @param session an <code>HttpSession</code> providing the details for session associated with incoming request.
     * @param attrName a <code>String</code> providing the name of the session attribute to get value for.
     * @return an <code>Object</code> providing the value of specified session attribute.
     * @throws HandlerExecutionException if user is not logged to server currently.
     */
    protected Object getSessionAttr(HttpSession session, String attrName) throws HandlerExecutionException {
        if (session != null) {
            return session.getAttribute(attrName);
        }
        throw new HandlerExecutionException("The user is not logged to Game Server");
    }

    /**
     * <p>Gets the game play statistics info for a player associated with incoming request.</p>
     *
     * @param context an <code>ActionContext</code> providing the details for context surrounding the incoming request.
     * @return a <code>GamePlayInfo</code> providing the game play history for current user associated with incoming
     *         request. 
     * @throws HandlerExecutionException if user is not logged to server currently.
     */
    protected GamePlayInfo getGamePlayInfo(ActionContext context) throws HandlerExecutionException {
        String gamePlayAttrName = getString(GAME_PLAY_ATTR_NAME_CONFIG);
        HttpSession session = context.getRequest().getSession(false);
        if (session != null) {
            synchronized (session) {
                GamePlayInfo gamePlayInfo = (GamePlayInfo) session.getAttribute(gamePlayAttrName);
                if (gamePlayInfo == null) {
                    gamePlayInfo = new GamePlayInfo();
                    session.setAttribute(gamePlayAttrName, gamePlayInfo);
                }
                return gamePlayInfo;
            }
        } else {
            throw new HandlerExecutionException("The user is not logged to Game Server");
        }
    }

    /**
     * <p>Gets the brainteaser update interval specifying the rate at which the players may issue a request for updating
     * displayed brainteaser.</p>
     *
     * @return a <code>long</code> providing the brainteaser update interval, in milliseconds.
     */
    protected final long getBrainteaserUpdateInterval() {
        return OrpheusFunctions.getBrainteaserUpdatePeriod() * 1000;
    }

    /**
     * <p>Gets an adapter for <code>Game Data EJB</code> which is initialized based on the configuation settings.</p>
     *
     * @param jndiContext a <code>Context</code> representing a <code>JNDI</code> context.
     * @return a <code>GameDataEJBAdapter</code> providing either remote or local interface to <code>Game Data EJB
     *         </code>.
     * @throws RemoteException if an error is reported while creating a remote <code>EJB</code> instance.
     * @throws CreateException if an error is reported while creating an <code>EJB</code> instance.
     * @throws NamingException if an error is reported while accessing the <code>JNDI</code> service.
     */
    protected GameDataEJBAdapter getGameDataEJBAdapter(Context jndiContext) throws NamingException, RemoteException,
                                                                                   CreateException {
        Object gameDataEJB = getGameDataEJB(jndiContext);
        if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
            return new GameDataEJBAdapter((GameData) gameDataEJB);
        } else {
            return new GameDataEJBAdapter((GameDataLocal) gameDataEJB);
        }
    }

    /**
     * <p>Gets an adapter for <code>Admin Data EJB</code> which is initialized based on the configuation settings.</p>
     *
     * @param jndiContext a <code>Context</code> representing a <code>JNDI</code> context.
     * @return a <code>AdminDataEJBAdapter</code> providing either remote or local interface to <code>Admin Data EJB
     *         </code>.
     * @throws RemoteException if an error is reported while creating a remote <code>EJB</code> instance.
     * @throws CreateException if an error is reported while creating an <code>EJB</code> instance.
     * @throws NamingException if an error is reported while accessing the <code>JNDI</code> service.
     */
    protected AdminDataEJBAdapter getAdminDataEJBAdapter(Context jndiContext) throws NamingException, RemoteException,
                                                                                     CreateException {
        Object adminDataEJB = getAdminDataEJB(jndiContext);
        if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
            return new AdminDataEJBAdapter((AdminData) adminDataEJB);
        } else {
            return new AdminDataEJBAdapter((AdminDataLocal) adminDataEJB);
        }
    }

    /**
     * <p>Gets the game manager.</p>
     *
     * @param context an <code>ActionContext</code> providing the details for context surrounding the incoming request.
     * @return a <code>GameDataManager</code> to be used for manipulating with games.
     */
    protected GameDataManager getGameDataManager(ActionContext context) {
        HttpSession session = context.getRequest().getSession(false);
        if (session != null) {
            return (GameDataManager) session.getServletContext().getAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);
        } else {
            throw new IllegalArgumentException("There is no session associated with incoming request");
        }
    }

    /**
     * <p>Gets the first upcoming domain for the specified game and player. Such a domain is determined as a domain for
     * the first slot which has started hosting but hasn't been completed by player yet.</p>
     * 
     * @param gameId a <code>long</code> providing the ID of a game.
     * @param playerId a <code>long</code> providing the ID of a current player.
     * @param gameDataEJBAdapter a <code>GameDataEJBAdapter</code> to be used to access <code>Game Data EJB</code>.
     * @param completedSlot a <code>HostingSlot</code> providing the details for current slot just completed by player.
     * @return a <code>Domain</code> representing the upcoming domain for specified game or <code>null</code> if no such
     *         domain is available. 
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException if there is any problem in the persistence layer.
     */
    protected Domain getUpcomingDomain(long gameId, long playerId, GameDataEJBAdapter gameDataEJBAdapter,
                                       HostingSlot completedSlot)
        throws RemoteException, PersistenceException {
        // Holds a reference to hosting block which the completed block belongs to
        HostingBlock currentBlock = null;
        // Load game details and find the first slot which has started but not finished hosting yet
        Game game = gameDataEJBAdapter.getGame(gameId);
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            HostingSlot[] slots = blocks[i].getSlots();
            for (int j = 0; j < slots.length; j++) {
                if (slots[j].getId().equals(completedSlot.getId())) {
                    currentBlock = blocks[i];
                } else if (currentBlock != null) {
                    if ((slots[j].getHostingStart() != null)) {
                        // Check if slot hasn't been completed by player yet
                        SlotCompletion[] slotCompletions
                            = gameDataEJBAdapter.findSlotCompletions(gameId, slots[j].getId().longValue());
                        boolean slotCompletedByPlayer = false;
                        for (int k = 0; !slotCompletedByPlayer && (k < slotCompletions.length); k++) {
                            if (slotCompletions[k].getPlayerId() == playerId) {
                                slotCompletedByPlayer = true;
                            }
                        }
                        // If the slot is not already completed by the player then this is the upcoming slot which is
                        // placed after the completed slot in the sequence. So, return it
                        if (!slotCompletedByPlayer) {
                            return slots[j].getDomain();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * <p>Gets the domain for the slot which the specified slot immediately follows up in hosting sequence.</p>
     *
     * @param game a <code>Game</code> providing the details for requested game.
     * @param slot a <code>HostingSlot</code> providing the details for slot to get the preceeding domain for. 
     * @return a <code>Domain</code> representing the preceeding domain for specified slot or <code>null</code> if
     *         such domain is available.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    protected Domain getPreceedingDomain(Game game, HostingSlot slot) throws RemoteException, PersistenceException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (slot == null) {
            throw new IllegalArgumentException("The parameter [slot] is NULL");
        }
        Domain prevDomain = null;
        HostingBlock[] blocks = game.getBlocks();
        for (int bidx = 0; bidx < blocks.length; bidx++) {
            HostingSlot[] slots = blocks[bidx].getSlots();
            for (int sidx = 0; sidx < slots.length; sidx++) {
                if (slots[sidx].getId().longValue() == slot.getId().longValue()) {
                    return prevDomain;
                } else {
                    prevDomain = slots[sidx].getDomain(); 
                }
            }
        }
        return null;
    }


    /**
     * <p>Broadcasts the specified message to all players registered to a game via their plug-ins' messaging channel.
     * </p>
     *
     * @param game a <code>Game</code> representing the game to broadcast related message for.
     * @param messageText a <code>String</code> providing the text of the message to broadcast.
     * @throws GameDataException if an unexpected error occurs.
     */
    protected void broadcastGameMessage(Game game, String messageText) throws GameDataException {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        try {
            OrpheusMessengerPlugin plugin
                = new RemoteOrpheusMessengerPlugin(OrpheusFunctions.getMessengerPluginNamespace());
            MessageAPI message = plugin.createMessage();
            message.setParameterValue(AdminMessage.GUID, UUIDUtility.getNextUUID(UUIDType.TYPE1).toString());
            message.setParameterValue(AdminMessage.CATEGORY, game.getName());
            message.setParameterValue(AdminMessage.CONTENT_TYPE, "text");
            message.setParameterValue(AdminMessage.CONTENT, messageText);
            message.setParameterValue(AdminMessage.TIMESTAMP, new Date());
            plugin.sendMessage(message);
        } catch (Exception e) {
            throw new GameDataException("An exception occurred while broadcasting a Bloom Filter update", e);
        }
    }

    /**
     * <p>Gets the servlet context.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return a <code>ServletContext</code> associated with current environment.
     * @throws IllegalStateException if there is no active session associated with specified request.
     */
    protected ServletContext getServletContext(ActionContext context) {
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getServletContext();
        } else {
            throw new IllegalStateException("There is no active session");
        }
    }

    /**
     * <p>Sends an email to specified address with specified message body and subject. Any exception which might occur
     * while sending an email is caught and logged.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param to a <code>String</code> providing the email address of recipient.
     * @param body a <code>String</code> providing the email message body.
     * @param subject a <code>String</code> providing the email message subject.
     */
    protected void sendEmail(ActionContext context, String to, String body, String subject) {
        System.out.println("ISV : Sending email : TO = " + to + ", SUBJECT = " + subject);
        try {
            String pluginName = (String) context.getAttribute("OrpheusMessagePlugin");
            MessengerPlugin plugin = Messenger.createInstance().getPlugin(pluginName);
            MessageAPI msg = plugin.createMessage();
            msg.setParameterValue("from", (String) context.getAttribute("from"));
            msg.setParameterValue("to", new String[] {to});
            msg.setParameterValue("body", body);
            msg.setParameterValue("subject", subject);
            plugin.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Checks whether target text exists in the provided statistics information.</p>
     *
     * @param textStatistics text statistics collected from some page of a hosting site. Used to check for the presence
     *        of the target text.
     * @param targetText target text to check its presence in the provided statistics information.
     * @return <code>true</code> if target text has been found in the provided statistics information, <code>false
     *        </code> if it has not.
     */
    protected static boolean checkForTextExistence(TextStatistics[] textStatistics, String targetText) {
        for (int i = 0; i < textStatistics.length; ++i)
            if (textStatistics[i].getText().equals(targetText))
                return true;

        return false;
    }

    /**
     * <p>Checks if specified target text exists on specified page and the specified page is accessible.</p>
     *
     * @param targetText a <code>String</code> providing the target text to check.
     * @param targetUrl a <code>String</code> providing the URL for the page to check.
     * @return a <code>Boolean</code> indicating whether target exists on specified page or <code>null</code> if the
     *         content of the specified page could not be retrieved and the status of the target is not known.
     */
    protected static Boolean isTargetValid(String targetText, String targetUrl) {
        HttpUtility http = new HttpUtility(HttpUtility.GET);
        http.setFollowRedirects(true);
        http.setDepthLimit(10);
        SiteStatistics siteStatistics;
        try {
            String contents = http.execute(targetUrl);
            siteStatistics = GameDataUtility.getConfiguredSiteStatisticsInstance("SiteStatistics");
            siteStatistics.accumulateFrom(contents, "document1");
        } catch (IOException ioe) {
            System.err.println("IOException while trying to check address: " + targetUrl);
            System.err.println("Message says: " + ioe.getMessage());
            return null;
        } catch (HttpException httpe) {
            System.err.println("HttpException while trying to check address: " + targetUrl);
            System.err.println("Message says: " + httpe.getMessage());
            return null;
        } catch (GameDataManagerConfigurationException gdmce) {
            System.err.println("Unable to obtain SiteStatistics object. Exception information follows.");
            gdmce.printStackTrace(System.err);
            return null;
        } catch (StatisticsException se) {
            System.err.println("Unable to accumulate statistics information from document located at: " + targetUrl);
            se.printStackTrace(System.err);
            return null;
        }
        // Check if target exists on a page
        TextStatistics[] stats = siteStatistics.getElementContentStatistics();
        if (!checkForTextExistence(stats, targetText)) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * <p>Gets the <code>SHA-1</code> hash value for specified text.</p>
     *
     * @param text a <code>String</code> providing the text to get hash value for.
     * @return a <code>String</code> providing the hash code for the specified text.
     * @throws com.topcoder.util.algorithm.hash.ConfigurationException if a configuration error occurs.
     * @throws HashException if a hash value can not be evaluated.
     */
    protected static String getHash(String text)
        throws com.topcoder.util.algorithm.hash.ConfigurationException, HashException {
        HashAlgorithmManager hashAlgManager = HashAlgorithmManager.getInstance();
        HashAlgorithm hasher = hashAlgManager.getAlgorithm("SHA-1");
        return hasher.hashToHexString(text.replaceAll("[\n\r \t\f\u00a0\u200b]+", ""), "UTF-8");
    }

    /**
     * 
     * @param text
     * @return
     */
    protected static String normalize(String text) {
        String newText = text.replaceAll("[ \t\f\r\n\u200b]+", " ");
        newText = newText.replaceFirst("^ +", "");
        newText = newText.replaceFirst(" +$", "");
        newText = newText.toLowerCase();
        return newText;
    }

    /**
     * <p>Generates the clue image for the specified target, persists it to persistent data store and returns the ID of
     * just generated image.</p>
     *
     * @param target a <code>DomainTarget</code> providing the details for the target to generate the clue image for. 
     * @param gameDataEJBAdapter a <code>GameDataEJBAdapter</code> to be used to access <code>Game Data EJB</code>.
     * @return a <code>long</code> providing the ID of a generated clue image.
     * @throws HandlerExecutionException if an unexpected error occurs.
     * @throws InvalidConfigException if an unexpected error occurs.
     * @throws IOException if an unexpected error occurs.
     * @throws ObfuscationException if an unexpected error occurs.
     * @throws PersistenceException if an unexpected error occurs.
     */
    protected long generateClueImage(DomainTarget target, GameDataEJBAdapter gameDataEJBAdapter)
        throws HandlerExecutionException, InvalidConfigException, IOException, ObfuscationException, PersistenceException {
        RandomStringImage stringImage = getRandomStringImage(getString(RANDOM_STRING_IMAGE_NS_VALUE_CONFIG));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stringImage.generate(target.getIdentifierText(), stream);
        return gameDataEJBAdapter.recordBinaryObject("clue_image.png", "image/png", stream.toByteArray());
    }

    /**
     * <p>Gets the <code>RandomStringImage</code> implementation which could be utilized for image generation.</p>
     *
     * @param namespace a <code>String</code> providing the name of configuration file to initialize the <code>Random
     *        String Image</code> component.
     * @return a <code>RandomStringImage</code> to be used for generating images.
     * @throws HandlerExecutionException if an unexpected error occurs.
     */
    private RandomStringImage getRandomStringImage(String namespace) throws HandlerExecutionException {
        try {
            RandomStringImage randomStringImage = new RandomStringImage(namespace);
            Configuration config = randomStringImage.getConfiguration();
            config.clearAlgorithms();
            config.addAlgorithm(new ObfuscationAlgorithm() {
                    public int getType() {
                        return ObfuscationAlgorithm.AFTER_TEXT;
                    }

                    public void obfuscate(BufferedImage image, Color textColor,
                        Color backgroundColor) {
                        /* does nothing */
                    }
                });
            return randomStringImage;
        } catch (Exception ice) {
            throw new HandlerExecutionException("Could not obtain a RandomStringImage instance", ice);
        }
    }
}
