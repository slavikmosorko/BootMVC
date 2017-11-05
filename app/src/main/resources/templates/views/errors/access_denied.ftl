<#include "../../includes/coreInclude.ftl">

<html>
<head>
    <title>403 – access denied!</title>
    <meta charset="utf-8">
    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin: 0px;
            padding: 0px;
            font-family: 'Open Sans', sans-serif;
            font-size: 16px
        }

        body {
            background: url("<@spring.url '/images/errors/403.gif'/>") center no-repeat #fff
        }

        .content {
            width: 100%;
            text-align: center;
            position: absolute;
            bottom: 10%;
            left: 0px;
        }

        .content a {
            display: inline-block;
            text-decoration: none
        }

        .content a:hover {
            opacity: 0.7
        }

        .content a, .content a:hover {
            color: #000;
        }

        @media only screen and (max-width: 460px), screen and (max-height: 700px) {
            .content {
                position: static;
            }

            .content a {
                display: block;
                width: 100%;
                height: 100%;
                position: absolute;
                top: 0px;
                left: 0px;
                font-size: 0px;
                opacity: 0;
            }

            body {
                background-size: cover
            }
        }
    </style>
    <p class="text-center">Access denied!</p>
</head>
<body>
<div class="content">
    <a href="/">Go to home page</a>
</div>
</body>
</html>
