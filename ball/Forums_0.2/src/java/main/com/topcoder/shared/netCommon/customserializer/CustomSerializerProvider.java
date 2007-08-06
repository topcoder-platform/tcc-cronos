/*
 * CustomSerializerProvider
 * 
 * Created 10/20/2006
 */
package com.topcoder.shared.netCommon.customserializer;

/**
 * Implementators of this interface are responsible for providing
 * CustomSerializers for an specific class.
 * 
 * 
 * @author Diego Belfer (mural)
 * @version $Id: CustomSerializerProvider.java,v 1.2 2006/12/01 17:56:55 thefaxman Exp $
 */
public interface CustomSerializerProvider {
    
    /**
     * Returns if this CustomSerializerProvider can provide a CustomSerializer
     * for the given class.
     * 
     * @param clazz The clazz for which a CustomSerializer is required
     * @return true if a CustomSerializer can be provided
     */
    public boolean canHandle(Class clazz);
    
    /**
     * Returns the CustomSerializer for the given class.
     * 
     * @param clazz The clazz for which a CustomSerializer is required
     * @return The customSerializer, null if one could not be provided
     */
    public CustomSerializer getSerializer(Class clazz);
}
