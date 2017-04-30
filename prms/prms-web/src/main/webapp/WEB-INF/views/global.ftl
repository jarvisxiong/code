<!-- Get system name -->
<#assign sys = BasePath?lower_case?replace('/', '')?replace('-web', '') />

<!-- Get module name -->
<#assign mod = springMacroRequestContext.getRequestUri()?lower_case?substring(1)?replace('/',' ')?replace('-web', '')?replace('.do','')?replace(' ', '-') />