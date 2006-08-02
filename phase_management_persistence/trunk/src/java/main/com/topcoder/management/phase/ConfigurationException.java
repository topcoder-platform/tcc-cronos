package com.topcoder.management.phase;

import com.topcoder.util.errorhandling.BaseException;

/**
 *
 *
 */
public class ConfigurationException extends BaseException {
    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
