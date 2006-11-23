/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * The utility class with helper methods used in tests.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class TestHelper {

    /**
     * Loads the configuration.
     * 
     * @throws Exception to JUnit.
     */
    public static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracy/auction_logic.xml");
        cm.add("accuracy/auction_framework.xml");
    }
    
    /**
     * Clears the configuration.
     * 
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }
    
    /**
     * Returns the instance field from the source object.
     * 
     * @param source the object.
     * @param fieldName the name of the field.
     * 
     * @return the field value (may be null).
     * 
     * @throws Exception to JUnit.
     */
    public static Object getFieldValue(Object source, String fieldName) throws Exception {
        Field field = source.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(source);
    }
    
    /**
     * Creates the Element from given XML.
     * 
     * @param xml the XML to parse.
     * @return Element instance.
     * 
     * @throws Exception to JUnit.
     */
    public static Element createElement(String xml) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        return doc.getDocumentElement();
    }

    /**
     * Creates the new Auction from the given parameters.
     * 
     * @param id the id of the auction.
     * @param minBid the minimum bid value for auction.
     * @param startDate the auction's start date.
     * @param endDate the auction's end date.
     * @param bids the Bids array.
     * 
     * @return new instance of AuctionImpl object.
     */
    public static Auction createAuction(long id, int minBid, int startDate, int endDate, Bid[] bids) {
        Date start = new Date(System.currentTimeMillis() + startDate * 1000);
        Date end = new Date(System.currentTimeMillis() + endDate * 1000);
        if (bids == null) {
            bids = new Bid[0];
        }
        
        return new AuctionImpl(new Long(id), "summary", "desc", 1, minBid, start, end, bids);
    }
}
