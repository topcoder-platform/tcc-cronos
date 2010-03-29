/*!
 * ===========================================================================
 * IFormRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-30
 * Description: Data Access repository for objects in the UI Forms such as 
 *              Devices, Sites, and Groups.
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

using System.Collections.Generic;
using System.Xml;
using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    // TODO: Break this apart into object-specific repositories: site, user, device, reading
    public interface IFormRepository
    {
        /// <summary>
        /// Gets the current types of readings from the database
        /// </summary>
        /// <returns>List of ReadingType objects</returns>
        List<ReadingType> GetReadingTypes();

        /// <summary>
        /// Gets the current depths of readings from the database
        /// </summary>
        /// <returns>List of ReadingDepth objects</returns>
        List<ReadingDepth> GetReadingDepths();

        /// <summary>
        /// Gets the Site with the specified Id
        /// </summary>
        /// <param name="siteId">Unique Id of the Site driven from the database</param>
        /// <returns>Site object</returns>
        Site GetSiteById(int siteId);

        /// <summary>
        /// Gets the Current Device Installations in a specified site
        /// </summary>
        /// <param name="siteId">Unique Id of the Site driven from the database</param>
        /// <returns>List of DeviceInstall objects</returns>
        List<DeviceInstall> GetCurrentDeviceInstallsBySite(int siteId);

        /// <summary>
        /// Gets a single device object by its Unique Id
        /// </summary>
        /// <param name="deviceId">Unique Id of the device</param>
        /// <returns>Device Object</returns>
        Device GetDeviceById(int deviceId);

        /// <summary>
        /// Gets the Primary organizational <c>DeviceGroup</c>s for the specified site
        /// </summary>
        /// <param name="siteId">Unique Id of the site driven from the database</param>
        /// <returns>List of DeviceGroup objects</returns>
        List<DeviceGroup> GetPrimaryGroupsBySite(int siteId);

        /// <summary>
        /// Gets the Secondary organizational <c>DeviceGroup</c>s for the specified site
        /// </summary>
        /// <param name="siteId">Unique Id of the site driven from the database</param>
        /// <returns>List of DeviceGroup objects</returns>
        List<DeviceGroup> GetSecondaryGroupsBySite(int siteId);

        /// <summary>
        /// Gets an Xml formatted to create a "Tree" Navigation structure for Groups
        /// and DeviceInstalls for the specified site
        /// </summary>
        /// <param name="siteId">Unique Id of the site driven from the database</param>
        /// <param name="sortBy">Sort order of the XML, "primary" or "secondary"</param>
        /// <returns>XMLDocument</returns>
        XmlDocument GetTreeNavigationXml(int siteId, string sortBy);

        /// <summary>
        /// Updates a Device record
        /// </summary>
        /// <param name="device">new device object</param>
        /// <returns>device that was written to the database</returns>
        Device UpdateDevice(Device device);

        /// <summary>
        /// Updates a DeviceInstall record
        /// </summary>
        /// <param name="install">DeviceInstall object to write</param>
        /// <returns>Object that was written to the database</returns>
        DeviceInstall UpdateDeviceInstall(DeviceInstall install);

        /// <summary>
        /// Gets a list of active alarms for a given site
        /// </summary>
        /// <param name="siteId">Unique Id of the site</param>
        /// <returns>List of DeviceAlarm</returns>
        List<DeviceAlarm> GetAlarmsBySite(int siteId);
    }
}