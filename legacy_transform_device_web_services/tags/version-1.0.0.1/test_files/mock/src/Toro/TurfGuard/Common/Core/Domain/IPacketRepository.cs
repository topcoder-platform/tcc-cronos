/*!
 * ===========================================================================
 * IPacketRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-26
 * Description: Data Access repository for packet objects
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IPacketRepository
    {
        /// <summary>
        /// Adds a packet to the repository
        /// </summary>
        /// <param name="packet">The packet to be added</param>
        /// <returns>True if successful</returns>
        void Add(Packet packet);
    }
}