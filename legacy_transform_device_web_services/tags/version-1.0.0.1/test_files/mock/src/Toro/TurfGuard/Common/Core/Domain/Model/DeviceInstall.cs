/*!
 * ===========================================================================
 * DeviceInstall.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-08-31
 * Description: DOM object for an installation of a Device into a site.
 *
 * ===========================================================================
 */

using System;
using System.Collections.Generic;
using System.Linq;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class DeviceInstall : PersistentObject
    {
        private readonly IList<Threshold> _thresholds = new List<Threshold>();

        public virtual string Name { get; set; }
        public virtual double Latitude { get; set; }
        public virtual double Longitude { get; set; }
        public virtual DeviceGroup PrimaryGroup { get; set; }
        public virtual DeviceGroup SecondaryGroup { get; set; }
        public virtual DateTime InstallationDate { get; set; }
        public virtual DateTime? RemovalDate { get; set; }

        public virtual Device Device { get; set; }

        public virtual void AddThreshold(Threshold threshold)
        {
            _thresholds.Add(threshold);
            threshold.DeviceInstall = this;
        }

        public virtual Threshold[] GetThresholds()
        {
            return _thresholds.ToArray();
        }

        #region deprecated

        public virtual int SiteId { get; set; }
        public virtual int DeviceId { get; set; }
        public virtual int PrimaryGroupId { get; set; }
        public virtual int SecondaryGroupId { get; set; }
        public virtual Geocode Geocode { get; set; }

        public virtual double UpperTemperature { get; set; }
        public virtual double UpperMoisture { get; set; }
        public virtual double UpperSalinity { get; set; }
        public virtual double LowerTemperature { get; set; }
        public virtual double LowerMoisture { get; set; }
        public virtual double LowerSalinity { get; set; }

        #endregion
    }
}