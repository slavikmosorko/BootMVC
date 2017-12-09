app.filter('sentFilter', function () {
    return function (sent) {
        switch (sent) {
            case true:
                return 'Yes';
            case false:
                return 'No';
        }
    }
});
