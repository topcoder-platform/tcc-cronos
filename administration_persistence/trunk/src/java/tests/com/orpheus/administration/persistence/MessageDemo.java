/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.persistence.impl.AdminMessage;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.RSSCategory;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.RSSLink;
import com.topcoder.util.rssgenerator.RSSText;
import com.topcoder.util.rssgenerator.SearchCriteria;

import com.topcoder.util.rssgenerator.impl.RSSCategoryImpl;
import com.topcoder.util.rssgenerator.impl.RSSItemImpl;
import com.topcoder.util.rssgenerator.impl.RSSLinkImpl;
import com.topcoder.util.rssgenerator.impl.RSSObjectImpl;
import com.topcoder.util.rssgenerator.impl.RSSTextImpl;

import java.util.Date;

/**
 * Demonstrates the use of the message functionality of the <code>Administration Persistence</code> component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class MessageDemo extends MessageTestBase {
    /**
     * Demonstrates the use of the data store interface.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_demo_data_store() throws Exception {
        getConfigManager().add("remote_data_store.xml");

        // create remote instance with a namespace
        OrpheusMessageDataStore client = new RemoteOrpheusMessageDataStore("remote.data.store");

        // we might begin by creating a new RSSItem
        RSSItem item1 = createItem("1");
        RSSItem item2 = createItem("2");
        client.createItem(item1);
        client.createItem(item2);
        // at this point, both are inserted

        // we now update an item, after we make some changes (not shown)
        client.updateItem(item2);

        // we want to delete an item
        client.deleteItem(item1);
        // item deleted from persistence

        // The find method uses SearchCriteria, and at this time
        // it is not possible to accurately demonstrate it, but we can
        // still make some assumptions:
        // retrieve all items with timestamp between the beginning of time and the present
        java.sql.Date startDate = new java.sql.Date(0);
        java.sql.Date endDate = new java.sql.Date(System.currentTimeMillis());
        final Filter betweenFilter = new BetweenFilter("update_time", startDate, endDate);
        SearchCriteria criteria = new SearchCriteria() {
                public Filter getSearchFilter() {
                    return betweenFilter;
                }
            };

        RSSItem[] itemsInRange = client.findItems(criteria);
    }

    /**
     * Demonstrates the use of the messenger plugin interface.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_demo_messenger_plugin() throws Exception {
        getConfigManager().add("remote_messenger_plugin.xml");

        // create remote instance with a namespace
        OrpheusMessengerPlugin client = new RemoteOrpheusMessengerPlugin("remote.messenger.plugin");

        // we simply send a AdminMessage
        //AdminMessage mesage1 = some admin message item with guid: 1
        AdminMessage message = new AdminMessage();
        message.setParameterValue(AdminMessage.GUID, "1");
        message.setParameterValue(AdminMessage.CATEGORY, "category");
        message.setParameterValue(AdminMessage.CONTENT_TYPE, "content type");
        message.setParameterValue(AdminMessage.CONTENT, "content");
        message.setParameterValue(AdminMessage.TIMESTAMP, new Date());

        client.sendMessage(message);
        // at this point, this message is persisted
    }

    /**
     * Returns a new <code>RSSItem</code> with the specified GUID.
     *
     * @param guid the GUID of the item to create
     * @return a new <code>RSSItem</code> with the specified GUID
     */
    private static RSSItem createItem(String guid) {
        RSSItem item = new RSSItemImpl(new RSSObjectImpl());

        // GUID
        item.setId(guid);

        // category
        RSSCategory category = new RSSCategoryImpl(new RSSObjectImpl());
        category.setName("category " + guid);
        item.addCategory(category);

        // content type
        RSSLink link = new RSSLinkImpl(new RSSObjectImpl());
        link.setMimeType("content type " + guid);
        item.setSelfLink(link);

        // content
        RSSText text = new RSSTextImpl(new RSSObjectImpl());
        text.setElementText("content " + guid);
        item.setDescription(text);

        // timestamp
        item.setPublishedDate(new Date());

        return item;
    }
}
