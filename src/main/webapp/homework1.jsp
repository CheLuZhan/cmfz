<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script>
        $(function () {
            $("#bun").click(function () {
                var str = $("#ip").val();
                if (str != "") {
                    //屏蔽浏览器差异
                    var xhr;
                    if (window.XMLHttpRequest) {//if中默认判断为true 是webkit内核
                        xhr = new XMLHttpRequest();
                    } else {
                        xhr = new ActiveXObject("Microsoft.XMLHttp");
                    }
                    //发送请求传送数据
                    xhr.open("post", "${pageContext.request.contextPath}/comment/add");
                    xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded")
                    xhr.send("str=" + str);
                    $("#ip").val("");

                    //处理响应
                    xhr.onreadystatechange = function () {
                        //判断正确完整的响应
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            var str = xhr.responseText;
                            //将json串转化为集合对象!!
                            var list = eval("(" + str + ")");

                            $("ul").remove();
                            //循环
                            $.each(list, function (index, comment) {
                                var commli = $("<li>").html("comm" + comment.comm);
                                var ul = $("<ul>").append(commli);
                                $("#bd").append(ul);
                            })
                        }

                    }
                }

            })
        })
    </script>
</head>
<body id="bd">
<img src="${pageContext.request.contextPath}/img/xuren.jpg">
<input id="ip" type="text"/>

<button id="bun">发送</button>

</body>
</html>