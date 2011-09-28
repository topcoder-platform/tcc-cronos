/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.topcoder.accounting.action.Helper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is a custom DefaultTypeConverter subclass that is responsible for
 * conversion between String and Date, according to the date format string
 * configured as servlet parameter. It is used by Struts2 to properly convert
 * the date string into Date object.
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> This class is thread-safe because it's
 * immutable assuming input parameters are used safely by the caller, and
 * because the super class is thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class DateConverter extends DefaultTypeConverter {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value. It is final and won't change once it is initialized as
     * part of variable.
     */
    private static final Log LOG = LogManager.getLog(DateConverter.class
            .toString());

    /** The date pattern parameter. */
    private static final String DATE_PATTERN = "datePattern";

    /**
     * This is the default constructor for the class.
     *
     */
    public DateConverter() {
    }

    /**
     * Convert a value object into certain type.
     *
     * @param context The context for the conversion.
     * @param value The value to convert.
     * @param toType the destination type of the conversion
     * @return the converted object for the value
     * @throws DateConversionException if any error occurs
     *
     */
    @SuppressWarnings("unchecked")
    public Object convertValue(Map<String, Object> context, Object value,
            Class toType) {
        final String methodName = "DateConverter.convertValue";
        String paramInfo = "[Input parameters[{context:"
                + String.valueOf(context) + "},{value:"
                + String.valueOf(value) + "},{toType:" + String.valueOf(toType)
                + "}]]";
        LOG.log(Level.DEBUG, "[Entering the method {" + methodName + "}] "
                + paramInfo);
        try {
            // Get the servlet context
            ServletContext servletContext = ServletActionContext
                    .getServletContext();
            if (servletContext == null) {
                throw Helper.logAndThrow(LOG, methodName,
                        new DateConversionException(
                                "ServletContext is null in context."));
            }
            // Get the date pattern string from servlet context
            String datePattern = servletContext.getInitParameter(DATE_PATTERN);
            if (datePattern == null) {
                throw Helper
                        .logAndThrow(
                                LOG,
                                methodName,
                                new DateConversionException(
                                        "The initial date pattern parameter is missing."));
            }
            // Create a SimpleDateFormat from the date pattern string
            DateFormat format = new SimpleDateFormat(datePattern);
            if (toType == String.class) {
                String result = format.format(value);
                Helper.logExitMethod(LOG, methodName, result);
                return result;
            } else if (toType == Date.class) {
                // Set the lenient parsing to false
                format.setLenient(false);
                // Convert the value to a string array
                String[] s = (String[]) value;
                // Parse the first string in the array
                Date date = format.parse(s[0]);
                Helper.logExitMethod(LOG, methodName, date);
                return date;
            }
        } catch (IllegalArgumentException e) {
            throw Helper.logAndThrow(LOG, methodName,
                    new DateConversionException(
                            "Fail to convert the given value.", e));
        } catch (ParseException e) {
            throw Helper.logAndThrow(LOG, methodName,
                    new DateConversionException(
                            "Fail to parse the date string.", e));
        } catch (ClassCastException e) {
            throw Helper.logAndThrow(LOG, methodName,
                    new DateConversionException("Fail to cast the class type.",
                            e));
        }
        Helper.logExitMethod(LOG, methodName, "null");
        return null;
    }
}
