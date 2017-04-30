
<#macro JSONArray dataDict>
[<#list data_dict(dataDict) as element>{"value":"${element.valueStr}","label":"${element.nameStr}"}<#if element_has_next>,</#if></#list>]
</#macro>

<#macro JSONObject dataDict>
{<#list data_dict(dataDict) as element>"${element.valueStr}":"${element.nameStr}"<#if element_has_next>,</#if></#list>}
</#macro>
