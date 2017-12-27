<#macro defaultLayout title="BootMVC">
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <style>
            <#include "css/defaultLeyout.css"/>
    </style>
</head>
<body class="defaultbody">
    <#include "header.ftl"/>
<table class="alllayout">
    <tr>
        <td class="menu" valign="top">
            <#include "menu.ftl"/>
        </td>
        <td class="defaultcontent">
            <#nested/>
        </td>
    </tr>
</table>
    <#include "footer.ftl"/>
</body>
    <#include "globalAngularJS.ftl"/>
</html>
</#macro>