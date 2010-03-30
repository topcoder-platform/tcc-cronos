/*!
 * ===========================================================================
 * ReadingThreshold.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-27
 * Description: DOM object for a threshold put on a DeviceInstalls Readings
 *              for a given ReadingType.
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class ReadingThreshold
    {
        /// <summary>
        /// Unique Id of this ReadingThreshold driven by the database.
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Unique Id of the DeviceInstall that owns this ReadingThreshold, 
        /// driven by the database.
        /// </summary>
        public int DeviceInstallId { get; set; }
        
        /// <summary>
        /// ReadingType that this ReadingThreshold is applicable for.
        /// </summary>
        public ReadingType Type { get; set; }

        /// <summary>
        /// Upper Reading limit for this ReadingThreshold.
        /// </summary>
        public double UpperThreshold { get; set; }

        /// <summary>
        /// Lower Reading limit for this ReadingThreshold.
        /// </summary>
        public double LowerThreshold { get; set; }
    }
}