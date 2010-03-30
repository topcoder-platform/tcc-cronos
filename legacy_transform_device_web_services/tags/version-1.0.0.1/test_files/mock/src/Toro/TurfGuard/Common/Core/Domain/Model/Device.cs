/*!
 * ===========================================================================
 * Device.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-27
 * Description: Core Domain Object for a Device (or Sensor)
 *
 * ===========================================================================
 */

using System;
using System.Collections.Generic;
using System.Linq;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    /// <summary>
    /// Core Domain Object for a Device (or Sensor)
    /// </summary>
    public class Device : PersistentObject
    {
        /// <summary>
        /// DeviceType for the 'TG2' Sensor
        /// </summary>
        public const int TG2 = 1;

        /// <summary>
        /// <c>Site</c> where this <c>Device</c> is located
        /// </summary>
        public virtual Site Site { get; set; }

        /// <summary>
        /// Node ID, or Serial Number, printed on the outside
        /// of this <c>Device</c>
        /// </summary>
        public virtual int NodeId { get; set; }

        /// <summary>
        /// Human-Friendly name of this device
        /// </summary>
        public virtual string Name { get; set; }

        /// <summary>
        /// Manufacturing type of this <c>Device</c>, IE: TG2
        /// </summary>
        public virtual int DeviceType { get; set; }

        private readonly IList<DeviceInstall> _deviceInstalls = new List<DeviceInstall>();

        /// <summary>
        /// Adds a new <c>DeviceInstall</c> to the list of installations
        /// this <c>Device</c> has had.
        /// </summary>
        /// <param name="deviceInstall"><c>DeviceInstall</c> to add</param>
        public virtual void AddDeviceInstall(DeviceInstall deviceInstall)
        {
            _deviceInstalls.Add(deviceInstall);
            deviceInstall.Device = this;
        }

        /// <summary>
        /// Returns an array of every installation this <c>Device</c> has had.
        /// </summary>
        /// <returns>Array of <c>DeviceInstall</c></returns>
        public virtual DeviceInstall[] GetDeviceInstalls()
        {
            return _deviceInstalls.ToArray();
        }

        #region Obsolete

        /// <summary>
        /// Obsolete property for the Serial Number of the device. This property
        /// is populated by the legacy 'Enterprise Block' persistence layer.
        /// </summary>
        //[Obsolete]
        public virtual string SerialNumber { get; set; }

        /// <summary>
        /// Obsolete property for the ID of the Site of this device. This 
        /// property is populated by the legacy 'Enterprise Block' persistence
        /// layer
        /// </summary>
        [Obsolete]
        public virtual int SiteId { get; set; }

        /// <summary>
        /// Obsolete property for the ID of the current DeviceInstall installation.
        /// This property is populated by the legacy 'Enterprise Block' persistence
        /// layer
        /// </summary>
        [Obsolete]
        public virtual int DeviceInstallId { get; set; }

        #endregion
    }
}