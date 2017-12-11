<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <style>
        <#include "style.css">
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="application/x-javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }

        $(function () {
            $("#registrationForm").submit(function (e) {
                e.preventDefault();
                $('input[name=username]').prop('disabled', true);
                $('input[name=username]').prop('disabled', true);
                $('#registerUser').prop('disabled', true);
                var request = $.ajax({
                    url: "/register/newuser",
                    type: "post",
                    data: $("#registrationForm").serialize()
                });
                request.done(function (response, textStatus, jqXHR) {
                    alert("Please, activate your account!");
                    $('input[name=username]').prop('disabled', false);
                    $('input[name=username]').prop('disabled', false);
                    $('#registerUser').prop('disabled', false);
                    window.location.href = "/";
                });
                request.fail(function (jqXHR, textStatus, errorThrown) {
                    alert("Registration failed!");
                    $('input[name=username]').prop('disabled', false);
                    $('input[name=username]').prop('disabled', false);
                    $('#registerUser').prop('disabled', false);
                });
            });
        });

    </script>
    <!--webfonts-->
    <link href='http://fonts.googleapis.com/css?family=Oxygen:400,300,700' rel='stylesheet' type='text/css'>
    <!--//webfonts-->
</head>
<body>
<div class="main">
    <div class="social-icons">
        <div class="clear"></div>
    </div>
    <h2>Sign up</h2>
    <form id="registrationForm">
    <#--<div class="lable">-->
    <#--<div class="col_1_of_2 span_1_of_2"><input type="text" class="text" value="First Name"-->
    <#--onfocus="this.value = '';"-->
    <#--onblur="if (this.value == '') {this.value = 'First Name';}"-->
    <#--id="active"></div>-->
    <#--<div class="col_1_of_2 span_1_of_2"><input type="text" class="text" value="Last Name"-->
    <#--onfocus="this.value = '';"-->
    <#--onblur="if (this.value == '') {this.value = 'Last Name';}"></div>-->
    <#--<div class="clear"></div>-->
    <#--</div>-->
        <div class="lable-2">
            <input type="text" name="email" class="text" placeholder="your@email.com">
            <input type="password" name="password" class="text" placeholder="Password">
        </div>
        <h3>By creating an account, you agree to our <span class="term"><a href="#">Terms & Conditions</a></span></h3>
        <div class="submit">
            <input type="submit" id="registerUser" value="Create account">
        </div>
        <div class="clear"></div>
    </form>
    <!-----//end-main---->
</div>
<!-----start-copyright---->
<div class="copy-right">
    <p>Template by Slavik</p>
</div>
<!-----//end-copyright---->
</body>
</html>