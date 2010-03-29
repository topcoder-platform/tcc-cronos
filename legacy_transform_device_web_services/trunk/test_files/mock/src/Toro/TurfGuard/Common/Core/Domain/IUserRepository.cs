/*!
 * ===========================================================================
 * IUserRepository.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-07-31
 * Description: Interface into the Data-Access layer for 'User' objects
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
    public interface IUserRepository : IRepository<User>
    {
        bool ValidateUser(string username, string password);
        User GetUserByName(string username);
    }
}