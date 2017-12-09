<#include "../../includes/coreInclude.ftl">
<@mainLayout.defaultLayout "Messages">
<script>
        <#include "js/messagesViewController.js">
        <#include "js/messagesViewService.js">
        <#include "js/messagesViewFilter.js">
    $(function () {
        $('.datetimepicker').datetimepicker({
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
                    <th data-field="Sent">Sent</th>
                    <th></th>
                </tr>
                </thead>
                <tbody ng-cloak>
                <tr ng-repeat="message in msgCtrl.messages">
                    <td>{{message.id}}</td>
                    <td>
                        <div ng-hide="msgCtrl.editingData[message.id]">{{message.content}}</div>
                        <div ng-show="msgCtrl.editingData[message.id]">
                            <input class="form-control" type="text"
                                   ng-show="msgCtrl.editingData[message.id]"
                                   value="{{message.content}}" ng-model="message.content">
                        </div>
                    </td>
                    <td>
                        <div ng-hide="msgCtrl.editingData[message.id]">{{message.sendingDate | date: 'yyyy-MM-dd
                            HH:mm'}}
                        </div>
                        <div ng-show="msgCtrl.editingData[message.id]">
                            <input type='text' class="form-control"
                                   value="{{message.sendingDate | date: 'yyyy-MM-dd HH:mm'}}"
                                   ng-model="message.sendingDate"/>
                        </div>
                    </td>
                    <td class="text-center" ng-class="message.sent ? 'success' : 'danger'">{{message.sent | sentFilter}}
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary" ng-hide="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.edit(message)">Edit
                        </button>
                        <button type="button" class="btn btn-danger" ng-hide="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.deleteMessage(message)">Delete
                        </button>
                        <button type="button" class="btn btn-primary" ng-show="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.editMessage(message)" ng-disabled="!msgCtrl.messageEdited">Save
                        </button>
                        <button type="button" class="btn btn-danger" ng-show="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.cancel(message)" ng-disabled="!msgCtrl.messageEdited">Cancel
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div id="loadingSpinner" class="col align-self-center text-center" ng-show="!msgCtrl.loaded">
                <div class="sk-cube-grid">
                    <div class="sk-cube sk-cube1"></div>
                    <div class="sk-cube sk-cube2"></div>
                    <div class="sk-cube sk-cube3"></div>
                    <div class="sk-cube sk-cube4"></div>
                    <div class="sk-cube sk-cube5"></div>
                    <div class="sk-cube sk-cube6"></div>
                    <div class="sk-cube sk-cube7"></div>
                    <div class="sk-cube sk-cube8"></div>
                    <div class="sk-cube sk-cube9"></div>
                </div>
            </div>
        </div>
        <button id="addButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#addMessageModal">
            Add message
        </button>
        <!-- Modal -->
        <div class="modal fade" id="addMessageModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content" ng-controller="messagesViewAddMessageModalController as msgAMCtrl">
                    <form name="messageForm" ng-submit="msgAMCtrl.addMessage()">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Add message</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="content">Content:</label>
                                <input class="form-control" type="text" name="content"
                                       ng-model="msgAMCtrl.messageData.content">
                            </div>
                            <div class="form-group">
                                <label for="sendingDate">Date:</label>
                                <div class='input-group date datetimepicker'>
                                    <input type='text' class="form-control" name="sendingDate"
                                           ng-model="msgAMCtrl.messageData.sendingDate"/>
                                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" ng-disabled="!msgAMCtrl.messageAdded">
                                Add
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
</@mainLayout.defaultLayout>