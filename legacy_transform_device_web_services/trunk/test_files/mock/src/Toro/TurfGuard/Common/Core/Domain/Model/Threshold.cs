/*!
 * ===========================================================================
 * Threshold.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-15
 * Description: Domain object for reading thresholds on device installations
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class Threshold : PersistentObject
    {
        public const int MOISTURE = 1;
        public const int TEMPERATURE = 2;
        public const int SALINITY = 3;

        public virtual int ReadingType { get; set; }
        public virtual double UpperBound { get; set; }
        public virtual double LowerBound { get; set; }

        internal virtual DeviceInstall DeviceInstall { get; set; }
    }
}
