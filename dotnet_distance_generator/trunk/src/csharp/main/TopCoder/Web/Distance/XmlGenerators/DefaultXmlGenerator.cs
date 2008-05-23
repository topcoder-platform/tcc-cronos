/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
using System;
using System.Collections.Generic;
using System.IO;
using System.Globalization;
using System.Text;
using System.Xml;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance.XmlGenerators
{

    /// <summary>
    /// The default implementation of the <see cref="IXmlGenerator"/> interface.
    /// It generates an XML string that conforms to the deifned XSD.
    /// </summary>
    /// <threadsafety>
    /// This class is thread-safe since it is immutable.
    /// </threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public class DefaultXmlGenerator : IXmlGenerator
    {

        /// <summary>
        /// The element name for the map node.
        /// </summary>
        private const string elementMap = "map";

        /// <summary>
        /// The element name for the coder node.
        /// </summary>
        private const string elementCoder = "coder";

        /// <summary>
        /// The element name for the coder_id node.
        /// </summary>
        private const string elementCoderId = "coder_id";

        /// <summary>
        /// The element name for the handle node.
        /// </summary>
        private const string elementHandle = "handle";

        /// <summary>
        /// The element name for the rating node.
        /// </summary>
        private const string elementRating = "rating";

        /// <summary>
        /// The element name for the image node.
        /// </summary>
        private const string elementImage = "image";

        /// <summary>
        /// The element name for the distance node.
        /// </summary>
        private const string elementDistance = "distance";

        /// <summary>
        /// The element name for the overlap node.
        /// </summary>
        private const string elementOverlap = "overlap";

        /// <summary>
        /// The element name for the country node.
        /// </summary>
        private const string elementCountry = "country";

        /// <summary>
        /// The name of the xml namespace attribute.
        /// </summary>
        private const string attributeXmlns = "xmlns";

        /// <summary>
        /// The value to use for the xmlns attribute.
        /// </summary>
        private const string xmlnsValue = "http://www.topcoder.com/Distance";

        /// <summary>
        /// The default do-nothing constructor.
        /// </summary>
        public DefaultXmlGenerator()
        {
        }

        /// <summary>
        /// Generates an XML string based on given distance data between the
        /// given member and the list of related members. Note that negative
        /// numbers should be treated as undefined and be ignored.
        /// </summary>
        /// <param name="member">
        /// The member for whom the distances are calculated.
        /// </param>
        /// <param name="relatedMembers">
        /// The list of related members to whom the distances are calculated
        /// for the given member.
        /// </param>
        /// <param name="distances">
        /// The distances to the related members, it should have exactly the same size as
        /// the <paramref name="relatedMembers"/> list, and each distance corresponds to a related
        /// member at the same index.
        /// </param>
        /// <returns>
        /// The generated XML.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// If any argument is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If <paramref name="relatedMembers"/> and <paramref name="distances"/> lists have different sizes or
        /// if the <paramref name="relatedMembers"/>list contains a null element.
        /// </exception>
        public string GenerateXml(Member member, IList<Member> relatedMembers,
            IList<float> distances)
        {
            Helper.ValidateNotNull("member", member);
            Helper.ValidateList<Member>("relatedMembers", relatedMembers, true);
            Helper.ValidateNotNull("distances", distances);
            Helper.ValidateArgument("distances", (distances.Count != relatedMembers.Count),
                "[distances] should have same number of elements as [relatedMembers].");

            // Use a StringBuilder to hold the result.
            StringBuilder sb = new StringBuilder();

            // Use a StringWriter to allow writing to the string builder.
            using (StringWriter stringWriter = new StringWriter(sb))
            {
                // Use an XmlTexWriter for writing to the StringWriter.
                using (XmlTextWriter writer = new XmlTextWriter(stringWriter))
                {
                    // Write the start of the map element.
                    writer.WriteStartElement(elementMap);

                    // Write the element and sub-elements for the member.
                    writer.WriteAttributeString(attributeXmlns, xmlnsValue);

                    GenerateXmlForMember(writer, member, 0f);

                    for (int idx = 0; idx < relatedMembers.Count; idx++)
                    {
                        // Include only those related members that have non-negative distances.
                        if (distances[idx] >= 0)
                        {
                            GenerateXmlForMember(writer, relatedMembers[idx], distances[idx]);
                        }
                    }

                    // Close the map element.
                    writer.WriteEndElement();
                }
            }

            // Return the string that was built in the string builder.
            return sb.ToString();
        }

        /// <summary>
        /// Generates the XML for a member.
        /// </summary>
        /// <param name="writer">
        /// The writer to use for generating the member.
        /// </param>
        /// <param name="member">
        /// The member.
        /// </param>
        /// <param name="distance">
        /// The distance of the member.
        /// </param>
        private void GenerateXmlForMember(XmlTextWriter writer, Member member, float distance)
        {
            // Write the start of the coder element.
            writer.WriteStartElement(elementCoder);

            // Write the sub-elements of the coder node with values from the member.
            writer.WriteElementString(elementCoderId, member.Id.ToString(CultureInfo.InvariantCulture));
            writer.WriteElementString(elementHandle, member.Handle);
            writer.WriteElementString(elementRating, member.MaxRating.ToString(CultureInfo.InvariantCulture));
            writer.WriteElementString(elementImage, member.Image);
            writer.WriteElementString(elementDistance,
                distance.ToString("0.0#########", CultureInfo.InvariantCulture));
            writer.WriteElementString(elementOverlap, member.MatchOverlap.ToString(CultureInfo.InvariantCulture));
            writer.WriteElementString(elementCountry, member.Country);

            // Close the coder element.
            writer.WriteEndElement();
        }
    }
}

