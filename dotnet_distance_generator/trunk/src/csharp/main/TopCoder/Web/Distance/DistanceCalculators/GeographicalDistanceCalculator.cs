/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance.DistanceCalculators
{

    /// <summary>
    /// This class is the implementation of the <see cref="IDistanceCalculator"/>
    /// interface that calculates the geographical distance. It implements the
    /// default geographical calculation formula as stated in the algorithm section
    /// of the component specification.
    /// </summary>
    /// <threadsafety>
    /// This class is thread-safe since it is immutable.
    /// </threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public class GeographicalDistanceCalculator : IDistanceCalculator
    {

        /// <summary>
        /// The default do-nothing constructor.
        /// </summary>
        public GeographicalDistanceCalculator()
        {
        }

        /// <summary>
        /// Calculates the geographical distances from the given member to all its
        /// related members. The returned list should contain distance to each
        /// related member in the same order as the related members are in the
        /// given list. A negative value in the returned list means that the
        /// distance is &quot;undefined&quot;.
        /// </summary>
        /// <param name="member">
        /// The member to calculate the distances for.
        /// </param>
        /// <param name="relatedMembers">
        /// All related members of the given member, cannot be empty list.
        /// </param>
        /// <param name="type">
        /// The competition types used to compute distance.
        /// </param>
        /// <returns>
        /// The geographical distances to each related member.
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

            // Get the maximum distance of all members involved in the calculation including the origin member.
            float maxDistance = member.GeographicalDistance;

            foreach (Member relatedMember in relatedMembers)
            {
                if (relatedMember.GeographicalDistance > maxDistance)
                {
                    maxDistance = relatedMember.GeographicalDistance;
                }
            }

            if ((member.GeographicalDistance < 0f) || (maxDistance < 0f))
            {
                for (int i = 0; i < result.Length; i++)
                {
                    result[i] = -1;
                }
            }
            else
            {
                for (int i = 0; i < relatedMembers.Count; i++)
                {
                    if (relatedMembers[i].GeographicalDistance >= 0)
                    {
                        result[i] = (maxDistance > 0) ? (relatedMembers[i].GeographicalDistance / maxDistance) : 0f;
                    }
                    else
                    {
                        result[i] = -1;
                    }
                }
            }

            // Return the result
            return result;
        }
    }
}

