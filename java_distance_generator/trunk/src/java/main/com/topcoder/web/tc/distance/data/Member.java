package com.topcoder.web.tc.distance.data;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.web.tc.distance.CompetitionType;

/**
 * This class is an immutable data transport object that holds all
 * data necessary to calculate the required distances between two members.
 *
 * Members unrated in a particular competition type are considered
 * to have rating zero.
 *
 * @author AdamSelene
 */
public class Member {

    /**
     * The country of the member.
     */
    private String country = null;

    /**
     * The image name for the member.
     */
    private String image = null;

    /**
     * The geographical distance in miles between this member and the related member.
     * If this member was not retrieved as a related member, the distance will be zero.
     *
     * The units for this are irrelevant and will be uniform across all members.
     */
    private int geoDistance = 0;

    /**
     * The number of matches shared between this member and the related member.
     * If this member was not retrieved as a related member, the overlap will be zero.
     */
    private int matchOverlap = 0;

    /**
     * The member's handle.
     */
    private String handle = null;

    /**
     * The member's ratings.  All ratings should be included.
     */
    private HashMap<CompetitionType, Integer> ratings = null;

    /**
     * The member's coder_id (From the database).
     */
    private long id = 0;

    /**
     * Member's maximum rating (of all current ratings).
     */
    private int maxRating = Integer.MIN_VALUE;

    /**
     * Gets the member's country.
     * @return The country of the member, or empty if unknown.
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Gets the image URL for the member.
     * @return The image URL for the member, or empty if unknown.
     */
    public String getImage()
    {
        return image;
    }

    /**
     * Gets the member's handle.
     * @return Member's handle.
     */
    public String getHandle()
    {
        return handle;
    }

    /**
     * Gets the member's highest rating regardless of competition type.
     * @return The single rating which is highest amongst the member's ratings.
     *         May be zero if member has never been rated.
     */
    public int getMaxRating()
    {
        return maxRating;
    }

    /**
     * Gets the member's rating for a specific competition type.
     *
     * @param type The type of competition desired.
     * @return The rating for the member in that type.  Zero if unrated.
     */
    public int getRating(CompetitionType type)
    {
        // Array is ordered by CompetitionType index.
        return ratings.get(type) == null ? -1 : ratings.get(type);
    }

    /**
     * Gets the member's coder_id, which uniquely identifies them.
     * @return The member's coder_id.
     */
    public long getId()
    {
        return id;
    }

    /**
     * Gets the geographical distance between this member and the related
     * member.
     * @return The geographical distance between this member and the related member
     * in units.  If this member is the member distances are being generated for,
     * the distance will be zero.
     */
    public int getGeographicalDistance()
    {
        return geoDistance;
    }

    /**
     * Gets the number of matches shared between this member and the related member.
     * @return The number of matches shared between this member and the related member.
     * If this member is the member distances are being generated for, the overlap
     * will be zero.
     */
    public int getMatchOverlap()
    {
        return matchOverlap;
    }

    /**
     * Creates a member instance.  All parameters are required.
     *
     * @param id The coder_id for the member.
     * @param handle The handle for the member
     * @param ratings The ratings for the member.
     * @param country The country for the member.  Empty is acceptable.
     * @param image The image for the member.  Empty is acceptable.
     */
    protected Member(long id, String handle, Map<CompetitionType, Integer> ratings, String country, String image)
    {
        this.id = id;
        this.handle = handle;
        this.country = country;
        this.image = image;

        // Copied manually to extract maximum in one loop.
        this.ratings = new HashMap<CompetitionType, Integer>(ratings);

        for(CompetitionType ct : this.ratings.keySet())
        {
            int cur = ratings.get(ct);
            if (cur > maxRating)
            {
                maxRating = cur;
            }
        }
    }

    /**
     * Creates a member instance.  All parameters are required.
     * @param id The coder_id for the member.
     * @param handle The handle for the member
     * @param ratings The ratings for the member.
     * @param country The country for the member.  Empty is acceptable.
     * @param image The image for the member.  Empty is acceptable.
     * @param geoDistance The geographical distance between this member and a related member.
     * @param matchOverlap The number of matches shared between this member and a related member.
     */
    protected Member(long id, String handle, Map<CompetitionType, Integer> ratings, String country, String image, int geoDistance, int matchOverlap)
    {
        this(id, handle, ratings, country, image);

        this.geoDistance = geoDistance;
        this.matchOverlap = matchOverlap;
    }
}