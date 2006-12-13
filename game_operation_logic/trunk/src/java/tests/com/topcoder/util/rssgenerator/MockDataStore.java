package com.topcoder.util.rssgenerator;

import com.topcoder.util.rssgenerator.impl.RSSItemImpl;
import com.topcoder.util.rssgenerator.impl.RSSObjectImpl;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Content;


public class MockDataStore implements DataStore {

	public static final RSSItem[] FIND_ITEMS = 
		new RSSItem[]{
			new RSSItemImpl(new Atom10Content()),
			new RSSItemImpl(new Atom10Content())};

	public void createFeed(RSSFeed feed) {
		// TODO Auto-generated method stub

	}

	public RSSFeed[] findFeeds(SearchCriteria searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateFeed(RSSFeed feed) {
		// TODO Auto-generated method stub

	}

	public void deleteFeed(RSSFeed feed) {
		// TODO Auto-generated method stub

	}

	public void createItem(RSSItem item) {
		// TODO Auto-generated method stub

	}

	public RSSItem[] findItems(SearchCriteria searchCriteria) {
		return FIND_ITEMS;
	}

	public void updateItem(RSSItem item) {
		// TODO Auto-generated method stub

	}

	public void deleteItem(RSSItem item) {
		// TODO Auto-generated method stub

	}

}
