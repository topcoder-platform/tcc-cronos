/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance.DistanceCalculators
{

    /// <summary>
    /// This class is the implementation of the <see cref="IDistanceCalculator"/> interface
    /// that calculates the rating distance. It implements the default distance calculation
    /// formula as stated in the algorithm section of the component specification.
    /// </summary>
    /// <threadsafety>
    /// This class is thread-safe since it is immutable.
    /// </threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public class RatingDistanceCalculator : IDistanceCalculator
    {
        /// <summary>
        /// The default do-nothing constructor.
        /// </summary>
        public RatingDistanceCalculator()
        {
        }

        /// <summary>
        /// Calculates the rating distances from the given member to all its
        /// related members. The returned list should contain the distances to
        /// each related member in the same order as the related members are
        /// given in the list. A negative value in the returned list means
        /// that the distance is &quot;undefined&quot;.
        /// </summary>
        /// <param name="member">
        /// The member to calculate the distances for.
        /// </param>
        /// <param name="relatedMembers">
        /// All related members of the given member, cannot be an empty list.
        /// </param>
        /// <param name="type">
        /// The competition types used to compute distance.
        /// </param>
        /// <returns>
        /// The rating distances to each related member.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// If either argument is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If the list is empty or contains a null element.
        /// </exception>
        public IList<float> CalculateDistance(Member member, IList<Member> relatedMembers, CompetitionTypes type)
        {
            Helper.ValidateNotNull("member", member);
            Helper.ValidateList<Member>("relatedMembers", relatedMembers, false);

            float[] result = new float[relatedMembers.Count];
            int numCtypes = 0;

            IDictionary<CompetitionTypes, double> maxRatings = new Dictionary<CompetitionTypes, double>();

            // Keep track if all members that are unrated under this competition type.
            IDictionary<CompetitionTypes, bool> isAllUnrated = new Dictionary<CompetitionTypes, bool>();

            foreach (CompetitionTypes ctype in Enum.GetValues(typeof(CompetitionTypes)))
            {
                if ((type & ctype) > 0)
                {
                    maxRatings[ctype] = member.GetRating(ctype);
                    isAllUnrated[ctype] = true;

                    foreach (Member relatedMember in relatedMembers)
                    {
                        int ratingB = relatedMember.GetRating(ctype);
                        if (ratingB > maxRatings[ctype])
                        {
                            maxRatings[ctype] = ratingB;
                        }
                    }

                    // Add the competition only if someone was rated in it.
                    if (maxRatings[ctype] > 0)
                    {
                        isAllUnrated[ctype] = false;
                        numCtypes++;
                    }
                }
            }

            for (int i = 0; i < relatedMembers.Count; i++)
            {
                Member relatedMember = relatedMembers[i];
                double sumDistances = 0;

                foreach (CompetitionTypes ctype in maxRatings.Keys)
                {
                    // Skip calculation for those that have already a negative distance.
                    if (result[i] >= 0)
                    {
                        if (!isAllUnrated[ctype])
                        {
                            double distance;
                            int ratingA = member.GetRating(ctype);
                            int ratingB = relatedMember.GetRating(ctype);

                            if (ratingA > 0)
                            {
                                if (ratingB > 0)
                                {
                                    // Rated-to-Rated member.
                                    distance = Math.Abs(ratingA - ratingB) / maxRatings[ctype];
                                }
                                else
                                {
                                    // Rated-to-Unrated member.
                                    distance = 1.0;
                                }
                            }
                            else
                            {
                                if (ratingB > 0)
                                {
                                    // Unrated-to-Rated member.
                                    distance = ratingB / maxRatings[ctype];
                                }
                                else
                                {
                                    // Unrated-to-Unrated member.
                                    distance = 0.0;
                                }
                            }


                            sumDistances += distance;
                        }
                    }

                    // Compute the average distance: sum(distances)/number of competitionTypes;
                    result[i] = (float)((numCtypes == 0) ? 0.0 : (sumDistances / numCtypes));
                }
            }

            // Return the result
            return result;
        }
    }
}

