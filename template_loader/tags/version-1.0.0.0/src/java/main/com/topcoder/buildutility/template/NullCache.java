/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import com.topcoder.util.cache.Cache;
import java.util.Set;
import java.util.Collections;


/**
 * <p>This class is a "dummy" implementation of <code>Cache</code> interface which is used by the <code>TemplateLoader
 * </code> if it is instructed not to use any caching of template hierarchies loaded from persistent data store into a
 * runtime memory. This implementation does nothing and always behaves like an empty cache thus causing the <code>
 * TemplateLoader</code> always to load the template hierarchies from the persistent data store. The introduction of
 * this class results in a simpler implementation of <code>TemplateLoader</code> class which is not required to check
 * whether the caching is used or not when loading the template hierarchies.</p>
 *
 * <p>This class implements the <code>Null</code> design pattern.</p>
 *
 * <p>Thread safety: this class is thread safe. It does not maintain any private state.</p>
 *
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
class NullCache implements Cache {

    /**
     * <p>Constructs new <code>NullCache</code> instance.</p>
     */
    NullCache() {
        // empty constructor
    }

    /**
     * <p>Dummy implementation of the get method which looks up the for value with specified key in the cache.</p>
     * <p>This dummy implementation always returns <code>null</code>.</p>
     *
     *
     * @return <code>null</code> always.
     * @param key an <code>Object</code> specifying the key.
     */
    public Object get(Object key) {
        return null;
    }

   /**
     * <p>Dummy implementation of the put method which puts value with its key into the cache.</p>
     * <p>This dummy implementation does nothing.</p>
     *
     * @param key an <code>Object</code> specifying the key.
     * @param value an <code>Object</code> specifying the value.
     */
    public void put(Object key, Object value) {
        // do nothing
    }

    /**
     * <p>Dummy implementation of the remove method which removes value with specified key from the cache.</p>
     * <p>This dummy implementation does nothing and always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     * @param key an <code>Object</code> specifying the key.
     */
    public Object remove(Object key) {
        return null;
    }

    /**
     * <p>Dummy implementation of the clear method which clears the cache.</p>
     * <p>This dummy implementation does nothing.</p>
     */
    public void clear() {
        // do nothing
    }

    /**
     * <p>Dummy implementation of the keySet method which returns an unmodifiable Set containing all
     * keys currently in the cache.</p>
     *
     * <p>This dummy implementation always returns a new empty <code>Set</code>.</p>
     *
     * @return a new empty <code>Set</code> always.
     */
    public Set keySet() {
        return Collections.EMPTY_SET;
    }
}
