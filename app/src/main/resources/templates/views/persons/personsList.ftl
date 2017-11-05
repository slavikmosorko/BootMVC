<#include "../../includes/coreInclude.ftl">
<html>
<script>
    function loadTable() {
        $.ajax({
            url: '/persons/list.json',
            type: 'GET',
            dataType: "json",
            success: function (response) {
                $('#loadingSpinner').hide();
                $('#personsTable').bootstrapTable({
                    formatLoadingMessage: function () {
                        return null;
                    },
                    data: response
                });
            }
        });
    }

    $(function () {
        loadTable();
    });
</script>
<style>
</style>
<head>
    <title>Persons List</title>
</head>
<body>
<div class="container">
    <h1 class="display-3">Persons List</h1>
    <br>
    <div class="row">
        <table class="table table-hover" id="personsTable">
            <thead>
            <tr>
                <th data-field="id">ID</th>
                <th data-field="firstName">First Name</th>
                <th data-field="lastName">Last Name</th>
                <th data-field="age">Age</th>
                <th data-field="adress">AdressID</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div id="loadingSpinner" class="col align-self-center text-center">
            <span class="fa fa-circle-o-notch fa-spin" style="font-size:70px;color:#5cb85c;margin-bottom: 30px;"></span>
        </div>
        <div class="col align-self-center text-center">
            <ul class="pagination">
                <li><a href="#">&laquo;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>
    </div>

</div>
</body>
</html>