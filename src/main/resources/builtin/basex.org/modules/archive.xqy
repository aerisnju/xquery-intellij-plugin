xquery version "3.0";
(:~
: BaseX archive module functions
:
: @see http://docs.basex.org/wiki/Archive_Module
:)
module namespace archive = "http://basex.org/modules/archive";

import module namespace a = "http://reecedunn.co.uk/xquery/annotations" at "res://reecedunn.co.uk/xquery/annotations.xqy";

declare %a:since("basex", "7.3") function archive:create($entries as item(), $contents as item()*) as xs:base64Binary external;
declare %a:since("basex", "7.3") function archive:create($entries as item(), $contents as item()*, $options as map(*)?) as xs:base64Binary external;
declare %a:since("basex", "8.3") function archive:create-from($path as xs:string) as xs:base64Binary external;
declare %a:since("basex", "8.3") function archive:create-from($path as xs:string, $options as map(*)?) as xs:base64Binary external;
declare %a:since("basex", "8.3") function archive:create-from($path as xs:string, $options as map(*)?, $entries as item()*) as xs:base64Binary external;
declare %a:since("basex", "7.3") function archive:entries($archive as xs:base64Binary) as element(archive:entry)* external;
declare %a:since("basex", "7.3") function archive:options($archive as xs:base64Binary) as map(*) (: as [7.3]element(archive:options) [8.5]map(*) :) external;
declare %a:since("basex", "7.3") function archive:extract-text($archive as xs:base64Binary) as xs:string* external;
declare %a:since("basex", "7.3") function archive:extract-text($archive as xs:base64Binary, $entries as item()*) as xs:string* external;
declare %a:since("basex", "7.3") function archive:extract-text($archive as xs:base64Binary, $entries as item()*, $encoding as xs:string) as xs:string* external;
declare %a:since("basex", "7.3") function archive:extract-binary($archive as xs:base64Binary) as xs:base64Binary* external;
declare %a:since("basex", "7.3") function archive:extract-binary($archive as xs:base64Binary, $entries as item()*) as xs:base64Binary* external;
declare %a:since("basex", "8.3") function archive:extract-to($path as xs:string, $archive as xs:base64Binary) as empty-sequence() external;
declare %a:since("basex", "8.3") function archive:extract-to($path as xs:string, $archive as xs:base64Binary, $entries as item()*) as empty-sequence() external;
declare %a:since("basex", "7.3") function archive:update($archive as xs:base64Binary, $entries as item()*, $contents as item()*) as xs:base64Binary external;
declare %a:since("basex", "7.3") function archive:delete($archive as xs:base64Binary, $entries as item()*) as xs:base64Binary external;
declare %a:since("basex", "7.7") %a:deprecated("basex", "8.3", "archive:create-from#2") function archive:write($path as xs:string, $archive as xs:base64Binary) as empty-sequence() external;
declare %a:since("basex", "7.7") %a:deprecated("basex", "8.3", "archive:create-from#3") function archive:write($path as xs:string, $archive as xs:base64Binary, $entries as item()*) as empty-sequence() external;