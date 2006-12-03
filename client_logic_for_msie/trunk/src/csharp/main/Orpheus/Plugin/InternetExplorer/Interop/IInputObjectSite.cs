using System;
using System.Runtime.InteropServices;

namespace Orpheus.Plugin.InternetExplorer.Interop
{
    /// <summary>
    /// The inteface definition.
    /// </summary>
    [ComImport]
    [Guid ("f1db8392-7331-11d0-8c99-00a0c92dbfe8")]
    [InterfaceType (ComInterfaceType.InterfaceIsIUnknown)]
    public interface IInputObjectSite
    {
        /// <summary>
        /// The method.
        /// </summary>
        /// <param name="punkObj"></param>
        /// <param name="fSetFocus"></param>
        /// <returns></returns>
        [PreserveSig] int OnFocusChangeIS (
            [MarshalAs (UnmanagedType.IUnknown)] ref object punkObj,
            [MarshalAs (UnmanagedType.Bool)] ref bool fSetFocus);
    }
}
