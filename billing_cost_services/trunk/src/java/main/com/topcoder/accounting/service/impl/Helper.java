/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.accounting.entities.dao.IdentifiableEntity;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * Helper class is used to check arguments. Should only be used in this component.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor. Make sure no instance of this class will be created.
     * </p>
     */
    private Helper() {
        // Empty
    }

    /**
     * <p>
     * Set the string for json object.
     * </p>
     *
     * @param jsonObject
     *            the json object
     * @param key
     *            the key
     * @param obj
     *            the object to set
     */
    public static void setJsonObject(JSONObject jsonObject, String key, Object obj) {
        if (obj == null) {
            jsonObject.setNull(key);
        } else if(obj instanceof String) {
            jsonObject.setString(key, (String)obj);
        } else if(obj instanceof Long) {
            jsonObject.setLong(key, (Long)obj);
        } else if(obj instanceof Date) {
            jsonObject.setString(key, ((Date)obj).toString());
        }
    }

    /**
     * <p>
     * Get the json string of entity.
     * </p>
     *
     * @param entity
     *            the entity
     * @return the json string
     */
    static String getString(JsonPrintable entity) {
        return entity == null ? null : entity.toJSONString();
    }

    /**
     * <p>
     * Get the json string of List.
     * </p>
     *
     * @param <T>
     *            the extension of IdentifiableEntity
     * @param entityList
     *            the entity list
     * @return the json string list
     */
    static <T extends JsonPrintable> List<String> getListString(List<T> entityList) {
        if (entityList == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for (JsonPrintable entity : entityList) {
            result.add(getString(entity));
        }
        return result;
    }

    /**
     * <p>
     * Check page and page size.
     * </p>
     *
     * @param pageNo
     *            the page (1-based, -1 if all users must be retrieved)
     * @param pageSize
     *            the page size (ignored if page is equal to -1)
     * @throws IllegalArgumentException
     *             if pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     */
    static void checkPageAndPageSize(int pageNo, int pageSize) {
        if (pageNo != -1) {
            ParameterCheckUtility.checkPositive(pageNo, "pageNo");
            ParameterCheckUtility.checkPositive(pageSize, "pageSize");
        }
    }

    /**
     * <p>
     * Get the entity from db.
     * </p>
     *
     * @param hibernateTemplate
     *            the hibernate template
     * @param hibernateCriteria
     *            the hibernate criteria
     * @param pageNo
     *            the page number
     * @param pageSize
     *            the page size
     * @param isInQuickBooks
     *            the isInQuickBooks flag
     * @param <T>
     *            the Identifiable entity
     * @return the paged result
     * @throws DataAccessException
     *             if any error occurs
     */
    @SuppressWarnings("unchecked")
    static <T extends IdentifiableEntity> PagedResult<T> getPagedResult(final HibernateTemplate hibernateTemplate,
        final DetachedCriteria hibernateCriteria, final int pageNo, final int pageSize, boolean isInQuickBooks) {
        // Create result object:
        PagedResult<T> searchResults = new PagedResult<T>();

        // Set page:
        searchResults.setPageNo(pageNo);
        // Set page size:
        searchResults.setPageSize(pageSize);

        // Set the total page and total records
        hibernateCriteria.setProjection(Property.forName("id").count());
        List<?> recordCount = hibernateTemplate.findByCriteria(hibernateCriteria);
        // Set total count:
        searchResults.setTotalRecords(((Long) recordCount.get(0)).intValue());
        if (pageNo != -1) {
            searchResults.setTotalPages((int) Math.ceil((float) searchResults.getTotalRecords() / pageSize));
        } else {
            searchResults.setTotalPages(1);
        }

        // if pageNo is greater than the total pages, just return empty list
        if (pageNo > searchResults.getTotalPages()) {
            searchResults.setRecords(new ArrayList<T>());
        } else {
            // if is inQuickBooks, add order
            if (isInQuickBooks) {
                hibernateCriteria.addOrder(Order.asc("customer"));
            }
            // Get the audit records
            List<T> records;
            // remove the projection
            hibernateCriteria.setProjection(null);
            if (pageNo > 0) {
                int startIndex = (pageNo - 1) * pageSize;
                records = findPageByCriteria(hibernateTemplate, hibernateCriteria, pageSize, startIndex);
            } else {
                records = hibernateTemplate.findByCriteria(hibernateCriteria);
            }
            // Set the records to search results
            searchResults.setRecords(new ArrayList<T>(records));
        }

        // return searchResults
        return searchResults;
    }

    /**
     * <p>
     * find the result with paged.
     * </p>
     *
     * @param hibernateTemplate
     *            the hibernateTemplate
     * @param detachedCriteria
     *            the criteria
     * @param pageSize
     *            the page size
     * @param startIndex
     *            the start index
     * @return the result
     * @throws DataAccessException
     *             if any error occurs
     */
    @SuppressWarnings("rawtypes")
    private static List findPageByCriteria(final HibernateTemplate hibernateTemplate,
        final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex) {
        List result = (List) hibernateTemplate.execute(new HibernateCallback() {
            /**
             * Do searching in hibernate.
             *
             * @param session
             *            the session
             * @return the result object
             */
            public Object doInHibernate(Session session) {
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                List<?> result = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
                return result;
            }
        });
        return result;
    }
}
