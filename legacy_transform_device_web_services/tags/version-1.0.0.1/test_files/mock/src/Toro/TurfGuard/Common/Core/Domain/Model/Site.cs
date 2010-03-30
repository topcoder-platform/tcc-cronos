/*!
 * ===========================================================================
 * Site.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-30
 * Description: Core Domain Object for a customer Site (golf course, 
 *              municipality, etc.).
 *
 * ===========================================================================
 */

using System;
using System.Collections.Generic;
using System.Linq;

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class Site : PersistentObject 
    {
        private readonly IList<BaseStation> _baseStations    = new List<BaseStation>();
        private readonly IList<DeviceGroup> _primaryGroups   = new List<DeviceGroup>();
        private readonly IList<DeviceGroup> _secondaryGroups = new List<DeviceGroup>();

        public virtual string       Name                { get; set; }
        public virtual string       Address             { get; set; }
        public virtual string       City                { get; set; }
        public virtual string       PostalCode          { get; set; }
        public virtual double       Latitude            { get; set; }
        public virtual double       Longitude           { get; set; }
        public virtual TimeZoneInfo TimeZone            { get; set; }
        public virtual string       PrimaryGroupName    { get; set; }
        public virtual string       SecondaryGroupName  { get; set; }

        public virtual void AddBaseStation(BaseStation baseStation)
        {
            _baseStations.Add(baseStation);
        }

        public virtual BaseStation[] GetBaseStations()
        {
            return _baseStations.ToArray();
        }

        public virtual void AddPrimaryGroup(DeviceGroup primaryGroup)
        {
            _primaryGroups.Add(primaryGroup);
            primaryGroup.Site = this;
        }

        public virtual DeviceGroup[] GetPrimaryGroups()
        {
            return _primaryGroups.ToArray();
        }

        public virtual void AddSecondaryGroup(DeviceGroup secondaryGroup)
        {
            _secondaryGroups.Add(secondaryGroup);
            secondaryGroup.Site = this;
        }

        public virtual DeviceGroup[] GetSecondaryGroups()
        {
            return _secondaryGroups.ToArray();
        }
    }
}