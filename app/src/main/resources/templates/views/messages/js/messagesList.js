var messagesView = angular
    .module('messagesView', [])
    .controller('messagesViewController', function ($scope, $http, $log) {
        $scope.messages = [];
        $scope.loaded = false;

        $http.get('/messages/list.json')
            .then(function (response) {
                    $scope.messages = response.data;
                    $scope.loaded = true;
                    toastr.success('Messages loaded successfully');
                },
                function (reason) {
                    toastr.success('Error while loading messages!');
                    $log.error(reason);
                });
    });