/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.PropertyNotFoundException;
import com.topcoder.configuration.PropertyTypeMismatchException;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * The {@code Helper} class provides utility methods for classes of this component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is thread safe since it has no state.
 * </p>
 *
 * @author NODESIGNER, KennyAlive
 * @version 1.0
 */
public final class Helper {
    /**
     * Filename of the schema file used to validate XML presentation of the {@code PaymentEvent} class.
     */
    public static final String PAYMENT_EVENT_SCHEMA_FILENAME = "PaymentEvent.xsd";

    /**
     * Constant for loggerName parameter name.
     */
    private static final String LOGGER_NAME_PARAMETER = "loggerName";

    /**
     * Do not allow to instantiate this class. All utility methods are static ones.
     */
    private Helper() {
        // Empty
    }

    /**
     * Converts the given payment event instance to an XML string.
     *
     * @param paymentEvent
     *            the payment event to be converted (assumed not {@code null})
     *
     * @return the XML string containing payment event details (not {@code null})
     *
     * @throws JAXBException
     *            if any error occurred
     */
    public static String convertPaymentEventToXml(PaymentEvent paymentEvent) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PaymentEvent.class);
        Marshaller marshaller = context.createMarshaller();

        QName name = new QName("", "PaymentEvent");
        Object jaxbElement = new JAXBElement<PaymentEvent>(name, PaymentEvent.class, paymentEvent);

        Writer writer = new StringWriter();
        marshaller.marshal(jaxbElement, writer);
        return writer.toString();
    }

    /**
     * Extracts the payment event details from the given XML string.
     *
     * @param xml
     *            the XML string with payment event details (assumed not {@code null})
     *
     * @return the extracted payment event (not {@code null})
     *
     * @throws JAXBException
     *            if some JAXB error occurred (including XML validation failure)
     * @throws SAXException
     *            if error occurred while parsing schema for payment event XML
     */
    public static PaymentEvent getPaymentEventFromXml(String xml)
        throws JAXBException, SAXException {
        JAXBContext context = JAXBContext.newInstance(PaymentEvent.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // set schema for validation. If validation fails we will get JAXBException
        SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = ClassLoader.getSystemResource(PAYMENT_EVENT_SCHEMA_FILENAME);
        Schema schema = schemaFactory.newSchema(url);
        unmarshaller.setSchema(schema);

        // unmarshall XML and do validation
        StreamSource streamSource = new StreamSource(new StringReader(xml));
        return unmarshaller.unmarshal(streamSource, PaymentEvent.class).getValue();
    }

    /**
     * Converts the object to a string.
     *
     * @param object
     *            the object to be converted
     *
     * @return the string presentation of the given object
     */
    public static String toString(Object object) {
        if (object instanceof BigDecimal) {
            return object == null ? "null" : ((BigDecimal) object).toPlainString();
        } else if (object instanceof List<?>) {
            return toString((List<?>) object);
        } else if (object instanceof Map<?, ?>) {
            return toString((Map<?, ?>) object);
        }
        return String.valueOf(object);
    }

    /**
     * Converts the given List object to a string.
     *
     * @param list
     *            the List (not {@code null}) to be converted
     *
     * @return the string presentation of the given list
     */
    private static String toString(List<?> list) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Object element : list) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(toString(element));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Converts the given Map object to a string.
     *
     * @param map
     *            the Map (not {@code null}) to be converted
     *
     * @return the string presentation of the given map
     */
    private static String toString(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append("(").append(toString(key)).append(", ").append(toString(value)).append(")");
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Closes the database connection.
     *
     * @param connection
     *            the connection
     * @param log
     *           the logger for logging exceptions
     * @param signature
     *           the method signature of the caller
     */
    public static void closeConnection(Connection connection, Log log, String signature) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) { // Just log and ignore
                LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
            }
        }
    }

    /**
     * Gets a Log instance with name specified by configuration object.
     *
     * @param config
     *            the configuration object
     *
     * @return the Log instance or {@code null} if the logger name is not specified.
     *
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *             if 'loggerName' is not a string, an empty string or some error occurred
     */
    public static Log getLog(ConfigurationObject config) {
        String loggerName = getProperty(config, LOGGER_NAME_PARAMETER, false);
        return (loggerName == null) ? null : LogManager.getLog(loggerName);
    }

    /**
     * Gets a property value from the given configuration object.
     *
     * @param config
     *              the configuration object
     * @param key
     *              the property key
     * @param required
     *              defines whether the property is a required property or an optional one
     *
     * @return the retrieved property value or {@code null} when property is not found and the required flag is false
     *
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *             when the property is missing and the required flag is true, or the property value is not a String,
     *             or it's an empty string, or some error occurred when accessing configuration object
     */
    public static String getProperty(ConfigurationObject config, String key, boolean required) {
        try {
            String value = config.getPropertyValue(key, String.class, required);

            if (value != null && value.trim().isEmpty()) {
                throw new AmazonFlexiblePaymentSystemComponentConfigurationException("The property '" + key
                    + "' can not be empty");
            }
            return value;
        } catch (ConfigurationAccessException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                "An error occurred while accessing the configuration", e);
        } catch (PropertyTypeMismatchException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "The property should be of type String", e);
        } catch (PropertyNotFoundException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "The required property '" + key + "' is not found", e);
        }
    }

    /**
     * Gets a child ConfigurationObject from given configuration object.
     *
     * @param configuration
     *            the parent configuration object
     * @param childName
     *            the name of the child configuration object (not {@code null}/empty)
     *
     * @return the retrieved child ConfigurationObject (not {@code null})
     *
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *             if the child ConfigurationObject is missing or some error occurred
     */
    public static ConfigurationObject getChildConfiguration(ConfigurationObject configuration, String childName) {
        try {
            ConfigurationObject child = configuration.getChild(childName);

            if (child == null) {
                throw new AmazonFlexiblePaymentSystemComponentConfigurationException("The child '" + childName
                        + "' is not found");
            }
            return child;
        } catch (ConfigurationAccessException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                "An error occurred while accessing the configuration object", e);
        }
    }
}
