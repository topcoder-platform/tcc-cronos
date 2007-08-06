package com.topcoder.shared.util;

import com.topcoder.shared.util.logging.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Locale;
import java.io.UnsupportedEncodingException;

/**
 * @author unknown
 * @version  $Revision: 1.8 $
 */
public final class TCResourceBundle {

    private static Logger log = Logger.getLogger(TCResourceBundle.class);

    private ResourceBundle bundle;

    /**
     *
     * @param baseName
     */
    public TCResourceBundle(String baseName) {
        this(baseName, Locale.US);
    }

    public TCResourceBundle(String baseName, Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            log.error(e);
        }

    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return  String
     */
    public String getProperty(String key, String defaultValue) {
        String ret = null;
        try {
            ret = getProperty(key);
            //log.debug("setting " + key + " = " + ret);z
        } catch (MissingResourceException e) {
            ret = defaultValue;
            log.debug("key not found, setting default " + key + " = " + ret);
        }
        return ret;
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return String
     */
    public int getIntProperty(String key, int defaultValue) {
        String str = getProperty(key, Integer.toString(defaultValue));
        if (str == null) {
        	return defaultValue;
        }
        return Integer.parseInt(str.trim());
    }

    /**
     * Type-safe getter for double properties, with default value.
     *
     * @param key The property's key.
     * @param defaultValue Value to use if the key isn't found.
     * @return The property's value as a double.
     */
    public double getDoubleProperty(String key, double defaultValue) {
        String str = getProperty(key, Double.toString(defaultValue));
        if (str == null) {
        	return defaultValue;
        }
        return Double.parseDouble(str.trim());
    }

    /**
     *
     * @param key
     * @return
     * @throws MissingResourceException
     */
    public String getProperty(String key) throws MissingResourceException {
    	if (bundle == null) {
    		return null;
    	}
        String ret = bundle.getString(key);
        if (ret == null) {
        	return null;
        }
        ret = ret.trim();
        try {
            ret = new String(ret.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            //ignore and just return the value we got. this shouldn't happen
            log.warn(e.getMessage());
        }
        return ret;
    }

    /**
     *
     * @param key
     * @return
     * @throws MissingResourceException
     */
    public int getIntProperty(String key) throws MissingResourceException {
        String str = getProperty(key);
        return Integer.parseInt(str.trim());
    }

    /**
     * Type-safe getter for double properties.
     *
     * @param key The property's key.
     * @return The property's value as a double.
     * @throws java.util.MissingResourceException If the key isn't found.
     */
    public double getDoubleProperty(String key) throws MissingResourceException {
        String str = getProperty(key);
        return Double.parseDouble(str.trim());
    }

}
