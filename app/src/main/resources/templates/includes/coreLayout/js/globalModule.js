var globalAppModule = angular
    .module('globalAppModule', []);

globalAppModule.controller('globalWebPushesController', function ($interval, globalWebPushesService) {
    var gWPCtrl = this;

    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toast-bottom-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    gWPCtrl.getWebPushes = function () {
        globalWebPushesService.getWebPushes()
            .then(
                function (response) {
                    angular.forEach(response.data, function (item) {
                        toastr.info(item.message + "\nFrom: " + item.userIdFrom, item.title);
                    });
                },
                function (reason) {
                });
    };

    $interval(gWPCtrl.getWebPushes, 5000);
});
