app.filter('booleanFilter', function () {
    return function (boolean) {
        switch (boolean) {
            case true:
                return 'Yes';
            case false:
                return 'No';
        }
    }
});
