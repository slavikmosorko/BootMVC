globalAppModule.service('globalWebPushesService', function ($http) {
    var gWPCService = this;

    var config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    };

    gWPCService.getWebPushes = function () {
        return $http.get('/webInfo/webPushes/info');
    };
});
