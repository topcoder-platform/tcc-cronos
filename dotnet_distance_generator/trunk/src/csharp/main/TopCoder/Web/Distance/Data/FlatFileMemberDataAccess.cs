/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Text;

using System.Xml;
using System.IO;

namespace TopCoder.Web.Distance.Data
{

    /// <summary>
    /// This class loads XML files named after coder ids (long values),
    /// supposed to contain all relevant members, with the
    /// member identifying the file first.
    /// </summary>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public class FlatFileMemberDataAccess : IMemberDataAccess
    {
        /// <summary>
        /// The base path.
        /// </summary>
        private string basePath;

        /// <summary>
        /// Provide a base path used for reading individual files.
        /// </summary>
        /// <param name="basePath">The path to the coder data.</param>
        public FlatFileMemberDataAccess(string basePath)
        {
            this.basePath = basePath;
        }

        /// <summary>
        /// A string constant indicating the name of the corresponding element in the XML input.
        /// </summary>
        private const string CODER_ID_ELEMENT = "coder_id";

        /// <summary>
        /// A string constant indicating the name of the corresponding element in the XML input.
        /// </summary>
        private const string HANDLE_ELEMENT = "handle";

        /// <summary>
        /// A string constant indicating the suffix of the corresponding element names in the XML input.
        /// </summary>
        private const string RATING_SUFFIX = "_rating";

        /// <summary>
        /// A string constant indicating the name of the corresponding element in the XML input.
        /// </summary>
        private const string IMAGE_ELEMENT = "image";

        /// <summary>
        /// A string constant indicating the name of the corresponding element in the XML input.
        /// </summary>
        private const string DISTANCE_ELEMENT = "distance";

        /// <summary>
        /// A string constant indicating the name of the corresponding element in the XML input.
        /// </summary>
        private const string OVERLAP_ELEMENT = "overlap";

        /// <summary>
        /// A string constant indicating the name of the corresponding element in the XML input.
        /// </summary>
        private const string COUNTRY_ELEMENT = "country";

        /// <summary>
        /// Parses a single coder from XML DOM.
        /// </summary>
        /// <param name="coder">The element to parse.</param>
        /// <returns>The resulting <see cref="Member"/> object.</returns>
        private Member ParseCoder(XmlNode coder)
        {
            String countryName = null;
            int geoDistance = -1;
            String handle = null;
            long id = long.MinValue;
            String imageName = null;
            int matchOverlap = 0;
            Dictionary<CompetitionTypes, int> ratings = new Dictionary<CompetitionTypes, int>();

            foreach (XmlNode subNode in coder)
            {
                String name = subNode.Name;
                String value = subNode.InnerText;

                if (name == CODER_ID_ELEMENT)
                {
                    id = long.Parse(value);
                }
                else if (name == HANDLE_ELEMENT)
                {
                    handle = value;
                }
                else if (name == IMAGE_ELEMENT)
                {
                    imageName = value;
                }
                else if (name == DISTANCE_ELEMENT)
                {
                    geoDistance = (int)(double.Parse(value) * 1000);
                }
                else if (name == OVERLAP_ELEMENT)
                {
                    matchOverlap = int.Parse(value);
                }
                else if (name == COUNTRY_ELEMENT)
                {
                    countryName = value;
                }
                else
                {
                    // Match all competition type names
                    // for (CompetitionType type : CompetitionType.values())
                    foreach (CompetitionTypes type in Enum.GetValues(typeof(CompetitionTypes)))
                    {
                        if (name == type.ToString().ToLower() + RATING_SUFFIX)
                        {
                            ratings[type] = int.Parse(value);
                        }
                    }
                }
            }

            foreach (CompetitionTypes t in Enum.GetValues(typeof(CompetitionTypes)))
            {
                if (!ratings.ContainsKey(t))
                {
                    ratings[t] = 0;
                }
            }

            return new Member(id, handle, ratings, countryName, imageName, geoDistance, matchOverlap);
        }

        /// <summary>
        /// Gets all member objects from the XML file for the matching id.
        /// </summary>
        /// <param name="id">The id to read from.</param>
        /// <returns>The final list.</returns>
        /// <exception cref="MemberDataAccessException">If error occurs during retrieval.</exception>
        private IList<Member> GetMembers(long id)
        {
            try
            {
                XmlDocument xmld = new XmlDocument();
                xmld.Load(basePath + id.ToString() + ".xml");
                List<Member> result = new List<Member>();

                if (xmld == null)
                {
                    return result;
                }

                XmlNode root = xmld.ChildNodes[0];

                // root element map
                foreach (XmlNode coder in root.ChildNodes)
                {
                    // coder elements
                    result.Add(ParseCoder(coder));
                }

                return result;
            }
            catch (IOException e)
            {
                throw new MemberDataAccessException("Error reading coder file.", e);
            }
            catch (XmlException e)
            {
                throw new MemberDataAccessException("Error parsing coder file.", e);
            }
        }

        /// <summary>
        /// Loads the single, first, member in the XML file for the specified coder.
        /// b</summary>
        /// <param name="id">The id of the coder.</param>
        /// <returns>
        /// A Member object for the coder, or null if there is no file for this coder.
        /// </returns>
        /// <exception cref="MemberDataAccessException">
        /// Thrown for any parsing error.
        /// </exception>
        public Member GetMember(long id)
        {
            IList<Member> members = GetMembers(id);
            if (members.Count == 0)
            {
                return null;
            }

            return members[0];
        }

        /// <summary>
        /// Loads all related members (not the "origin member") from the file of
        /// the specified coder.
        /// </summary>
        /// <param name="id">
        /// The id of the coder.
        /// </param>
        /// <param name="competitionTypes">
        /// The competition types requested.
        /// </param>
        /// <returns>
        /// A list containing all the members related to the origin member.
        /// </returns>
        public IList<Member> GetRelatedMembers(long id, CompetitionTypes competitionTypes)
        {
            List<Member> result = new List<Member>();
            IList<Member> members = GetMembers(id);

            if (members.Count >= 2)
            {
                members.Remove(members[0]);
                result = new List<Member>(members);
            }

            return result;
        }
    }

}
