/*!
 * ===========================================================================
 * IDeviceInstallRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-15
 * Description: Generic interface to persist DeviceInstall objects
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IDeviceInstallRepository : IRepository<DeviceInstall>
    {
        DeviceInstall[] GetAllForDevice(Device device);
        DeviceInstall[] GetAllForDeviceGroup(DeviceGroup deviceGroup);
    }
}
