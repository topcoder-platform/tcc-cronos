/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// <para>This interface defines the contract to calculate the various distances.
    /// This component provides three implementations to support three kinds of
    /// distance calculations: rating diatance, geographical distance and match overlap
    /// distance. Implementations of this interface will be plugged to implementations
    /// of the <see cref="IDistanceGenerator"/> interface and will not be used directly.</para>
    /// </summary>
    /// <remarks>
    /// Thread-Safety: Implementations of this interface should be thread-safe.
    /// </remarks>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public interface IDistanceCalculator
    {

        /// <summary>
        /// Calculates distances from the given member to all its related members.
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
        /// The distances to each related member. Any negative value in the
        /// returned list means the distance is &quot;undefined&quot;.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// If either argument is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If the list is empty or contains a null element.
        /// </exception>
        IList<float> CalculateDistance(Member member, IList<Member> relatedMembers, CompetitionTypes type);


    }
}
