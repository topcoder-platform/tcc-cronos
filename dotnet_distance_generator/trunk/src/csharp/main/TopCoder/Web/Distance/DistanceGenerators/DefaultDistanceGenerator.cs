/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Configuration;
using TopCoder.Configuration;
using TopCoder.Util.ObjectFactory;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance.DistanceGenerators
{
    /// <summary>
    /// This class is the default implementation of the <see cref="IDistanceGenerator"/> interface.
    /// It uses pluggable member data access instance, distance calculator and
    /// XML generator to accomplish the task. The calculation formula could be
    /// easily replaced or plugged in by providing new <see cref="IDistanceCalculator"/>
    /// implementations.
    /// </summary>
    /// <example>
    /// Below is an example of how this class can be instantiated using an IConfiguration object.
    /// <code>
    /// // Set up the configuration object that defines the objects.
    /// IConfiguration objectDefChild = new DefaultConfiguration(&quot;objectDefChild&quot;);
    ///
    /// IConfiguration objectConfig = new DefaultConfiguration(&quot;object_1&quot;);
    /// objectConfig.SetSimpleAttribute(&quot;name&quot;, &quot;fileBasedDataAccess&quot;);
    /// IConfiguration typeNameConfig = new DefaultConfiguration(&quot;type_name&quot;);
    /// typeNameConfig.SetSimpleAttribute(&quot;value&quot;, typeof(MyDataAccess).AssemblyQualifiedName);
    /// objectConfig.AddChild(typeNameConfig);
    /// objectDefChild.AddChild(objectConfig);
    ///
    /// objectConfig = new DefaultConfiguration(&quot;object_1.parameters&quot;);
    /// objectConfig.SetSimpleAttribute(&quot;name&quot;, &quot;fileBasedDataAccess&quot;);
    ///
    /// objectConfig = new DefaultConfiguration(&quot;object_2&quot;);
    /// objectConfig.SetSimpleAttribute(&quot;name&quot;, &quot;myXmlGenerator&quot;);
    /// typeNameConfig = new DefaultConfiguration(&quot;type_name&quot;);
    /// typeNameConfig.SetSimpleAttribute(&quot;value&quot;, typeof(DefaultXmlGenerator).AssemblyQualifiedName);
    /// objectConfig.AddChild(typeNameConfig);
    /// objectDefChild.AddChild(objectConfig);
    ///
    /// objectConfig = new DefaultConfiguration(&quot;object_3&quot;);
    /// objectConfig.SetSimpleAttribute(&quot;name&quot;, &quot;rCal&quot;);
    /// typeNameConfig = new DefaultConfiguration(&quot;type_name&quot;);
    /// typeNameConfig.SetSimpleAttribute(&quot;value&quot;, typeof(RatingDistanceCalculator).AssemblyQualifiedName);
    /// objectConfig.AddChild(typeNameConfig);
    /// objectDefChild.AddChild(objectConfig);
    ///
    /// objectConfig = new DefaultConfiguration(&quot;object_4&quot;);
    /// objectConfig.SetSimpleAttribute(&quot;name&quot;, &quot;oCal&quot;);
    /// typeNameConfig = new DefaultConfiguration(&quot;type_name&quot;);
    /// typeNameConfig.SetSimpleAttribute(&quot;value&quot;, typeof(OverlapDistanceCalculator).AssemblyQualifiedName);
    /// objectConfig.AddChild(typeNameConfig);
    /// objectDefChild.AddChild(objectConfig);
    ///
    /// objectConfig = new DefaultConfiguration(&quot;object_5&quot;);
    /// objectConfig.SetSimpleAttribute(&quot;name&quot;, &quot;gCal&quot;);
    /// typeNameConfig = new DefaultConfiguration(&quot;type_name&quot;);
    /// typeNameConfig.SetSimpleAttribute(&quot;value&quot;,
    ///     typeof(GeographicalDistanceCalculator).AssemblyQualifiedName);
    /// objectConfig.AddChild(typeNameConfig);
    /// objectDefChild.AddChild(objectConfig);
    ///
    /// // Set up the configuration object used by DefaultDistanceGenerator class.
    /// IConfiguration config = new DefaultConfiguration(&quot;root&quot;);
    /// config.SetSimpleAttribute(&quot;dataAccessKey&quot;, &quot;fileBasedDataAccess&quot;);
    /// config.SetSimpleAttribute(&quot;xmlGeneratorKey&quot;, &quot;myXmlGenerator&quot;);
    /// string[] calculatorKeys = new string[] { &quot;Rating:rCal&quot;,
    ///     &quot;Overlap:oCal&quot;, &quot;Country:gCal&quot; };
    /// config.SetSimpleAttribute(&quot;calculatorKeys&quot;, calculatorKeys);
    ///
    /// // Add the config should that contains the object definitions.
    /// config.AddChild(objectDefChild);
    ///
    /// // Create a DefaultDistanceGenerator instance using config.
    /// IDistanceGenerator generator = new DefaultDistanceGenerator(config);
    /// </code>
    /// </example>
    /// <threadsafety>
    /// This class is thread-safe since it is immutable.
    /// </threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public class DefaultDistanceGenerator : IDistanceGenerator
    {
        /// <summary>
        /// The key used to retrieve the "dataAccessKey" configuration item.
        /// </summary>
        private const string configForDataAccessKey = "dataAccessKey";

        /// <summary>
        /// The key used to retrieve the "xmlGeneratorKey" configuration item.
        /// </summary>
        private const string configForXmlGeneratorKey = "xmlGeneratorKey";

        /// <summary>
        /// The key used to retrieve the "calculatorKeys" configuration item.
        /// </summary>
        private const string configForCalculatorKeys = "calculatorKeys";

        /// <summary>
        /// The key used to retrieve the "weights" configuration item.
        /// </summary>
        private const string configForWeights = "weights";

        /// <summary>
        /// The key used to retrieve the "objectDefChild" configuration item.
        /// </summary>
        private const string configForObjectDefChild = "objectDefChild";

        /// <summary>
        /// The IMemberDataAccess instance used to retrieve member data.
        /// It is initialized in the constructor and will not change afterwards.
        /// It will never be null. It is used in the two GenerateDistanceXml methods.
        /// </summary>
        private readonly IMemberDataAccess memberDataAccess;

        /// <summary>
        /// The distance calculators used to perform distance calculation.
        /// It is initialized in the constructor and will not change afterwards.
        /// It will not be null. It is used in the two GenerateDistanceXml methods.
        /// The key for the dictionary is a single DistanceTypes value
        /// (i.e. no combination of flags) and the value should be the corresponding
        /// distance calculator. It cannot be null.
        /// </summary>
        private readonly IDictionary<DistanceTypes, IDistanceCalculator> calculators;

        /// <summary>
        /// The weights used to aggregate distance data.
        /// It is initialized in the constructor and may be null implying
        /// that default equal weights are used. It is immutable and will
        /// never be empty (i.e., it can only be either null or non-empty).
        /// It is used in the two GenerateDistanceXml methods. The key represents
        /// a single DistanceTypes value (i.e., no combination value is allowed)
        /// and the value should be a positive float number. The values should
        /// sum up to 100.
        /// </summary>
        private readonly IDictionary<DistanceTypes, float> weights;

        /// <summary>
        /// The generator that is used to generate the XML.
        /// It is initialized in the constructor and will not change afterwards.
        /// It will never be null. It is used in the two GenerateDistanceXml methods.
        /// </summary>
        private readonly IXmlGenerator xmlGenerator;

        /// <summary>
        /// Creates a new generator from configuration.
        /// </summary>
        /// <param name="config">
        /// The configuration from which to create the generator.
        /// </param>
        /// <exception cref="ArgumentNullException">
        /// If <paramref name="config"/> is null.
        /// </exception>
        /// <exception cref="ConfigurationErrorsException">
        /// If <paramref name="config"/> is invalid (for example: missing required configuration parameter,
        /// configuration parameter has invalid value, etc...).
        /// </exception>
        public DefaultDistanceGenerator(IConfiguration config)
        {
            Helper.ValidateNotNull("config", config);

            try
            {
                // Retrieve the "dataAccessKey" attribute.
                string dataAccessKey = (string)GetRequiredConfig(config, configForDataAccessKey);

                // Retrieve the "xmlGeneratorKey" attribute.
                string xmlGeneratorKey = (string)GetRequiredConfig(config, configForXmlGeneratorKey);


                // Retrieve the "weights" attribute and parse it to a dictionary based on
                // the defnied format in CS 3.2
                object weightsKeysObj = config.GetSimpleAttribute(configForWeights);

                DistanceTypes key;
                string value;

                if (weightsKeysObj != null)
                {
                    string[] weightsKeys = (string[])weightsKeysObj;

                    if (weightsKeys.Length > 0)
                    {
                        // NOTE: The weights configuration is optional. If it is not provided,
                        // this.weights is left as null, and the GenerateDistanceXml method will
                        // process with equal weights.
                        this.weights = new Dictionary<DistanceTypes, float>();

                        foreach (string weightsKey in weightsKeys)
                        {
                            ParseKeyAndValue(weightsKey, out key, out value);

                            if (weights.ContainsKey(key))
                            {
                                throw new ConfigurationErrorsException(string.Format(
                                    "The weights key '{0}' is already used.", key));
                            }

                            weights[key] = float.Parse(value);
                        }
                    }
                }


                // Retrieve "objectDefChild".
                IConfiguration configOF = config.GetChild(configForObjectDefChild);

                if (configOF == null)
                {
                    throw new ConfigurationErrorsException(string.Format(
                        "Child configuration '{0}' is missing", configForObjectDefChild));
                }

                // Use the configuration to create a new ConfigurationAPIObjectFactory
                ConfigurationAPIObjectFactory factory = new ConfigurationAPIObjectFactory(configOF);

                // Retrieve the "calculatorKeys" attribute and parse it as DistanceTypes and
                // calculatorKey pairs based on the format in CS 3.2.
                string[] calcKeys = (string[])GetRequiredConfig(config, configForCalculatorKeys);
                if ((calcKeys == null) || (calcKeys.Length == 0))
                {
                    throw new ConfigurationErrorsException(
                        "The value for 'calculatorKeys' must be an array of string.");
                }

                this.calculators = new Dictionary<DistanceTypes, IDistanceCalculator>();
                foreach (string calcKey in calcKeys)
                {
                    ParseKeyAndValue(calcKey, out key, out value);

                    if (calculators.ContainsKey(key))
                    {
                        throw new ConfigurationErrorsException(string.Format(
                            "The calculator key '{0}' is already used.", key));
                    }

                    // Use the object factory to create the IDistanceCalculator instance.
                    calculators[key] = (IDistanceCalculator)factory.CreateDefinedObject(value);
                }

                // Use the object factory to create the IMemberDataAccess and IXmlGenerator instances.
                memberDataAccess = (IMemberDataAccess)factory.CreateDefinedObject(dataAccessKey);
                xmlGenerator = (IXmlGenerator)factory.CreateDefinedObject(xmlGeneratorKey);
            }
            catch (ConfigurationErrorsException)
            {
                throw;
            }
            catch (Exception exc)
            {
                throw new ConfigurationErrorsException("A configuration error was encountered.", exc);
            }
        }

        /// <summary>
        /// Creates a new generator with the given arguments.
        /// </summary>
        /// <param name="memberDataAccess">
        /// The <see cref="IMemberDataAccess"/> instance used to retrieve member data.
        /// </param>
        /// <param name="calculators">
        /// The <see cref="IDistanceCalculator"/> instances used to perform distance
        /// calculation, it must not be empty.
        /// </param>
        /// <param name="xmlGenerator">
        /// The <see cref="IXmlGenerator"/> instance used to generate xml string based on given distance data.
        /// </param>
        /// <exception cref="ArgumentNullException">
        /// If any argument is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If <paramref name="calculators"/> is an empty dictionary or any key or value is invalid.
        /// </exception>
        public DefaultDistanceGenerator(IMemberDataAccess memberDataAccess,
            IDictionary<DistanceTypes, IDistanceCalculator> calculators,
            IXmlGenerator xmlGenerator)
        {
            Helper.ValidateNotNull("memberDataAccess", memberDataAccess);
            Helper.ValidateNotNull("calculators", calculators);
            Helper.ValidateNotNull("xmlGenerator", xmlGenerator);
            Helper.ValidateArgument("calculators", (calculators.Count == 0),
                "[calculators] cannot be empty.");

            foreach (DistanceTypes distanceType in calculators.Keys)
            {
                Helper.ValidateArgument("calculators", !Enum.IsDefined(typeof(DistanceTypes), distanceType),
                    "[calculators] must have keys that are singularly defined in the DistanceTypes enumeration.");

                Helper.ValidateArgument("calculators", (calculators[distanceType] == null),
                    "[calculators] must not contain null elements.");
            }

            // Assign the arguments to the namesake fields.
            this.memberDataAccess = memberDataAccess;
            this.xmlGenerator = xmlGenerator;

            // Create a shallow copy of the weights parameter and assign it to the namesake field.
            this.calculators = new Dictionary<DistanceTypes, IDistanceCalculator>(calculators);

            // NOTE: there is no need to assign a value to weights here. The
            // GenerateDistanceXml method takes a null value in weights
            // to mean using the default weighting.
        }

        /// <summary>
        /// Creates a new generator with the given arguments.
        /// </summary>
        /// <param name="memberDataAccess">
        /// The <see cref="IMemberDataAccess"/> instance used to retrieve member data.
        /// </param>
        /// <param name="calculators">
        /// The <see cref="IDistanceCalculator"/> instances used to perform distance
        /// calculation, it must not be empty.
        /// </param>
        /// <param name="xmlGenerator">
        /// The <see cref="IXmlGenerator"/> instance used to generate xml string based on given distance data.
        /// </param>
        /// <param name="weights">
        /// The weights to use when aggregating distance data.
        /// </param>
        /// <exception cref="ArgumentNullException">
        /// If any argument is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If <paramref name="calculators"/> is an empty dictionary or any key or value is invalid.
        /// </exception>
        public DefaultDistanceGenerator(IMemberDataAccess memberDataAccess,
            IDictionary<DistanceTypes, IDistanceCalculator> calculators,
            IXmlGenerator xmlGenerator,
            IDictionary<DistanceTypes, float> weights)
            : this(memberDataAccess, calculators, xmlGenerator)
        {
            Helper.ValidateNotNull("weights", weights);

            // Create a shallow copy of the weights parameter and assign it to the namesake field.
            this.weights = new Dictionary<DistanceTypes, float>(weights);
        }


        /// <summary>
        /// Generates the distance XML as specified by the defined XSD.
        /// It uses the default weights (equal weights) to calculate
        /// the final aggregated distance data.
        /// </summary>
        /// <param name="coderId">
        /// The id of the coder to generate distance for.
        /// </param>
        /// <param name="distanceType">
        /// The distance types.
        /// </param>
        /// <param name="compType">
        /// The competition types.
        /// </param>
        /// <returns>
        /// The XML conforming to the defined XSD, containing the aggregated distance data.
        /// </returns>
        /// <exception cref="ArgumentException">
        /// If coder id is not positive or distanceType or compType is not a valid predefined
        /// value (they are not valid combinations of defined enum values).
        /// </exception>
        /// <exception cref="DistanceGenerationException">
        /// If any other error occurs during generation.
        /// </exception>
        public string GenerateDistanceXml(long coderId, DistanceTypes distanceType, CompetitionTypes compType)
        {
            IDictionary<DistanceTypes, float> weights;

            // If the default weights are assigned, use it.
            if ((this.weights != null) && (this.weights.Count > 0))
            {
                weights = new Dictionary<DistanceTypes, float>(this.weights);
                foreach (DistanceTypes distType in Enum.GetValues(typeof(DistanceTypes)))
                {
                    if (!weights.ContainsKey(distType))
                    {
                        weights[distType] = 0;
                    }
                }
                return GenerateDistanceXml(coderId, distanceType, compType, this.weights);
            }

            // The weights dictionary is null or empty. Create a dictionary of equal weights to use.
            weights = new Dictionary<DistanceTypes, float>();

            // Check all enumeration values and see if they are included.
            foreach (DistanceTypes distType in Enum.GetValues(typeof(DistanceTypes)))
            {
                if ((distType & distanceType) > 0)
                {
                    // The enumeration value is included. Add a weight.
                    weights[distType] = 0;
                }
            }

            IList<DistanceTypes> keys = new List<DistanceTypes>(weights.Keys);

            // Divide each weight by the number of actual distance types used.
            foreach (DistanceTypes distType in keys)
            {
                weights[distType] = 100f / weights.Count;
            }

            // Call the other overload.
            return GenerateDistanceXml(coderId, distanceType, compType, weights);
        }

        /// <summary>
        /// Generates the distance XML as specified by the defined XSD.
        /// It uses the given weights to calculate the final aggregated distance data.
        /// </summary>
        /// <param name="compType">
        /// The competition types
        /// </param>
        /// <param name="weights">
        /// The weights for each distance type. Note that each key in this dictionary
        /// should represent a single existing field defined in DistanceTypes.
        /// </param>
        /// <param name="distanceType">
        /// the distance types
        /// </param>
        /// <param name="coderId">
        /// The id of the coder to generate distance for.
        /// </param>
        /// <returns>
        /// The xml conforming to the defined XSD, containing the aggregated distance data.
        /// </returns>
        /// <exception cref="ArgumentNullException">
        /// If <paramref name="weights"/> is null.
        /// </exception>
        /// <exception cref="ArgumentException">
        /// If <paramref name="weights"/> is an empty dictionary or the coder id is
        /// not positive or distanceType or compType is not a valid predefined value
        /// (they are not valid combinations of defined enum values) or if any key
        /// in the dictionary is not a single existing enum as defined DistanceTypes
        /// (i.e., combination of the fields is not allowed in the dictionary) or
        /// if any value is not positive float number or if the sum of values does
        /// not equal 100.
        /// </exception>
        /// <exception cref="DistanceGenerationException">
        /// If any other error occurs during generation.
        /// </exception>
        public string GenerateDistanceXml(long coderId, DistanceTypes distanceType,
            CompetitionTypes compType, IDictionary<DistanceTypes, float> weights)
        {
            try
            {
                Helper.ValidateNotNull("weights", weights);
                Helper.ValidateArgument("weights", (weights.Count == 0), "[weights] cannot be an empty dictionary.");
                Helper.ValidateArgument("coderId", (coderId <= 0), "[coderId] must be positive.");

                float totalWeight = 0;
                float unusedWeight = 0;
                int numUsedWeights = 0;

                foreach (DistanceTypes distType in weights.Keys)
                {
                    Helper.ValidateArgument("weights", !Enum.IsDefined(typeof(DistanceTypes), distType),
                        "[weights] must have keys that are single values defined in the DistanceTypes enumeration.");

                    // Get the weight.
                    float weight = weights[distType];
                    totalWeight += weight;
                    Helper.ValidateArgument("weights", (weight <= 0),
                        "[weights] must have values that are positive.");

                    // Track unused weights and the number of weights that were used.
                    if ((distanceType & distType) == 0)
                    {
                        unusedWeight += weight;
                    }
                    else
                    {
                        numUsedWeights++;
                    }
                }

                float diff = Math.Abs((totalWeight - 100));
                Helper.ValidateArgument("weights", (diff >= float.Epsilon),
                    "[weights] must have values that when add up to 100.");

                Helper.ValidateDistanceType("distanceType", distanceType);
                Helper.ValidateCompetitionType("compType", compType);

                // Get the member.
                Member member = memberDataAccess.GetMember(coderId);

                // Get the related members.
                IList<Member> relatedMembers = memberDataAccess.GetRelatedMembers(coderId, compType);

                // Copy the original weights dictionary to the adjusted weights.
                IDictionary<DistanceTypes, float> adjustedWeights = new Dictionary<DistanceTypes, float>(weights);

                // Calculate the weight to distribute if there are any unutilized weights.
                float weightToDistribute = 0;

                // Compute the weight to distribute evenly among the rest of the used weights.
                if ((numUsedWeights > 0) && (unusedWeight > 0))
                {
                    weightToDistribute = unusedWeight / numUsedWeights;
                }

                // Prepare the list that will hold the final distances.
                float[] distances = new float[relatedMembers.Count];

                foreach (DistanceTypes distType in weights.Keys)
                {
                    if ((distanceType & distType) > 0)
                    {
                        // Call the appropriate method in the calculator to compute the distance.
                        IList<float> calcDistances = calculators[distType].CalculateDistance(
                            member, relatedMembers, compType);

                        // Use the adjusted weights to aggregate final distance data.
                        adjustedWeights[distType] += weightToDistribute;

                        for (int i = 0; i < calcDistances.Count; i++)
                        {
                            distances[i] += calcDistances[i] * adjustedWeights[distType] / 100f;
                        }
                    }
                    else
                    {
                        adjustedWeights[distType] = 0;
                    }
                }

                // Use the xmlGenerator to generate the XML string based on the calculated distance data.
                return xmlGenerator.GenerateXml(member, relatedMembers, distances);
            }
            catch (ArgumentNullException)
            {
                throw;
            }
            catch (ArgumentException)
            {
                throw;
            }
            catch (Exception exc)
            {
                throw new DistanceGenerationException("An error was encountered generating the distance.", exc);
            }
        }


        /// <summary>
        /// Gets a required configuraiton item.
        /// </summary>
        /// <param name="config">
        /// The IConfiguration to retrieve the item from.
        /// </param>
        /// <param name="configKey">
        /// The key to use in retrieving the item.
        /// </param>
        /// <returns>
        /// The attribute that was retrieved.
        /// </returns>
        /// <exception cref="ConfigurationErrorsException">
        /// If the configuration item was not found.
        /// </exception>
        private object GetRequiredConfig(IConfiguration config, string configKey)
        {
            object result = config.GetSimpleAttribute(configKey);

            if ((result == null) || ((result is string) && ((result as string).Trim().Length == 0)))
            {
                throw new ConfigurationErrorsException(
                    string.Format("Configuration item '{0}' was not found.", configKey));
            }

            return result;
        }


        /// <summary>
        /// Parses an enumeration key and value.
        /// </summary>
        /// <typeparam name="T">
        /// The type of the enumeration.
        /// </typeparam>
        /// <param name="keyAndValueToSplit">
        /// The key and value to parse.
        /// </param>
        /// <param name="key">
        /// The resulting key.
        /// </param>
        /// <param name="value">
        /// The resulting value.
        /// </param>
        /// <exception cref="ConfigurationErrorsException">
        /// If a parsing error is encountered.
        /// </exception>
        private void ParseKeyAndValue<T>(string keyAndValueToSplit, out T key, out string value)
        {
            try
            {
                string[] keyAndValue = keyAndValueToSplit.Split(':');
                key = (T)Enum.Parse(typeof(T), keyAndValue[0]);
                value = keyAndValue[1];
            }
            catch (Exception exc)
            {
                throw new ConfigurationErrorsException("Error parsing key and value.", exc);
            }
        }

    }
}

