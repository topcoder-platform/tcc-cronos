/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.remoteapi.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.topcoder.bamboo.remoteapi.RemoteException;

/**
 * This class provides common remote API utility methods.
 * 
 * @author romanoTC
 * @version 1.0
 */
public abstract class AbstractRESTCommand {
    private static final int HTTP_STATUS_OK = 200;

    private final HttpClient httpClient = new HttpClient(new SimpleHttpConnectionManager());

    /**
     * Default constructor.
     */
    protected AbstractRESTCommand() {
        // empty
    }

    /**
     * Executes the POST method on the given URL sending the name/value information as XML (REST).
     * 
     * @param methodUrl the server's URL.
     * @param nameValuePairs the values to be added to the XML.
     * @return a String with the HTTP response.
     * @throws RemoteException if any error occurs while performing the HTTP connection.
     */
    public String executePostMethod(String methodUrl, NameValuePair[] nameValuePairs) throws RemoteException {
        PostMethod postMethod = new PostMethod(methodUrl);
        postMethod.setRequestBody(nameValuePairs);

        try {
            int httpStatus = httpClient.executeMethod(postMethod);
            if (httpStatus != HTTP_STATUS_OK) {
                throw new RemoteException("http returned error code " + httpStatus);
            }

            String response = StringUtils.trim(getHttpResponse(postMethod));
            if (responseIsAnErrorMessage(response)) {
                throw new RemoteException("error response: " + extractTagData("error", response));
            }

            if (response == null) {
                throw new RemoteException("invalid response [null]");
            }

            return response;
        } catch (IOException ex) {
            throw new RemoteException("unable to connect", ex);
        }
    }

    /**
     * Helper method to create an "auth" name/value pair with the given token.
     * 
     * @param token the token to use as a value in the pair.
     * @return the "auth" name/value pair with the given token.
     */
    protected NameValuePair getAuthArg(String token) {
        return new NameValuePair("auth", token);
    }

    /**
     * Extracts the value from a tag in the response string.
     * 
     * @param tagName the tag whose value should be returned
     * @param httpResponse the POST HTTP response.
     * @return the value of the tag.
     * @throws RemoteException if the tag is malformed or does not exist.
     */
    protected String extractTagData(String tagName, String httpResponse) throws RemoteException {
        int start = httpResponse.indexOf("<" + tagName + ">") + tagName.length() + 2;
        if (start < 0 || start >= httpResponse.length()) {
            throw new RemoteException(tagName + ": invalid start = " + start);
        }
        int end = httpResponse.indexOf("</" + tagName + ">");
        if (end < start || end >= httpResponse.length()) {
            throw new RemoteException(tagName + ": invalid end = " + end);
        }
        return httpResponse.substring(start, end);
    }

    /**
     * Verifies if the given response contains the "errors" tag.
     * 
     * @param httpResponse the response to be validated.
     * @return whether the given response contains the "errors" tag.
     */
    private static boolean responseIsAnErrorMessage(String httpResponse) {
        return httpResponse.startsWith("<errors>");
    }

    /**
     * Reads the POST response.
     * 
     * @param post the post method response.
     * @return the string containing the HTTP response.
     * @throws IOException if any occurs while reading from the HTTP connection.
     */
    private static String getHttpResponse(PostMethod post) throws IOException {
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return stringBuffer.toString();
    }
}
