xquery version "3.0";

(:~
 : XQuery 1.0 and XPath 2.0 Functions and Operators (Second Edition)
 : <em>W3C Recommendation 14 December 2010</em>
 :
 : @see https://www.w3.org/TR/2010/REC-xpath-functions-20101214/
 :)
module  namespace fn = "http://www.w3.org/2005/xpath-functions";
declare namespace xs = "http://www.w3.org/2001/XMLSchema";

declare function fn:QName($paramURI as xs:string?, $paramQName as xs:string) as xs:QName external;
declare function fn:abs($arg as xs:numeric?) as xs:numeric? external;
declare function fn:adjust-date-to-timezone($arg as xs:date?) as xs:date? external;
declare function fn:adjust-date-to-timezone($arg as xs:date?, $timezone as xs:dayTimeDuration?) as xs:date? external;
declare function fn:adjust-dateTime-to-timezone($arg as xs:dateTime?) as xs:dateTime? external;
declare function fn:adjust-dateTime-to-timezone($arg as xs:dateTime?, $timezone as xs:dayTimeDuration?) as xs:dateTime? external;
declare function fn:adjust-time-to-timezone($arg as xs:time?) as xs:time? external;
declare function fn:adjust-time-to-timezone($arg as xs:time?, $timezone as xs:dayTimeDuration?) as xs:time? external;
declare function fn:avg($arg as xs:anyAtomicType*) as xs:anyAtomicType? external;
declare function fn:base-uri() as xs:anyURI? external;
declare function fn:base-uri($arg as node()?) as xs:anyURI? external;
declare function fn:boolean($arg as item()*) as xs:boolean external;
declare function fn:ceiling($arg as xs:numeric?) as xs:numeric? external;
declare function fn:codepoint-equal($comparand1 as xs:string?, $comparand2 as xs:string?) as xs:boolean? external;
declare function fn:codepoints-to-string($arg as xs:integer*) as xs:string external;
declare function fn:collection() as node()* external;
declare function fn:collection($arg as xs:string?) as node()* external;
declare function fn:compare($comparand1 as xs:string?, $comparand2 as xs:string?) as xs:integer? external;
declare function fn:compare($comparand1 as xs:string?, $comparand2 as xs:string?, $collation as xs:string) as xs:integer? external;
declare %variadic("xs:anyAtomicType?") function fn:concat($arg1 as xs:anyAtomicType?, $arg2 as xs:anyAtomicType?) as xs:string external;
declare function fn:contains($arg1 as xs:string?, $arg2 as xs:string?) as xs:boolean external;
declare function fn:contains($arg1 as xs:string?, $arg2 as xs:string?, $collation as xs:string) as xs:boolean external;
declare function fn:count($arg as item()*) as xs:integer external;
declare function fn:current-date() as xs:date external;
declare function fn:current-dateTime() as xs:dateTime external;
declare function fn:current-time() as xs:time external;
declare function fn:data($arg as item()*) as xs:anyAtomicType* external;
declare function fn:dateTime($arg1 as xs:date?, $arg2 as xs:time?) as xs:dateTime? external;
declare function fn:day-from-date($arg as xs:date?) as xs:integer? external;
declare function fn:day-from-dateTime($arg as xs:dateTime?) as xs:integer? external;
declare function fn:days-from-duration($arg as xs:duration?) as xs:integer? external;
declare function fn:deep-equal($parameter1 as item()*, $parameter2 as item()*) as xs:boolean external;
declare function fn:deep-equal($parameter1 as item()*, $parameter2 as item()*, $collation as string) as xs:boolean external;
declare function fn:default-collation() as xs:string external;
declare function fn:distinct-values($arg as xs:anyAtomicType*) as xs:anyAtomicType* external;
declare function fn:distinct-values($arg as xs:anyAtomicType*, $collation as xs:string) as xs:anyAtomicType* external;
declare function fn:doc($uri as xs:string?) as document-node()? external;
declare function fn:doc-available($uri as xs:string?) as xs:boolean external;
declare function fn:document-uri($arg as node()?) as xs:anyURI? external;
declare function fn:element-with-id($arg as xs:string*) as element()* external;
declare function fn:element-with-id($arg as xs:string*, $node as node()) as element()* external;
declare function fn:empty($arg as item()*) as xs:boolean external;
declare function fn:encode-for-uri($uri-part as xs:string?) as xs:string external;
declare function fn:ends-with($arg1 as xs:string?, $arg2 as xs:string?) as xs:boolean external;
declare function fn:ends-with($arg1 as xs:string?, $arg2 as xs:string?, $collation as xs:string) as xs:boolean external;
declare function fn:error() as none external;
declare function fn:error($error as xs:QName) as none external;
declare function fn:error($error as xs:QName?, $description as xs:string) as none external;
declare function fn:error($error as xs:QName?, $description as xs:string, $error-object as item()*) as none external;
declare function fn:escape-html-uri($uri as xs:string?) as xs:string external;
declare function fn:exactly-one($arg as item()*) as item() external;
declare function fn:exists($arg as item()*) as xs:boolean external;
declare function fn:false() as xs:boolean external;
declare function fn:floor($arg as xs:numeric?) as xs:numeric? external;
declare function fn:hours-from-dateTime($arg as xs:dateTime?) as xs:integer? external;
declare function fn:hours-from-duration($arg as xs:duration?) as xs:integer? external;
declare function fn:hours-from-time($arg as xs:time?) as xs:integer? external;
declare function fn:id($arg as xs:string*) as element()* external;
declare function fn:id($arg as xs:string*, $node as node()) as element()* external;
declare function fn:idref($arg as xs:string*) as node()* external;
declare function fn:idref($arg as xs:string*, $node as node()) as node()* external;
declare function fn:implicit-timezone() as xs:dayTimeDuration external;
declare function fn:in-scope-prefixes($element as element()) as xs:string* external;
declare function fn:index-of($seqParam as xs:anyAtomicType*, $srchParam as xs:anyAtomicType) as xs:integer* external;
declare function fn:index-of($seqParam as xs:anyAtomicType*, $srchParam as xs:anyAtomicType, $collation as xs:string) as xs:integer* external;
declare function fn:insert-before($target as item()*, $position as xs:integer, $inserts as item()*) as item()* external;
declare function fn:iri-to-uri($iri as xs:string?) as xs:string external;
declare function fn:lang($testlang as xs:string?) as xs:boolean external;
declare function fn:lang($testlang as xs:string?, $node as node()) as xs:boolean external;
declare function fn:last() as xs:integer external;
declare function fn:local-name() as xs:string external;
declare function fn:local-name($arg as node()?) as xs:string external;
declare function fn:local-name-from-QName($arg as xs:QName?) as xs:NCName? external;
declare function fn:lower-case($arg as xs:string?) as xs:string external;
declare function fn:matches($input as xs:string?, $pattern as xs:string) as xs:boolean external;
declare function fn:matches($input as xs:string?, $pattern as xs:string, $flags as xs:string) as xs:boolean external;
declare function fn:max($arg as xs:anyAtomicType*) as xs:anyAtomicType? external;
declare function fn:max($arg as xs:anyAtomicType*, $collation as string) as xs:anyAtomicType? external;
declare function fn:min($arg as xs:anyAtomicType*) as xs:anyAtomicType? external;
declare function fn:min($arg as xs:anyAtomicType*, $collation as string) as xs:anyAtomicType? external;
declare function fn:minutes-from-dateTime($arg as xs:dateTime?) as xs:integer? external;
declare function fn:minutes-from-duration($arg as xs:duration?) as xs:integer? external;
declare function fn:minutes-from-time($arg as xs:time?) as xs:integer? external;
declare function fn:month-from-date($arg as xs:date?) as xs:integer? external;
declare function fn:month-from-dateTime($arg as xs:dateTime?) as xs:integer? external;
declare function fn:months-from-duration($arg as xs:duration?) as xs:integer? external;
declare function fn:name() as xs:string external;
declare function fn:name($arg as node()?) as xs:string external;
declare function fn:namespace-uri() as xs:anyURI external;
declare function fn:namespace-uri($arg as node()?) as xs:anyURI external;
declare function fn:namespace-uri-for-prefix($prefix as xs:string?, $element as element()) as xs:anyURI? external;
declare function fn:namespace-uri-from-QName($arg as xs:QName?) as xs:anyURI? external;
declare function fn:nilled($arg as node()?) as xs:boolean? external;
declare function fn:node-name($arg as node()?) as xs:QName? external;
declare function fn:normalize-space() as xs:string external;
declare function fn:normalize-space($arg as xs:string?) as xs:string external;
declare function fn:normalize-unicode($arg as xs:string?) as xs:string external;
declare function fn:normalize-unicode($arg as xs:string?, $normalizationForm as xs:string) as xs:string external;
declare function fn:not($arg as item()*) as xs:boolean external;
declare function fn:number() as xs:double external;
declare function fn:number($arg as xs:anyAtomicType?) as xs:double external;
declare function fn:one-or-more($arg as item()*) as item()+ external;
declare function fn:position() as xs:integer external;
declare function fn:prefix-from-QName($arg as xs:QName?) as xs:NCName? external;
declare function fn:remove($target as item()*, $position as xs:integer) as item()* external;
declare function fn:replace($input as xs:string?, $pattern as xs:string, $replacement as xs:string) as xs:string external;
declare function fn:replace($input as xs:string?, $pattern as xs:string, $replacement as xs:string, $flags as xs:string) as xs:string external;
declare function fn:resolve-QName($qname as xs:string?, $element as element()) as xs:QName? external;
declare function fn:resolve-uri($relative as xs:string?) as xs:anyURI? external;
declare function fn:resolve-uri($relative as xs:string?, $base as xs:string) as xs:anyURI? external;
declare function fn:reverse($arg as item()*) as item()* external;
declare function fn:root() as node() external;
declare function fn:root($arg as node()?) as node()? external;
declare function fn:round($arg as xs:numeric?) as xs:numeric? external;
declare function fn:round-half-to-even($arg as xs:numeric?) as xs:numeric? external;
declare function fn:round-half-to-even($arg as xs:numeric?, $precision as xs:integer) as xs:numeric? external;
declare function fn:seconds-from-dateTime($arg as xs:dateTime?) as xs:decimal? external;
declare function fn:seconds-from-duration($arg as xs:duration?) as xs:decimal? external;
declare function fn:seconds-from-time($arg as xs:time?) as xs:decimal? external;
declare function fn:starts-with($arg1 as xs:string?, $arg2 as xs:string?) as xs:boolean external;
declare function fn:starts-with($arg1 as xs:string?, $arg2 as xs:string?, $collation as xs:string) as xs:boolean external;
declare function fn:static-base-uri() as xs:anyURI? external;
declare function fn:string() as xs:string external;
declare function fn:string($arg as item()?) as xs:string external;
declare function fn:string-join($arg1 as xs:string*, $arg2 as xs:string) as xs:string external;
declare function fn:string-length() as xs:integer external;
declare function fn:string-length($arg as xs:string?) as xs:integer external;
declare function fn:string-to-codepoints($arg as xs:string?) as xs:integer* external;
declare function fn:subsequence($sourceSeq as item()*, $startingLoc as xs:double) as item()* external;
declare function fn:subsequence($sourceSeq as item()*, $startingLoc as xs:double, $length as xs:double) as item()* external;
declare function fn:substring($sourceString as xs:string?, $startingLoc as xs:double) as xs:string external;
declare function fn:substring($sourceString as xs:string?, $startingLoc as xs:double, $length as xs:double) as xs:string external;
declare function fn:substring-after($arg1 as xs:string?, $arg2 as xs:string?) as xs:string external;
declare function fn:substring-after($arg1 as xs:string?, $arg2 as xs:string?, $collation as xs:string) as xs:string external;
declare function fn:substring-before($arg1 as xs:string?, $arg2 as xs:string?) as xs:string external;
declare function fn:substring-before($arg1 as xs:string?, $arg2 as xs:string?, $collation as xs:string) as xs:string external;
declare function fn:sum($arg as xs:anyAtomicType*) as xs:anyAtomicType external;
declare function fn:sum($arg as xs:anyAtomicType*, $zero as xs:anyAtomicType?) as xs:anyAtomicType? external;
declare function fn:timezone-from-date($arg as xs:date?) as xs:dayTimeDuration? external;
declare function fn:timezone-from-dateTime($arg as xs:dateTime?) as xs:dayTimeDuration? external;
declare function fn:timezone-from-time($arg as xs:time?) as xs:dayTimeDuration? external;
declare function fn:tokenize($input as xs:string?, $pattern as xs:string) as xs:string* external;
declare function fn:tokenize($input as xs:string?, $pattern as xs:string, $flags as xs:string) as xs:string* external;
declare function fn:trace($value as item()*, $label as xs:string) as item()* external;
declare function fn:translate($arg as xs:string?, $mapString as xs:string, $transString as xs:string) as xs:string external;
declare function fn:true() as xs:boolean external;
declare function fn:unordered($sourceSeq as item()*) as item()* external;
declare function fn:upper-case($arg as xs:string?) as xs:string external;
declare function fn:year-from-date($arg as xs:date?) as xs:integer? external;
declare function fn:year-from-dateTime($arg as xs:dateTime?) as xs:integer? external;
declare function fn:years-from-duration($arg as xs:duration?) as xs:integer? external;
declare function fn:zero-or-one($arg as item()*) as item()? external;
