/*!
 * ===========================================================================
 * IAuthenticationService.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-11
 * Description: Interface for service to authenticate users.
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Services
{
    public interface IAuthenticationService
    {
        bool PasswordMatches(User user, string password);
    }
}
