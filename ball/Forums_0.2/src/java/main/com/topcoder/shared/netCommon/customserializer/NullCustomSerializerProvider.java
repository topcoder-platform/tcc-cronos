/*
 * NullCustomSerializerProvider
 * 
 * Created 10/20/2006
 */
package com.topcoder.shared.netCommon.customserializer;


/**
 * Null pattern for CustomSerializerProvider
 * 
 * @author Diego Belfer (mural)
 * @version $Id: NullCustomSerializerProvider.java,v 1.2 2006/12/01 17:56:55 thefaxman Exp $
 */
public class NullCustomSerializerProvider implements CustomSerializerProvider {
    
    public boolean canHandle(Class clazz) {
        return false;
    }

    public CustomSerializer getSerializer(Class clazz) {
        return null;
    }
}
