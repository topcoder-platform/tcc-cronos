/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.topcoder.util.rssgenerator.RSSFeed;
import com.topcoder.util.rssgenerator.io.RSSParser;
import com.topcoder.util.rssgenerator.io.atom10.Atom10Parser;

import java.io.IOException;
import java.io.StringReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;


/**
 * <p>
 * This class handles making polling requests at specific intervals to a configured Orpheus server URL.
 * </p>
 *
 * <p>
 * This class will make the request and then call the FirefoxExtensionHelper's serverMessageReceived(RSSFeed) method to
 * handle the response. All this class has to do is to call the server and then pass the response to the listener
 * member variable for handling. The response should be an Atom 1.0 feed document that outlines content either as a
 * serialized Bloom filter instance, or as text, html, or xhtml, which is displayed by the {@link
 * FirefoxExtensionHelper} class to the user.
 * </p>
 *
 * <p>
 * This class implements the <code>Runnable</code> interface, allowing the parent {@link FirefoxExtensionHelper} class
 * to run the polling in a separate thread.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable, as the pollTime can change after instantiation.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class OrpheusServer extends Thread {
    /** Represents the millisecond representation of one minute. */
    private static final long MINUTE_IN_MILLISECONDS = 60 * 1000;

    /**
     * <p>
     * Represents the default date format for the timestamp which will be used as one part of the http query value.
     * </p>
     */
    private final DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    /**
     * <p>
     * Represents the URL string that is requested after each polling interval. The response retrieved from requesting
     * this URL should be an Atom 1.0 document that is passed to the {@link FirefoxExtensionHelper} for processing
     * after an <code>RSSFeed</code> instance has been created from it.
     * </p>
     *
     * <p>
     * This value is set in the constructor, cannot be null or empty, and can't be changed after instantiation.
     * </p>
     */
    private final String url;

    /**
     * <p>
     * Represents the {@link FirefoxExtensionHelper} instance that creates this instance and is used to process the
     * documents retrieved as a result of each poll request.
     * </p>
     *
     * <p>
     * This value cannot be null or changed after instantiation.
     * </p>
     */
    private final FirefoxExtensionHelper listener;

    /**
     * <p>
     * Represents the number of minutes to wait between each poll request.
     * </p>
     *
     * <p>
     * This value is set in the constructor to a positive value. This value can be changed through the {@link
     * OrpheusServer#setPollTime(int)} method and retrieved through the {@link OrpheusServer#getPollTime()} method.
     * </p>
     */
    private int pollTime;

    /**
     * <p>
     * Represents the timestamp of the latest feed retrieved from the server. This value is passed on each request to
     * the server as an HTTP parameter whose name is held in the timestampParameter member variable.
     * </p>
     *
     * <p>
     * This value is set in {@link OrpheusServer#pollServerNow()} method and is set in the constructor to the value
     * read from persistence, which could be null if it has never been set.
     * </p>
     */
    private Calendar timestamp;

    /**
     * <p>
     * This value represents the name of the HTTP parameter used to set the timestamp value in requests to the server
     * for updates.
     * </p>
     *
     * <p>
     * This value is set in the constructor, can't be null or an empty string and can't change after instantiation.
     * This value is used in "pollServerNow".
     * </p>
     */
    private String timestampParameter;

    /**
     * <p>
     * This member variable is used to tell this class to stop polling the server.
     * </p>
     *
     * <p>
     * This value is set to "true" in the "stop()" method. Once this value has been set, the polling will stop at the
     * next attempt to contact the server.
     * </p>
     */
    private boolean stopPolling = false;

    /**
     * <p>
     * This constructor sets the member variable values to the values of the parameters given.
     * </p>
     *
     * @param url The URL of the Atom feed to request after each poll interval.
     * @param timeParameter The name of the URL timestamp parameter to send with the request.
     * @param timestamp The initial timestamp value, read from persistence. Can be null if it has never been set.
     * @param pollTime The number of minutes to wait between each poll request.
     * @param listener The FirefoxExtensionHelper that processes the server request.
     *
     * @throws IllegalArgumentException if url or listener are null, or if url is an empty string, or if pollTime is
     *         not positive, or if timeParameter is null or an empty string.
     */
    public OrpheusServer(String url, String timeParameter, Calendar timestamp, int pollTime,
        FirefoxExtensionHelper listener) {
        ClientLogicForFirefoxHelper.validateString(url, "url");
        ClientLogicForFirefoxHelper.validateString(timeParameter, "timeParameter");
        ClientLogicForFirefoxHelper.validatePollTime(pollTime, "pollTime");
        ClientLogicForFirefoxHelper.validateNotNull(listener, "listener");
        this.url = url;
        this.timestamp = timestamp;
        this.timestampParameter = timeParameter;
        this.pollTime = pollTime;
        this.listener = listener;
    }

    /**
     * <p>
     * This method performs the actual URL request to the Orpheus Server, creating an <code>RSSFeed</code> instance
     * from the Atom response, passing it to the {@link FirefoxExtensionHelper} instance contained in the listener
     * member variable. Note that if any errors occur while performing this operation, they should be ignored, as this
     * method is called continuously in a thread.
     * </p>
     *
     * <p>
     * Note: format yyyy-MM-dd'T'hh:mm:ss will be used to represent the timestamp in String representation in the
     * request url.
     * </p>
     */
    public void pollServerNow() {
        try {
            String newUrl = ClientLogicForFirefoxHelper.buildUrl(url, new String[] {timestampParameter},
                    new String[] {timestamp == null ? null : defaultDateFormat.format(timestamp.getTime())});
            URL urlObj = new URL(newUrl);
            HttpURLConnection urlCon = (HttpURLConnection) urlObj.openConnection();
            urlCon.setRequestMethod("GET");

            // get the response
            String response = ClientLogicForFirefoxHelper.getInputStreamContent(urlCon.getInputStream());

            RSSParser parser = new Atom10Parser();
            RSSFeed feed = parser.parseFeed(new StringReader(response));

            // invoke the FirefoxExtensionHelper
            listener.serverMessageReceived(feed);

            Calendar c = Calendar.getInstance();
            c.setTime(feed.getUpdatedDate());
            this.timestamp = c;
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * <p>
     * This method sets the poll time to use between requests to the OrpheusServer. Note that the value won't be used
     * in the thread until after the next poll.
     * </p>
     *
     * @param time The number of minutes to wait between each server poll.
     *
     * @throws IllegalArgumentException if the given value is not positive.
     */
    public void setPollTime(int time) {
        ClientLogicForFirefoxHelper.validatePollTime(time, "pollTime");
        this.pollTime = time;
    }

    /**
     * <p>
     * This method returns the poll time between server requests.
     * </p>
     *
     * @return The value of the pollTime member variable.
     */
    public int getPollTime() {
        return pollTime;
    }

    /**
     * <p>
     * This method is the thread loop method for this polling class. This method just loops until stopPolling is true,
     * and after the number of minutes in "pollTime" this method should call "pollServerNow" to perform the server poll.
     * </p>
     */
    public void run() {
        stopPolling = false;
        while (!stopPolling) {
            try {
                Thread.sleep(pollTime * MINUTE_IN_MILLISECONDS);
            } catch (InterruptedException e) {

                return;
            }

            pollServerNow();
        }
    }

    /**
     * <p>
     * This method is used to query the Orpheus server for the nubmer of active games in a particular domain. This
     * method should create a new HttpURLConnection using the "url" member variable, and then adding a query parameter
     * using the domainParameter name and the domain value given in the parameters. This method should then get the
     * response from the server for that request, parsing the response as a double value.
     * </p>
     *
     * @param domainParameter The name of the query string parameter.
     * @param domain The query string parameter value.
     *
     * @return The number of active games in the given domain.
     *
     * @throws IllegalArgumentException if either parameter is null or an empty string.
     * @throws FirefoxClientException if errors occur while making the request.
     */
    public double queryDomain(String domainParameter, String domain) throws FirefoxClientException {
        ClientLogicForFirefoxHelper.validateString(domainParameter, "domainParameter");
        ClientLogicForFirefoxHelper.validateString(domain, "domain");

        try {
            String newUrl = ClientLogicForFirefoxHelper.buildUrl(url, new String[] {domainParameter},
                    new String[] {domain});
            HttpURLConnection urlCon = (HttpURLConnection) new URL(newUrl).openConnection();
            urlCon.setRequestMethod("GET");

            // get the response
            String response = ClientLogicForFirefoxHelper.getInputStreamContent(urlCon.getInputStream());

            return Double.parseDouble(response);
        } catch (NumberFormatException e) {
            throw new FirefoxClientException("The content of the data from the given url address is not double.", e);
        } catch (MalformedURLException e) {
            throw new FirefoxClientException("The url is not correct.", e);
        } catch (IOException e) {
            throw new FirefoxClientException("Can not open the connection for the url successfully.", e);
        }
    }

    /**
     * <p>
     * This method indirectly stops the polling thread, by setting "stopPolling" to true, which tells the "run" method
     * loop to stop polling and exit gracefully.
     * </p>
     */
    public void stopThread() {
        stopPolling = true;
    }
}
