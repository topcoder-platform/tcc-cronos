rem midl MsHtmHstInterop.idl /tlb MsHtmHstInterop.tlb
midl build.idl /tlb MsHtmHstInterop.tlb

aximp D:\\WINDOWS\\System32\SHDocVw.dll /out:D:\TopCoder\Client_Logic_for_MSIE\lib\AxSHDocVw.dll /keyfile:msie.snk
tlbimp Orpheus.tlb /out:D:\TopCoder\Client_Logic_for_MSIE\lib\MsHtmHstInterop.dll /keyfile:msie.snk
tlbimp D:\\WINDOWS\\system32\\shdocvw.dll /out:D:\TopCoder\Client_Logic_for_MSIE\lib\SHDocVw.dll /keyfile:msie.snk
tlbimp D:\\WINDOWS\\system32\\mshtml.tlb /out:D:\TopCoder\Client_Logic_for_MSIE\lib\Mshtml.dll /keyfile:msie.snk

del MsHtmHstInterop.tlb