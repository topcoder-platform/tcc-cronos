package com.topcoder.util.rssgenerator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URI;
import java.util.Date;

public class MockRssItem implements RSSItem {
	private String id;
	
	public MockRssItem(String id){
		this.id = id;
	}

	public String getSourceId() {
		
		return null;
	}

	public void setSourceId(String sourceId) {
		

	}

	public String getId() {
		return id;
	}

	public RSSText getTitle() {
		
		return null;
	}

	public RSSLink getSelfLink() {
		
		return null;
	}

	public RSSLink[] getLinks() {
		
		return null;
	}

	public RSSText getDescription() {
		
		return null;
	}

	public RSSText getCopyright() {
		
		return null;
	}

	public Date getPublishedDate() {
		
		return null;
	}

	public Date getUpdatedDate() {
		
		return null;
	}

	public RSSEntity[] getAuthors() {
		
		return null;
	}

	public RSSCategory[] getCategories() {
		
		return null;
	}

	public void setId(String id) {
		

	}

	public void setTitle(RSSText title) {
		

	}

	public void setSelfLink(RSSLink selfLink) {
		

	}

	public void setLinks(RSSLink[] links) {
		

	}

	public void addLink(RSSLink link) {
		

	}

	public void removeLink(RSSLink link) {
		

	}

	public void clearLinks() {
		

	}

	public void setDescription(RSSText description) {
		

	}

	public void setCopyright(RSSText copyright) {
		

	}

	public void setPublishedDate(Date publishedDate) {
		

	}

	public void setUpdatedDate(Date updatedDate) {
		

	}

	public void setAuthors(RSSEntity[] authors) {
		

	}

	public void addAuthor(RSSEntity author) {
		

	}

	public void removeAuthor(RSSEntity author) {
		

	}

	public void clearAuthors() {
		

	}

	public void setCategories(RSSCategory[] categories) {
		

	}

	public void addCategory(RSSCategory category) {
		

	}

	public void removeCategory(RSSCategory category) {
		

	}

	public void clearCategories() {
		

	}

	public RSSObject[] getExtensionElements() {
		
		return null;
	}

	public void setExtensionElements(RSSObject[] elements) {
		

	}

	public void addExtensionElement(RSSObject element) {
		

	}

	public void removeExtensionElement(RSSObject element) {
		

	}

	public void clearExtensionElements() {
		

	}

	public String getElementName() {
		
		return null;
	}

	public String getElementText() {
		
		return null;
	}

	public String getAttribute(String name) {
		
		return null;
	}

	public String[] getAttributeNames() {
		
		return null;
	}

	public RSSObject getChildElement(String name) {
		
		return null;
	}

	public RSSObject[] getChildElements(String name) {
		
		return null;
	}

	public String[] getAllChildElementNames() {
		
		return null;
	}

	public RSSObject[] getAllChildElements() {
		
		return null;
	}

	public void setElementName(String name) {
		

	}

	public void setElementText(String text) {
		

	}

	public void setAttribute(String name, String value) {
		

	}

	public void clearAttributes() {
		

	}

	public void setChildElement(String name, RSSObject element) {
		

	}

	public void setChildElements(String name, RSSObject[] elements) {
		

	}

	public void addChildElement(String name, RSSObject element) {
		

	}

	public void removeChildElement(String name, RSSObject element) {
		

	}

	public void clearChildElements(String name) {
		

	}

	public void clearAllChildElements() {
		

	}

	public URI getBaseUri() {
		
		return null;
	}

	public String getLanguage() {
		
		return null;
	}

	public void setBaseUri(URI baseUri) {
		

	}

	public void setLanguage(String language) {
		

	}

	public void writeExternal(ObjectOutput out) throws IOException {
		

	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		

	}

}
