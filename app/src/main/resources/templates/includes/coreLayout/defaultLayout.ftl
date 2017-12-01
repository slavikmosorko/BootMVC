<#macro defaultLayout title="BootMVC">
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<style>
    .defaultbody {
        /*font: 18px Montserrat, sans-serif;*/
        position: relative;
        margin: 0;
        padding-bottom: 3rem;
        min-height: 100%;
        background-color: #ffffff; /* White */
    }
</style>
<body class="defaultbody">
    <#include "header.ftl"/>
    <#nested/>
    <#include "footer.ftl"/>
</body>
</html>
</#macro>