/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// This enumeration contains the various competition types
    /// available for ratings and competition overlap.
    /// </summary>
    /// <threadsafety>Enumerations are thread-safe.</threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [Flags]
    public enum CompetitionTypes
    {
        /// <summary>
        /// Algorithm competition.
        /// </summary>
        Algorithm = 0x0001,

        /// <summary>
        /// Component development competition.
        /// </summary>
        Development = 0x0002,

        /// <summary>
        /// Component design competition.
        /// </summary>
        Design = 0x0004,

        /// <summary>
        /// Component design competition.
        /// </summary>
        Studio = 0x0008,

        /// <summary>
        /// Marathon match.
        /// </summary>
        Marathon = 0x0010,

        /// <summary>
        /// Assembly competition (currently unrated).
        /// </summary>
        Assembly = 0x0020,

        /// <summary>
        /// High-school algorithm competition.
        /// </summary>
        HighSchool = 0x0040
    }
}
