rem midl MsHtmHstInterop.idl /tlb MsHtmHstInterop.tlb
midl build.idl /tlb MsHtmHstInterop.tlb

aximp C:\\WINNT\\SHDocVw.dll /out:D:\client_logic_for_msie_finalfix2\lib\AxSHDocVw.dll /keyfile:msie.snk
tlbimp Orpheus.tlb /out:D:\client_logic_for_msie_finalfix2\lib\MsHtmHstInterop.dll /keyfile:msie.snk
tlbimp C:\\WINNT\\shdocvw.dll /out:D:\client_logic_for_msie_finalfix2\lib\SHDocVw.dll /keyfile:msie.snk
tlbimp C:\\WINNT\\system32\\mshtml.tlb /out:D:\client_logic_for_msie_finalfix2\lib\Mshtml.dll /keyfile:msie.snk

del MsHtmHstInterop.tlb