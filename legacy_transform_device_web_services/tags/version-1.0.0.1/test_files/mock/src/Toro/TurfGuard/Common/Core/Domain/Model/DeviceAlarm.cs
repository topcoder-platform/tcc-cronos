/*!
 * ===========================================================================
 * DeviceAlarm.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-08-10
 * Description: Domain object for a 'Alarm' on the readings from an installed
 *              device.
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class DeviceAlarm
    {
        /// <summary>
        /// Unique Id of this record from the database.
        /// </summary>
        public long Id { get; set; }
        
        /// <summary>
        /// Unique Id of the DeviceInstall record from the database that produced this alarm.
        /// </summary>
        public long InstallId { get; set;}

        /// <summary>
        /// Friendly name of the DeviceInstall record that produced this alarm.
        /// </summary>
        public string InstallName { get; set; }

        /// <summary>
        /// Friendly name of the PrimaryGroup that the DeviceInstall belongs to
        /// </summary>
        public string GroupName { get; set; }

        /// <summary>
        /// ReadingThreshold that this alarm is applying for.
        /// </summary>
        public ReadingThreshold Threshold { get; set; }

        /// <summary>
        /// Whether this alarm is currently occuring.
        /// </summary>
        public bool IsActive { get; set; }

        /// <summary>
        /// Whether this alarm is high or low.
        /// </summary>
        public bool IsHigh { get; set; }
    }
}