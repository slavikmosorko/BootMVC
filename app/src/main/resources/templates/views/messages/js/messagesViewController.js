var app = angular
    .module('messagesView', []);

app.controller('messagesViewController', function ($rootScope, $log, messagesViewService) {
    var msgCtrl = this;
    msgCtrl.messages = [];
    msgCtrl.loaded = false;

    messagesViewService.getMessagesList()
        .then(function (response) {
                msgCtrl.messages = response.data;
                msgCtrl.loaded = true;
            },
            function (reason) {
                toastr.error('Error while loading messages!');
                $log.error(reason);
            });

    $rootScope.$on("messageAdded", function(){
        messagesViewService.getMessagesList()
            .then(function (response) {
                    msgCtrl.messages = response.data;
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
