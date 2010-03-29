/*!
 * ===========================================================================
 * IReadingThresholdRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-27
 * Description: Interface into the data-access repository for 
 *              ReadingThreshold objects.
 * 
 * Modifier:
 * Modified:
 * Description:
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IReadingThresholdRepository
    {
        /// <summary>
        /// Adds a new <c>ReadingThreshold</c> object to the repository.
        /// </summary>
        /// <param name="readingThreshold"></param>
        /// <returns>A ReadingThreshold with properties updated by what was
        /// provided from the repository.</returns>
        ReadingThreshold Add(ReadingThreshold readingThreshold);

        /// <summary>
        /// Returns the <c>ReadingThreshold</c> specified for the 
        /// <c>DeviceInstall</c> and <c>ReadingType</c> provided.
        /// </summary>
        /// <param name="deviceInstallId">Unique Id of the DeviceInstall</param>
        /// <param name="readingType">ReadingType</param>
        /// <returns></returns>
        ReadingThreshold GetByDeviceInstall(int deviceInstallId, ReadingType readingType);

        /// <summary>
        /// Updates a ReadingThreshold Record
        /// </summary>
        /// <param name="readingThreshold">Object to write</param>
        /// <returns>Object as written to the database</returns>
        ReadingThreshold SetThreshold(ReadingThreshold readingThreshold);
    }
}