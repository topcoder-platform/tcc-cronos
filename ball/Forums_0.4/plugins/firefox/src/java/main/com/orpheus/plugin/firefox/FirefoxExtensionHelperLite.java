/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.DataFormatException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.topcoder.bloom.BloomFilter;
import com.topcoder.bloom.BloomFilterException;
import com.topcoder.lang.StringUtil;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.compression.CompressionUtility;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.rssgenerator.RSSFeed;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Content;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Item;
import com.topcoder.util.rssgenerator.io.RSSParser;
import com.topcoder.util.rssgenerator.io.atom10.Atom10Parser;

/**
 * <p>
 * This class is the main class of the plugin. It has the minimal subset of methods required to implement the
 * expected functionality. It is used for preforming backgroud request to the server. Most of the code is taken
 * from the FirefoxExtensionHelper and OrpheusServer class.
 * </p>
 * 
 * <p>
 * <b>Thread Safety: </b>This class is mutable, as its internal state can change in a number of ways, including in
 * response to polls made to the Orpheus server.
 * </p>
 * 
 * @author PE, kr00tki
 * @version 1.0
 */
public class FirefoxExtensionHelperLite {

    /**
     * <p>
     * Represents the name of the config property with message's date format.
     * </p>
     */
    private static final String DATE_FORMAT_PROPERTY = "date_format";

    /**
     * <p>
     * Represents the name of the config property with message's format.
     * </p>
     */
    private static final String MESSAGE_TEMPLATE_PROPERTY = "message_template";

    /**
     * <p>
     * Represents the default namespace for this class to load the config values.
     * </p>
     */
    private static final String DEFAULT_NAMESPACE = "com.orpheus.plugin.firefox.FirefoxExtensionHelper";

    /**
     * <p>
     * Represents the property name to retrieve the hash algorithm class name.
     * </p>
     */
    private static final String HASH_ALGORITHM_PROPERTY = "hash_algorithm";

    /**
     * <p>
     * Represents the default date format for the timestamp which will be used as one part of the http query value.
     * </p>
     */
    private final DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * The date formatter used for formatting messages from RSS feed. Initialized in <code>initialize</code>
     * method.
     */
    private DateFormat messageDateFormat;

    /**
     * <p>
     * This member variable holds a <code>BloomFilter</code> that is propogated by the Orpheus server with domain
     * names of domains that are participating in the application.
     * </p>
     * 
     * <p>
     * This filter is initially set to an empty filter, but it is changed through Atom feed responses from the
     * Orpheus server. As part of the polling, the Orpheus server will send down serialized
     * <code>BloomFilter</code> instances, which are deserialized and used to set this value. This value cannot
     * be null, but it can be empty. It cannot be set to null. Access to this value should be locked in all methods
     * that access it.
     * </p>
     */
    private BloomFilter filter;

    /**
     * The timestamp of the feed.
     */
    private String timestamp = "";

    /**
     * The hash algorithm used in object tests.
     */
    private HashAlgorithm hashAlgorithm = null;

    /**
     * This intrnal field stores the serialized Bloom filter that is retrieved from RSS update.
     */
    private String serializedBloomFilter = null;

    /**
     * The message template used to formatting items from RSS feed.
     */
    private String messageTemplate = null;

    /**
     * Default constructor. Initializes date formatter.
     */
    public FirefoxExtensionHelperLite() {
        defaultDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
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
        this.filter = new BloomFilter(1, 0.01f);

        messageTemplate = getStringPropertyValue(namespace, MESSAGE_TEMPLATE_PROPERTY, true);
        messageDateFormat = new SimpleDateFormat(getStringPropertyValue(namespace, DATE_FORMAT_PROPERTY, true));
        String algorithmName = getStringPropertyValue(namespace, HASH_ALGORITHM_PROPERTY, true);
        try {
            hashAlgorithm = (HashAlgorithm) Class.forName(algorithmName).newInstance();
        } catch (Exception ex) {
            throw new FirefoxExtensionConfigurationException("Error occurs while creating hash algorithm.", ex);
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
     * @throws FirefoxExtensionConfigurationException if the namespace doesn't exist, or the parameter doesn't
     *         exist if it is required to get, or the parameter value is an empty string.
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
                throw new FirefoxExtensionConfigurationException("The parameter value of " + name
                        + " in namespace " + namespace + " is an empty string.");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            throw new FirefoxExtensionConfigurationException("The namespace with the name of " + namespace
                    + " doesn't exist.", une);
        }
    }

