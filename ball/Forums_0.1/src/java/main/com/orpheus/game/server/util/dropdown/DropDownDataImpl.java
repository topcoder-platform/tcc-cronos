/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.dropdown;

import com.topcoder.web.dropdown.DropDownData;
import com.topcoder.web.dropdown.DropDownItem;

import java.util.Collection;
import java.util.List;
import java.util.LinkedHashMap;

/**
 * <p>A basic implementation of {@link DropDownData} interface provided by <code>TCS Drop-Down Manager Tag</code>
 * component.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class DropDownDataImpl implements DropDownData {

    /**
     * <p>A <code>Map</code> mapping the key to an item. The item's value serves as a key.</p>
     */
    private LinkedHashMap dropDownItems;

    /**
     * <p>Constructs new <code>DropDownDataImpl</code> instance. The list of items is empty.</p>
     */
    public DropDownDataImpl() {
        this.dropDownItems = new LinkedHashMap();
    }

    /**
     * <p>Gets the drop-down items collected by this <code>DropDownDataImpl</code> instance.</p>
     *
     * @return a <code>Collection</code> of <code>DropDownItem</code> instances.
     */
    public Collection getDropDownItems() {
        return this.dropDownItems.values();
    }

    /**
     * <p>Adds specified drop-down item to this data.</p>
     *
     * @param item a <code>DropDownItem</code> to be added to this data.
     */
    public void addDropDownItem(DropDownItem item) {
        this.dropDownItems.put(item.getValue(), item);
        if (item instanceof DropDownItemImpl) {
            ((DropDownItemImpl) item).setData(this);
        }
    }

    /**
     * <p>Gets a list of parent data objects. Any item in this data will have a parent item in each of the returned
     * objects. This implementation returns <code>null</code>.</p>
     *
     * @return null.
     */
    public List getParentDatas() {
        return null;
    }

    /**
     * <p>Clears this drop down data. All contained items are removed.</p>
     */
    public void clear() {
        this.dropDownItems.clear();
    }

    /**
     * <p>Gets the drop-down item referenced by the specified key.</p>
     *
     * @param key a <code>String</code> providing the key referencing the requested drop-down item.
     * @return a <code>DropDownItem</code> matching the specified key or <code>null</code> if such an item does not
     *         exist.
     */
    public DropDownItem getDropDownItem(String key) {
        return (DropDownItem) this.dropDownItems.get(key);
    }
}