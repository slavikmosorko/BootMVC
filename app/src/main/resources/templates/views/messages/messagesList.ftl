<#include "../../includes/coreInclude.ftl">
<@mainLayout.defaultLayout "Messages">
<script>
        <#include "js/messagesViewController.js">
        <#include "js/messagesViewService.js">
        <#include "js/messagesViewFilter.js">
    $(function () {
        $('#datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss'
        });
    });
</script>
<html ng-app="messagesView" class="ng-scope">
<head>
    <title>Messages List</title>
</head>
<body>
<div class="container">
    <h1 class="display-3">Messages List</h1>
    <br>
    <div class="row">
        <div ng-controller="messagesViewController as msgCtrl">
            <table class="table table-hover" id="messagesTable">
                <thead class="thead-dark">
                <tr>
                    <th data-field="id">ID</th>
                    <th data-field="content">Content</th>
                    <th data-field="sendingDate">Sending Date</th>
                    <th data-field="sendingDate">Sent</th>
                    <th></th>
                </tr>
                </thead>
                <tbody ng-cloak>
                <tr ng-repeat="message in msgCtrl.messages">
                    <td>{{message.id}}</td>
                    <td>{{message.content}}</td>
                    <td>{{message.sendingDate | date: 'yyyy-MM-dd HH:mm'}}</td>
                    <td class="success" ng-class="message.sent ? 'success' : 'danger'">{{message.sent | sentFilter}}
                    </td>
                    <td>
                        <button type="button" class="btn btn-success">Edit</button>
                        <button type="button" class="btn btn-danger">Delete</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div id="loadingSpinner" class="col align-self-center text-center" ng-show="!msgCtrl.loaded">
                <span class="fa fa-circle-o-notch fa-spin"
                      style="font-size:70px;color:#5cb85c;margin-bottom: 30px;"></span>
            </div>
        </div>
        <button id="addButton" type="button" class="btn btn-success" data-toggle="modal" data-target="#addMessageModal">
            Add message
        </button>
        <!-- Modal -->
        <div class="modal fade" id="addMessageModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content" ng-controller="messagesViewAddMessageModalController as msgAMCtrl">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add message</h4>
                    </div>
                    <div class="modal-body">
                        <form name="messageForm" ng-submit="msgAMCtrl.addMessage()">
                            <div class="form-group">
                                <label for="content">Content:</label>
                                <input class="form-control" type="text" name="content"
                                       ng-model="msgAMCtrl.messageData.content">
                            </div>
                            <div class="form-group">
                                <label for="sendingDate">Date:</label>
                                <div class='input-group date' id='datetimepicker'>
                                    <input type='text' class="form-control" name="sendingDate"
                                           ng-model="msgAMCtrl.messageData.sendingDate"/>
                                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success" ng-disabled="!msgAMCtrl.messageAdded">
                                        Add
                                    </button>
                                    <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
                                </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
</@mainLayout.defaultLayout>