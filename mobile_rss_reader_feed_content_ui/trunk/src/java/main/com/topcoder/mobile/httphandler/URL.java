/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobile.httphandler;

/**
 * <p>
 * This class represents a generic URL. This is not only related with http or https connection but also for generic
 * urls. The URL is defined from the protocol, the host, the path after the host and the optional port. For example:
 * </p>
 * <p>
 * http://forums.topcoder.com/?module=ThreadList&amp;forumID=516242&amp;mc=66
 * </p>
 * <p>
 * &quot;http&quot; is the protocol, &quot;forums.topcoder.com&quot; is the host and
 * &quot;/?module=ThreadList&amp;forumID=516242&amp;mc=66&quot; is the path after the host. In this case thr\e port is
 * omitted, but with the port will be:
 * </p>
 * <p>
 * http://forums.topcoder.com:80/?module=ThreadList&amp;forumID=516242&amp;mc=66
 * </p>
 * <p>
 * Thereby, the class with the many constructors, parses the argument of constructors to construct its properties.
 * </p>
 * <p>
 * Thread safe: It's impossible to change the state of this class, hence it's thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class URL {

    /**
     * <p>
     * Represents the protocol of this url, for example &quot;http&quot; or &quot;https&quot;.
     * </p>
     */
    private final String protocol;

    /**
     * <p>
     * Represents the host this url, for example &quot;www.topcoder.com&quot;.
     * </p>
     * <p>
     * Valid Values: not null and not trim to empty
     * </p>
     */
    private final String host;

    /**
     * <p>
     * Represents the port this url, for example &quot;80&quot;.
     * </p>
     * <p>
     * Valid Values: -1 means that is not set, 0 inclusive...port...65535 inclusive
     * </p>
     */
    private final int port;

    /**
     * <p>
     * Represents the path this url, for example /?module=ThreadList&amp;forumID=516242&amp;mc=66.
     * </p>
     * <p>
     * Valid Values: can be null and empty
     * </p>
     */
    private final String path;

    /**
     * <p>
     * Construct an URL with its string representation.
     * </p>
     * @param url
     *            the string representation of url
     * @throws IllegalArgumentException
     *             if url is null, trim to empty or don't have the structure defined in the implementation algorithm
     */
    public URL(String url) {
        this.protocol = "protocol";
        this.host = "host";
        this.port = 80;
        this.path = "/path";
    }

    /**
     * <p>
     * Construct an URL with its properties.
     * </p>
     * @param protocol
     *            the url's protocol
     * @param host
     *            the url's host
     * @param port
     *            the url's port; possible -1: means that is not set
     * @param path
     *            the url's path; possibly null and empty
     * @throws IllegalArgumentException
     *             if protocol or host are null, if protocol or host are trim to empty, if port < -1 or > 65535
     */
    public URL(String protocol, String host, int port, String path) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
    }

    /**
     * <p>
     * Construct an URL with its properties without port.
     * </p>
     * @param protocol
     *            the url's protocol
     * @param host
     *            the url's host
     * @param path
     *            the url's path
     * @throws IllegalArgumentException
     *             if protocol or host are null, if protocol or host or path are trim to empty
     */
    public URL(String protocol, String host, String path) {
        this(protocol, host, -1, path);
    }

    /**
     * <p>
     * Return the string representation of Url. This method will be used in HTTPRequest class to define the
     * HttpConnection.
     * </p>
     * @return the url string representation of Url
     */
    public String getURL() {
        // if the port is -1, then it should be 'protocol://host/path'.
        String portBuff = port == -1 ? "" : ":" + port;
        // if the path is null, add '', else add the path
        String pathBuff = path == null ? "" : path;
        return new StringBuffer().append(protocol).append("://").append(host).append(portBuff).append(pathBuff)
                .toString();
    }

    /**
     * <p>
     * Return the string representation of Url.
     * </p>
     * @return the the string representation of Url
     */
    public String toString() {
        return getURL();
    }

    /**
     * <p>
     * Compare the URL object with another object.
     * </p>
     * @return true if the obj is an URL and has the properties equal to this URL, false otherwise
     * @param obj the object to compare with.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof URL)) {
            return false;
        }
        URL url = (URL) obj;
        boolean pathEquals = (this.path == null) ? (url.path == null) : this.path.equals(url.path);
        // check if all the properties are the same, protocol ignoring case sensitive, the host and path is case
        // sensitive.
        return this.host.equals(url.host) && pathEquals && (this.protocol.equalsIgnoreCase(url.protocol))
                && (this.port == url.port);
    }

    /**
     * <p>
     * Return the hashcode of URL. This method must be implemented by the contract with the equals method.
     * </p>
     * @return the hashCode of URL string representation
     */
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * <p>
     * Return the url's host.
     * </p>
     *
     * @return the url's host
     */
    public String getHost() {
        return host;
    }

    /**
     * <p>
     * Return the path of URL. Return null if doesn't exist.
     * </p>
     *
     * @return the path of URL.
     */
    public String getPath() {
        return path;
    }

    /**
     * <p>
     * Return the url's port, -1 if is not defined.
     * </p>
     *
     * @return the url's port, -1 if is not defined.
     */
    public int getPort() {
        return port;
    }

    /**
     * <p>
     * Return the protocol of URL. This will be used in HTTPRequest to define if the URL is a http or a https.
     * </p>
     *
     * @return the url's protocol
     */
    public String getProtocol() {
        return protocol;
    }
}
