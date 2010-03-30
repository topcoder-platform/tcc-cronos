/*!
 * ===========================================================================
 * ReadingType.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-06-30
 * Description: Domain object for describing the type of reading reecorded 
 *              from a device. Represented in the database in the 
 *              'reading_type' table.
 *
 * Modifier:
 * Modified:
 * Description:
 *
 * ===========================================================================
 */
namespace Toro.TurfGuard.Common.Core.Domain.Model
{
    public class ReadingType
    {
        /// <summary>
        /// The reserved Id used when 'All' is needed
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
        /// Upper limit that readings of this type should be within
        /// </summary>
        public float UpperBound { get; set; }

        /// <summary>
        /// Lower limit that readings of this type should be within
        /// </summary>
        public float LowerBound { get; set; }

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