using System;
using System.Collections;
using TopCoder.Util.BloomFilter;

namespace Orpheus.Plugin.InternetExplorer.BloomFilter
{
    /// <summary>
    /// The custom implementation of IBitArraySerializer interface, use to provide Bloom filter interop.
    /// This class implements only GetBitArray as we only care about deserialization process.
    /// It is direct translation of the DefaultBitSetSerializer from Java Bloom Filter component to C#.
    /// Authors of the original Java class: <b>AleaActaEst, yinzi</b>.
    /// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2007 TopCoder Inc., All Rights Reserved.</copyright>
    public class CustomBitArraySerializer : IBitArraySerializer
    {
        /// <summary>
        /// Represents the splitter used to split the length of the bit array and the serialized string of the bit
        /// array.
        /// </summary>
        private const char SPLITTER = ':';

        /// <summary>
        /// Represents the number of bits for a byte.
        /// </summary>
        private const int BITS_PER_BYTE = 8;

        /// <summary>
        /// <p> Represents the constant string used to look up characters while base64 coding/decoding.
        /// Real value should be: "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_:". </p>
        /// </summary>
        private const String BASE64_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_:";

        /// <summary>
        /// <p> Each base 64 character represents 6 bits. </p>
        /// </summary>
        private const int BITS_PER_BASE64_CHAR = 6;

        /// <summary>
        /// Empty constructor.
        /// </summary>
        public CustomBitArraySerializer()
        {
        }

        /// <summary>
        /// Gets a <code>BitArray</code> for the given serialized string.
        /// </summary>
        /// <remarks>
        /// It is decoded using variation of base 64 codec.
        /// </remarks>
        /// <param name="serialized">The serialized string to restore the <code>BitArray</code> from.</param>
        /// <returns>The restored <code>BitArray</code> for given serialized string.</returns>
        /// <exception cref="ArgumentNullException">If <paramref name="serialized"/> is null.</exception>
        /// <exception cref="BloomFilterSerializationException">If <paramref name="serialized"/> is not a valid
        /// serialized string for an <code>BitArray</code>.</exception>
        public BitArray GetBitArray(string serialized)
        {
            // Split the string in 2 (there could be more than 1 ':' because it is a valid base 64 character).
            string[] parts = serialized.Split(new char[] {SPLITTER}, 2);
            // it should have 2 parts
            if (parts.Length != 2) 
            {
                throw new BloomFilterSerializationException("serialized should have 2 parts splitted by ':''.");
            }

            // the first part is the length. The regular expression already checked that it is a number.
            int bitSetSize = -1;
            try 
            {
                bitSetSize = int.Parse(parts[0]);
            } 
            catch (FormatException e) 
            {
                throw new BloomFilterSerializationException("Invalid size: " + parts[0], e);
            }

            if (bitSetSize < 2) 
            {
                throw new BloomFilterSerializationException("The length of bit set must be at least 2.");
            }

            // Check that there are enough base 64 characters to match the size.
            if (bitSetSize > (parts[1].Length * BITS_PER_BASE64_CHAR)
                || bitSetSize <= (parts[1].Length - 1) * BITS_PER_BASE64_CHAR) 
            {
                throw new BloomFilterSerializationException("Not enough characters in base 64 representation");
            }

            // Create the bitSet, and if there is not enough memory throw BloomFilterSerializeException
            BitArray bitSet = new BitArray(bitSetSize);

            //loop through all the base 64 characters and set the bitset
            for (int i = 0, bitPos = 0; i < parts[1].Length; i++) 
            {

                // get the decimal value of the current base 64 character.
                int sum = BASE64_CHARS.IndexOf(parts[1][i]);

                if (sum < 0) 
                {
                    throw new BloomFilterSerializationException("Invalid character for base 64: " + parts[1][i]);
                }

                // convert the decimal value to binary and set the bits in bitset, finishing the loop if
                // it arrived to the bit set size.
                for (int j = 0; (j < BITS_PER_BASE64_CHAR) && (bitPos < bitSetSize); bitPos++, j++) 
                {
                    if ((sum & 1) == 1) 
                    {
                        bitSet.Set(bitPos, true);
                    }
                    sum >>= 1;
                }
            }

            return bitSet;
        }

        /// <summary>
        /// Gets a string representation of the given <code>BitArray</code>.
        /// </summary>
        /// <remarks>
        /// Not implemented.
        /// </remarks>
        /// <param name="bitArray">The <code>BitArray</code> to get serialized string from.</param>
        /// <returns>The string representation of the <code>BitArray</code>.</returns>
        /// <exception cref="NotImplementedException">always</exception>
        public string GetSerialized(BitArray bitArray)
        {
            throw new NotImplementedException("This method is not used in the plug-in context.");
        }
    }
}
