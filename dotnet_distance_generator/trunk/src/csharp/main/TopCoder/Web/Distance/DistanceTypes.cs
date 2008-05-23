/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// This enumeration contains the basic types of distances between users.
    /// </summary>
    /// <threadsafety>Enumerations are thread-safe.</threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [Flags]
    public enum DistanceTypes
    {
        /// <summary>
        /// The number of competitions shared between members. Lower
        /// overlap indicates more distance.
        /// </summary>
        Overlap = 0x01,

        /// <summary>
        /// The geographical distance between members. Larger geographical
        /// distances indicates larger distance between two members.
        /// </summary>
        Country = 0x02,

        /// <summary>
        /// The rating distance between two members.
        /// </summary>
        Rating = 0x04
    }
}
