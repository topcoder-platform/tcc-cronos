/*!
 * ===========================================================================
 * IKeyedRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-01
 * Description: Generic interface into keyed object repositories
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Domain
{
    public interface IKeyedRepository<T> : IRepository<T> where T : PersistentObject
    {
        T GetByKey(string key);
    }
}