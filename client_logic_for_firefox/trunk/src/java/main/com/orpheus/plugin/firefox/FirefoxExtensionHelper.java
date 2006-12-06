/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import java.applet.Applet;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.orpheus.plugin.firefox.eventlisteners.JavascriptUIEventListener;
import com.topcoder.bloom.BloomFilter;
import com.topcoder.bloom.BloomFilterException;
import com.topcoder.util.algorithm.hash.HashException;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.algorithm.hash.algorithm.SHAAlgorithm;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.rssgenerator.RSSFeed;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Content;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Item;


/**
 * <p>
 * This class is the main class in the component. This class implements both the Observer pattern for event handling,
 * as well as the delegation pattern for delegating functionality to multiple different classes. We use the delegation
 * pattern to allow this class to encompass the API that needs to be used by the firefox extension in a single class,
 * allowing access to the helper methods to be much easier.
 * </p>
 *
 * <p>
 * This class provides methods for handling events, setting persistent values, polling the server and popping up
 * windows. This class also provides functionality for testing a given object to see if it matches the current target
 * ID hash string and handling that particular event appropriately. When popping up windows in response to events, all
 * windows should be opened with minimal adornments of toolbars, all the boolean parameter values should be false.
 * </p>
 *
 * <p>
 * This class extends the <code>Applet</code> class so it can be embedded on a page via XUL.
 * </p>
 *
 * <p>
 * Config manager is used here to load some pre-defined config values as some http query parameter names. Here is
 * the sample config file:<br>
 * &lt;CMConfig&gt;<br>
 * &lt;Config name=&quot;com.orpheus.plugin.firefox.FirefoxExtensionHelper&quot;&gt;<br>
 * &lt;!-- The query parameter name used when testing whether or not a domain is host to any games. Required --&gt;<br>
 * &lt;Property name=&quot;domain_parameter_name&quot;&gt;<br>
 * &lt;Value&gt;domainParameter&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The URL to the Orpheus server, used to initialize the &quot;server&quot; member variable. Required --&gt;<br>
 * &lt;Property name=&quot;orpheus_url&quot;&gt;<br>
 * &lt;Value&gt;http://localhost:8080/Client_Logic_for_Firefox/pollServer.html&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- An integer value indicating how many minutes to wait between each server poll. Required --&gt;<br>
 * &lt;Property name=&quot;orpheus_poll_time&quot;&gt;<br>
 * &lt;Value&gt;1&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- A string parameter name for passing the timestamp along with poll requests. Required --&gt;<br>
 * &lt;Property name=&quot;orpheus_timestamp_parameter&quot;&gt;<br>
 * &lt;Value&gt;timestampParameter&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- A class name, like &quot;com.orpheus.plugin.firefox.persistence.CookieExtensionPersistence&quot;, used to
 * initialize the persistence member variable. Required --&gt;<br>
 * &lt;Property name=&quot;persistence_class&quot;&gt;<br>
 * &lt;Value&gt;com.orpheus.plugin.firefox.persistence.CookieExtensionPersistence&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The URL to load when a given object matches the current target. Required --&gt;<br>
 * &lt;Property name=&quot;hash_match_URL&quot;&gt;<br>
 * &lt;Value&gt;http://localhost:8080/Client_Logic_for_Firefox/hashMatch.html&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The query string parameter name to use when passing the domain name to the hash_match_URL.
 * Required --&gt;<br>
 * &lt;Property name=&quot;hash_match_domain_parameter&quot;&gt;<br>
 * &lt;Value&gt;hashMatchDomainParameter&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The query string parameter name to use when passing the sequence number to the hash_match_URL.
 * Required --&gt;<br>
 * &lt;Property name=&quot;hash_match_sequence_number_parameter&quot;&gt;<br>
 * &lt;Value&gt;hashMatchSequenceNumberParameter&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The query string parameter name to use when passing the current target string to the hash_match_URL.
 * Required --&gt;<br>
 * &lt;Property name=&quot;target_text_parameter&quot;&gt;<br>
 * &lt;Value&gt;targetTextParameter&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The default height of pop-up windows. Optional --&gt;<br>
 * &lt;Property name=&quot;default_height&quot;&gt;<br>
 * &lt;Value&gt;300&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The default width of pop-up windows. Optional --&gt;<br>
 * &lt;Property name=&quot;default_width&quot;&gt;<br>
 * &lt;Value&gt;400&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- The base of the URL to Any valid URL request when the user requests a new page in a domain that
 * participates in theOrpheus application. Required --&gt;<br>
 * &lt;Property name=&quot;page_changed_URL&quot;&gt;<br>
 * &lt;Value&gt;pageChanged.html&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;!-- A list of comma-delimited event / page pairs, used to link event types with pages to load, should be
 * specified like &quot;LOGIN_CLICK, http://www.google.com&quot;. Required --&gt;<br>
 * &lt;Property name=&quot;event_pages&quot;&gt;<br>
 * &lt;Value&gt;successful login,successfulLogin.html&lt;/Value&gt;<br>
 * &lt;Value&gt;show active games,showActiveGames.html&lt;/Value&gt;<br>
 * &lt;Value&gt;show my games,showMyGames.html&lt;/Value&gt;<br>
 * &lt;Value&gt;show unlocked domains,showUnlockedDomains.html&lt;/Value&gt;<br>
 * &lt;Value&gt;show upcoming games,showUpcomingGames.html&lt;/Value&gt;<br>
 * &lt;Value&gt;show leaders,showLeaders.html&lt;/Value&gt;<br>
 * &lt;Value&gt;show latest clue,showLatestClue.html&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Config&gt;<br>
 * &lt;/CMConfig&gt;<br>
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable, as its internal state can change in a number of ways, including in
 * response to polls made to the Orpheus server.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionHelper extends Applet {
    /**
     * <p>
     * Represents the default namespace for this class to load the config values.
     * </p>
     */
    private static final String DEFAULT_NAMESPACE = "com.orpheus.plugin.firefox.FirefoxExtensionHelper";

    /**
     * <p>
     * Represents the property name to retrieve the domain_parameter_name value.
     * </p>
     */
    private static final String DOMAIN_PARAMETER_NAME_PROPERTY = "domain_parameter_name";

    /**
     * <p>
     * Represents the property name to retrieve the hash_match_URL value.
     * </p>
     */
    private static final String HASH_MATCH_URL_PROPERTY = "hash_match_URL";

    /**
     * <p>
     * Represents the property name to retrieve the hash_match_domain_parameter value.
     * </p>
     */
    private static final String HASH_MATCH_DOMAIN_PARAMETER_PROPERTY = "hash_match_domain_parameter";

    /**
     * <p>
     * Represents the property name to retrieve the hash_match_sequence_number_parameter value.
     * </p>
     */
    private static final String HASH_MATCH_SEQUENCE_NUMBER_PARAMETER_PROPERTY = "hash_match_sequence_number_parameter";

    /**
     * <p>
     * Represents the property name to retrieve the target_text_parameter value.
     * </p>
     */
    private static final String TARGET_TEXT_PARAMETER_PROPERTY = "target_text_parameter";

    /**
     * <p>
     * Represents the property name to retrieve the default_height value.
     * </p>
     */
    private static final String DEFAULT_HEIGHT_PROPERTY = "default_height";

    /**
     * <p>
     * Represents the property name to retrieve the default_width value.
     * </p>
     */
    private static final String DEFAULT_WIDTH_PROPERTY = "default_width";

    /**
     * <p>
     * Represents the property name to retrieve the page_changed_URL value.
     * </p>
     */
    private static final String PAGE_CHANGED_URL_PROPERTY = "page_changed_URL";

    /**
     * <p>
     * Represents the property name to retrieve the persistence_class value.
     * </p>
     */
    private static final String PERSISTENCE_CLASS_PROPERTY = "persistence_class";

    /**
     * <p>
     * Represents the property name to retrieve the orpheus_url value.
     * </p>
     */
    private static final String ORPHEUS_URL_PROPERTY = "orpheus_url";

    /**
     * <p>
     * Represents the property name to retrieve the orpheus_poll_time value.
     * </p>
     */
    private static final String ORPHEUS_POLL_TIME_PROPERTY = "orpheus_poll_time";

    /**
     * <p>
     * Represents the property name to retrieve the orpheus_timestamp_parameter value.
     * </p>
     */
    private static final String ORPHEUS_TIMESTAMP_PARAMETER_PROPERTY = "orpheus_timestamp_parameter";

    /**
     * <p>
     * Represents the property name to retrieve the event_pages value.
     * </p>
     */
    private static final String EVENT_PAGES_PROPERTY = "event_pages";

    /**
     * <p>
     * This member variable holds a <code>BloomFilter</code> that is propogated by the Orpheus server with domain names
     * of domains that are participating in the application.
     * </p>
     *
     * <p>
     * This filter is initially set to an empty filter, but it is changed through Atom feed responses from the Orpheus
     * server. As part of the polling, the Orpheus server will send down serialized <code>BloomFilter</code>
     * instances, which are deserialized and used to set this value. This value cannot be null, but it can be empty.
     * It cannot be set to null. Access to this value should be locked in all methods that access it.
     * </p>
     */
    private BloomFilter filter;

    /**
     * <p>
     * This member variable holds the {@link OrpheusServer} instance used to poll the server.
     * </p>
     *
     * <p>
     * This value is created every time when the user makes a successful login, created with configuration values read
     * from the configuration file. That particular instance thread is started after the client has successfully
     * logged in, via the <code>OrpheusServer.start()</code> method. This value cannot be null.
     * </p>
     */
    private OrpheusServer server;

    /**
     * <p>
     * This member variable holds a list of {@link UIEventListener} instances that are called in the event handler
     * methods in this class.
     * </p>
     *
     * <p>
     * The values in this List are manipulated through the add / remove / clear event listeners methods. This member
     * variable is initially an empty List. It can be empty, but it cannot contain null values.
     * </p>
     */
    private final List eventListeners = new ArrayList();

    /**
     * <p>
     * This member variable holds the {@link ExtensionPersistence} instance used to persist the values out to the
     * client browser instance.
     * </p>
     *
     * <p>
     * This value is set in the {@link FirefoxExtensionHelper#initialize(String)} and is used in the get / set working
     * game ID, get / set currentTargetID, and in the <code>serverMessageReceived()</code> method to set the timestamp
     * for the latest feed received from the server. This value cannot be null.
     * </p>
     */
    private ExtensionPersistence persistence;

    /**
     * <p>
     * This member variable holds a reference to the window the JavaScript  code is running in. We use this value to
     * pop-up new windows and to initialize event listeners and persistence mechanisms.
     * </p>
     *
     * <p>
     * This value is initially set in the {@link FirefoxExtensionHelper#initialize(String)} and can't change after
     * instantiation. This value is initialized through the <code>JSObject.getWindow()</code> call, passing in "this"
     * as the Applet argument in the constructor. Since this class is an applet, the window retrieved is the window
     * the applet is embedded in.
     * </p>
     */
    private JSObject clientWindow;

    /**
     * <p>
     * This Map maps {@link UIEventType} keys to string values. The String value for each {@link UIEventType} key is a
     * valid URL path to a page to load in response to that particular event being raised. If a specific {@link
     * UIEventType} key doesn't have a value, no page will be loaded in response to that particular event.
     * </p>
     *
     * <p>
     * This value is set in the constructor via configuration values. Note that this doesn't apply to the PAGE_CHANGED
     * value.
     * </p>
     */
    private Map eventPages;

    /**
     * <p>
     * This member variable holds the string query parameter name used when querying the server to determine if a
     * particular domain is a host for games.
     * </p>
     *
     * <p>
     * This value is used in "pageChanged". This value is set in the {@link FirefoxExtensionHelper#initialize(String)},
     * can't be null or an empty string.
     * </p>
     */
    private String domainParameter;

    /**
     * <p>
     * This member variable holds a reference to the popup window opened by this class. Holding the reference to the
     * window allows us to reuse the same popup window, instead of reopening one for each new popup.
     * </p>
     *
     * <p>
     * This value is initially set in either "popupWindow" or "popupWindowWithContent".
     * </p>
     */
    private JSObject popupWindow = null;

    /**
     * <p>
     * This member variable holds the value of the URL loaded when a tested object matches the current target object.
     * </p>
     *
     * <p>
     * This value is used in "isCurrentTarget", is set in the {@link FirefoxExtensionHelper#initialize(String)}, and
     * can't be null or an empty string.
     * </p>
     */
    private String hashMatchURL;

    /**
     * <p>
     * This member variable holds the value of the domain parameter name used in the query string when a tested object
     * matches the current target object.
     * </p>
     *
     * <p>
     * This value is used in "isCurrentTarget", is set in the {@link FirefoxExtensionHelper#initialize(String)}, and
     * can't be null or an empty string.
     * </p>
     */
    private String hashMatchDomainParameter;

    /**
     * <p>
     * This member variable holds the value of the sequence parameter name used in the query string when a tested
     * object matches the current target object.
     * </p>
     *
     * <p>
     * This value is used in "isCurrentTarget", is set in the {@link FirefoxExtensionHelper#initialize(String)}, and
     * can't be null or an empty string.
     * </p>
     */
    private String hashMatchSequenceNumberParameter;

    /**
     * <p>
     * This member variable holds the value of the target text parameter name used in the query string when a tested
     * object matches the current target object.
     * </p>
     *
     * <p>
     * This value is used in "isCurrentTarget", is set in the {@link FirefoxExtensionHelper#initialize(String)}, and
     * can't be null or an empty string.
     * </p>
     */
    private String targetTextParameter;

    /**
     * <p>
     * This value is the current domain loaded in the browser.
     * </p>
     *
     * <p>
     * This value is set in "pageChanged(newPage)" to the current domain loaded. This value is initially null, but it
     * is changed in each "pageChanged" call.
     * </p>
     */
    private String currentDomain = null;

    /**
     * <p>
     * This member holds the default width of popup windows, if the user doesn't specify one. This value is also used
     * when popping up pages in response to events raised by the user.
     * </p>
     *
     * <p>
     * This value can be set in the {@link FirefoxExtensionHelper#initialize(String)} to a configured value. Any value
     * greater than 0 is accepted, but should be larger for a meaningful window.
     * </p>
     */
    private int defaultPopupWidth = 200;

    /**
     * <p>
     * This member holds the default height of popup windows, if the user doesn't specify one. This value is also used
     * when popping up pages in response to events raised by the user.
     * </p>
     *
     * <p>
     * This value can be set in the {@link FirefoxExtensionHelper#initialize(String)} to a configured value. Any value
     * greater than 0 is accepted, but should be larger for a meaningful window.
     * </p>
     */
    private int defaultPopupHeight = 200;

    /**
     * <p>
     * This member variable holds the value of the URL loaded when a page change is to a domain in the bloom filter
     * that participates in the Orpheus application.
     * </p>
     *
     * <p>
     * This value is used in "pageChanged", is set in the {@link FirefoxExtensionHelper#initialize(String)}, and can't
     * be null or an empty string.
     * </p>
     */
    private String pageChangedURL;

    /**
     * <p>
     * This member variable holds the value of the URL for the Orpheus server.
     * </p>
     *
     * <p>
     * This value is used in "successfulLogin" to create the {@link OrpheusServer} instance, is set in the {@link
     * FirefoxExtensionHelper#initialize(String)}, and can't be null or an empty string.
     * </p>
     */
    private String pollURL;

    /**
     * <p>
     * Represents the number of minutes to wait between each poll request for the Orpheus server.
     * </p>
     *
     * <p>
     * This value is used in "successfulLogin" to create the {@link OrpheusServer} instance, is set in the {@link
     * FirefoxExtensionHelper#initialize(String)}, and should be positive.
     * </p>
     */
    private int pollTime;

    /**
     * <p>
     * Represents the name of the HTTP parameter used to set the timestamp value in requests to the Orpheus server for
     * updates.
     * </p>
     *
     * <p>
     * This value is used in "successfulLogin" to create the {@link OrpheusServer} instance, is set in the {@link
     * FirefoxExtensionHelper#initialize(String)}, and can't be null or an empty string.
     * </p>
     */
    private String timestampParameter;

    /**
     * <p>
     * The empty constructor. Note: {@link FirefoxExtensionHelper#initialize()} should be called after this class
     * is newly created.
     * </p>
     */
    public FirefoxExtensionHelper() {
    }

    /**
     * <p>
     * This methods initializes this class, reading in configuration values from a default namespace.
     * </p>
     *
     * @throws FirefoxExtensionConfigurationException if errors occur while reading in the configuration values, or
     *         some configuration values are invalid.
     */
    public void initialize() throws FirefoxExtensionConfigurationException {
        this.initialize(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * This methods initializes this class, reading in configuration values from a given namespace.
     * </p>
     *
     * @param namespace The namespace to load configuration values from.
     *
     * @throws IllegalArgumentException if namespace is <code>null</code> or an empty string.
     * @throws FirefoxExtensionConfigurationException if errors occur while reading in the configuration values, or
     *         some configuration values are invalid.
     */
    public void initialize(String namespace) throws FirefoxExtensionConfigurationException {
        ClientLogicForFirefoxHelper.validateString(namespace, "namespace");

        if (clientWindow == null) {
            try {
                clientWindow = JSObject.getWindow(this);
            } catch (JSException e) {
                throw new FirefoxExtensionConfigurationException("The clientWindow can not be retrieved properly.", e);
            }
        }

        this.filter = new BloomFilter(1000, 0.01f);

        domainParameter = getStringPropertyValue(namespace, DOMAIN_PARAMETER_NAME_PROPERTY, true);
        hashMatchURL = getUrlPropertyValue(namespace, HASH_MATCH_URL_PROPERTY);
        hashMatchDomainParameter = getStringPropertyValue(namespace, HASH_MATCH_DOMAIN_PARAMETER_PROPERTY, true);
        hashMatchSequenceNumberParameter = getStringPropertyValue(namespace,
                HASH_MATCH_SEQUENCE_NUMBER_PARAMETER_PROPERTY, true);
        targetTextParameter = getStringPropertyValue(namespace, TARGET_TEXT_PARAMETER_PROPERTY, true);
        defaultPopupHeight = getWindowParameter(namespace, DEFAULT_HEIGHT_PROPERTY, defaultPopupHeight);
        defaultPopupWidth = getWindowParameter(namespace, DEFAULT_WIDTH_PROPERTY, defaultPopupWidth);
        pageChangedURL = getStringPropertyValue(namespace, PAGE_CHANGED_URL_PROPERTY, true);
        pollURL = getUrlPropertyValue(namespace, ORPHEUS_URL_PROPERTY);
        pollTime = parsePositiveInteger(getStringPropertyValue(namespace, ORPHEUS_POLL_TIME_PROPERTY, true),
                ORPHEUS_POLL_TIME_PROPERTY);
        timestampParameter = getStringPropertyValue(namespace, ORPHEUS_TIMESTAMP_PARAMETER_PROPERTY, true);

        generateEventPages(namespace);

        try {
            this.persistence = buildExtensionPersistence(namespace);
            this.persistence.setClientWindow(clientWindow);
        } catch (FirefoxExtensionPersistenceException e) {
            throw new FirefoxExtensionConfigurationException("Error happens when accessing the "
                    + "ExtensionPersistence.", e);
        }
    }

    /**
     * <p>
     * Gets the default <code>UIEventListener</code> instance for this component.
     * </p>
     *
     * @return the newly created <code>JavascriptUIEventListener</code> instance.
     */
    public UIEventListener getUIEventListener() {
        return new JavascriptUIEventListener();
    }

    /**
     * <p>
     * Gets the UIEventType enum value based on a string value given. This string value should be used as a match
     * to the values used to define each member of the enum.
     * </p>
     *
     * @param value the string value.
     *
     * @return he UIEventType enum value based on the given string value.
     */
    public UIEventType getUIEventTypeEnum(String value) {
        return (UIEventType) Enum.getEnumByStringValue(value, UIEventType.class);
    }

    /**
     * <p>
     * This method processes the given Atom document received from the Orpheus Server in response to a poll request.
     * </p>
     *
     * <p>
     * The feed will contain a "content" node that contains one of the following 4 types: text, html, xhtml, or
     * application/x-tc-bloom-filter. If the content node is text, html, or xhtml, the content should be parsed and
     * displayed to the user in a pop-up window; If the content node is of the "application/x-tc-bloom-filter" type, a
     * new <code>BloomFilter</code> instance should be created based on the serialized data parsed from the content
     * node. This value should be used to set the "filter" member variable. The atom:update timestamp of the feed will
     * be parsed from the feed and persisted to the client browser.
     * </p>
     *
     * @param feed The Atom feed to process.
     *
     * @throws IllegalArgumentException if the given value is null.
     * @throws FirefoxClientException if errors occur while processing the response.
     */
    void serverMessageReceived(RSSFeed feed) throws FirefoxClientException {
        ClientLogicForFirefoxHelper.validateNotNull(feed, "feed");

        // update the last feed timestamp
        Calendar c = Calendar.getInstance();
        c.setTime(feed.getUpdatedDate());
        this.persistence.setFeedTimestamp(c);

        // get the atom 10 content
        RSSItem[] items = feed.getItems();

        for (int i = 0; i < items.length; i++) {
            Atom10Content content = ((Atom10Item) items[i]).getContent();

            // only process the first item we found
            if ("text".equals(content.getType()) || "html".equals(content.getType())
                    || "xhtml".equals(content.getType())) {
                popupWindowWithContent(content.getElementText(), false, false, false, false, false, false, false,
                    this.defaultPopupHeight, this.defaultPopupWidth);

                break;
            } else if ("application/x-tc-bloom-filter".equals(content.getType())) {
                String data = content.getElementText();
                try {
                    this.filter = new BloomFilter(data);
                } catch (BloomFilterException e) {
                    throw new FirefoxClientException("The serialized data from the feed is invalid.", e);
                }

                break;
            }
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the "Log
     * In" button has been clicked.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void logInClick() throws FirefoxClientException {
        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.logInClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that a login
     * request has been successful. A new page will popup to display to the user if there is a mapping url for this
     * event type. The polling of the orpheus server is also started in this method.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void successfulLogIn() throws FirefoxClientException {
        this.popupWindow(UIEventType.SUCCESSFUL_LOGIN);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.successfulLogIn();
        }

        // start the orpheus server
        server = new OrpheusServer(pollURL, timestampParameter, this.persistence.getFeedTimestamp(),
                pollTime, this);
        server.start();
    }

    /**
     * <p>
     * A new page will popup to display to the user if there is a mapping url for the given event type.
     * </p>
     *
     * @param eventType the given event type.
     */
    private void popupWindow(UIEventType eventType) {
        String url = (String) eventPages.get(eventType);

        if (url != null) {
            popupWindow(url, false, false, false, false, false, false, false, defaultPopupHeight, defaultPopupWidth);
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * "Show Active Games" button has been clicked. A new page will popup to display to the user if there is a mapping
     * url for this event type.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void showActiveGamesClick() throws FirefoxClientException {
        this.popupWindow(UIEventType.SHOW_ACTIVE_GAMES_CLICK);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.showActiveGamesClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * "Show My Games" button has been clicked. A new page will popup to display to the user if there is a mapping url
     * for this event type.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void showMyGamesClick() throws FirefoxClientException {
        this.popupWindow(UIEventType.SHOW_MY_GAMES_CLICK);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.showMyGamesClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * "Show Unlocked Domains" button has been clicked. A new page will popup to display to the user if there is a
     * mapping url for this event type.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void showUnlockedDomainsClick() throws FirefoxClientException {
        this.popupWindow(UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.showUnlockedDomainsClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * "Show Upcoming Games" button has been clicked. A new page will popup to display to the user if there is a
     * mapping url for this event type.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void showUpcomingGamesClick() throws FirefoxClientException {
        this.popupWindow(UIEventType.SHOW_UPCOMING_GAMES_CLICK);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.showUpcomingGamesClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * "Show Leaders" button has been clicked. A new page will popup to display to the user if there is a mapping url
     * for this event type.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void showLeadersClick() throws FirefoxClientException {
        this.popupWindow(UIEventType.SHOW_LEADERS_CLICK);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.showLeadersClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * "Show Latest Clue" button has been clicked. A new page will popup to display to the user if there is a mapping
     * url for this event type.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void showLatestClueClick() throws FirefoxClientException {
        this.popupWindow(UIEventType.SHOW_LATEST_CLUE_CLICK);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.showLatestClueClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the
     * current pages has changed. We test the new page to see if it is in the "filter" member variable. currentDomain
     * will be replaced with the new domain. A new page will popup to display to the user if the number of active
     * games in the new domain is larger than 0.
     * </p>
     *
     * <p>
     * Note: Before testing the page name, we first must strip just the domain portion from the page name to use when
     * validating the domain. For instance "http://www.google.com?q="asdf"" would be stripped to just www.google.com.
     * If the domain isn't in the <code>BloomFilter</code>, no work is done.
     * </p>
     *
     * @param newPage The page the user switched to.
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void pageChanged(String newPage) throws FirefoxClientException {
        if (server == null) {
            throw new IllegalStateException("You should login first.");
        }

        // strip the domain portion
        String domain = stripDomainPortion(newPage);

        if ((domain == null) || !this.filter.contains(domain)) {
            return;
        }

        this.currentDomain = domain;

        double activeGames = server.queryDomain(this.domainParameter, domain);

        if (activeGames > 0) {
            try {
                String requestURL = ClientLogicForFirefoxHelper.buildUrl(pageChangedURL,
                        new String[] {domainParameter}, new String[] {domain});

                // pop it up in a new window with minimal adornments
                popupWindow(requestURL, false, false, false, false, false, false, false, defaultPopupHeight,
                    defaultPopupWidth);
            } catch (UnsupportedEncodingException e) {
                throw new FirefoxClientException("The query name or value can not be encoded.", e);
            }
        }
    }

    /**
     * <p>
     * Strip the domain portion. For instance "http://www.google.com?q="asdf"" would be stripped to just
     * www.google.com.
     * </p>
     *
     * @param newPage The page the user switched to.
     *
     * @return the stripped part, or null if no domain portion exists.
     *
     * @throws FirefoxClientException if the url is malformed.
     */
    private String stripDomainPortion(String newPage) throws FirefoxClientException {
        if (newPage == null) {
            return null;
        }

        // get the domain part
        try {
            URL url = new URL(newPage);

            return url.getHost();
        } catch (MalformedURLException e) {
            throw new FirefoxClientException("The url is malformed.", e);
        }
    }

    /**
     * <p>
     * This method adds the given {@link UIEventListener} implementation instance as an observer that is notified of
     * events raised by the JavaScript  and handled in this class.
     * </p>
     *
     * @param listener The listener to add.
     *
     * @throws IllegalArgumentException if the given parameter is null.
     */
    public void addEventListener(UIEventListener listener) {
        ClientLogicForFirefoxHelper.validateNotNull(listener, "listener");
        listener.setClientWindow(clientWindow);
        eventListeners.add(listener);
    }

    /**
     * <p>
     * This method removes the given listener from receiving future event notifications.
     * </p>
     *
     * @param listener The listener to remove from the List.
     *
     * @throws IllegalArgumentException if the given parameter is null.
     */
    public void removeEventListener(UIEventListener listener) {
        ClientLogicForFirefoxHelper.validateNotNull(listener, "listener");
        eventListeners.remove(listener);
    }

    /**
     * <p>
     * This method clears all listeners from the eventListeners List.
     * </p>
     */
    public void clearEventListeners() {
        eventListeners.clear();
    }

    /**
     * <p>
     * This method wraps the {@link ExtensionPersistence#setWorkingGameID(long)} method, allowing the JavaScript  the
     * ability to change the working game ID in the browser persistent store. After the persistence mechanism has been
     * called to store the value, each {@link UIEventListener} should be alerted of the update.
     * </p>
     *
     * @param id The ID of the current working game.
     *
     * @throws IllegalArgumentException if the given id is negative.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    public void setWorkingGameID(long id) throws FirefoxExtensionPersistenceException {
        persistence.setWorkingGameID(id);

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.workingGameUpdate(id);
        }
    }

    /**
     * <p>
     * This method wraps {@link ExtensionPersistence#getWorkingGameID()} method, allowing the JavaScript  in the
     * extension the ability to retrieve the current working ID from the persistent store.
     * </p>
     *
     * @return The ID of the working game.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur while retrieving the value in the persistent store.
     */
    public long getWorkingGameID() throws FirefoxExtensionPersistenceException {
        return persistence.getWorkingGameID();
    }

    /**
     * <p>
     * This method wraps {@link ExtensionPersistence#setCurrentTargetID(String, int)} method, allowing the
     * JavaScript in the extension the ability to change the target ID in the browser persistent store. The string
     * given will be a 40 character SHA-1 hash of the current target identifier, but we only validate that the
     * string is not null or empty, to accommodate future formats for the identifier.
     * </p>
     *
     * @param id The new ID of the current target.
     * @param sequenceNumber The sequence number to use.
     *
     * @throws IllegalArgumentException if the given value is null or an empty string, or if the sequence number is
     *         negative.
     * @throws FirefoxExtensionPersistenceException if errors occur while saving the value in the persistent store.
     */
    public void setCurrentTargetID(String id, int sequenceNumber) throws FirefoxExtensionPersistenceException {
        persistence.setCurrentTargetID(id, sequenceNumber);
    }

    /**
     * <p>
     * This method wraps {@link ExtensionPersistence#getCurrentTargetID()} method, allowing the JavaScript  in the
     * extension the ability to retrieve the current target ID from the persistent store.
     * </p>
     *
     * @return The current target ID.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur while retrieving the value in the persistent store.
     */
    public String getCurrentTargetID() throws FirefoxExtensionPersistenceException {
        return persistence.getCurrentTargetID();
    }

    /**
     * <p>
     * This method can be called from the JavaScript  to force a poll to the server. This is done immediately, outside
     * of the regular polling schedule to the server.
     * </p>
     *
     * @throws IllegalStateException if errors occur while handling the event.
     */
    public void pollServerNow() {
        if (server == null) {
            throw new IllegalStateException("You should login first.");
        }

        server.pollServerNow();
    }

    /**
     * <p>
     * This method sets the poll time to use between requests to the {@link OrpheusServer}. Note that the value won't
     * be used in the thread until after the next poll.
     * </p>
     *
     * @param time The new time, in minutes between each poll to the server
     *
     * @throws IllegalArgumentException if the given value is not positive.
     * @throws IllegalStateException if errors occur while handling the event.
     */
    public void setPollTime(int time) {
        if (server == null) {
            throw new IllegalStateException("You should login first.");
        }

        server.setPollTime(time);
    }

    /**
     * <p>
     * This method returns the poll time between server requests.
     * </p>
     *
     * @return The amount of time, in minutes, between server polls.
     *
     * @throws IllegalStateException if errors occur while handling the event.
     */
    public int getPollTime() {
        if (server == null) {
            throw new IllegalStateException("You should login first.");
        }

        return server.getPollTime();
    }

    /**
     * <p>
     * This method determines if the given String DOM Element matches the current target.
     * </p>
     *
     * @param element The element to test.
     *
     * @throws FirefoxClientException if anything wrong with the encoding and IO issues.
     */
    public void currentTargetTest(String element) throws FirefoxClientException {
        // extract the text
        String text = extractText(element);

        if (text == null) {
            return;
        }

        try {
            // encode the text, this also handles converting the text to the appropriate
            // application/x-www-form-urlencoded format for posting back to the server, if necessary.
            String encodedString = URLEncoder.encode(text, ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING);

            // create a new algorithm instance for hashing the data.
            HashAlgorithm algorithm = new SHAAlgorithm();

            // compare the hex string to the current target ID string
            if (!algorithm.hashToHexString(encodedString).equals(persistence.getCurrentTargetID())) {
                return;
            }

            // create the data to post
            String data = ClientLogicForFirefoxHelper.buildUrl("",
                    new String[] {hashMatchDomainParameter, hashMatchSequenceNumberParameter, targetTextParameter},
                    new String[] {currentDomain, "" + persistence.getSequenceNumber(), text});

            // Send data
            URL url = new URL(hashMatchURL);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            OutputStreamWriter wr = null;

            try {
                wr = new OutputStreamWriter(new BufferedOutputStream(conn.getOutputStream()));
                wr.write(data.substring(1));
                wr.flush();
            } finally {
                if (wr != null) {
                    try {
                        wr.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }

            // get the response
            String response = ClientLogicForFirefoxHelper.getInputStreamContent(conn.getInputStream());

            // show the content retrieved from the server
            popupWindowWithContent(response, false, false, false, false, false, false, false, this.defaultPopupHeight,
                this.defaultPopupWidth);
        } catch (UnsupportedEncodingException e) {
            throw new FirefoxClientException("Error happens in the encoding process.", e);
        } catch (IOException e) {
            throw new FirefoxClientException("IO errors happen.", e);
        } catch (HashException e) {
            throw new FirefoxClientException("Error happens when try to get the hash value.", e);
        }
    }

    /**
     * <p>
     * This method will create an {@link org.w3c.dom.Element} from the given String and then this method will traverse
     * the element given, extracting the text content of each node, removing the markup tags of the element. For each
     * text content item, all trailing and leading whitespace will be removed, and all sequences of more than one
     * whitespace character will be compressed to a single space. Finally, all characters in the resultant string will
     * be changed to lower case.
     * </p>
     *
     * @param element the given string to extract the text.
     *
     * @return the extracted text content.
     *
     * @throws FirefoxClientException if errors happen when parsing the string element.
     */
    private String extractText(String element) throws FirefoxClientException {
        if (element == null) {
            return null;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = factory.newDocumentBuilder();
            Document docuemnt = parser.parse(new InputSource(new StringReader(element)));
            element = extractText(docuemnt.getDocumentElement());

            // normalize all sequences of whitespace to a single space,
            element = element.replaceAll("\\s+", " ");

            return element.toLowerCase();
        } catch (ParserConfigurationException e) {
            throw new FirefoxClientException("Errors happen when parsing the string element.", e);
        } catch (SAXException e) {
            throw new FirefoxClientException("Errors happen when parsing the string element.", e);
        } catch (IOException e) {
            throw new FirefoxClientException("Errors happen when parsing the string element.", e);
        }
    }

    /**
     * <p>
     * A utility method for extracting the text content of a DOM Node, accounting for Text nodes, CDATA sections, and
     * intervening nodes of other types. For each text content item, all trailing and leading whitespace will be
     * removed.
     * </p>
     *
     * @param node the Node to extract the string value.
     *
     * @return a String containing the text content of the specified Node.
     */
    private String extractText(Node node) {
        // using StringBuffer to append all the text.
        StringBuffer buffer = new StringBuffer();

        if ((node.getNodeType() == Node.CDATA_SECTION_NODE) || (node.getNodeType() == Node.TEXT_NODE)) {
            buffer.append(node.getNodeValue().trim());
        }

        // iterate through all the text or CDATA children.
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            // Only CDATA_SECTION_NODE and TEXT_NODE will be added to the result.
            if ((child.getNodeType() == Node.CDATA_SECTION_NODE) || (child.getNodeType() == Node.TEXT_NODE)) {
                buffer.append(child.getNodeValue().trim());
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                buffer.append(extractText(child));
            }
        }

        return buffer.toString();
    }

    /**
     * <p>
     * This method pops up a window containing the given URL, with the decoration and size options shown. If there is
     * already a popup window, we will reuse it instead of opening a new one. For the reused window, we don't
     * dynamically change the decoration, just the size.
     * </p>
     *
     * @param url The URL of the page to load in the popup.
     * @param status Whether or not to show the status bar on the new page.
     * @param toolbar Whether or not to show the toolbar on the new page.
     * @param location Whether or not to show the location bar on the new page.
     * @param menubar Whether or not to show the menubar on the new window.
     * @param directories Whether or not to show the directories on the new window.
     * @param resizable Whether or not the new window should be resizable.
     * @param scrollbars Whether or not the new window should contain scrollbars.
     * @param height The height of the new window.
     * @param width The width of the new window.
     */
    public void popupWindow(String url, boolean status, boolean toolbar, boolean location, boolean menubar,
        boolean directories, boolean resizable, boolean scrollbars, int height, int width) {
        if (popupWindow != null) {
            // for an already open window
            ((JSObject) ((JSObject) popupWindow.getMember("document")).getMember("location")).setMember("href", url);
            popupWindow.call("resizeTo", new Object[] {new Integer(height), new Integer(width)});
        } else {
            // if the window isn't currently open, we need to open it using JavaScript  run on the clientWindow object
            // We also need to make sure we keep a reference to the opened window
            popupWindow(url, null, status, toolbar, location, menubar, directories, resizable,
                    scrollbars, height, width);
        }
    }

    /**
     * <p>
     * To ensure the scripting interface is available in the new window, we make sure to write out the "&lt;script
     * src="FirefoxExtension.js"&gt;&lt;/script&gt;" in the popup window.
     * </p>
     */
    private void writeCommonScript() {
        ((JSObject) popupWindow.getMember("document")).call("write",
                new Object[] {"<script src=\"FirefoxExtension.js\"></script>"});
    }

    /**
     * <p>
     * This method pops up a window containing the given URL, with the decoration and size options shown. This will be
     * a new popup window.
     * </p>
     *
     * @param url The URL of the page to load in the popup.
     * @param content the content of the page. null if nothing to display.
     * @param status Whether or not to show the status bar on the new page.
     * @param toolbar Whether or not to show the toolbar on the new page.
     * @param location Whether or not to show the location bar on the new page.
     * @param menubar Whether or not to show the menubar on the new window.
     * @param directories Whether or not to show the directories on the new window.
     * @param resizable Whether or not the new window should be resizable.
     * @param scrollbars Whether or not the new window should contain scrollbars.
     * @param height The height of the new window.
     * @param width The width of the new window.
     */
    private void popupWindow(String url, String content, boolean status, boolean toolbar, boolean location,
        boolean menubar, boolean directories, boolean resizable, boolean scrollbars, int height, int width) {
        StringBuffer optionsString = new StringBuffer();

        if (status) {
            optionsString.append("status=1,");
        }

        if (toolbar) {
            optionsString.append("toolbar=1,");
        }

        if (location) {
            optionsString.append("location=1,");
        }

        if (menubar) {
            optionsString.append("menubar=1,");
        }

        if (directories) {
            optionsString.append("directories=1,");
        }

        if (resizable) {
            optionsString.append("resizable=1,");
        }

        if (scrollbars) {
            optionsString.append("scrollbars=1,");
        }

        optionsString.append("height=" + height + ",");
        optionsString.append("width=" + width);

        popupWindow = (JSObject) clientWindow.call("open", new Object[] {url,
            "OrpheusChild", optionsString.toString()});

        if (content != null) {
            ((JSObject) popupWindow.getMember("document")).call("write", new Object[] {content});
        }

        popupWindow.call("setParentWindow", new Object[] {this});
    }

    /**
     * <p>
     * This method pops up a window containing the given content, with the decoration and size options shown. If there
     * is already a popup window, we will reuse it instead of opening a new one. For the reused window, we don't
     * dynamically change the decoration, just the size and the inner content.
     * </p>
     *
     * @param content The new content for the window.
     * @param status Whether or not to show the status bar in the new window.
     * @param toolbar Whether or not to show the toolbar in the new window.
     * @param location Whether or not to show the location bar in the new window.
     * @param menubar Whether or not to show the menubar in the new window.
     * @param directories Whether or not to show the directories in the new window.
     * @param resizable Whether or not the new window is resizable.
     * @param scrollbars Whether or not the new window has scrollbards.
     * @param height The height of the new window.
     * @param width The width of the new window.
     */
    public void popupWindowWithContent(String content, boolean status, boolean toolbar, boolean location,
        boolean menubar, boolean directories, boolean resizable, boolean scrollbars, int height, int width) {
        if (popupWindow != null) {
            // for an already open window
            ((JSObject) popupWindow.getMember("document")).call("write", new Object[] {content});
            popupWindow.call("resizeTo", new Object[] {new Integer(height), new Integer(width)});
            writeCommonScript();
        } else {
            // if the window isn't currently open, we need to open it using JavaScript  run on the clientWindow object
            // We also need to make sure we keep a reference to the opened window
            popupWindow("", content, status, toolbar, location, menubar, directories, resizable, scrollbars, height,
                width);
        }
    }

    /**
     * <p>
     * This method is called from JavaScript  in the actual extension, alerting this component to the fact that the "Log
     * Out" button has been clicked.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void logOutClick() throws FirefoxClientException {
        String urlStr = (String) eventPages.get(UIEventType.LOGOUT_CLICK);

        if (urlStr != null) {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection httpURL = (HttpURLConnection) url.openConnection();
                httpURL.setRequestMethod("GET");
                ClientLogicForFirefoxHelper.getInputStreamContent(httpURL.getInputStream());
            } catch (MalformedURLException e) {
                throw new FirefoxClientException("Error when create the url.", e);
            } catch (IOException e) {
                throw new FirefoxClientException("Error occurs while handling the event", e);
            }
        }

        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.logOutClick();
        }
    }

    /**
     * <p>
     * This method is called from JavaScript in the actual extension, alerting this component to the fact that a logout
     * request has been successful. The polling of the orpheus server will also be stopped.
     * </p>
     *
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public void successfulLogOut() throws FirefoxClientException {
        for (Iterator iter = eventListeners.iterator(); iter.hasNext();) {
            UIEventListener listener = (UIEventListener) iter.next();
            listener.successfulLogOut();
        }

        // stop the orpheus server polling
        if (server != null) {
            server.stopThread();
            server = null;
        }
    }

    /**
     * <p>
     * Get the latest update time of the Orpheus server timestamp, as read from the persistence mechanism.
     * </p>
     *
     * @return The latest update time of the Orpheus server timestamp.
     *
     * @throws FirefoxExtensionPersistenceException if errors occur reading from the persistent store
     */
    public Calendar getFeedTimestamp() throws FirefoxExtensionPersistenceException {
        return persistence.getFeedTimestamp();
    }

    /**
     * <p>
     * Gets the height or width parameters of the popup windows from the config file. If the user doesn't specify one,
     * the default value will be used.
     * </p>
     *
     * @param namespace The namespace to load configuration values from.
     * @param name the property name.
     * @param defaultValue the default value if the property does not exist.
     *
     * @return the height or width parameters of the popup windows.
     *
     * @throws FirefoxExtensionConfigurationException if errors occur while reading in the configuration values, or
     *         some configuration values are invalid.
     */
    private int getWindowParameter(String namespace, String name, int defaultValue)
        throws FirefoxExtensionConfigurationException {
        String value = getStringPropertyValue(namespace, name, false);

        return (value == null) ? defaultValue : this.parsePositiveInteger(value, name);
    }

    /**
     * <p>
     * Parses the given string value into an integer representation. The integer value should be positive.
     * </p>
     *
     * @param value the string value to parse.
     * @param name the name of the string value.
     *
     * @return the parsed integer value.
     *
     * @throws FirefoxExtensionConfigurationException if the string value can not be parsed into an integer or the
     *         integer value is not positive.
     */
    private int parsePositiveInteger(String value, String name) throws FirefoxExtensionConfigurationException {
        try {
            int ret = Integer.parseInt(value);

            if (ret <= 0) {
                throw new FirefoxExtensionConfigurationException("The value of " + name
                    + " is not a positive integer.");
            }

            return ret;
        } catch (NumberFormatException e) {
            throw new FirefoxExtensionConfigurationException("The value of " + name
                + " is not a well-formated integer.", e);
        }
    }

    /**
     * <p>
     * Create the {@link ExtensionPersistence} instance with the config values under given namespace.
     * </p>
     *
     * @param namespace the namespace to load the config values.
     *
     * @return the created {@link ExtensionPersistence} instance.
     *
     * @throws FirefoxExtensionConfigurationException if errors occur while reading in the configuration values, or
     *         some configuration values are invalid.
     */
    private ExtensionPersistence buildExtensionPersistence(String namespace)
        throws FirefoxExtensionConfigurationException {
        String persistenceClassName = getStringPropertyValue(namespace, PERSISTENCE_CLASS_PROPERTY, true);

        try {
            // create an object using reflection
            Object object = Class.forName(persistenceClassName).newInstance();

            if (!(object instanceof ExtensionPersistence)) {
                throw new FirefoxExtensionConfigurationException("The created class is not sub-class of "
                    + ExtensionPersistence.class.getName() + ".");
            }

            return (ExtensionPersistence) object;
        } catch (ClassNotFoundException cnfe) {
            throw new FirefoxExtensionConfigurationException("Error occurs, the class can't be found.", cnfe);
        } catch (IllegalAccessException iae) {
            throw new FirefoxExtensionConfigurationException("Error occurs during creating the object.", iae);
        } catch (InstantiationException ie) {
            throw new FirefoxExtensionConfigurationException("Error occurs during creating the object.", ie);
        }
    }

    /**
     * <p>
     * Loads the mappings of event pages from the config file under the given namespace.
     * </p>
     *
     * @param namespace the namespace to load the config values.
     *
     * @throws FirefoxExtensionConfigurationException if errors occur while reading in the configuration values, or
     *         some configuration values are invalid.
     */
    private void generateEventPages(String namespace) throws FirefoxExtensionConfigurationException {
        this.eventPages = new HashMap();

        String[] eventPagesValues = this.getStringArrayPropertyValue(namespace, EVENT_PAGES_PROPERTY);

        if (eventPagesValues == null) {
            throw new FirefoxExtensionConfigurationException("The required parameter event_pages in namespace "
                + namespace + " doesn't exist.");
        }

        for (int i = 0; i < eventPagesValues.length; i++) {
            String[] parts = eventPagesValues[i].split(",");

            if ((parts.length != 2) || (parts[0].trim().length() == 0) || (parts[1].trim().length() == 0)) {
                throw new FirefoxExtensionConfigurationException("The eventPage value of " + eventPagesValues[i]
                    + " is invalid.");
            }

            UIEventType eventType = (UIEventType) Enum.getEnumByStringValue(parts[0], UIEventType.class);

            if (eventType == null) {
                throw new FirefoxExtensionConfigurationException("The event type: " + parts[0] + " is not matched.");
            }

            eventPages.put(eventType, parts[1]);
        }
    }

    /**
     * <p>
     * Get the string property value in <code>ConfigManager</code> with specified namespace and name.
     * </p>
     *
     * @param namespace the namespace of the config string property value.
     * @param name the name of the config string property value.
     * @param required whether the property value is required to get.
     *
     * @return the config string property value in <code>ConfigManager</code>.
     *
     * @throws FirefoxExtensionConfigurationException if the namespace doesn't exist, or the parameter doesn't exist if
     *         it is required to get, or the parameter value is an empty string.
     */
    private String getStringPropertyValue(String namespace, String name, boolean required)
        throws FirefoxExtensionConfigurationException {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);

            if ((value == null)) {
                if (required) {
                    throw new FirefoxExtensionConfigurationException("The required parameter " + name
                        + " in namespace " + namespace + " doesn't exist.");
                }

                return null;
            }

            if (value.trim().length() == 0) {
                throw new FirefoxExtensionConfigurationException("The parameter value of " + name + " in namespace "
                    + namespace + " is an empty string.");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            throw new FirefoxExtensionConfigurationException("The namespace with the name of " + namespace
                + " doesn't exist.", une);
        }
    }

    /**
     * <p>
     * Get the string property value in <code>ConfigManager</code> with specified namespace and name. Note it will be a
     * valid url string value.
     * </p>
     *
     * @param namespace the namespace of the config string property value.
     * @param name the name of the config string property value.
     *
     * @return the valid url string value.
     *
     * @throws FirefoxExtensionConfigurationException if the namespace doesn't exist, or the parameter doesn't exist,
     *         or the parameter value is not a valid url.
     */
    private String getUrlPropertyValue(String namespace, String name) throws FirefoxExtensionConfigurationException {
        String url = getStringPropertyValue(namespace, name, true);

        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new FirefoxExtensionConfigurationException("The parameter value of " + name + " in namespace "
                + namespace + " is not a valid url.", e);
        }

        return url;
    }

    /**
     * <p>
     * Get the string array property value in <code>ConfigManager</code> with specified namespace and name.
     * </p>
     *
     * @param namespace the namespace of the config string array property value.
     * @param name the name of the config string array property value.
     *
     * @return the config string array property value in <code>ConfigManager</code>.
     *
     * @throws FirefoxExtensionConfigurationException if the namespace doesn't exist.
     */
    private String[] getStringArrayPropertyValue(String namespace, String name)
        throws FirefoxExtensionConfigurationException {
        try {
            return ConfigManager.getInstance().getStringArray(namespace, name);
        } catch (UnknownNamespaceException une) {
            throw new FirefoxExtensionConfigurationException("The namespace with the name of " + namespace
                + " doesn't exist.", une);
        }
    }
}
