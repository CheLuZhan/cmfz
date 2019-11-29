<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户活跃度'
        },
        tooltip: {},
        legend: {
            data: ['活跃度']
        },
        xAxis: {
            data: ["一周内", "两周内", "三周内"]
        },
        yAxis: {},
        series: [{
            name: '活跃度',
            type: 'bar',
        }]
    };

    $.ajax({
        url: "${pageContext.request.contextPath}/use/selectState",
        datatype: "json",
        type: "post",
        success: function (data) {
            myChart.setOption({
                series: [{
                    data: data
                }]
            });
        }
    })
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    //初始化对象
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-0a8a85328dd5485aa6fa93b71ce91d22", //替换为您的应用appkey
    });
    //接收消息
    goEasy.subscribe({
        channel: "my_channel", //替换为您自己的channel
        onMessage: function (message) {
            $.ajax({
                url: "${pageContext.request.contextPath}/use/selectState",
                datatype: "json",
                type: "post",
                success: function (data) {
                    myChart.setOption({
                        series: [{
                            data: data
                        }]
                    });
                }
            })
        }
    });
</script>
