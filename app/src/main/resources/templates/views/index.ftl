<#include "../includes/coreInclude.ftl">
<@mainLayout.defaultLayout "Home">
<html>
<head>
    <title>Main Page</title>
    <style>
        .margin {
            margin-bottom: 45px;
        }

        .container-fluid2 {
            padding-top: 30px;
            padding-bottom: 30px;
        }

        .bg-1 {
            background-color: #1abc9c; /* Green */
            color: #ffffff;
        }

        .bg-2 {
            background-color: #474e5d; /* Dark Blue */
            color: #ffffff;
        }
    </style>
</head>
<body>
<!-- First Container -->
<div class="container-fluid2 bg-1 text-center">
    <h3 class="margin">Who Am I?</h3>
    <img src="http://www.unixstickers.com/image/cache/data/stickers/spring/spring-leaf.sh-600x600.png"
         class="img-responsive img-circle margin" style="display:inline" alt="Bird" width="350"
         height="350">
    <h3>I'm an adventurer</h3>
</div>

<!-- Second Container -->
<div class="container-fluid2 bg-2 text-center">
    <h3 class="margin">What Am I?</h3>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. </p>
    <a href="#" class="btn btn-default btn-lg">
        <span class="glyphicon glyphicon-search"></span> Search
    </a>
</div>
</body>
</html>
</@mainLayout.defaultLayout>