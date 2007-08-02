/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.dropdown;


import com.topcoder.web.dropdown.DropDownData;
import com.topcoder.web.dropdown.DropDownItem;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>A basic implementation of {@link DropDownItem} interface provided by <code>TCS Drop-Down Manager Tag</code>
 * component.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class DropDownItemImpl implements DropDownItem {

    /**
     * <p>A <code>String</code> providing the value for this item. Such a value must uniquely identify this item among
     * other items.</p>
     */
    private String value;

    /**
     * <p>A <code>String</code> providing the label to be used for presentation of this item.</p>
     */
    private String label;

    /**
     * <p>A <code>boolean</code> flag indicating if this item is selected by default.</p>
     */
    private boolean selected;

    /**
     * <p>A <code>DropDownData</code> which this item belongs to.</p>
     */
    private DropDownData data;

    /**
     * <p>A <code>List</code> of parent item.</p>
     */
    private List parents;

    /**
     * <p>Constructs new <code>DropDownItemImpl</code> instance with specified value and label.</p>
     *
     * @param value a <code>String</code> value of this item.
     * @param label a <code>String</code> label
     */
    public DropDownItemImpl(String value, String label) {
        this.value = value;
        this.label = label;
        this.parents = new ArrayList();
        setSelected(false);
    }

    /**
     * <p>Gets the drop-down data which this item belongs to.</p>
     *
     * @return data a <code>DropDownData</code> which this item belongs to.
     */
    public DropDownData getData() {
        return this.data;
    }

    /**
     * <p>Gets the parent items for this item.</p>
     *
     * @return a <code>List</code> of parent drop-down items for this item.
     */
    public List getParentItems() {
        return this.parents;
    }

    /**
     * <p>Gets the value for this item.</p>
     *
     * @return value a <code>String</code> value of this item.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * <p>Gets the textual label for this item.</p>
     *
     * @return value a <code>String</code> label for this item.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * <p>Gets the contents of this item. This implementation returns same value as {@link #getValue()}.</p>
     *
     * @return contents a <code>String</code> content of this item.
     */
    public String getContents() {
        return this.value;
    }

    /**
     * <p>Checks if this item is selected by default.</p>
     *
     * @return <code>true</code> if this item is selected by default; <code>false</code> otherwise.
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * <p>Sets the flag indicating if this item is selected by default.
     *
     * @param selected <code>true</code> if this item is selected by default; <code>false</code> otherwise.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * <p>Sets the drop-down data which this item belongs to.</p>
     *
     * @param data a <code>DropDownData</code> which this item belongs to.
     */
    public void setData(DropDownData data) {
        this.data = data;
    }

    /**
     * <p>Adds specified parent items to list of parent items for this item.</p>
     *
     * @param parent a <code>DropDownItem</code> to add.
     */
    public void addParent(DropDownItem parent) {
        this.parents.add(parent);
    }
}
