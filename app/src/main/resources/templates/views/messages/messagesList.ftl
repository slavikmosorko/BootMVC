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
<div class="container" ng-app="messagesViewModule" class="ng-scope" ng-cloak>
    <div class="row">
        <h1 class="display-3">Messages List</h1>
        <br>
    </div>
    <div class="row">
        <div ng-controller="messagesViewController as msgCtrl">
            <div class="col-sm-12 text-right">
                <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#addMessageModal">
                    Add message
                </button>
                <button type="button" class="btn btn-info" ng-click="msgCtrl.getMessagesList()">
                    <i class="fa fa-refresh" style="font-size:23px"></i>
                </button>
            </div>
            <table class="table table-hover" id="messagesTable">
                <thead class="thead-dark">
                <tr>
                    <th data-field="id">ID</th>
                    <th data-field="subject">Subject</th>
                    <th data-field="content">Content</th>
                    <th data-field="addressee">Addressee</th>
                    <th data-field="sendingDate">Sending Date</th>
                    <th data-field="Sent">Sent</th>
                    <th data-field="Valid">Valid</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr dir-paginate="message in msgCtrl.messages|orderBy:message.id:reverse|itemsPerPage:msgCtrl.msgPerPage">
                    <td>{{message.id}}</td>
                    <td>
                        <div ng-hide="msgCtrl.editingData[message.id]">{{message.subject}}</div>
                        <div ng-show="msgCtrl.editingData[message.id]">
                            <input class="form-control" type="text"
                                   ng-show="msgCtrl.editingData[message.id]"
                                   ng-disabled="!msgCtrl.messageEdited[message.id]"
                                   value="{{message.subject}}" ng-model="message.subject">
                        </div>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                                data-target="#previewModel" ng-click="msgCtrl.preview(message)">Preview
                        </button>
                    </td>
                    <td>
                        <div ng-hide="msgCtrl.editingData[message.id]">{{message.addressee}}</div>
                        <div ng-show="msgCtrl.editingData[message.id]">
                            <input class="form-control" type="email"
                                   ng-show="msgCtrl.editingData[message.id]"
                                   ng-disabled="!msgCtrl.messageEdited[message.id]"
                                   value="{{message.addressee}}" ng-model="message.addressee">
                        </div>
                    </td>
                    <td>
                        <div ng-hide="msgCtrl.editingData[message.id]">
                            {{message.sendingDate | date: 'yyyy-MM-dd HH:mm'}}
                        </div>
                        <div ng-show="msgCtrl.editingData[message.id]">
                            <input type='text' class="form-control"
                                   ng-value="message.sendingDate | date: 'yyyy-MM-dd HH:mm'"
                                   ng-disabled="!msgCtrl.messageEdited[message.id]"
                                   ng-model="message.sendingDate"/>
                        </div>
                    </td>
                    <td class="text-center" ng-class="message.sent ? 'success' : 'danger'">{{message.sent |
                        booleanFilter}}
                    </td>
                    <td class="text-center" ng-class="message.valid ? 'success' : 'danger'">{{message.valid |
                        booleanFilter}}
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-sm" ng-hide="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.edit(message)"
                                ng-disabled="!msgCtrl.messageEdited[message.id]">Edit
                        </button>
                        <button type="button" class="btn btn-danger btn-sm" ng-hide="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.deleteMessage(message)"
                                ng-disabled="!msgCtrl.messageEdited[message.id]">
                            <div ng-show="msgCtrl.messageEdited[message.id]">Delete</div>
                            <i class="fa fa-cog fa-spin" style="font-size:22px;"
                               ng-hide="msgCtrl.messageEdited[message.id]"></i>
                        </button>
                        <button type="button" class="btn btn-primary btn-sm" ng-show="msgCtrl.editingData[message.id]"
                                ng-click="msgCtrl.editMessage(message)"
                                ng-disabled="!msgCtrl.messageEdited[message.id]">
                            <div ng-show="msgCtrl.messageEdited[message.id]">Save</div>
                            <i class="fa fa-cog fa-spin" style="font-size:22px;"
                               ng-hide="msgCtrl.messageEdited[message.id]"></i>
                        </button>
                        <button type="button" class="btn btn-danger btn-sm" ng-show="msgCtrl.editingData[message.id]"
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
            <!-- Modal -->
            <div class="modal fade" id="previewModel" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Email preview</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <iframe width="100%" height="50%"
                                        src="{{msgCtrl.previewMessageUrl}}">
                                </iframe>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close
                            </button>
                        </div>
                    </div>
                </div>
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
                            <label for="addressee">Addressee:</label>
                            <input class="form-control" type="email" name="addressee"
                                   ng-model="msgAMCtrl.messageData.addressee" ng-disabled="!msgAMCtrl.messageAdded"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="subject">Subject:</label>
                            <input class="form-control" type="text" name="subject"
                                   ng-model="msgAMCtrl.messageData.subject" ng-disabled="!msgAMCtrl.messageAdded"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="content">Content:</label>
                            <textarea class="form-control" rows="15" name="emailContent"
                                      ng-model="msgAMCtrl.messageData.content"
                                      ng-disabled="!msgAMCtrl.messageAdded" required></textarea>
                        </div>
                        <div class="form-group">
                            <label for="sendingDate">Sending Date:</label>
                            <div class='input-group date datetimepicker'>
                                <input type='text' class="form-control" name="sendingDate"
                                       ng-model="msgAMCtrl.messageData.sendingDate"
                                       ng-disabled="!msgAMCtrl.messageAdded" required/>
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
</div>
</@mainLayout.defaultLayout>