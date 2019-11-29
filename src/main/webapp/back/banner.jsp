<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Banner</title>
    <script>
        $(function () {
            $("#bannerList").jqGrid({
                url: "${pageContext.request.contextPath}/banner/getAllBanner",//请求访问路径
                editurl: "${pageContext.request.contextPath}/banner/edit",   //前台增删改请求走的路径
                datatype: "json",//数据传输的类型(后台传来字符串通过json.parse()解析为js对象)
                colNames: ["编号", "标题", "状态", "描述", "创建时间", "图片"],//表头数组
                colModel: [
                    {name: "id"},
                    {name: "title", editable: true},
                    {name: "state", editable: true},
                    {name: "description", editable: true},
                    {name: "date"},
                    {
                        name: "src", editable: true, edittype: "file",
                        formatter: function (cellvalue, options, rowObject) {
                            return "<img style='width:120px;height:60px' src='${pageContext.request.contextPath}/img/" + cellvalue + "'>"
                        }
                    }
                ],          //展示信息的类型
                styleUI: "Bootstrap",    //引入bootstrap格式
                autowidth: true,   //  自动适应页面宽度
                pager: "#bannerPager",   //分页栏
                rowNum: 2,   //默认每页展示的条数
                page: 1,     //默认展示的页数
                rowList: [2, 4, 6, 8],  //选择每页展示的条数
                multiselect: true,   //多选
                viewrecords: true,   //共几条
                height: "100%",

            }).jqGrid("navGrid", "#bannerPager",
                {//处理前台按钮组的样式 以及是否展示
                    search: false
                },
                {//控制编辑按钮，再编辑之前之后进行的额外操作，
                    beforeShowForm: function (obj) {
                        obj.find("#src").attr("disabled", true)
                    },
                    afterSubmit: function (response) {
                        $("#bannerList").trigger("reloadGrid");
                        return "ss";
                    }
                },
                {//控制添加按钮，再添加之前之后进行的额外操作，
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var bannerId = response.responseText; //响应页面传来大的id值
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            fileElementId: "src",
                            data: {"bid": bannerId},  //传递的id
                            success: function (data) {
                                $("#bannerList").trigger("reloadGrid");
                            }
                        })
                        return "123"
                    }
                },
                {//控制删除按钮，再删除之前之后进行的额外操作，

                }
            )
        });

        function down() {
            location.href = "${pageContext.request.contextPath}/banner/down";
            /*  $.ajax({
                  url:"
            ${pageContext.request.contextPath}/banner/down",
                type:"post",
                success:function (data) {
                }
            })*/
        }

    </script>
</head>
<body>

<%--标签页--%>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">轮播图列表</a></li>
        <li role="presentation"><a href="#profile" onclick="down()" aria-controls="profile" role="tab"
                                   data-toggle="tab">下载文档</a></li>
    </ul>
</div>

<%--表格--%>
<table id="bannerList"></table>
<%--分页栏--%>
<div id="bannerPager"></div>
</body>
</html>