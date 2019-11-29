<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%--引入bootstrap的核心css--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.min.css">
    <%--引入jqgrid的核心css--%>
    <%--jqgird的主题css--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jquery的js--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <%--引入bootstrap的js--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.min.js"></script>
    <%--jqgird的国际化js--%>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqgird的js--%>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script>
        $(function () {
            $("#submit").click(
                $.ajax({
                    url: "${pageContext.request.contextPath}/use/insert",
                    datatype: "json",
                    type: "post",
                    data: $("#for").serialize(),
                    success: function (data) {

                    }
                })
            )
        })
    </script>
</head>


<body>
<form id="for" class="col-md-4">
    <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" placeholder="名字">
    </div>
    <div class="form-group">
        <label for="date">date</label>
        <input type="date" class="form-control" id="date">
    </div>
    <div class="form-group">
        <label for="province">Province</label>
        <input type="text" class="form-control" id="province" placeholder="省份">
    </div>
    <div class="form-group">
        <label for="sex">Sex</label>
        <input type="text" class="form-control" id="sex" placeholder="性别">
    </div>

    <button id="submit" class="btn btn-default">Submit</button>
</form>
</body>
</html>