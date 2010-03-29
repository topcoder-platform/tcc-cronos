/*!
 * ===========================================================================
 * DeviceGroup.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-30
 * Description: DOM object parent for all Device Groups
 *
 * ===========================================================================
 */

using System;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class DeviceGroup : PersistentObject
    {
        /// <summary>
        /// Constant DeviceGroupType for PrimaryGroups
        /// </summary>
        public const int PRIMARY = 1;

        /// <summary>
        /// Constant DeviceGroupType for SecondaryGroups
        /// </summary>
        public const int SECONDARY = 2;

        /// <summary>
        /// Type of this <c>DeviceGroup</c>. IE Primary or Secondary
        /// </summary>
        public virtual int DeviceGroupType { get; set; }

        /// <summary>
        /// Human friendly name of this <c>DeviceGroup</c>.
        /// </summary>
        public virtual string Name { get; set; }

        /// <summary>
        /// Internal helper property for the <c>Site</c> this <c>DeviceGroup</c>
        /// belongs to.
        /// </summary>
        internal virtual Site Site { get; set; }

        #region Obsolete

        [Obsolete]
        public virtual int SiteId { get; set; }

        [Obsolete]
        public override string ToString()
        {
            return Name;
        }

        #endregion
    }
}