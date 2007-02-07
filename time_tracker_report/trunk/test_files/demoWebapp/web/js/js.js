/* Top level namespace */
var js = { };

/* Generic object base class */
js.Class = (function() {

    Class.getCustomEventHandler = function Class_getCustomEventHandler(obj, methodName)
    {
        return ( function (e) { return obj[methodName](e); } );
    };

    function Class(props) 
    {
        this.properties = props || { };
        if (props) this.properties.elem = document.getElementById(props.id);
        this.properties.window = this.properties.window || window;
    };
    
    return Class;
})();

/* Helper function for subclassing mh.Class */
Function.prototype.extendsClass = function(baseClass) 
{
  this.prototype = new baseClass();
  this.prototype.constructor = this;  
};