    /**
     * <p>
     * This method determines if the given String DOM Element matches the current target.
     * </p>
     * 
     * @param text The element to test.
     * @param currentHash the hash of the current object
     * 
     * @return true - if the object hash match the expected one; false otherwise;
     * @throws FirefoxClientException if anything wrong with the encoding and IO issues.
     */
    public boolean currentTargetTest(String text, String currentHash) throws FirefoxClientException {
        try {
            // compare the hex string to the current target ID string
            return (text != null) && hashAlgorithm.hashToHexString(text.getBytes("UTF-8")).equals(currentHash);
        } catch (Exception e) {
            throw new FirefoxClientException("Error happens when try to get the hash value.", e);
        }
    }

    /**
     * <p>
     * This method will create an {@link org.w3c.dom.Element} from the given String and then this method will
     * traverse the element given, extracting the text content of each node, removing the markup tags of the
     * element. For each text content item, all trailing and leading whitespace will be removed, and all sequences
     * of more than one whitespace character will be compressed to a single space. Finally, all characters in the
     * resultant string will be changed to lower case.
     * </p>
     * 
     * @param element the given string to extract the text.
     * 
     * @return the extracted text content.
     * 
     * @throws FirefoxClientException if errors happen when parsing the string element.
     */
    public String extractText(String element) throws FirefoxClientException {
        if (element == null) {
            return null;
        }
        element = "<html>" + element + "</html>";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = factory.newDocumentBuilder();
            Document document = parser.parse(new InputSource(new StringReader(element)));
            element = extractText(document.getDocumentElement());

            // normalize all sequences of HTML whitespace to a single space, and remove leading and trailing space
            // The "\\s" regex character class and String.trim() don't quite do the right things.
            element = element.replaceAll("[ \t\f\r\n\u00a0\u200b]+", " ");
            element = element.replaceFirst("^ +", "");
            element = element.replaceFirst(" +$", "");

            return element.toLowerCase(Locale.US);
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
     * A utility method for extracting the text content of a DOM Element, accounting for Text nodes, CDATA
     * sections, and intervening nodes of other types. Elements that are considered "natural separators" have
     * spaces inserted before and after their text.
     * </p>
     * 
     * @param element the Element to extract the string value.
     * 
     * @return a String containing the text content of the specified Element
     */
    private String extractText(Element element) {
        // using StringBuffer to append all the text.
        StringBuffer buffer = new StringBuffer();

        // iterate through all the text or CDATA children.
        NodeList childNodes = element.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            // Only TEXT_NODE will be added to the result. (Note that CDATA is not permitted in HTML)
            if (child.getNodeType() == Node.TEXT_NODE) {
                buffer.append(child.getNodeValue());
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                buffer.append(extractText((Element) child));
            }
        }

        return buffer.toString();
    }

