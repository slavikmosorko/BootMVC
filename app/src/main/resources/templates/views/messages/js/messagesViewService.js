app.service('messagesViewService', function ($http) {
    var msgService = this;

    var config = {
        headers : {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    }

    msgService.getMessagesList = function() {
        return $http.get('/messages/list.json');
    }

    msgService.addMessage = function(message) {
        return $http.post('/messages/add', $.param(message), config);
    }
});
