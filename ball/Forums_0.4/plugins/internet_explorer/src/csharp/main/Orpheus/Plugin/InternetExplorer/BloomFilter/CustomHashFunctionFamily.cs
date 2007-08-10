using System;
using TopCoder.Util.BloomFilter;

namespace Orpheus.Plugin.InternetExplorer.BloomFilter
{
	/// <summary>
    /// The custom implementation of IHashFunctionFamily interface, use to provide Bloom filter interop.
    /// This class does not implement GetSerialized as we only care about deserialization process.
    /// Authors of the original DefaultHashFunctionFamily class: <b>rage_true, vividmxx</b>.
	/// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2007, TopCoder, Inc. All rights reserved</copyright>
	public class CustomHashFunctionFamily : IHashFunctionFamily
	{

        /// <summary>
        /// Represents the array of seeds used by <code>ComputeHash()</code> method to compute hashes. The length of
        /// array is equal to the number of hash functions in this family.
        /// </summary>
        /// <remarks>
        /// It is initialized in the constructor, and should not be null or empty.
        /// </remarks>
        private int[] functionSeeds;

        /// <summary>
        /// The get property to retrieve the number of functions in this family.
        /// </summary>
        /// <value>The number of functions in this family.</value>
        public int FunctionCount
        {
            get
            {
                return functionSeeds.Length;
            }
        }

        /// <summary>
        /// Creates a hash function family restored from string representation. In this implementation, the string
        /// representation is just the number of functions.
        /// </summary>
        /// <remarks>
        /// Individual function seed values are obtained using <code>System.Random</code> class.
        /// </remarks>
        /// <param name="serialized">A string representation of the hash function family (number of functions).</param>
        /// <exception cref="ArgumentNullException">If <paramref name="serialized"/> is null.</exception>
        /// <exception cref="BloomFilterSerializationException">if <paramref name="serialized"/> is not the string
        /// representation of a positive integer.</exception>
        public CustomHashFunctionFamily(string serialized)
		{
            if (serialized == null) 
            {
                throw new ArgumentNullException("serialized", "Serialized form cannot be null.");
            }

            try
            {
                string[] pairs = serialized.Split(',');

                if (pairs.Length < 1) 
                {
                    throw new BloomFilterSerializationException("Should have at least function family number. " + 
                        "Serialized: " + serialized);
                }
                int count = -1;
                // first pair should start with "function_number="
                if (pairs[0].StartsWith("function_number=")) 
                {
                    count = int.Parse(pairs[0].Substring(pairs[0].LastIndexOf("=") + 1));
                }
                else 
                {
                    throw new BloomFilterSerializationException("Expected \"function_number=\". Serialized: " 
                        + serialized);
                }

                if (count <= 0)
                {
                    throw new BloomFilterSerializationException("serialized is not a positive number.");
                }

                if (count != pairs.Length -1) 
                {
                    throw new BloomFilterSerializationException("Insufficient seed number. Expected: " 
                        + count + " but was: " + (pairs.Length -1));
                }

                // create the array of seeds and propagate it with data
                functionSeeds = new int[count];
                for (int i = 1; i < pairs.Length; i++) 
                {
                    functionSeeds[i-1] = int.Parse(pairs[i].Substring(pairs[1].LastIndexOf("=") + 1));
                }
            }
            catch (FormatException e)
            {
                throw new BloomFilterSerializationException("serialized dose not contain valid integer.", e);
            }
            catch (OverflowException e)
            {
                throw new BloomFilterSerializationException("serialized contains overflowed integer.", e);
            }
            
		}

        /// <summary>
        /// Gets the string representation of this hash function family.
        /// </summary>
        /// <remarks>
        /// <p>
        /// This method is not implemented.
        /// </p>
        /// </remarks>
        /// <returns>The string representation of this hash function family.</returns>
        public string GetSerialized()
        {
            throw new NotImplementedException("This method is not used in the plug-in context.");
        }

        /// <summary>
        /// Computes the hash code for the specified object, using the function given by the function index, and using
        /// the range from 0 to the max hash inclusive.
        /// </summary>
        /// <remarks>
        /// The formula used is:
        /// <pre>
        /// hashCode = Abs(seed ^ obj.GetHashCode()) % (maxHash + 1), if the obj is not a string;
        /// </pre>
        /// <pre>
        /// hashCode = Abs(seed ^ GetHashCodeForString(obj as string)) % (maxHash + 1), if the obj is a string.
        /// </pre>
        /// Where "seed" is an integer value different for every function in the family, and "GetHashCodeForString"
        /// is a method that will return the JAVA compatible hash code for the given string.
        /// </remarks>
        /// <param name="functionIndex">The index of function to use for computation, it should between 0 (inclusive)
        /// and the number of functions in the family (exclusive).</param>
        /// <param name="maxHash">The maximal value that can be returned, it should be a positive integer.</param>
        /// <param name="obj">The object to compute hash value.</param>
        /// <returns>A hash code for the object, between 0 and <paramref name="maxHash"/> inclusive.</returns>
        /// <exception cref="ArgumentNullException">If <paramref name="obj"/> is null.</exception>
        /// <exception cref="ArgumentException">If <paramref name="maxHash"/> is not positive.</exception>
        /// <exception cref="ArgumentOutOfRangeException">If <paramref name="functionIndex"/> is negative or not less
        /// than the number of functions, or <paramref name="maxHash"/> is <code>Int32.MaxValue</code>.</exception>
        public int ComputeHash(int functionIndex, int maxHash, object obj)
        {
            if (obj == null) 
            {
                throw new ArgumentNullException("obj", "The object for hash cannot be null.");
            }
            if (maxHash == Int32.MaxValue)
            {
                throw new ArgumentOutOfRangeException("maxHash", "Max hash should be less than the max value of int");
            }
            
            int hashCodeForObj = (obj is string) ? GetHashCodeForString(obj as string) : obj.GetHashCode();
            return Math.Abs(functionSeeds[functionIndex] ^ hashCodeForObj) % (maxHash + 1);
        }

        /// <summary>
        /// This private helper method is used to get the hash code for the given string using JAVA compatible hash code
        /// formular.
        /// </summary>
        /// <remarks>
        /// The formular is: s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1], using int arithmetic, where s[i] is the ith
        /// character of the string, n is the length of the string, and ^ indicates exponentiation. (The hash value of
        /// the empty string is zero.)
        /// </remarks>
        /// <param name="str">The string to get hash code.</param>
        /// <returns>The JAVA compatible hash code for the given string.</returns>
        private int GetHashCodeForString(string str)
        {
            int hash = 0;
            foreach (char c in str)
            {
                hash = 31 * hash + c;
            }
            return hash;
        }
	}
}