    /**
     * <p>
     * This method is called from JavaScript in the actual extension, alerting this component to the fact that the
     * current pages has changed. We test the new page to see if it is in the "filter" member variable.
     * currentDomain will be replaced with the new domain. A new page will popup to display to the user if the
     * number of active games in the new domain is larger than 0.
     * </p>
     * 
     * <p>
     * Note: Before testing the page name, we first must strip just the domain portion from the page name to use
     * when validating the domain. For instance "http://www.google.com?q="asdf"" would be stripped to just
     * www.google.com. If the domain isn't in the <code>BloomFilter</code>, no work is done.
     * </p>
     * 
     * @param domain The page the user switched to.
     * 
     * @return if the domain contains any active games, an URL to alert page will be returned; null otherwise.
     * @throws FirefoxClientException if errors occur while handling the event.
     */
    public boolean domainTest(String domain) {
        if ((domain == null) || !this.filter.contains(domain)) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * This method can be called from the JavaScript to parse the polling RSS response. The poll response is parsed
     * and internal Bloom filter updated (if applicable). If there are any other messages in the feed, the last one
     * will be returned to display.
     * </p>
     * 
     * @param response the response from RSS polling.
     * 
     * @return the message to be displayed to the user; null otherwise.
     * @throws FirefoxClientException if error occurs during operation.
     */
    public String[] pollServerResponse(String response) throws FirefoxClientException {
        try {
            RSSParser parser = new Atom10Parser();
            RSSFeed feed = parser.parseFeed(new StringReader(response));

            // SimpleDateFormat is not thread-safe
            synchronized (defaultDateFormat) {
                this.timestamp = defaultDateFormat.format(feed.getUpdatedDate());
            }

            // invoke the FirefoxExtensionHelper
            return serverMessageReceived(feed);
        } catch (Exception e) {
            throw new FirefoxClientException("Error occurs during poll request.", e);
        }
    }

    /**
     * Creates the message returned to the user. The <code>items</code> list contains the RSS feed items to be
     * formatted.
     * 
     * @param items the list with Atom10Item objects to be formatted.
     * 
     * @return the array of messages to be returned to the user. This implementation will always return one
     *         message.
     */
    private String[] formatMessage(List items) {
        if (items.size() == 0) {
            return null;
        }
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><body><table>");
        for (Iterator it = items.iterator(); it.hasNext();) {
            Atom10Item item = (Atom10Item) it.next();
            buffer.append("<tr>");
            
            String msg = StringUtil.replace(messageTemplate, "title", item.getTitle().getElementText());
            msg = StringUtil.replace(msg, "date", messageDateFormat.format(item.getUpdatedDate()));
            msg = StringUtil.replace(msg, "msg_body", item.getContent().getElementText());

            buffer.append(msg);
            buffer.append("<br/></tr>");
        }
        buffer.append("</table></body></html>");
        return new String[] {buffer.toString()};
    }

    /**
     * <p>
     * This method processes the given Atom document received from the Orpheus Server in response to a poll
     * request.
     * </p>
     * 
     * <p>
     * The feed will contain a "content" node that contains one of the following 4 types: text, html, xhtml, or
     * application/x-tc-bloom-filter. If the content node is text, html, or xhtml, the content should be parsed and
     * displayed to the user in a pop-up window; If the content node is of the "application/x-tc-bloom-filter"
     * type, a new <code>BloomFilter</code> instance should be created based on the serialized data parsed from
     * the content node. This value should be used to set the "filter" member variable. The atom:update timestamp
     * of the feed will be parsed from the feed and persisted to the client browser.
     * </p>
     * 
     * @param feed The Atom feed to process.
     * 
     * @throws IllegalArgumentException if the given value is null.
     * @throws FirefoxClientException if errors occur while processing the response.
     */
    private String[] serverMessageReceived(RSSFeed feed) throws FirefoxClientException {
        ClientLogicForFirefoxHelper.validateNotNull(feed, "feed");

        // get the atom 10 content
        RSSItem[] items = feed.getItems();

        List htmlContent = new ArrayList();
        Atom10Content filterUpdate = null;
        Date filterUpdateDate = null;
        for (int i = 0; i < items.length; i++) {
            Atom10Item item = (Atom10Item) items[i];
            Atom10Content content = item.getContent();
            // only process the first item we found
            if ("text".equals(content.getType()) || "html".equals(content.getType())
                    || "xhtml".equals(content.getType())) {
                htmlContent.add(item);
            } else if ("application/x-tc-bloom-filter".equals(content.getType())) {
                Date itemDate = item.getUpdatedDate() == null ? item.getPublishedDate() : item.getUpdatedDate();
                if ((filterUpdate == null) || (!filterUpdateDate.after(itemDate))) {
                    filterUpdate = content;
                    filterUpdateDate = itemDate;
                }
            }
        }

        serializedBloomFilter = null;
        if (filterUpdate != null) {
            String data = decodeBase64(filterUpdate.getElementText());
            setBloomFilter(data);
            serializedBloomFilter = data;
        }

        // return (String[]) htmlContent.toArray(new String[htmlContent.size()]);
        return formatMessage(htmlContent);
    }

    /**
     * Returns the timestamp of the polling request that should be made prior this call.
     * 
     * @return the RSS feed update timestamp.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Creates the Bloom Filter from the serizlized form and sets it to instance field.
     * 
     * @param serizlizedFilter the serialized Bloom filter.
     * @throws FirefoxClientException if the serizlized string in not in correct format.
     */
    public void setBloomFilter(String serizlizedFilter) throws FirefoxClientException {
        try {
            this.filter = new BloomFilter(serizlizedFilter);
        } catch (BloomFilterException e) {
            throw new FirefoxClientException("The serialized data from the feed is invalid.", e);
        }
    }

    /**
     * A utility method th decode base64 encoded string.
     * 
     * @param input the input string to decode.
     * @return the decoded input, or null if unexpected error occurs.
     * @throws FirefoxClientException if the given input cannot be decoded.
     */
    private static String decodeBase64(String input) throws FirefoxClientException {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            CompressionUtility util = new CompressionUtility("com.topcoder.util.compression.Base64Codec", os);
            util.decompress(new StringBuffer(input));
            return os.toString();
        } catch (ClassNotFoundException ex) {
            // should never happen
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            // should never happen
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            // should never happen
            ex.printStackTrace();
        } catch (IOException ex) {
            // should never happen
            ex.printStackTrace();
        } catch (DataFormatException ex) {
            throw new FirefoxClientException("The Bloom filter update cannot be decoded.", ex);
        }
        return null;
    }

    /**
     * Returns the serialized Bloom filter that is set after the successful filter update from RSS feed.
     * 
     * @return serialized Bloom Filter or null, if there were no filters updates yet.
     */
    public String getSerializedBloomFilter() {
        return serializedBloomFilter;
    }
}
