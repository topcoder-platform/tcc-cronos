package com.topcoder.util.rssgenerator;


public class MockDataStore implements DataStore {

	public static final RSSItem[] FIND_ITEMS = new RSSItem[]{new MockRssItem ("1"),new MockRssItem ("1")};

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
