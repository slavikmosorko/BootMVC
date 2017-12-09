<#include "../../includes/coreInclude.ftl">
<@mainLayout.defaultLayout "Messages">
<script>
        <#include "js/messagesViewController.js">
        <#include "js/messagesViewService.js">
        <#include "js/messagesViewFilter.js">
        <#include "../../includes/angular/directives/loadingSpinner.js">
        <#include "../../includes/angular/directives/dirPagination.js">
    $(function () {
        $('#messagesli').addClass('active');
        $('.datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss'
        });
    });
</script>
<html ng-app="messagesView" class="ng-scope">
<body>
<div class="container" ng-cloak>
    <div class="row">
        <h1 class="display-3">Messages List</h1>
        <br>
    </div>
    <div class="row">
        <div class="col-sm text-right">
            <button id="addButton" type="button" class="btn btn-primary" data-toggle="modal"
                    data-target="#addMessageModal">
                Add message
            </button>
        </div>
    </div>
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
                <tbody>
                <tr dir-paginate="message in msgCtrl.messages|orderBy:message.id:reverse|itemsPerPage:msgCtrl.msgPerPage">
                    <td>{{message.id}}</td>
                    <td>
                        <div ng-hide="msgCtrl.editingData[message.id]">{{message.content}}</div>
                        <div ng-show="msgCtrl.editingData[message.id]">
                            <input class="form-control" type="text"
                                   ng-show="msgCtrl.editingData[message.id]"
                                   ng-disabled="!msgCtrl.messageEdited[message.id]"
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
                                   ng-disabled="!msgCtrl.messageEdited[message.id]"
                                   ng-model="message.sendingDate"/>
                        </div>
                    </td>
                    <td class="text-center" ng-class="message.sent ? 'success' : 'danger'">{{message.sent | sentFilter}}
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary" ng-hide="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.edit(message)"
                                ng-disabled="!msgCtrl.messageEdited[message.id]">Edit
                        </button>
                        <button type="button" class="btn btn-danger" ng-hide="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.deleteMessage(message)"
                                ng-disabled="!msgCtrl.messageEdited[message.id]">
                            <div ng-show="msgCtrl.messageEdited[message.id]">Delete</div>
                            <i class="fa fa-cog fa-spin" style="font-size:22px;"
                               ng-hide="msgCtrl.messageEdited[message.id]"></i>
                        </button>
                        <button type="button" class="btn btn-primary" ng-show="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.editMessage(message)"
                                ng-disabled="!msgCtrl.messageEdited[message.id]">
                            <div ng-show="msgCtrl.messageEdited[message.id]">Save</div>
                            <i class="fa fa-cog fa-spin" style="font-size:22px;"
                               ng-hide="msgCtrl.messageEdited[message.id]"></i>
                        </button>
                        <button type="button" class="btn btn-danger" ng-show="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.cancel(message)" ng-disabled="!msgCtrl.messageEdited[message.id]">
                            Cancel
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="col align-self-center text-center" ng-show="!msgCtrl.loaded">
                <loading-spinner></loading-spinner>
            </div>
            <div class="col-sm text-center">
                <dir-pagination-controls class="pagination"
                                         max-size="msgCtrl.maxPaginationLinks"
                                         direction-links="true"
                                         boundary-links="true">
                </dir-pagination-controls>
            </div>
        </div>
    </div>
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
                                   ng-model="msgAMCtrl.messageData.content" ng-disabled="!msgAMCtrl.messageAdded">
                        </div>
                        <div class="form-group">
                            <label for="sendingDate">Date:</label>
                            <div class='input-group date datetimepicker'>
                                <input type='text' class="form-control" name="sendingDate"
                                       ng-model="msgAMCtrl.messageData.sendingDate"
                                       ng-disabled="!msgAMCtrl.messageAdded"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"
                                ng-disabled="!msgAMCtrl.messageAdded">Close
                        </button>
                        <button type="submit" class="btn btn-primary" ng-disabled="!msgAMCtrl.messageAdded">
                            <div ng-show="msgAMCtrl.messageAdded">Add</div>
                            <i class="fa fa-circle-o-notch fa-spin" style="font-size:22px;"
                               ng-hide="msgAMCtrl.messageAdded"></i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
</@mainLayout.defaultLayout>