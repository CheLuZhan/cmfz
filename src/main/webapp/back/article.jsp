<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Article</title>
    <script>
        $(function () {
            $("#articleList").jqGrid({
                url: "${pageContext.request.contextPath}/article/getAllArticle",//请求访问路径
                editurl: "${pageContext.request.contextPath}/article/edit",   //前台增删改请求走的路径
                datatype: "json",//数据传输的类型(后台传来字符串通过json.parse()解析为js对象)
                colNames: ["编号", "标题", "作者", "内容", "状态", "时间", "操作"],//表头数组
                colModel: [
                    {name: "id", editable: true},
                    {name: "title", editable: true},
                    {name: "author", editable: true},
                    {name: "content", hidden: true},
                    {
                        name: "state", editable: true, edittype: "select",
                        editoptions: {value: "T:显示;Y:不显示"},
                        formatter: function (a, b, c) {
                            if (a == "T") {
                                return "展示";
                            } else {
                                return "不展示";
                            }
                        }
                    },
                    {name: "date"},
                    {
                        name: "",  //查看详细信息
                        formatter: function (a, b, c) {  //传递该行数据的id值
                            return "<a href='#' onclick='lookModel(\"" + c.id + "\")'>查看详细信息</a>"
                        }
                    }
                ],          //展示信息的类型
                styleUI: "Bootstrap",    //引入bootstrap格式
                autowidth: true,   //  自动适应页面宽度
                pager: "#articlePager",   //分页栏
                rowNum: 2,   //默认每页展示的条数
                page: 1,     //默认展示的页数
                rowList: [2, 4, 6, 8],  //选择每页展示的条数
                multiselect: true,   //多选
                viewrecords: true,   //共几条
                height: "100%",

            }).jqGrid("navGrid", "#articlePager",
                {//处理前台按钮组的样式 以及是否展示
                    search: false
                },
                {//控制编辑按钮，再编辑之前之后进行的额外操作，
                    beforeShowForm: function (obj) {
                        obj.find("#author").attr("disabled", true)
                    },
                    afterSubmit: function (response) {
                        $("#articleList").trigger("reloadGrid");
                        return "ss";
                    }
                },
                {//控制添加按钮，再添加之前之后进行的额外操作，
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var bannerId = response.responseText; //响应页面传来大的id值
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/article/upload",
                            fileElementId: "src",
                            data: {"bid": bannerId},  //传递的id
                            success: function (data) {
                                $("#articleList").trigger("reloadGrid");
                            }
                        })
                        return "123"
                    }
                },
                {//控制删除按钮，再删除之前之后进行的额外操作，

                });


            //form表单提交内容
            $("#but").click(function () {
                var text = $("#exampleInputName2").val();
                $("#articleList").jqGrid('setGridParam', {
                    url: "${pageContext.request.contextPath}/article/search?query=" + text,//请求访问路径
                    editurl: "${pageContext.request.contextPath}/article/edit",   //前台增删改请求走的路径
                    datatype: "json",//数据传输的类型(后台传来字符串通过json.parse()解析为js对象)
                    postdata: [
                        {name: "id", editable: true},
                        {name: "title", editable: true},
                        {name: "author", editable: true},
                        {name: "content", hidden: true},
                        {
                            name: "state", editable: true, edittype: "select",
                            editoptions: {value: "T:显示;Y:不显示"},
                            formatter: function (a, b, c) {
                                if (a == "T") {
                                    return "展示";
                                } else {
                                    return "不展示";
                                }
                            }
                        },
                        {name: "date"},
                        {
                            name: "",  //查看详细信息
                            formatter: function (a, b, c) {  //传递该行数据的id值
                                return "<a href='#' onclick='lookModel(\"" + c.id + "\")'>查看详细信息</a>"
                            }
                        }
                    ],          //展示信息的类型
                    styleUI: "Bootstrap",    //引入bootstrap格式
                    autowidth: true,   //  自动适应页面宽度
                    pager: "#articlePager",   //分页栏
                    rowNum: 2,   //默认每页展示的条数
                    page: 1,     //默认展示的页数
                    rowList: [2, 4, 6, 8],  //选择每页展示的条数
                    multiselect: true,   //多选
                    viewrecords: true,   //共几条
                    height: "100%"
                }).trigger("reloadGrid")
            })
        });

        //展示添加文章的模态框
        function showModel() {
            $("#myModal").modal("show");
            $("#modal_footer").html(
                "<button type=\"button\" onclick='addArticle()' class=\"btn btn-primary\">保存</button>\n" +
                "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
            )
            KindEditor.create("#editor", {   //创建编辑器
                //上传下载图片
                uploadJson: "${pageContext.request.contextPath}/kindeditor/upload",
                filePostName: "img",
                fileManagerJson: "${pageContext.request.contextPath}/kindeditor/getAllImg",
                allowFileManager: true,
                afterBlur: function () { //丢失焦点 将文本域内容同步到表单name值中
                    this.sync();
                }
            });
            $("#addArticleFrom")[0].reset(); // 每次打开form表单清除之前的记录
            KindEditor.html("#editor", "");
        }

        //添加文章addArticle()事件
        function addArticle() {
            $.ajax({
                url: "${pageContext.request.contextPath}/article/addArticle",
                datatype: "json",
                type: "post",
                data: $("#addArticleFrom").serialize(),
                success: function (data) {
                    $("#myModal").modal("toggle");//关闭模态框
                    $("#articleList").trigger("reloadGrid");
                }

            })
        }

        //查看详细信息
        function lookModel(id) {    //id 为当前文章的id
            $("#myModal").modal("show");//展示模态框
            //动态生成按钮
            $("#modal_footer").html(
                "<button type=\"button\" onclick='updateArticle(\"" + id + "\")' class=\"btn btn-primary\">修改</button>\n" +
                "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
            );
            //设置回显
            var article = $("#articleList").getRowData(id);//通过id获取对象
            $("#title").val(article.title);
            $("#author").val(article.author);
            if (article.state == "展示") {
                $("#state").val("T");
            } else if (article.state == "不展示") {
                $("#state").val("Y");
            }
            KindEditor.create("#editor", {   //创建编辑器
                //上传下载图片
                uploadJson: "${pageContext.request.contextPath}/kindeditor/upload",
                filePostName: "img",
                fileManagerJson: "${pageContext.request.contextPath}/kindeditor/getAllImg",
                allowFileManager: true,
                afterBlur: function () { //丢失焦点 将文本域内容同步到表单name值中
                    this.sync();
                }
            });
            KindEditor.html("#editor", "");
            KindEditor.appendHtml("#editor", article.content);

        }

        //修改文章updateArticle(id)事件
        function updateArticle(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/article/updateArticle?id=" + id,
                datatype: "json",
                type: "post",
                data: $("#addArticleFrom").serialize(),
                success: function (data) {
                    $("#myModal").modal("toggle");
                    $("#articleList").trigger("reloadGrid");
                }
            })
        }


    </script>
</head>


<body>
<%--输入框--%>
<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <input id="exampleInputName2" type="text" class="form-control" placeholder="Search for...">
            <span class="input-group-btn">
        <button id="but" class="btn btn-default" type="button">查询</button>
      </span>
        </div><!-- /input-group -->
    </div><!-- /.col-lg-6 -->
</div><!-- /.row -->


<%--标签页--%>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">文章展示</a></li>
        <li role="presentation"><a href="#profile" onclick="showModel()" aria-controls="profile" role="tab"
                                   data-toggle="tab">添加文章</a></li>
    </ul>
</div>

<%--表格--%>
<table id="articleList"></table>
<%--分页栏--%>
<div id="articlePager"></div>

<%--模态框--%>
<div class="modal fade" id="myModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>

            <!--模态框内容体-->
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/article/editArticle" class="form-horizontal"
                      id="addArticleFrom">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">作者</label>
                        <div class="col-sm-5">
                            <input type="text" name="author" id="author" placeholder="请输入作者" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="state" id="state">
                                <option value="T">展示</option>
                                <option value="Y">不展示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">
                <%--<button type="button" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>--%>
            </div>
        </div>
    </div>
</div>

</body>
</html>