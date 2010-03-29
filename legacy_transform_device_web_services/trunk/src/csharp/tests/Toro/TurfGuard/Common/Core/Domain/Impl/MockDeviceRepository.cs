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
    /// This class is a mock implementation of <see cref="IDeviceRepository"/> used in the unit tests.
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
    public class MockDeviceRepository : IDeviceRepository
    {
        /// <summary>
        /// The mock repository used for unit tests.
        /// </summary>
        public static IDictionary<int, Device> Repository = new Dictionary<int, Device>();

        /// <summary>
        /// Gets the device for given <paramref name="id"/>.
        /// </summary>
        ///
        /// <param name="id">The ID of the device.</param>
        ///
        /// <returns>The matching device.</returns>
        public Device GetById(int id)
        {
            return Repository.ContainsKey(id) ? Repository[id] : null;
        }

        /// <summary>
        /// Saves the given device to the repository.
        /// </summary>
        ///
        /// <param name="entity">The device to save.</param>
        public void Save(Device entity)
        {
            Repository[entity.NodeId] = entity;
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <returns>All devices.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public Device[] GetAll()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="entity">The entity.</param>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public void Delete(Device entity)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets all devices for given site.
        /// </summary>
        ///
        /// <param name="site">The site.</param>
        ///
        /// <returns>All devices for given site.</returns>
        public Device[] GetAllForSite(Site site)
        {
            IList<Device> devices = new List<Device>();

            foreach (Device device in Repository.Values)
            {
                if (device.Site.Id == site.Id)
                {
                    devices.Add(device);
                }
            }

            return (new List<Device>(devices)).ToArray();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="nodeId">The node ID.</param>
        ///
        /// <returns>The device.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public Device GetDevice(int nodeId)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Adds the given device to the repository.
        /// </summary>
        ///
        /// <param name="device">The device to add.</param>
        public void Add(Device device)
        {
            Repository[device.NodeId] = device;

            // store the device installs for the device
            foreach (DeviceInstall deviceInstall in device.GetDeviceInstalls())
            {
                MockDeviceInstallRepository.Repository[MockDeviceInstallRepository.SequenceNumber++] = deviceInstall;

                // mark other device installs as removed for the device installs for the device's site
                for (int i = 0; i < MockDeviceInstallRepository.SequenceNumber - 1; ++i)
                {
                    if (MockDeviceInstallRepository.Repository[i].SiteId == device.Site.Id)
                    {
                        MockDeviceInstallRepository.Repository[i].RemovalDate = DateTime.Now;
                    }
                }
            }
        }
    }
}
