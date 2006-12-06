/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * A Mock class which extends the <code>JSObject</code> class for testing. Some inner fields will be maintained in this
 * class to store the information of the coming events.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockJSObject extends JSObject {
    /** Represents the member information for testing. */
    private Map memberMap = new HashMap();

    /** Represents the content written by function document.write. */
    private StringBuffer document = new StringBuffer();

    /** Represents the all function names call from eval method. */
    private List functionNames = new ArrayList();

    /** Represents the url for the current window. */
    private String currentWindowUrl = null;

    /**
     * <p>
     * Creates the MockJSObject instance.
     * </p>
     */
    public MockJSObject() {
        this.setMember("document", new MockJSObject("document"));
        ((JSObject) this.getMember("document")).setMember("cookie", "");
        ((JSObject) this.getMember("document")).setMember("location", new MockJSObject("location"));
    }

    /**
     * <p>
     * Creates the MockJSObject instance.
     * </p>
     *
     * @param name the name of the JSObject.
     */
    public MockJSObject(String name) {
    }

    /**
     * <p>
     * This method is not implemented.
     * </p>
     *
     * @param arg0 ignored.
     *
     * @return always null.
     */
    public Object getSlot(int arg0) {
        return null;
    }

    /**
     * <p>
     * This method is not implemented.
     * </p>
     *
     * @param arg0 ignored.
     * @param arg1 ignored.
     */
    public void setSlot(int arg0, Object arg1) {
    }

    /**
     * <p>
     * This method is not implemented.
     * </p>
     *
     * @param arg0 ignored.
     */
    public void removeMember(String arg0) {
    }

    /**
     * <p>
     * A mock method to add the functionName to the list.
     * </p>
     *
     * @param functionName The name of the Javascript function to eval.
     *
     * @return the function name to add.
     */
    public Object eval(String functionName) {
        functionNames.add(functionName.substring(0, functionName.indexOf("(")));

        return functionName;
    }

    /**
     * <p>
     * Retrive the functionName list.
     * </p>
     *
     * @return the functionName list.
     */
    public List getFunctionNames() {
        return functionNames;
    }

    /**
     * <p>
     * Gets the url for the current window.
     * </p>
     *
     * @return the url for the current window.
     */
    public String getCurrentWindowUrl() {
        return currentWindowUrl;
    }

    /**
     * <p>
     * Retrive value from map with the key.
     * </p>
     *
     * @param key whose associated value is to be returned.
     *
     * @return the value to which this map maps the specified key, or null if the map contains no mapping for this key.
     */
    public Object getMember(String key) {
        return memberMap.get(key);
    }

    /**
     * <p>
     * Associates the specified value with the specified key in memberMap.
     * </p>
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     */
    public void setMember(String key, Object value) {
        if (key.equals("href")) {
            currentWindowUrl = value.toString();
        }
        if (key.equals("cookie")) {
            String pre = (String) memberMap.get("cookie");
            int pos = ((String) value).indexOf(";");
            if (pos < 0) {
                memberMap.put(key, pre + ";" + value);
            } else {
                memberMap.put(key, pre + ";" + ((String) value).substring(0, pos));
            }
        } else {
            memberMap.put(key, value);
        }
    }

    /**
     * <p>
     * Simulates the call function of js client.
     * </p>
     *
     * @param functionName the function name.
     * @param args the arguments of the function.
     *
     * @return some object.
     */
    public Object call(String functionName, Object[] args) {
        if (functionName.equals("open")) {
            currentWindowUrl = args[0].toString();
            return new MockJSObject();
        } else if (functionName.equals("write")) {
            for (int i = 0; i < args.length; i++) {
                document.append(args[i]);
            }
        }

        return null;
    }

    /**
     * <p>
     * Gets the content written by function document.write.
     * </p>
     *
     * @return the content written by function document.write.
     */
    public String getDocument() {
        return document.toString();
    }
}
