/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.tc.distance.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.topcoder.web.tc.distance.CompetitionType;

/**
 * <p>
 * This class loads XML files named after coder ids (long values), supposed to contain all relevant members, with the
 * member identifying the file first.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FlatFileMemberDataAccess implements MemberDataAccess {
    /**
     * <p>
     * Path prepended to the individual file names.
     * </p>
     */
    private String basePath;

    /**
     * <p>
     * Provide a base path used for reading individual files.
     * </p>
     *
     * @param basePath Path to XML files.
     */
    public FlatFileMemberDataAccess(String basePath) {
        this.basePath = basePath;
    }


    /**
     * <p>
     * A string constant indicating the name of the corresponding element in the XML input.
     * </p>
     */
    private static final String CODER_ID_ELEMENT = "coder_id";

    /**
     * <p>
     * A string constant indicating the name of the corresponding element in the XML input.
     * </p>
     */
    private static final String HANDLE_ELEMENT = "handle";

    /**
     * <p>
     * A string constant indicating the suffix of the corresponding element names in the XML input.
     * </p>
     */
    private static final String RATING_SUFFIX = "_rating";

    /**
     * <p>
     * A string constant indicating the name of the corresponding element in the XML input.
     * </p>
     */
    private static final String IMAGE_ELEMENT = "image";

    /**
     * <p>
     * A string constant indicating the name of the corresponding element in the XML input.
     * </p>
     */
    private static final String DISTANCE_ELEMENT = "distance";

    /**
     * <p>
     * A string constant indicating the name of the corresponding element in the XML input.
     * </p>
     */
    private static final String OVERLAP_ELEMENT = "overlap";

    /**
     * <p>
     * A string constant indicating the name of the corresponding element in the XML input.
     * </p>
     */
    private static final String COUNTRY_ELEMENT = "country";

    /**
     * <p>
     * Parses a single coder from XML DOM.
     * </p>
     *
     * @param elem The element to parse.
     * @return The resulting <code>Member</code> object.
     */
    private Member parseCoder(Element elem) {
        NodeList nodes = elem.getChildNodes();
        String countryName = null;
        int geoDistance = -1;
        String handle = null;
        long id = Long.MIN_VALUE;
        String imageName = null;
        int matchOverlap = 0;
        Map<CompetitionType,Integer> ratings = new HashMap<CompetitionType,Integer>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node instanceof Element) {
                String name = node.getNodeName();
                String value = node.getTextContent();

                if (name.equals(CODER_ID_ELEMENT)) {
                    id = Long.valueOf(value);
                } else if (name.equals(HANDLE_ELEMENT)) {
                    handle = value;
                } else if (name.equals(IMAGE_ELEMENT)) {
                    imageName = value;
                } else if (name.equals(DISTANCE_ELEMENT)) {
                    geoDistance = (int) (Double.parseDouble(value) * 1000.0);
                } else if (name.equals(OVERLAP_ELEMENT)) {
                    matchOverlap = Integer.valueOf(value);
                } else if (name.equals(COUNTRY_ELEMENT)) {
                    countryName = value;
                } else {
                    // Match all competition type names
                    for (CompetitionType type : CompetitionType.values()) {
                        if (name.equals(type.toString().toLowerCase() + RATING_SUFFIX)) {
                            ratings.put(type, Integer.valueOf(value));
                        }
                    }
                }
            }
        }

        return new Member(id, handle, ratings, countryName, imageName, geoDistance, matchOverlap);
    }

    /**
     * <p>
     * Gets all member objects from the XML file for the matching id.
     * </p>
     *
     * @param id The id to read from.
     * @return The final list.
     */
    private List<Member> getMembers(long id) throws MemberDataAccessException {
        try {
            File file = new File(basePath + Long.toString(id) + ".xml");
            List<Member> result = new ArrayList<Member>();

            if (!file.exists()) {
                return result;
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file);

            // root element map
            NodeList nodes = document.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node instanceof Element) {
                    NodeList nodes2 = node.getChildNodes();
                    // coder elements
                    for (int j = 0; j < nodes2.getLength(); j++) {
                        Node node2 = nodes2.item(j);
                        if (node2 instanceof Element) {
                            result.add(parseCoder((Element) node2));
                        }
                    }
                }
            }

            return result;
        } catch (IOException e) {
            throw new MemberDataAccessException("Error reading coder file.", e);
        } catch (SAXException e) {
            throw new MemberDataAccessException("Error parsing coder file.", e);
        } catch (ParserConfigurationException e) {
            throw new MemberDataAccessException("Error parsing configuration when reading coder file.", e);
        }
    }

    /**
     * <p>
     * Loads the single, first, member in the XML file for the specified coder.
     * </p>
     *
     * @param id The id of the coder.
     * @return A <code>Member</code> object for the coder, or null if there is no file for this coder.
     *
     * @throws MemberDataAccessException Thrown for any parsing error.
     */
    public Member getMember(long id) throws MemberDataAccessException {
        List<Member> members = getMembers(id);
        if (members.size() == 0) {
            return null;
        }

        return members.get(0);
    }

    /**
     * <p>
     * Loads all related members (not the "origin member") from the file of the specified coder.
     * </p>
     *
     * @param id The id of the coder.
     * @param competitionTypes The competition types requested.
     *
     * @return A <code>List</code> of <code>Member</code> objects, possibly empty.
     */
    public List<Member> getRelatedMembers(long id,
            EnumSet<CompetitionType> competitionTypes)
            throws MemberDataAccessException {
        List<Member> result = new ArrayList<Member>();

        List<Member> members = getMembers(id);
        if (members.size() >= 2) {
            result.addAll(members.subList(1, members.size()));
        }

        return result;
    }
}
