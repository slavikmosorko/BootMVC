<#include "../../includes/coreInclude.ftl">
<html>
<script>
    function initTable() {
        $.ajax({
            url: '/articles/get/list',
            type: 'GET',
            dataType: "json",
            success: function (response) {
                var trHTML = '';
                $('#articlesTable tbody').remove();
                $.each(response, function (key, value) {
                    trHTML +=
                            '<tr><td>' + value.articleId +
                            '</td><td>' + value.category +
                            '</td><td>' + value.title +
                            '</td><td>' + '<button onclick="deleteArticle(' + value.articleId + ')" type="button" class="btn btn-danger" data-loading-text="' + '<i class=\'fa fa-spinner fa-spin\'></i> Deleting' + '">Delete</button>' +
                            '</td></tr>';
                });
                $('#loadingSpinner').hide();
                $('#articlesTable').append('<tbody>' + trHTML + '</tbody>');

            }
        });
    }

    function refreshTable() {
        initTable();
    }

    function deleteArticle(id) {
        $.ajax({
            url: '/articles/delete/article/' + id,
            success: function () {
                refreshTable();
            }
        });
    }

    $(document).ready(function () {
        initTable();

        $("#articleForm").validate({
            rules: {
                title: {
                    required: true,
                    minlength: 3
                },
                category: {
                    required: true,
                    minlength: 5
                }
            },
            messages: {
                title: {
                    required: "Title is required!",
                    minlength: "Title is to short!"
                },
                category: {
                    required: "Category is required!",
                    minlength: "Category is to short!"
                }
            },
        });

        $("#articleForm").submit(function (e) {
            if ($("#articleForm").valid()) {
                e.preventDefault();
                $("#sendButton").button('loading');
                $("#category").prop('readonly', true);
                $("#title").prop('readonly', true);
                $.ajax({
                    url: '/articles/add',
                    type: 'post',
                    data: $('#articleForm').serialize(),
                    success: function () {
                        refreshTable();
                        $("#sendButton").button('reset');
                        $("#category").prop('readonly', false);
                        $("#title").prop('readonly', false);
                    },
                    error: function () {
                        alert("Error, try again!");
                        $("#sendButton").button('reset');
                        $("#category").prop('readonly', false);
                        $("#title").prop('readonly', false);
                    }
                });
            }
        });

        $("#addButton").click(function () {
            $("#addButton").hide();
        });
    });

</script>
<head>
    <title>Articles List</title>
</head>
<body>
<div class="container">
    <h1 class="display-3">Articles List</h1>
    <br>
    <table class="table table-hover" id="articlesTable">
        <thead>
        <tr>
            <th>ID</th>
            <th>Category</th>
            <th>Title</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <div id="loadingSpinner" style="margin-left: 45%;">
        <span class="fa fa-circle-o-notch fa-spin" style="font-size:70px;color:#5cb85c;margin-bottom: 30px;"></span>
    </div>
    <br>
    <button id="addButton" type="button" class="btn btn-success" data-toggle="collapse" data-target="#addDiv">Add
        article
    </button>
    <div id="addDiv" class="collapse">
        <h1 class="display-3">Add article</h1>
        <br>
        <form id="articleForm" name="articleForm">
            <div class="form-group">
                <label for="title">Title:</label>
                <input class="form-control" type="text" name="title" id="title" value="">
            </div>
            <div class="form-group">
                <label for="category">Category:</label>
                <input class="form-control" type="text" name="category" id="category" value="">
            </div>
            <button id="sendButton" type="submit" class="btn btn-success"
                    data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Adding">Add
            </button>
        </form>
    </div>
</div>
</body>
</html>