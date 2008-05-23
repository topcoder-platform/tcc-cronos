/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// This interface defines the contract to generate XML string for the calculated
    /// distance data. Implementations of this interface will be plugged to the
    /// <c>DefaultDistanceGenerator</c> class and are not supposed to be
    /// directly used.
    /// </summary>
    /// <remarks>
    /// Thread-Safety: Implementations of this interface should be thread-safe.
    /// </remarks>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public interface IXmlGenerator
    {

        /// <summary>
        /// Generates the XML string based on the given distance data between the
        /// given member and the list of related members.
        /// </summary>
        /// <param name="member">
        /// The member for whom the distances are calculated.
        /// </param>
        /// <param name="relatedMembers">
        /// The list of related members to whom the distances are calcuated for
        /// the given member.
        /// </param>
        /// <param name="distances">
        /// A list of the distances to the related members which should have
        /// exactly the same size as the relatedMembers list, with each distance
        /// corresponding to a related member at the same index.
        /// </param>
        /// <returns>
        /// The generated XML.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// If any argument is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If <paramref name="relatedMembers"/> and <paramref name="distances"/> lists
        /// have different size or the <paramref name="relatedMembers"/> contains a null element.
        /// </exception>
        string GenerateXml(Member member, IList<Member> relatedMembers, IList<float> distances);


    }
}
