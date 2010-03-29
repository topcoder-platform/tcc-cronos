/*!
 * ===========================================================================
 * Packet.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-27
 * Description: DOM Object for a packet sent or received on the Turf Guard 
 *              Protocol.
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class Packet
    {
        /// <summary>
        /// The serial number of the base station on the remote endpoint for 
        /// this packet.
        /// </summary>
        public int BaseStationSerialNumber { get; set; }

        /// <summary>
        /// The serial number of the device this packet originated from.
        /// </summary>
        public int DeviceSerialNumber { get; set; }

        /// <summary>
        /// The type number of the packet, driven from the Turf Guard protocol
        /// For example, 13 is a TG2 Data Packet
        /// </summary>
        public int TypeNumber { get; set; }

        /// <summary>
        /// The payload portion of the packet.
        /// </summary>
        public byte[] Payload { get; set; }
    }
}