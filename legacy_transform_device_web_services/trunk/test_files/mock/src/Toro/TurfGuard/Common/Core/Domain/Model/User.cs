/*!
 * ===========================================================================
 * User.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-31
 * Description: User domain object
 *
 * ===========================================================================
 */

using System;
using System.Collections.Generic;
using System.Linq;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class User : PersistentObject
    {
        private readonly IList<Site> _sites = new List<Site>();

        public virtual string Username { get; set; }
        // Todo: Change password to hash or password salt when user info is moved to LDAP
        public virtual string Password { get; set; }
        public virtual string FirstName { get; set; }
        public virtual string LastName { get; set; }

        public User()
        {
            _sites.Add(new Site { Id = 1, Name = "site1" });
            _sites.Add(new Site { Id = 2, Name = "site2" });
            _sites.Add(new Site { Id = 3, Name = "site3" });
        }

        public virtual void AddSite(Site site)
        {
            _sites.Add(site);
        }

        public virtual Site[] GetSites()
        {
            return _sites.ToArray();
        }
    }
}