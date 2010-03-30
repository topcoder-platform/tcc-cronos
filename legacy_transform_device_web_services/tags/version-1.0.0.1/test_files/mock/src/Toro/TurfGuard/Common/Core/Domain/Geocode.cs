/*!
 * ===========================================================================
 * Geocode.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-20
 * Description: Custom Domain Object to represent a Latitude/Longitude Pair
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain
{
    public class Geocode
    {
        /// <summary>
        /// Friendly name for this Geocode
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// The Latitude
        /// </summary>
        public double Latitude { get; set; }

        /// <summary>
        /// The Logitude
        /// </summary>
        public double Longitude { get; set; }
    }
}