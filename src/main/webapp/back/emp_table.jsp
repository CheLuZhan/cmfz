<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%--引入bootstrap的核心css--%>
    <link rel="stylesheet" href="${app}/statics/boot/css/bootstrap.min.css">
    <%--引入jqgrid的核心css--%>
    <%--jqgird的主题css--%>
    <link rel="stylesheet" href="${app}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jquery的js--%>
    <script src="${app}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <%--引入bootstrap的js--%>
    <script src="${app}/statics/boot/js/bootstrap.min.js"></script>
    <%--jqgird的国际化js--%>
    <script src="${app}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqgird的js--%>
    <script src="${app}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script>
        $(function () {
            $("#tb").jqGrid({
                styleUI: "Bootstrap",
                url: "${app}/emp/selectPage",
                datatype: "json",
                mtype: "post",
                colNames: ["编号", "姓名", "部门编号", "部门"],
                autowidth: true,
                pager: "#page",
                rowNum: 2,
                viewrecords: true,
                editurl: "${app}/emp/add",
                rowList: [2, 4, 6, 8],
                colModel: [
                    {
                        name: "id",
                    },
                    {
                        name: "name",
                        editable: true
                    },
                    {
                        name: "departId",
                        editable: true
                    },
                    {
                        name: "depart.id",
                        editable: true,  //在功能里添加 deprt.name属性
                        edittype: "select",  //下拉选框
                        editoptions: {
                            dataUrl: "${app}/depart/selectAllDepart",
                        },      //添加的后台路径
                        formatter: function (cellvalue, options, rowObject) {
                            return rowObject.depart.name;
                        }      //页面展示的返回name值
                    }
                ]

            }).jqGrid("navGrid", "#page", {})
        })
    </script>
</head>
<body>
<table id="tb"></table>
<div id="page"></div>
</body>
</html>