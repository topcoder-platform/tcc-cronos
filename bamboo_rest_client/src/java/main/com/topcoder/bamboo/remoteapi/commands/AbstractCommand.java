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
 * This class provides common remote api utility methods.
 */
public abstract class AbstractCommand {
    private static final int HTTP_STATUS_OK = 200;

    private final HttpClient httpClient = new HttpClient(new SimpleHttpConnectionManager());

    /**
     * Default constructor.
     */
    protected AbstractCommand() {
        // empty
    }

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

    protected NameValuePair getAuthArg(String token) {
        return new NameValuePair("auth", token);
    }

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

    private static boolean responseIsAnErrorMessage(String httpResponse) {
        return httpResponse.startsWith("<errors>");
    }

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
