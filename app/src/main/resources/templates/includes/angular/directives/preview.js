app.directive("preview", function () {
    function link(scope, element) {
        var iframe = document.createElement('iframe');
        var element0 = element[0];
        element0.appendChild(iframe);
        var body = iframe.contentDocument.body;

        scope.$watch('content', function () {
            body.innerHTML = scope.content;
        });
    }

    return {
        link: link,
        restrict: 'E',
        scope: {
            content: '='
        }
    };
});