/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.result;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.ParameterCheck;
import com.orpheus.game.XMLHelper;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.rssgenerator.DataStore;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.SearchCriteria;
import com.topcoder.util.rssgenerator.datastore.SearchCriteriaImpl;
import com.topcoder.util.rssgenerator.io.RSSWriteException;
import com.topcoder.util.rssgenerator.io.atom10.Atom10Writer;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Result;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * A Result for responding to message polling requests. It will obtain a DataStore from an application context
 * attribute of configurable name and use it to construct an Atom 1.0 feed document, which it returns (in XML format)
 * as the HTTP response entity. It will select items for the feed as follows:  The Result will be configured with a
 * request parameter name with which it will accept an item update date and time. If the request specifies a parameter
 * of that name then this Result will parse it as a java.util.Date in ISO 8601:2004 format
 * (http://en.wikipedia.org/wiki/ISO_8601), and only items with a creation or update date strictly later than the
 * parsed date will be included in the resulting feed. The Result will determine the games for which the requesting
 * player (the one currently logged-in) is registered; the name of each will be used as an item category, and items in
 * any of those categories will be included in the feed, subject to the date restriction already described. The Result
 * will be configured with zero or more category names for categories whose items are always included in the feed,
 * subject to the date restriction criterion. The response will be assigned the content type registered for Atom,
 * ��application/atom+xml��. This class is thread safe since it does not contain any mutable state.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class MessagePollResult implements Result {
    /** Content type while output the Atom1.0 to response. */
    private static final String RESPONSE_CONTENT_TYPE = "application/atom+xml";

    /** A key to get the date string from the request parameters. */
    private final String dateParamKey;

    /**
     * An array contains the category names that will be used to search for the feeds in the execute method. It's set
     * in the constructor.
     */
    private final String[] categoryNames;

    /**
     * Creates a MessagePollResult instance with dateParamKey and categoryNames.
     *
     * @param dateParamKey the date param key
     * @param categoryNames the category names
     *
     * @throws IllegalArgumentException if categoryNames is null or contains null or empty element.
     */
    public MessagePollResult(String dateParamKey, String[] categoryNames) {
        this.dateParamKey = dateParamKey;
        ParameterCheck.checkEmptyArray("categoryNames", categoryNames);

        String[] copy = new String[categoryNames.length];
        System.arraycopy(categoryNames, 0, copy, 0, categoryNames.length);
        this.categoryNames = copy;
    }

    /**
     * Create the instance from the element. The structure of the element should be:
     * <pre>&lt;result name=&quot;x&quot;&gt;
     *  &lt;date_param_key&gt;date&lt;/date_param_key&gt;
     *  &lt;catetory_names&gt;
     *      &lt;value&gt;some category name&lt;/value&gt;
     *      &lt;value&gt;some category name &lt;/value&gt;
     *      &lt;value&gt;some category name &lt;/value&gt;
     *  &lt;/category_names&gt;
     *&lt;/result&gt;</pre>
     *
     * <p>
     * Following is simple explanation of the above XML structure.<br>
     * The handler��s type attribute is required by Front Controller component, it won��t be used in this design. <br>
     * The date node��s value represents the http request parameter name to get the date string<br>
     * The category_names node contains an array of category names that will be used to construct the SearchCriteria
     * </p>
     *
     * @param element the xml element
     *
     * @throws IllegalArgumentException if element is null or invalid.
     */
    public MessagePollResult(Element element) {
        ParameterCheck.checkNull("element", element);

        this.dateParamKey = XMLHelper.getNodeValue(element, "date_param_key", false);
        this.categoryNames = XMLHelper.getNodeValues(element, "catetory_names");
    }

    /**
     * Executes this result, obtains data to form a Atom1.0 feed and output to response.
     *
     * @param context the action context
     *
     * @throws ResultExecutionException if and error occurred while executing this result
     * @throws IllegalArgumentException if the context is null
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        ParameterCheck.checkNull("context", context);

        HttpServletRequest request = context.getRequest();

        Date date = getDateParameter(request); //obtain date from parameter
        long userId = getUserId(request);

        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();
        DataStore dataStore = (DataStore) request.getSession().getServletContext().getAttribute(golu.getDataStoreKey());

        try {
        	 //a list of categories that should be involved in search
            List categories = new ArrayList(Arrays.asList(this.categoryNames));
            
            if (golu.isUseLocalInterface()) {
            	GameDataLocal gameData = golu.getGameDataLocalHome().create();
            	
            	long[] gameIds = gameData.findGameRegistrations(userId);

                for (int i = 0; i < gameIds.length; i++) {
                    //add the use's registration game to the categories list
                    categories.add(gameData.getGame(gameIds[i]).getName());
                }
            } else {
            	GameData gameData = golu.getGameDataRemoteHome().create();
            	long[] gameIds = gameData.findGameRegistrations(userId);

                for (int i = 0; i < gameIds.length; i++) {
                    //add the use's registration game to the categories list
                    categories.add(gameData.getGame(gameIds[i]).getName());
                }
            }

            //creates searchCriteria along with the categories and date
            SearchCriteria searchCriteria = getSearchCriteria(categories, date);

            //search RSSItem with the searchCriteria
            RSSItem[] items = dataStore.findItems(searchCriteria);

            //write to response the items
            writeAtom10Feed(context.getResponse(), items);
        } catch (Exception e) {
            throw new ResultExecutionException("error occurs while recording user registation", e);
        }
    }

    /**
     * Parses the given ISO 8601:2004 format date string into Date.
     *
     * @param str String representaion of the date
     *
     * @return date Date parsed from the str
     *
     * @throws ResultExecutionException if the date is invalid, or not ISO 8601 format
     */
    private Date getDate(String str) throws ResultExecutionException {
        //pattern to parse the date
        StringBuffer pattern = new StringBuffer("yyyy-MM-dd'T'HH:mm:ss");

        //pattern to recognize the ISO 8601 date format
        String regExp = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})(,\\d{3})?(Z)?([-|+]\\d{2}:\\d{2})?";
        Pattern datePattern = Pattern.compile(regExp);
        Matcher matcher = datePattern.matcher(str);
        StringBuffer source = new StringBuffer();
        boolean utc = false;
        String timeZoneValue = null;

        //the str is valid ISO 8601 format
        if (matcher.matches()) {
            source.append(matcher.group(1));

            //matches millisecond
            if (matcher.group(2) != null) {
                pattern.append(",SSS");
                source.append(matcher.group(2));
            }

            //matches UTC identification
            if (matcher.group(3) != null) {
                utc = true;
                timeZoneValue = "GMT0:00";
                pattern.append("'Z'");
                source.append(matcher.group(3));
            }

            //matches time zone
            if (matcher.group(4) != null) {
                pattern.append("Z");

                if (utc) {
                    timeZoneValue = "GMT" + matcher.group(4);
                }

                source.append(matcher.group(4));
                source.deleteCharAt(source.length() - 3); //remove : from time zone string
            }
        } else {
            throw new ResultExecutionException(str + " is not valid ISO 8601:2004 date format");
        }

        //use SimpleDateFormat to parse the given date time string
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.toString());

        Date date;

        try {
            date = sdf.parse(source.toString());
        } catch (ParseException e) {
            throw new ResultExecutionException(str + " is not valid ISO 8601:2004 date format");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //if UTC identification exists, set time zone to it
        if (utc) {
            cal.setTimeZone(TimeZone.getTimeZone(timeZoneValue));
        }

        return cal.getTime();
    }

    /**
     * Obtains Date from request parameter, if dateParamKey is set.
     *
     * @param request HttpServletRequest
     *
     * @return Date date parsed from request.
     *
     * @throws ResultExecutionException if parsing failed
     */
    private Date getDateParameter(HttpServletRequest request)
        throws ResultExecutionException {
        if (this.dateParamKey == null) {
            return null;
        }

        String dateStr = request.getParameter(this.dateParamKey);

        if (dateStr == null) {
            throw new ResultExecutionException("parameter:" + this.dateParamKey + " does not exist");
        } else {
            return getDate(dateStr);
        }
    }

    /**
     * Creates SearchCriteria from given categories and date conditions.
     *
     * @param categories categories of the SearchCriteria
     * @param date date condition of the SearchCriteria
     *
     * @return SearchCriteria created by the given categories and date
     */
    private SearchCriteria getSearchCriteria(List categories, Date date) {
        //published date or updated date should later than the given date, if it's not null
        Filter dateFilter = null;

        if (date != null) {
            dateFilter = new GreaterThanFilter(SearchCriteria.PUBLISHED_DATE, date);
            dateFilter = new OrFilter(dateFilter, new GreaterThanFilter(SearchCriteria.UPDATED_DATE, date));
        }

        //the category that equals to any one in the list
        Filter catFilter = null;

        if (categories.size() > 0) {
            Iterator iter = categories.iterator();

            String catName = (String) iter.next();
            catFilter = new EqualToFilter(SearchCriteria.CATEGORY, catName);

            while (iter.hasNext()) {
                catName = (String) iter.next();
                catFilter = new OrFilter(catFilter, new EqualToFilter(SearchCriteria.CATEGORY, catName));
            }
        }

        Filter filter = null;

        if ((dateFilter != null) && (catFilter != null)) {
            filter = new AndFilter(dateFilter, catFilter); //restricted by dateFilter and catFilter with and relation
        } else if (dateFilter == null) {
            filter = catFilter;
        } else {
            filter = dateFilter;
        }

        if (filter == null) {
            return null;
        }

        return new SearchCriteriaImpl(filter);
    }

    /**
     * Obtains current userId from request.
     *
     * @param request HttpServletRequest
     *
     * @return userId
     *
     * @throws ResultExecutionException if user does not login in
     */
    private long getUserId(HttpServletRequest request)
        throws ResultExecutionException {
        long userId;
        UserProfile user = LoginHandler.getAuthenticatedUser(request.getSession());

        if (user == null) {
            throw new ResultExecutionException("user does not login yet");
        }

        userId = ((Long) user.getIdentifier()).longValue();

        return userId;
    }

    /**
     * Writes the array of RSSItem to response.
     *
     * @param response HttpServletResponse
     * @param items RSSItem[] to be written
     *
     * @throws ResultExecutionException if any error occurred
     */
    private void writeAtom10Feed(HttpServletResponse response, RSSItem[] items)
        throws ResultExecutionException {
        response.setContentType(RESPONSE_CONTENT_TYPE);

        PrintWriter responseWriter = null;

        try {
            responseWriter = response.getWriter();

            Atom10Writer writer = new Atom10Writer();

            for (int i = 0; i < items.length; i++) {
                writer.writeItem(items[i], responseWriter);
            }

            responseWriter.close();
        } catch (IOException e) {
            throw new ResultExecutionException("failed to write content to response", e);
        } catch (RSSWriteException e) {
            throw new ResultExecutionException("failed to write RSSItme to response", e);
        } finally {
            if (responseWriter != null) {
                responseWriter.close();
            }
        }
    }
}
