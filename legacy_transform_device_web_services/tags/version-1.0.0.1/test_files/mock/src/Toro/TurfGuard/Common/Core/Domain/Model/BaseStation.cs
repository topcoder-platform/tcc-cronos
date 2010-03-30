/*!
 * ===========================================================================
 * BaseStation.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-01
 * Description: Core domain object for a TurfGuard base station device 
 *              installed at a customer's site.
 *
 * ===========================================================================
 */

using System.Collections.Generic;
using System.Linq;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class BaseStation : PersistentObject
    {
        private readonly IList<Connection> _connections = new List<Connection>();

        public virtual string NodeId { get; set; }

        public virtual void AddConnection(Connection connection)
        {
            _connections.Add(connection);
            connection.BaseStation = this;
        }

        public virtual Connection[] GetConnections()
        {
            return _connections.ToArray();
        }
    }
}
