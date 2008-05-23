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
    /// that calculates match overlap distance. It implements the default match overlap
    /// calculation formula as stated in the algorithm section of the component specification.
    /// </summary>
    /// <threadsafety>
    /// This class is thread-safe since it is immutable.
    /// </threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public class OverlapDistanceCalculator : IDistanceCalculator
    {

        /// <summary>
        /// The default do-nothing constructor.
        /// </summary>
        public OverlapDistanceCalculator()
        {
        }

        /// <summary>
        /// Calculates the match overlap distances from the given member to all
        /// its related members. The returned list should contain the distances
        /// to each related member in the same order as the related members are
        /// given in the list. A negative value in the returned list means that
        /// the distance is &quot;undefined&quot;.
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
        /// The match overlap distances to each related member.
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

            double minOverlap = double.MaxValue;
            double maxOverlap = 0;

            // Get the minimum and maximum overlap of all members involved in the calculation.
            foreach (Member relatedMember in relatedMembers)
            {
                if (relatedMember.MatchOverlap > maxOverlap)
                {
                    maxOverlap = relatedMember.MatchOverlap;
                }
                if (relatedMember.MatchOverlap < minOverlap)
                {
                    minOverlap = relatedMember.MatchOverlap;
                }
            }

            for (int i = 0; i < relatedMembers.Count; i++)
            {
                Member relatedMember = relatedMembers[i];

                // Compute the distance. Note that the cast to float is needed as
                // there is no implicit conversion from double to float.
                result[i] = (float)((maxOverlap == 0) ? 1.0 :
                    (1.0 - (((relatedMember.MatchOverlap - minOverlap) / maxOverlap))));
            }

            // Return the result
            return result;
        }
    }
}

