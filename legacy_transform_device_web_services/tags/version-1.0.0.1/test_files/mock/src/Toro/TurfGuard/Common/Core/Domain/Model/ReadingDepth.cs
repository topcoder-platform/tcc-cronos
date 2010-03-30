/*!
 * ===========================================================================
 * ReadingDepth.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-30
 * Description: DOM object describing the depth of reading from a Device
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */

namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class ReadingDepth
    {
        /// <summary>
        /// The Id used when 'All' <c>ReadingDepth</c> is needed
        /// </summary>
        public const int All = 0;

        /// <summary>
        /// The unique Id driven from the database
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Friendly Name
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// Converts this object into a string representing its name
        /// </summary>
        /// <returns>String</returns>
        public override string ToString()
        {
            return Name;
        }
    }
}