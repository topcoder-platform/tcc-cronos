/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using System.Collections.Generic;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService;

namespace Toro.TurfGuard.Common.Core.Domain.Impl
{
    /// <summary>
    /// <para>
    /// This class is a mock implementation of <see cref="IDeviceInstallRepository"/> used in the unit tests.
    /// </para>
    /// </summary>
    ///
    /// <threadsafety>
    /// <para>
    /// Thread safety is not important here since this class is only used for unit testing.
    /// </para>
    /// </threadsafety>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [CoverageExclude]
    public class MockDeviceInstallRepository : IDeviceInstallRepository
    {
        /// <summary>
        /// The sequence number to use when adding entities to repository.
        /// </summary>
        public static int SequenceNumber;

        /// <summary>
        /// The mock repository used for unit tests.
        /// </summary>
        public static IDictionary<int, DeviceInstall> Repository = new Dictionary<int, DeviceInstall>();

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="id">The ID.</param>
        ///
        /// <returns>The device install.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DeviceInstall GetById(int id)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="entity">The device install.</param>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public void Save(DeviceInstall entity)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <returns>The device installs.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DeviceInstall[] GetAll()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="entity">The device install.</param>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public void Delete(DeviceInstall entity)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets all device installs for given <paramref name="device"/>.
        /// </summary>
        ///
        /// <param name="device">The device for which to get all device installs.</param>
        ///
        /// <returns>All device installs for given <paramref name="device"/>.</returns>
        public DeviceInstall[] GetAllForDevice(Device device)
        {
            IList<DeviceInstall> deviceInstalls = new List<DeviceInstall>();

            // find installs that match for given device
            foreach (DeviceInstall install in Repository.Values)
            {
                if (install.Device.NodeId == device.NodeId)
                {
                    deviceInstalls.Add(install);
                }
            }

            return ((List<DeviceInstall>)deviceInstalls).ToArray();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="deviceGroup">The device group.</param>
        ///
        /// <returns>The device installs.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DeviceInstall[] GetAllForDeviceGroup(DeviceGroup deviceGroup)
        {
            throw new NotImplementedException();
        }
    }
}
