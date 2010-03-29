/*!
 * ===========================================================================
 * PersistentObject.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-01
 * Description: Abstract type for all persistent objects.
 * 
 *              Adapted from open-source project Tarantino-Core
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public abstract class PersistentObject
    {
        public virtual int Id { get; set; }

        public virtual bool IsPersistent
        {
            get { return IsPersistentObject(); }
        }

        public override bool Equals(object obj)
        {
            if (IsPersistentObject())
            {
                var persistentObject = obj as PersistentObject;
                return (persistentObject != null) && (Id == persistentObject.Id);
            }

            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return IsPersistentObject() ? Id.GetHashCode() : base.GetHashCode();
        }

        private bool IsPersistentObject()
        {
            return (Id != 0);
        }
    }
}