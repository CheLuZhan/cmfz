<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Album</title>
    <script>
        $(function () {
            $("#albumList").jqGrid({
                url: "${pageContext.request.contextPath}/album/getAllAlbum",//请求访问路径
                editurl: "${pageContext.request.contextPath}/album/edit",   //前台增删改请求走的路径
                datatype: "json",//数据传输的类型(后台传来字符串通过json.parse()解析为js对象)
                colNames: ["编号", "标题", "分数", "作者", "播音员", "章节数", "专辑简介", "状态", "发行时间", "上传时间", "插图"],//表头数组
                colModel: [
                    {name: "id"},
                    {name: "title", editable: true},
                    {name: "grade", editable: true},
                    {name: "author", editable: true},
                    {name: "announcer", editable: true},
                    {name: "number"},
                    {name: "intro", editable: true},
                    {name: "state", editable: true},
                    {name: "publishTime", editable: true, edittype: "date"},
                    {name: "uploadTime"},
                    {
                        name: "src", editable: true, edittype: "file",
                        formatter: function (cellvalue, options, rowObject) {//渲染页面
                            return "<img style='width:120px;height:60px' src='${pageContext.request.contextPath}/img/" + cellvalue + "'>"
                        }
                    }
                ],          //展示信息的类型
                styleUI: "Bootstrap",    //引入bootstrap格式
                autowidth: true,   //  自动适应页面宽度
                pager: "#albumPager",   //分页栏
                rowNum: 2,   //默认每页展示的条数
                page: 1,     //默认展示的页数
                rowList: [2, 4, 6, 8],  //选择每页展示的条数
                multiselect: true,   //多选
                viewrecords: true,   //共几条
                height: "100%",
                subGrid: true,
                subGridRowExpanded: function (subGridId, albumId) {
                    var tableId = subGridId + "table";
                    var pagerId = subGridId + "pager";
                    $("#" + subGridId).html(
                        "<table id=" + tableId + "></table>\n" +
                        "<div id=" + pagerId + "></div>"
                    );
                    $("#" + tableId).jqGrid({
                        url: "${pageContext.request.contextPath}/chapter/getAllChapter?aid=" + albumId,//请求访问路径
                        editurl: "${pageContext.request.contextPath}/chapter/edit?aid=" + albumId,   //前台增删改请求走的路径
                        datatype: "json",//数据传输的类型(后台传来字符串通过json.parse()解析为js对象)
                        colNames: ["标题", "大小", "时长", "上传时间", "音频", "操作"],//表头数组
                        colModel: [
                            {name: "title", editable: true},
                            {name: "size"},
                            {name: "time"},
                            {name: "uploadTime"},
                            {
                                name: "music", editable: true, edittype: "file"
                                /*   formatter:function (value,option,rows) {
                                       return"<audio controls loop>\n" +
                                           "  <source src=\"/audio/"+value+"\" type=\"audio/mpeg\">\n" +
                                           "</audio>";
                                   }*/
                            },
                            {
                                name: "",
                                formatter: function (value, option, rows) {/*value:name的属性值| option:该表格的信息 |row:该行的信息*/
                                    return "<a href='#'><span onclick='playAudio(\"" + rows.music + "\")' class='glyphicon glyphicon-play-circle'></span></a>" + '   ' +
                                        "<a href='#'><span onclick='downAudio(\"" + rows.music + "\")' class='glyphicon glyphicon-download'></span></a>  "
                                    /*转义拼接可用：return "<audio controls loop src=\"
                                    ${pageContext.request.contextPath}/audio/"+rows.music+"\"></audio>"*/
                                }
                            }
                        ],          //展示信息的类型
                        styleUI: "Bootstrap",    //引入bootstrap格式
                        autowidth: true,   //  自动适应页面宽度
                        pager: "#" + pagerId,   //分页栏
                        rowNum: 2,   //默认每页展示的条数
                        page: 1,     //默认展示的页数
                        rowList: [2, 4, 6, 8],  //选择每页展示的条数
                        multiselect: true,   //多选
                        viewrecords: true,   //共几条
                        height: "100%"
                    }).jqGrid("navGrid", "#" + pagerId,
                        {
                            //处理前台按钮组的样式 以及是否展示
                        },
                        {  //控制编辑按钮，再编辑之前之后进行的额外操作，
                            closeAfterEdit: true,
                            beforeShowForm: function (obj) {
                                obj.find("#music").attr("disabled", true)
                                obj.find("#uploadTime").attr("disable", true)
                            },
                            afterSubmit: function (response) {
                                $("#" + tableId).trigger("reloadGrid");
                                return response;
                            }
                        },
                        {//控制添加按钮，再添加之前之后进行的额外操作，
                            closeAfterAdd: true,

                            afterSubmit: function (response) {
                                var chapterId = response.responseText;  //获取页面响应的id值
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    type: "json",
                                    fileElementId: "music",
                                    data: {"bid": chapterId, "albumId": albumId},
                                    success: function (data) {
                                        $("#" + tableId).trigger("reloadGrid");
                                    }
                                })
                                return "zzz"
                            }
                        },
                        {
                            //控制删除按钮，再删除之前之后进行的额外操作，
                        }
                    )
                }
            }).jqGrid("navGrid", "#albumPager",
                {
                    //处理前台按钮组的样式 以及是否展示
                },
                {  //控制编辑按钮，再编辑之前之后进行的额外操作，
                    closeAfterEdit: true,
                    beforeShowForm: function (obj) {
                        obj.find("#src").attr("disabled", true)
                    },
                    afterSubmit: function (response) {
                        $("#albumList").trigger("reloadGrid");
                        return response;
                    }
                },
                {//控制添加按钮，再添加之前之后进行的额外操作，
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var albumId = response.responseText;  //获取页面响应的id值
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            type: "json",
                            fileElementId: "src",
                            data: {"bid": albumId},
                            success: function (data) {
                                $("#albumList").trigger("reloadGrid");
                            }
                        })
                        return "zzz"
                    }
                },
                {
                    //控制删除按钮，再删除之前之后进行的额外操作，
                }
            )
        })

        function playAudio(c) {
            $("#audioModel").modal("show");
            $("#audio").attr("src", "${pageContext.request.contextPath}/audio/" + c);
        }

        function downAudio(d) {
            location.href = "${pageContext.request.contextPath}/chapter/down?music=" + d
        }
    </script>
</head>
<body>
<%--表格--%>
<table id="albumList"></table>
<%--分页栏--%>
<div id="albumPager"></div>
<%--模态框--%>
<div id="audioModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audio" controls loop src=""></audio>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>