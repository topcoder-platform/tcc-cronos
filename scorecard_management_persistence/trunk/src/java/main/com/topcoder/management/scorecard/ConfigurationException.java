package com.topcoder.management.scorecard;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Represents an exception related to loading configuration settings. Inner exception should be provided to give
 * more details about the error. It is used in classes that have to load configuration settings such as
 * ScoreCardManagerImpl and InformixScorecardPersistence. Thread safety: This class is immutable and thread safe.
 *
 */
public class ConfigurationException extends BaseException {

    /**
     * Create a new ConfigurationException instance with the specified error message. Implementation notes: - Call
     * super(message);
     *
     *
     * @param message the error message of the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Create a new ConfigurationException instance with the specified error message and inner exception.
     * Implementation notes: - Call super(message, cause);
     *
     *
     * @param message the error message of the exception
     * @param cause the inner exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
