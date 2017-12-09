var app = angular
    .module('messagesView', []);

app.controller('messagesViewController', function ($rootScope, $log, messagesViewService) {
    var msgCtrl = this;
    msgCtrl.loaded = false;
    msgCtrl.messages = [];
    msgCtrl.editingData = {};
    msgCtrl.messageEdited = true;

    messagesViewService.getMessagesList()
        .then(function (response) {
                msgCtrl.messages = response.data;
                updateEditingData();
                msgCtrl.loaded = true;
            },
            function (reason) {
                toastr.error('Error while loading messages!');
                $log.error(reason);
            });

    msgCtrl.cancel = function (message) {
        msgCtrl.editingData[message.id] = false;
    };

    msgCtrl.edit = function (message) {
        msgCtrl.editingData[message.id] = true;
    };

    msgCtrl.editMessage = function (message) {
        msgCtrl.messageEdited = false;
        messagesViewService.editMessage(message)
            .then(function (response) {
                    msgCtrl.editingData[message.id] = false;
                    msgCtrl.messageEdited = true;
                    toastr.success('Message edited successfully!');
                },
                function (reason) {
                    msgCtrl.messageEdited = true;
                    toastr.error('Error while editing message!');
                    $log.error(reason);
                });
    };

    msgCtrl.deleteMessage = function (message) {
        messagesViewService.deleteMessage(message.id)
            .then(function (response) {
                    var index = msgCtrl.messages.indexOf(message);
                    msgCtrl.messages.splice(index, 1);
                    msgCtrl.editingData.splice(index, 1);
                },
                function (reason) {
                    toastr.error('Error while deleting message!');
                    $log.error(reason);
                });
    };

    var updateEditingData = function () {
        for (var i = 0, length = msgCtrl.messages.length; i < length; i++) {
            msgCtrl.editingData [msgCtrl.messages[i].id] = false;
        }
    };

    $rootScope.$on("messageAdded", function () {
        messagesViewService.getMessagesList()
            .then(function (response) {
                msgCtrl.messages = response.data;
                updateEditingData();
            });
    });
});

app.controller('messagesViewAddMessageModalController', function ($rootScope, $log, messagesViewService) {
    var msgAMCtrl = this;

    msgAMCtrl.messageData = {};
    msgAMCtrl.messageAdded = true;

    msgAMCtrl.addMessage = function () {
        msgAMCtrl.messageAdded = false;
        messagesViewService.addMessage(msgAMCtrl.messageData)
            .then(function (response) {
                    msgAMCtrl.messageAdded = true;
                    $rootScope.$broadcast("messageAdded");
                    toastr.success('Message added successfully');
                },
                function (reason) {
                    msgAMCtrl.messageAdded = true;
                    toastr.error('Error while adding message!');
                    $log.error(reason);
                });
    }
});
