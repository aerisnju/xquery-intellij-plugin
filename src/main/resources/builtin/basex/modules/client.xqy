xquery version "3.0";
(:~
: BaseX client module functions
:
: @see http://docs.basex.org/wiki/Client_Module
:)
module namespace client = "http://basex.org/";

import module namespace a = "http://reecedunn.co.uk/xquery/annotations" at "res://reecedunn.co.uk/xquery/annotations.xqy";

declare %a:since("basex", "9.0") function client:connect($host as xs:string, $port as xs:integer, $user as xs:string, $password as xs:string) as xs:anyURI external;
declare %a:since("basex", "9.0") function client:execute($id as xs:anyURI, $command as xs:string) as xs:string external;
declare %a:since("basex", "9.0") function client:info($id as xs:anyURI) as xs:string external;
declare %a:since("basex", "9.0") function client:query($id as xs:anyURI, $query as xs:string) as item()* external;
declare %a:since("basex", "9.0") function client:query($id as xs:anyURI, $query as xs:string, $bindings as map(*)?) as item()* external;
declare %a:since("basex", "9.0") function client:close($id as xs:anyURI) as empty-sequence() external;