/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
using System;
using System.Collections.Generic;
using TopCoder.Configuration;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;
using TopCoder.Web.Distance.DistanceGenerators;
using TopCoder.Web.Distance.XmlGenerators;
using NUnit.Framework;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// Demonstrates the use of the component.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class Demo
    {

        /// <summary>
        /// Demonstrates the use of the component.
        /// </summary>
        [Test]
        public void TestDemo()
        {
            // Set up the configuration object that defines the objects.
            IConfiguration objectDefChild = new DefaultConfiguration("objectDefChild");

            IConfiguration objectConfig = new DefaultConfiguration("object_1");
            objectConfig.SetSimpleAttribute("name", "fileBasedDataAccess");
            IConfiguration typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(MyDataAccess).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_1.parameters");
            objectConfig.SetSimpleAttribute("name", "fileBasedDataAccess");

            objectConfig = new DefaultConfiguration("object_2");
            objectConfig.SetSimpleAttribute("name", "myXmlGenerator");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(DefaultXmlGenerator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_3");
            objectConfig.SetSimpleAttribute("name", "rCal");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(RatingDistanceCalculator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_4");
            objectConfig.SetSimpleAttribute("name", "oCal");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(OverlapDistanceCalculator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            objectConfig = new DefaultConfiguration("object_5");
            objectConfig.SetSimpleAttribute("name", "gCal");
            typeNameConfig = new DefaultConfiguration("type_name");
            typeNameConfig.SetSimpleAttribute("value", typeof(GeographicalDistanceCalculator).AssemblyQualifiedName);
            objectConfig.AddChild(typeNameConfig);
            objectDefChild.AddChild(objectConfig);

            // Set up the configuration object used by DefaultDistanceGenerator class.
            IConfiguration config = new DefaultConfiguration("root");
            config.SetSimpleAttribute("dataAccessKey", "fileBasedDataAccess");
            config.SetSimpleAttribute("xmlGeneratorKey", "myXmlGenerator");
            string[] calculatorKeys = new string[] { "Rating:rCal", "Overlap:oCal", "Country:gCal" };
            config.SetSimpleAttribute("calculatorKeys", calculatorKeys);

            // Add the config should that contains the object definitions.
            config.AddChild(objectDefChild);

            // Create a DefaultDistanceGenerator instance using config.
            IDistanceGenerator generator = new DefaultDistanceGenerator(config);

            // We may also create the generator without the use of any configuration.

            // Create a data access.
            IMemberDataAccess memberDataAccess = new FlatFileMemberDataAccess("../../test_files/");

            // Create the distance calculators.
            IDictionary<DistanceTypes, IDistanceCalculator> distanceCalculators =
                new Dictionary<DistanceTypes, IDistanceCalculator>();
            distanceCalculators.Add(DistanceTypes.Rating, new RatingDistanceCalculator());
            distanceCalculators.Add(DistanceTypes.Country, new GeographicalDistanceCalculator());
            distanceCalculators.Add(DistanceTypes.Overlap, new OverlapDistanceCalculator());

            // Create the XML Generator.
            IXmlGenerator xmlGenerator = new DefaultXmlGenerator();

            // Create the distance generator.
            generator = new DefaultDistanceGenerator(memberDataAccess, distanceCalculators,
                xmlGenerator);

            // Generate the XML.
            string result = generator.GenerateDistanceXml(1975, DistanceTypes.Rating,
                (CompetitionTypes.Algorithm | CompetitionTypes.Design));

            // Output the result to the console.
            Console.WriteLine(result.Replace("<", "\r\n<"));

            // We can also use the overloaded method with weights.
            IDictionary<DistanceTypes, float> weights =
                new Dictionary<DistanceTypes, float>();
            weights.Add(DistanceTypes.Rating, 40);
            weights.Add(DistanceTypes.Country, 60);
            result = generator.GenerateDistanceXml(1975, DistanceTypes.Rating,
                (CompetitionTypes.Algorithm | CompetitionTypes.Design), weights);
        }
    }
}
