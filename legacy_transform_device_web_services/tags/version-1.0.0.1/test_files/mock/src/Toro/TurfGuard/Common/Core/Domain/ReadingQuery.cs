/*!
 * ===========================================================================
 * ReadingQuery.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-02
 * Description: Helper object for passing reading query parameters to the Reading Repository.
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

using System;
using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public class ReadingQuery
    {
        /// <summary>
        /// DeviceInstall to retrieve readings for. Mutually exclusive with DeviceGroup
        /// </summary>
        public DeviceInstall DeviceInstall { get; set; }

        /// <summary>
        /// DeviceGroup of DeviceInstalls to retrieve readings for. Mutually exclusive with DeviceInstall.
        /// </summary>
        public DeviceGroup Group { get; set; }

        /// <summary>
        /// Restriction if DeviceGroup is used to retrieve readings. This returns only the intersect of both groups.
        /// </summary>
        public DeviceGroup RestrictGroup { get; set; }
        
        /// <summary>
        /// Type of readings to retreive.
        /// </summary>
        public ReadingType Type { get; set; }

        /// <summary>
        /// Depth of readings to retreive
        /// </summary>
        public ReadingDepth Depth { get; set; }

        /// <summary>
        /// Start Date for the query
        /// </summary>
        public DateTime StartDate { get; set; }

        /// <summary>
        /// End Date for the query
        /// </summary>
        public DateTime EndDate { get; set; }

        /// <summary>
        /// Timescale to return in the query. Get options from the IReadingRepository
        /// </summary>
        public string Scale { get; set; }
    }
}