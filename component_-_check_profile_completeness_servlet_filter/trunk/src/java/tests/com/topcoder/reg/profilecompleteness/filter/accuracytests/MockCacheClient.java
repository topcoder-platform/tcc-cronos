/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.web.common.cache.CacheClient;
import com.topcoder.web.common.cache.MaxAge;
import com.topcoder.web.common.cache.address.CacheAddress;


/**
 * Mock implementation of CacheClient to be used for accuracy testing.
 *
 * @author gets0ul
 * @version 1.0
 */
public class MockCacheClient implements CacheClient {
    /**
     * Does nothing.
     *
     * @param key
     *            not used
     * @param value
     *            not used
     */
    public void set(String key, Object value) {
    }

    /**
     * Does nothing.
     *
     * @param address
     *            not used
     * @param value
     *            not used
     */
    public void set(CacheAddress address, Object value) {
    }

    /**
     * Does nothing.
     *
     * @param address
     *            not used
     * @param value
     *            not used
     * @param maxAge
     *            not used
     */
    public void set(CacheAddress address, Object value, MaxAge maxAge) {
    }

    /**
     * Does nothing.
     *
     * @param key
     *            not used
     * @return null
     */
    public Object get(String key) {
        return null;
    }

    /**
     * Returns a mapping between a String to ResultSetContainer for Basic Authentication. The ResultSetContainer
     * will return either "status" or "password" for first row.
     *
     * @param address
     *            the address to get in the cache
     * @return a mapping between a String to ResultSetContainer for Basic Authentication
     */
    public Object get(CacheAddress address) {
        Map<String, ResultSetContainer> map = new HashMap<String, ResultSetContainer>();
        ResultSetContainer rsc = new ResultSetContainer() {
            private static final long serialVersionUID = 1L;

            public String getStringItem(int iRow, String sCol) {
                if (iRow == 0) {
                    return "status".equals(sCol) ? "status" : "password";
                }
                return super.getStringItem(iRow, sCol);
            }

        };
        map.put("userid_to_password", rsc);

        return map;
    }

    /**
     * Does nothing.
     *
     * @param key
     *            not used
     * @return null
     */
    public Object remove(String key) {
        return null;
    }

    /**
     * Does nothing.
     *
     * @param address
     *            not used
     * @return null
     */
    public Object remove(CacheAddress address) {
        return null;
    }

    /**
     * Does nothing.
     */
    public void clearCache() {
    }
}
