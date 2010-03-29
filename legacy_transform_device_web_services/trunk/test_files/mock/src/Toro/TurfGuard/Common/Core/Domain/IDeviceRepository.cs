/*!
 * ===========================================================================
 * IDeviceRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-11-02
 * Description: Data Access repository for objects in the UI Forms Devices.
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IDeviceRepository : IRepository<Device>
    {
        Device[] GetAllForSite(Site site);

        // Todo: Deprecated
        Device GetDevice(int nodeId);
        // Todo: Deprecated
        void Add(Device device);
    }
}
