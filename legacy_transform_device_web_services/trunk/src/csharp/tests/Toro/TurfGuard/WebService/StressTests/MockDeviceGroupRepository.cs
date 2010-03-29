/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using System.Collections.Generic;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService;
using Toro.TurfGuard.Common.Core.Domain;

namespace Toro.TurfGuard.WebService.StressTests
{
    /// <summary>
    /// <para>
    /// This class is a mock implementation of <see cref="IDeviceGroupRepository"/>.
    /// </para>
    /// </summary>
    ///
    /// <author>assistant</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [CoverageExclude]
    public class MockDeviceGroupRepository : IDeviceGroupRepository
    {
        /// <summary>
        /// The mock repository used for stress tests.
        /// </summary>
        public static IDictionary<string, DeviceGroup> Repository = new Dictionary<string, DeviceGroup>();

        /// <summary>
        /// Map which maps device groups to a given site.
        /// </summary>
        public static IDictionary<int, IList<DeviceGroup>> DeviceGroupsForSite =
            new Dictionary<int, IList<DeviceGroup>>();

        /// <summary>
        /// The sequence number to use when adding entities to repository.
        /// </summary>
        public static int SequenceNumber = 1;

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="id">The ID.</param>
        ///
        /// <returns>The device group.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DeviceGroup GetById(int id)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Saves the given device group to the repository.
        /// </summary>
        ///
        /// <param name="entity">The device group to save.</param>
        public void Save(DeviceGroup entity)
        {
            if (entity.Id == 0)
            {
                entity.Id = SequenceNumber++;
            }
            Repository[entity.Name] = entity;
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <returns>The device groups.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public DeviceGroup[] GetAll()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="entity">The device group.</param>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public void Delete(DeviceGroup entity)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets all device groups for given parameters.
        /// </summary>
        ///
        /// <param name="site">The site.</param>
        /// <param name="deviceGroupType">The device group type.</param>
        ///
        /// <returns>All device groups for given parameters.</returns>
        public DeviceGroup[] GetForSite(Site site, int deviceGroupType)
        {
            IList<DeviceGroup> deviceGroups = new List<DeviceGroup>();

            if (!DeviceGroupsForSite.ContainsKey(site.Id))
            {
                return new DeviceGroup[0];
            }

            foreach (DeviceGroup group in DeviceGroupsForSite[site.Id])
            {
                // primary groups have positive id
                if (deviceGroupType == DeviceGroup.PRIMARY && group.Id >= 1)
                {
                    deviceGroups.Add(group);
                }

                // secondary groups have negative id
                else if (deviceGroupType == DeviceGroup.SECONDARY && group.Id <= -1)
                {
                    deviceGroups.Add(group);
                }
            }

            return ((List<DeviceGroup>)deviceGroups).ToArray();
        }
    }
}
