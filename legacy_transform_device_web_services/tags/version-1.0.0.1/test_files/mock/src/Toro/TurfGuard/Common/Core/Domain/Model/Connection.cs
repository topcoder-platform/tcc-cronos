/*!
 * ===========================================================================
 * Connection.cs
 * Copyright (c) 2010 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2010-02-08
 * Description: Core domain object for tracking a network connection from a 
 *              BaseStation to Toro.
 *
 * ===========================================================================
 */

using System;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class Connection : PersistentObject
    {
        public virtual string SourceIpAddress { get; set; }
        public virtual DateTime? ConnectedOn { get; set;}
        public virtual DateTime? DisconnectedOn { get; set; }
        public virtual int MessageCount { get; set; }

        internal virtual BaseStation BaseStation { get; set; }  // Helper Property
    }
}