/*!
 * ===========================================================================
 * IReadingRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-30
 * Description: Data Access repository for device readings
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

using System.Collections.Generic;
using System.Data;
using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IReadingRepository
    {
        /// <summary>
        /// Gets the list of string values that are accepted for a time scale
        /// </summary>
        /// <returns>List of time scales</returns>
        List<string> GetReadingScale();

        /// <summary>
        /// Gets a table of readings for the criterion specified
        /// </summary>
        /// <param name="query">Reading Query object with either devices or groups specified.</param>
        /// <returns>DataTable with columns for data specified and datetime recorded</returns>
        DataTable GetReadings(ReadingQuery query);

        /// <summary>
        /// Gets a table of current readings for the criterion specified
        /// </summary>
        /// <param name="siteId">Unique Id of the site requesting readings</param>
        /// <param name="groupTypeId">The type of group to organize the current readings by. "PrimaryGroup" or "SecondaryGroup"</param>
        /// <param name="restrictGroupId">Unique Id of the group to restrict current readings for. Should belong to the opposite DeviceGroup Type specified in groupType.</param>
        /// <param name="type">The reading type for the readings</param>
        /// <param name="depth">The reading depth for the readings. If null is passed, all depths are averaged together</param>
        /// <returns>DataTable with columns for the group_id and reading value</returns>
        DataTable GetCurrentReadings(int siteId, int groupTypeId, int restrictGroupId, ReadingType type, ReadingDepth depth);

        /// <summary>
        /// Gets the most current reading for a specified install, reading type, and reading depth
        /// </summary>
        /// <param name="deviceInstall">Install to lookup current readings for</param>
        /// <param name="typeId">Reading types to fetch</param>
        /// <param name="depthId">Reading depths to fetch</param>
        /// <returns>Updated DeviceInstall Object with current readings populated.</returns>
        DeviceInstall GetCurrentReadings(DeviceInstall deviceInstall, int typeId, int depthId);
    }
}