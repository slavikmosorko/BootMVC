<div id="globalAppModuleDiv" ng-app="globalAppModule" ng-show="false" ng-cloak>
    <div ng-controller="globalWebPushesController"></div>
</div>
<script>
    <#include "js/globalModule.js">
    <#include "js/globalService.js">

    var globalAppModuleDiv = document.getElementById('globalAppModuleDiv');

    angular.element(document).ready(function () {
        if (!angular.element(globalAppModuleDiv).injector()) {
            angular.bootstrap(globalAppModuleDiv, ['globalAppModule']);
        }
    });
</script>