package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.utils
{
    import com.darronschall.serialization.ObjectTranslator;
    
    import flash.utils.describeType;
    import flash.utils.getDefinitionByName;
    import flash.xml.XMLDocument;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.xml.SimpleXMLDecoder;
    import mx.utils.ArrayUtil;
    import mx.utils.ObjectProxy;
    import mx.utils.ObjectUtil;

    public class ObjectTranslatorUtils {

        /**
        * Tries to decode given xml document to the specified class instance.
        * 
        * @param xml xml document to be decoded.
        * @param toClass concrete type.
        * 
        * @return array of decoded objects of specified toClass type.
        */
        public static function xmlDecode(xml:XMLDocument, toClass:Class):ArrayCollection {
            var coll:ArrayCollection=new ArrayCollection();
            var xmlDecoder:SimpleXMLDecoder=new SimpleXMLDecoder();

            if (xml.firstChild.childNodes.length > 0) {
                var objectTree:Object=xmlDecoder.decodeXML(xml.firstChild);
                var objs:Array;

                if (objectTree is Array) {
                    objs=objectTree as Array;
                } else {
                    objs=new Array(objectTree);
                }

                for (var i:int=0; i < objs.length; i++) {
                    var obj:*=ObjectTranslator.objectToInstance(objs[i], toClass);
                    coll.addItem(obj);
                }
            }

            return coll;
        }

        /**
        * Translates given object instance to array of specified concrete types.
        * 
        * @param fromObj object to be translated.
        * @param toClass concrete type element of the array.
        * 
        * @return array of translated objects of specified class type.
        */
        public static function translateCollection(fromObj:Object, toClass:Class):ArrayCollection {
            //trace("fromObj is ArrayCollection: " + fromObj);

            //
            // Module: Flex Cockpit Launch Contest - Integrate Software Contests v1.0 
            // Updated to avoid 'duplicate variable definition' warning
            //
            var retColl:ArrayCollection=null;
            if (fromObj is ArrayCollection) {

                var coll:ArrayCollection=fromObj as ArrayCollection;
                retColl=new ArrayCollection();
                for (var i:int=0; i < coll.length; i++) {
                    //trace("@@@@ before translate: " + coll[i]);
                    var obj:*=translate(coll[i], toClass);
                    //trace("@@@@ after translate: " + obj);
                    retColl.addItem(obj);
                }
                
                return retColl;
            } else {
                //
                // Module: Flex Cockpit Launch Contest - Integrate Software Contests v1.0 
                // Updated to avoid 'duplicate variable definition' warning
                //
                retColl=new ArrayCollection();
                var obj2:*=translate(fromObj, toClass);
                retColl.addItem(obj2);

                return retColl;
            }
            
            return null;
        }
        
        //----------------------------
        // Private Methods
        //----------------------------
        /**
         * Uses AS3 reflection to iterate through target class, converting source object
         * and children to proper types.
         * @private
         */
        public static function translate(fromObj:Object, toClass:Class):Object
        {
            if (fromObj is  mx.utils.ObjectProxy) {
                fromObj = (fromObj as ObjectProxy).valueOf();
            }
            
            // pull string className values from source object and conversion class
            var classNameFrom:String=getObjectType(fromObj);
            var classNameTo:String=getObjectType(toClass);
            
            //trace("converting from " + classNameFrom + " to " + toClass);

            // if class is an object, traverse its properties to instantiate proper types for each
            if ((classNameFrom == "Object") && classNameTo != "Object")
            {
                // pull type information from passed class definition
                var typeInfo:XML=describeType(toClass);
                // pull properties and object information from source object
                var objInfo:Object=ObjectUtil.getClassInfo(fromObj);
                // iterate through properties
                for(var i:int=0; i < objInfo.properties.length; i++)
                {
                    var key:String=objInfo.properties[i].localName;
                    // determine property type based on typeInfo XML "type" attribute matching that property name
                    var propertyClassName:String=typeInfo.factory.variable.(@name == key).@type.toString().replace(/::/, ".");
                    
                    //trace("Property key: " + key + ", propertyClassName: " + propertyClassName + ", of-Class: " + classNameTo);

                    // only continue if target class contains this property and type lookup is successful
                    if (propertyClassName != null && propertyClassName != "")
                    {
                        if (fromObj[key] is ArrayCollection || fromObj[key] is Array) {
                            // find appropriate arrayElementClass.
                            var arrayElementClassName:String=typeInfo.factory.variable.(@name == key).metadata.(@name=="ArrayElementType")..child("arg").attribute("value")[0];
                            //trace("Array Element class: " + arrayElementClassName + ", toClass: " + classNameTo);
                            if (!arrayElementClassName) {
                                fromObj[key]=null;
                            }
                            else {
                                var arrayElementClass:Class=getClassByName(arrayElementClassName);
                                var ret:ArrayCollection=translateCollection(fromObj[key], arrayElementClass);
                                if (propertyClassName == "Array") {
                                    fromObj[key]=ret.toArray();
                                }
                                else {
                                    fromObj[key]=ret;
                                }
                            }
                        }
                        else {
                            var propertyClass:Class=getClassByName(propertyClassName);
                            // make recursive call to traverse any potential nested custom classes for this property
                            fromObj[key]=translate(fromObj[key], propertyClass)as propertyClass;
                        }
                        
                        //trace("Result OBJ: " + fromObj[key]);
                    }
                }
            }
            
            if (classNameTo == "Object") {
                if (classNameFrom == "int" || classNameFrom == "long" || classNameFrom == "double" || classNameFrom == "short" || classNameFrom == "float") {
                    return fromObj as Number;            
                } else if (classNameFrom == "String") {
                    return fromObj as String;
                } else {
                    return fromObj.toString();
                }
            }

            // if className's are identical, or the source object value is null, simply return
            if (classNameFrom == classNameTo || fromObj == null)
            {
                return fromObj;
                // if a number conversion is necessary, simply rely on that
            }
            else if ((classNameFrom == "int" || classNameFrom == "long" || classNameFrom == "double" || classNameFrom == "short" || classNameFrom == "float") 
	         && classNameTo == "Number")
            {
                return fromObj as Number;
                // otherwise, default to full object to instance conversion
            }
            else
            {
                return ObjectTranslator.objectToInstance(fromObj, toClass)as toClass;
            }

        }

        /**
         * Get the class type of the passed object
         * @private
         */
        private static function getObjectType(object:Object):String
        {
            var typeInfo:XML=describeType(object);
            return typeInfo.@name.toString().replace(/::/, ".");
        }


        /**
         * Return a Class instance based on a string class name
         * @private
         */
        private static function getClassByName(className:String):Class
        {
            if (className != "Object")
            {
                var classReference:Class=getDefinitionByName(className)as Class;
                return classReference;
            }
            else
            {
                return Object;
            }
        }
    }
}
