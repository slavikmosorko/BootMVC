<#include "../../includes/coreInclude.ftl">
<@mainLayout.defaultLayout "Messages">
<script><#include "js/messagesList.js"></script>
<html ng-app="messagesView" class="ng-scope">
<script>
    (function ($) {
        $.fn.serializeFormJSON = function () {

            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };
    })(jQuery);

    $(function () {
        $('#datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss'
        });
        $("#messageForm").submit(function (e) {
            console.log($(this).serializeFormJSON());
            console.log($('#sendingDate').val());
            if ($("#messageForm").valid()) {
                e.preventDefault();
                $("#sendButton").button('loading');
                $("#content").prop('readonly', true);
                $("#sendingDate").prop('readonly', true);
                $.ajax({
                    url: '/messages/add',
                    type: 'post',
                    data: $(this).serializeFormJSON(),
                    success: function () {
                        $("#sendButton").button('reset');
                        $("#content").prop('readonly', false);
                        $("#sendingDate").prop('readonly', false);
                    },
                    error: function () {
                        alert("Error, try again!");
                        $("#sendButton").button('reset');
                        $("#content").prop('readonly', false);
                        $("#sendingDate").prop('readonly', false);
                    }
                });
            }
        });

    });
</script>
<style>
</style>
<head>
    <title>Messages List</title>
</head>
<body>
<div class="container">
    <h1 class="display-3">Messages List</h1>
    <br>
    <div class="row">
        <div ng-controller="messagesViewController">
            <table class="table table-hover" id="messagesTable">
                <thead>
                <tr>
                    <th data-field="id">ID</th>
                    <th data-field="content">Content</th>
                    <th data-field="sendingDate">Sending Date</th>
                    <th data-field="sendingDate">Sent</th>
                </tr>
                </thead>
                <tbody ng-cloak>
                <tr ng-repeat="message in messages">
                    <td>{{message.id}}</td>
                    <td>{{message.content}}</td>
                    <td>{{message.sendingDate | date: 'yyyy-MM-dd HH:mm'}}</td>
                    <td>{{message.sent}}</td>
                </tr>
                </tbody>
            </table>
            <div id="loadingSpinner" class="col align-self-center text-center" ng-show="loaded == false">
                <span class="fa fa-circle-o-notch fa-spin"
                      style="font-size:70px;color:#5cb85c;margin-bottom: 30px;"></span>
            </div>
        </div>
        <button id="addButton" type="button" class="btn btn-success" data-toggle="collapse" data-target="#addDiv">Add
            message
        </button>
        <div id="addDiv" class="collapse">
            <h1 class="display-3">Add message</h1>
            <br>
            <form id="messageForm" name="messageForm">
                <div class="form-group">
                    <label for="content">Content:</label>
                    <input class="form-control" type="text" name="content" id="content" value="">
                </div>
                <div class="form-group">
                    <label for="sendingDate">Date:</label>
                    <div class='input-group date' id='datetimepicker'>
                        <input type='text' class="form-control" name="sendingDate" id="sendingDate"/>
                        <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                    </div>
                    <br>
                    <button id="sendButton" type="submit" class="btn btn-success" onclick="loadTable()"
                            data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Adding">Add
                    </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
</@mainLayout.defaultLayout>