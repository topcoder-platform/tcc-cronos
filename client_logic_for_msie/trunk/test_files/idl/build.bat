rem midl MsHtmHstInterop.idl /tlb MsHtmHstInterop.tlb
midl build.idl /tlb MsHtmHstInterop.tlb

aximp %SystemRoot%\\System32\SHDocVw.dll /out:AxSHDocVw.dll /keyfile:msie.snk
tlbimp MsHtmHstInterop.tlb /out:MsHtmHstInterop.dll /keyfile:msie.snk
tlbimp %SystemRoot%\\system32\\shdocvw.dll /out:SHDocVw.dll /keyfile:msie.snk
tlbimp %SystemRoot%\\system32\\mshtml.tlb /out:Mshtml.dll /keyfile:msie.snk

del MsHtmHstInterop.tlb