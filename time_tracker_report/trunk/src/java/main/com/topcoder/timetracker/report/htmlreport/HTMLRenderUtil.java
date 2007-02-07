/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.htmlreport;

import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.ColumnDecorator;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.timetracker.report.StyleConstant;
import com.topcoder.timetracker.report.dbhandler.DBHandler;
import com.topcoder.timetracker.report.dbhandler.DBHandlerFactory;
import com.topcoder.timetracker.report.dbhandler.DBHandlerNotFoundException;
import com.topcoder.timetracker.report.dbhandler.ReportSQLException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * This class provides the rendering of a {@link java.sql.ResultSet} obtained from a call to {@link
 * DBHandler#getReportData(ReportConfiguration)} into a HTML table.
 * <p/>
 * This class has been introduced to extract and centralize the otherwise duplicate code from the {@link
 * com.topcoder.timetracker.report.AbstractReport#executeReport(ReportConfiguration)} implementations of {@link
 * ExpenseReport}, {@link TimeExpenseReport} and {@link TimeReport}.
 * <p/>
 * This class is completely thread safe as it has only static methods and cannot be instantiated.
 *
 * @author traugust
 * @version 1.0
 * @see #renderTable(ReportConfiguration,DBHandlerFactory,Aggregator[])
 */
final class HTMLRenderUtil {
    /**
     * This is the name of the property used to lookup the DBHandler name from {@link ConfigManager}.
     */
    private static final String DBHANDLER_PROPERTY_NAME = "DBHandler";
    /**
     * This is a constant to be used to check whether a value in a {@link ResultSet} is a placeholder for a NULL value.
     * This is needed because the SELECT NULL is not supported by all databases.
     */
    private static final BigDecimal NULL_VALUE_PLACEHOLDER = new BigDecimal("-1");

    /**
     * Maximum Length to display for a description.
     */
    private static final int MAX_DESC_LENGTH = 45;

    /**
     * Maximum Length of a line in the tooltip.
     */
    private static final int MAX_TOOLTIP_CHUNK = 48;

    /**
     * This is a private constructor that has been added to avoid instantiation of this utility class.
     */
    private HTMLRenderUtil() {
    }

    /**
     * This method uses the given {@link DBHandler} and {@link ReportConfiguration} to call {@link
     * DBHandler#getReportData(ReportConfiguration)}. The {@link ResultSet} obtained from the invocation of this method
     * will then be iterated and rendered into a HTML table as specified by the rendering configuration contained in the
     * given {@link ReportConfiguration}.
     * <p/>
     * Additionally a {@link Aggregator}[] can be given to this method. Upon each row of the {@link ResultSet} iterated
     * during the execution of this method, the numeric value of the {@link Column} of each aggregator ({@link
     * Aggregator#getColumn()}) is retrieved from the {@link ResultSet} and the aggregator's {@link
     * Aggregator#add(java.math.BigDecimal)} method is called with the value retrieved, unless the value retrieved was a
     * <tt>null</tt> value (as identified by {@link ResultSet#wasNull()}) . This way the sum of all rows of a specific
     * {@link Column} in the {@link ResultSet} can be calculated during rendering. The calculated sum can be obtained by
     * calling the aggregators {@link Aggregator#getCurrentValue()} method after this method has returned without
     * Exceptions.
     *
     * @param config           the ReportConfiguration that describes the query to be executed and the customization
     *                         that shall occur upon HTML rendering
     * @param dbHandlerFactory the {@link DBHandlerFactory} to be used to execute the query
     * @param aggregators      an optional Array of {@link HTMLRenderUtil.Aggregator}s that can be used for sum
     *                         calculations on the {@link ResultSet} rendered by this method, or <tt>null</tt> to use no
     *                         aggregators
     *
     * @return the HTML rendered table
     *
     * @throws ReportSQLException           in case errors occur during Database access or any of the given aggregators
     *                                      references a {@link Column} which is not part of the {@link ResultSet} for
     *                                      the given {@link ReportConfiguration}
     * @throws ReportConfigurationException in case a misconfiguration is encountered during lookup of config data
     * @throws DBHandlerNotFoundException   in case the name of the {@link DBHandler} read from the {@link
     *                                      ReportConfiguration#getNamespace()} namespace is not registered with the
     *                                      given {@link DBHandlerFactory}
     * @throws NullPointerException         if the config or handler given was <tt>null</tt>
     * @throws IllegalArgumentException     if the given aggregators array contained a <tt>null</tt>-value
     */
    static String renderTable(final ReportConfiguration config, final DBHandlerFactory dbHandlerFactory,
                              final Aggregator[] aggregators)
        throws ReportSQLException, ReportConfigurationException, DBHandlerNotFoundException {
        if (config == null) {
            throw new NullPointerException("The parameter named [config] was null.");
        }
        if (dbHandlerFactory == null) {
            throw new NullPointerException("The parameter named [dbHandlerFactory] was null.");
        }
        if (aggregators != null) {
            for (int i = 0; i < aggregators.length; i++) {
                final Aggregator aggregator = aggregators[i];
                if (aggregator == null) {
                    throw new IllegalArgumentException(
                        "The given parameter named [aggregators] contained a null value.");
                }
            }
        }

        final String dbHandlerName;
        final String namespace = config.getNamespace();
        try {
            dbHandlerName = ConfigManager.getInstance().getString(namespace, DBHANDLER_PROPERTY_NAME);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("Unable to lookup a DBHandler name from property ["
                + DBHANDLER_PROPERTY_NAME + "], namespace [" + namespace + "].", e);
        }
        if (dbHandlerName == null) {
            throw new ReportConfigurationException(
                "Unable to lookup a DBHandler name from property [" + DBHANDLER_PROPERTY_NAME + "], namespace ["
                    + namespace + "], the value looked up from property [" + DBHANDLER_PROPERTY_NAME + "] was null.");
        }
        if (dbHandlerName.trim().length() == 0) {
            throw new ReportConfigurationException(
                "Unable to lookup a DBHandler name from property [" + DBHANDLER_PROPERTY_NAME + "], namespace ["
                    + namespace + "], the value looked up from property [" + DBHANDLER_PROPERTY_NAME
                    + "] was an empty String.");
        }

        final DBHandler dbHandler = dbHandlerFactory.getDBHandler(dbHandlerName);

        final Map styles = config.getStyles();
        final List columnDecorators = config.getColumnDecorators();

        final StringBuffer ret = new StringBuffer();

        //lookup the styles to be used
        // these values are never null, so no checking here
        final String trStyle = safeGetStyleValue(styles, StyleConstant.TR_STYLE);
        final String tdStyle = (String) styles.get(StyleConstant.TD_STYLE);
        final String tableStyle = (String) styles.get(StyleConstant.TABLE_STYLE);
        final String thStyle = safeGetStyleValue(styles, StyleConstant.TH_STYLE);
		
        String tableId = "tbl" + config.hashCode();
        ret.append("<SCRIPT language=\"JavaScript\" type=\"text/javascript\">\r\n");
        ret.append("var "+ tableId + " = new SortTable(\"" + tableId + "\");\r\n");
        for (Iterator iterator = columnDecorators.iterator(); iterator.hasNext();) {
            iterator.next();
            ret.append(tableId + ".AddColumn(\"\",'");//bgColor=\"#FFFFFF\" ");
            ret.append(tdStyle);
            //ret.append(columnDecorator.getStyle());
            ret.append("',\"\",\"\");\r\n");
        }
        
        // execute the query
        final ResultSet rs = dbHandler.getReportData(config);
        try {
            try {
            	int li = 0;
                while (rs.next()) {
                	li++;
                    ret.append(tableId + ".AddLine(");
                    boolean first = true;                    
                    //the cells
                    for (Iterator iterator = columnDecorators.iterator(); iterator.hasNext();) {
                        final ColumnDecorator columnDecorator = (ColumnDecorator) iterator.next();
                        try {
                            String string = rs.getString(columnDecorator.getColumnName());
                            int intValue = 1;
                            float floatValue = 0.0f;

                            // this is to check whether the dummy '-1' value used as replacement for
                            // the not supported "Select Null" is the value of this row + column
                            // as this is actually meant to be a null value, render it to '*' also
                            // As the string value also can be "-1.00", checking the string
                            // value is not sufficient.
                            try {
                                intValue = Integer.parseInt(string);
                            } catch (Exception e) {
                                //ignored, can occur on non-numeric columns
                            }
                            try {
                            	floatValue = Float.parseFloat(string);
                            } catch (Exception e) {
                                //ignored, can occur on non-numeric columns
                            }

                            // if the value returned was a null value, render it as '*'
                            if (string == null || rs.wasNull() || intValue <= 0 || floatValue < 0) {
                                string = "0";
                            }
                            if (!first) {
                            	ret.append(",");
                            }
                            first = false;
                            // let the decorator render the value
                            //ret.append("\"" + escapeForHTMLTagContent(escapeForQuote(columnDecorator.decorateColumn(string)))); //replace by block below

                            String actualData = escapeForHTMLTagContent(columnDecorator.decorateColumn(string));
                            String wrapper = "\"" + escapeForQuote(actualData);

                            if (columnDecorator.getColumnName().equalsIgnoreCase("description"))    {

                                wrapper = "\"" + escapeForQuote(prepareStringForToolTip(actualData));
                            }
                            
                            ret.append(wrapper);

                            if (li == 1) {
                            	ret.append("<br>");
                            	ret.append("<" + columnDecorator.getStyle() + ">");
                            }
                            ret.append("\"");
                        } catch (SQLException e) {
                            throw new ReportSQLException("Unable to read a value for column ["
                                + columnDecorator.getColumnName() + "] from the ResultSet.", e);
                        }                        
                    }
                    ret.append(");\r\n");
                    //update all aggregators with their column's data of the current row
                    if (aggregators != null) {
                        for (int i = 0; i < aggregators.length; i++) {
                            final Aggregator aggregator = aggregators[i];
                            try {
                                final BigDecimal value = rs.getBigDecimal(aggregator.getColumn().getName());
                                if (!rs.wasNull() && NULL_VALUE_PLACEHOLDER.compareTo(value) != 0) {
                                    aggregator.add(value);
                                }
                            } catch (SQLException e) {
                                throw new ReportSQLException(
                                    "Unable to read a value for column [" + aggregator.getColumn()
                                        + "] from the ResultSet.",
                                    e);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new ReportSQLException("An error occurred while iterating over the ResultSet." + e.getMessage(), e);
            }
        } finally {
            // release the resultSet no matter whether there was an exception
            //
            // This is an additional finally-block, as we separately catch the
            // SQLExceptions at the points where they can occur to give additional
            // context information on the exceptions caught
            try {
                dbHandler.release(rs);
            } catch (ReportSQLException e) {
                // This exception is to be ignored an will not be propagated
                // as we either have a valid result and must not fail in that case:
                // see https://software.topcoder.com/forum/c_forum_message.jsp?f=16191891&r=20805646
                // or already have an exception
            }
        }
        ret.append("</SCRIPT>");
		
        //the table
        ret.append("<TABLE ");
        ret.append(tableStyle);
        ret.append(">\r\n");

        //the header row
        ret.append("<TBODY><TR");

        ret.append(trStyle);

        ret.append(">\r\n");

        //the table header cells
        int i = 0;
        for (Iterator iterator = columnDecorators.iterator(); iterator.hasNext();) {
            final ColumnDecorator columnDecorator = (ColumnDecorator) iterator.next();
            ret.append("<TH>");
            ret.append(columnDecorator.getColumnDisplayText());
            if (columnDecorator.isSortable()) {
            	ret.append("<a href=\"javascript:SortRows(document," + tableId + "," + i + ")\">");
            	ret.append("<img src=\"images/icon_down.gif\" width=\"11\" height=\"8\"></a>");
            }
            ret.append("<br>");
    		ret.append("<" + columnDecorator.getStyle() + ">");
            ret.append("</TH>\r\n");
            ++i;
        }
        //end of header row
        //ret.append("</TR></TBODY></TABLE>"); //we stop putting the header in its own table
        //ret.append("<table class='results_table' name=\"" + tableId); // we remove the beginning of data table
        //ret.append( "\" id=\"" + tableId + "\" width='100%'" + ">");
        ret.append("</TR>");
        


        ret.append("<SCRIPT>" +tableId + ".WriteRows();</SCRIPT>");	   
        ret.append("</TABLE>");
        return ret.toString();
    }

    /**
     * shortens a string for display
     * @param text
     * @return the substring from the beginning to the max allowable characters
     */
    private static String getShortenedVersion(String text) {
        if (text.length() > MAX_DESC_LENGTH)   {
            return text.substring(0, MAX_DESC_LENGTH - 1) + "...";
        }
        return text;
    }

    /**
     * Breaks a string into chunks of BREAK_LENGTH
     * characters (depending on spacing).
     *
     * @return a string with breaks inserted.
     */
    public static String breakIntoChunks(String original, int size) {
        StringBuilder tooltip = new StringBuilder("");
        StringBuilder remnant = new StringBuilder(original);
        while(remnant.length() > size)
        {
            int ix = size;
            while ((ix >= 0)
                    && (remnant.charAt(ix) != ' ')
                    && (remnant.charAt(ix) != '\n')
                    && (remnant.charAt(ix) != '\t')
                   )
            {
                --ix;
            }
            if (ix < 0)
            {
                ix = size;
            }
            tooltip.append(remnant.substring(0, ix + 1));
            tooltip.append("\\\\n");
            remnant.delete(0, ix + 1);
        }
        tooltip.append(remnant);
        return tooltip.toString();
    }

    /**
     * turns a string into an HTML block
     * that will appear as a shortened string followed by an image element
     * that shows the whole string on Roll-Over
     * @param actualData the string to display
     * @return an HTML block
     */
    private static String prepareStringForToolTip(String actualData)
    {
        return getShortenedVersion(actualData)
                + "<img border=\"0\" src=\"images/icon_plus.gif\" width=\"12\" height=\"12\" style=\"margin-bottom:-3px;padding-left:5px;\" onmouseover=\"return escape('"
                + breakIntoChunks(actualData, MAX_TOOLTIP_CHUNK)
                + "');\">";
    }
    
    /**
     * This method safely retrieves a String value to be used as HTML style fragment from the given map for the given
     * key, i.e. when an non-<tt>null</tt>, non-empty String is found,<tt> STYLE=&quot;&lt;the value
     * found&gt;&quot;</tt> (note the preceding blank) . Any character that would be illegal in an HTML tag attribute is
     * escaped in the String returned.
     * <p/>
     * This is done, as in the context where this method is used ({@link #renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}) <tt>null</tt>s and empty Strings have equal meaning, but null
     * checking can be performed somewhat more efficient.
     *
     * @param map the map to retrieve the value from
     * @param key the key for which to retrieve the value from the map
     *
     * @return an HTML Style attribute fragment containing the value retrieved, or an empty String if no value was found
     *         for the given key in the given map
     */
    private static String safeGetStyleValue(final Map map, final StyleConstant key) {
        final String s = (String) map.get(key);
        if (s == null || s.trim().length() == 0) {
            return "";
        }
        return " STYLE=\"" + escapeForHTMLTagAttribute(s) + "\"";
    }

    /**
     * Replace characters having special meaning <em>inside</em> HTML tags with their escaped equivalents, using
     * character entities such as <tt>'&amp;amp;'</tt>.
     * <p/>
     * The escaped characters are :   '&lt;','&gt;','&quot;' and '&amp;'.
     * <p/>
     * This method ensures that arbitrary text appearing inside a tag attribute value does not break HTML syntax.
     *
     * @param string the String to be escaped
     *
     * @return the escaped version of the given String
     */
    private static String escapeForHTMLTagAttribute(final String string) {
        final StringBuffer result = new StringBuffer();

        final StringCharacterIterator iterator = new StringCharacterIterator(string);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            if (character == '\"') {
                result.append("&quot;");
            } else if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '&') {
                result.append("&amp;");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * Replace characters having special meaning <em>inside</em> HTML tags with their escaped equivalents, using
     * character entities such as <tt>'&amp;amp;'</tt>.
     *
     * @param string the String to be escaped
     *
     * @return the escaped version of the given String
     */
    private static String escapeForQuote(final String string) {
        final StringBuffer result = new StringBuffer();

        final StringCharacterIterator iterator = new StringCharacterIterator(string);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            if (character == '\"') {
                result.append("\\\"");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * Replace characters having special meaning <em>between</em> HTML tags with their escaped equivalents, using
     * character entities such as <tt>'&amp;amp;'</tt>.
     * <p/>
     * The escaped characters are :  '&lt;','&gt;' and '&amp;'.
     * <p/>
     * This method ensures that arbitrary text appearing inside a tag does not break HTML syntax.
     *
     * @param string the String to be escaped
     *
     * @return the escaped version of the given String
     */
    private static String escapeForHTMLTagContent(final String string) {
        final StringBuffer result = new StringBuffer();

        final StringCharacterIterator iterator = new StringCharacterIterator(string);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '&') {
                result.append("&amp;");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * An Aggregator can be used to aggregate the values of a {@link ResultSet}'s rows for the aggregator's column.
     * <p/>
     * Aggregators can be given to {@link HTMLRenderUtil#renderTable(ReportConfiguration,DBHandlerFactory,
     * HTMLRenderUtil.Aggregator[])} and will get their {@link #add(java.math.BigDecimal)} method called with the column
     * value of each of the rows of the {@link ResultSet} iterated during execution of that method. After the invocation
     * of {@link HTMLRenderUtil#renderTable(ReportConfiguration,DBHandlerFactory,HTMLRenderUtil.Aggregator[])} has
     * returned, the collator will contain the sum of all values encountered during the {@link ResultSet} iteration.
     * This value can be retrieved by a call to {@link #getCurrentValue()}.
     * <p/>
     * This class could be subclassed to implement some different aggregation strategy, e.g. calculating the average
     * instead of the sum of all values.
     * <p/>
     * Instances of this class are not thread safe and should thus be used in a tread safe manner, i.e. should only be
     * used from one thread and should be held privately (not exposed externally via public API).
     */

    static final class Aggregator {
        /**
         * the current value of this Aggregator.
         */
        private BigDecimal currentValue = new BigDecimal(0);
        /**
         * the column over whose values this Aggregator should aggregate.
         */
        private final Column column;

        /**
         * Creates a new Aggregator for the given column.
         *
         * @param column the column over whose values this Aggregator should aggregate
         *
         * @throws NullPointerException if the given column is <tt>null</tt>
         */
        Aggregator(final Column column) {
            if (column == null) {
                throw new NullPointerException("The parameter named [column] was null.");
            }

            this.column = column;
        }

        /**
         * Getter for this Aggregator's column.
         *
         * @return the column over whose values this Aggregator should aggregate
         */
        public Column getColumn() {
            return column;
        }

        /**
         * Getter for the current value.
         *
         * @return the current value of this Aggregator
         */
        public BigDecimal getCurrentValue() {
            return currentValue;
        }

        /**
         * This method adds the given value to this Aggregator's current value.
         *
         * @param value the value to be added
         *
         * @throws NullPointerException if the given value is <tt>null</tt>
         */
        public void add(final BigDecimal value) {
            if (value == null) {
                throw new NullPointerException("The parameter named [value] was null.");
            }

            currentValue = currentValue.add(value);
        }
    }
}
