/*!
 * ===========================================================================
 * IDeviceGroupRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-14
 * Description: Specific Interface for persistence of DeviceGroup objects
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IDeviceGroupRepository : IRepository<DeviceGroup>
    {
        DeviceGroup[] GetForSite(Site site, int deviceGroupType);
    }
}
