/*!
 * ===========================================================================
 * KeyedObject.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-11-30
 * Description: Abstract Keyed Domain Object
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public abstract class KeyedObject : PersistentObject
    {
        public virtual string Key { get; set; }
    }
}