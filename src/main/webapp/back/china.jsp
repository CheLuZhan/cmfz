<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="china" style="width: 600px;height: 600px;margin-top: 30px;margin-left: 30px">

</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('china'));

    function randomData() {
        return Math.round(Math.random() * 10000);
    }

    var option = {
        title: {
            text: '用户省份分布图',
            subtext: '2019年11月26日 最新数据',
            left: 'center'
        },
        tooltip: {},
        // 说明
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男生', '女生']
        },
        visualMap: {
            min: 0,
            max: 30000,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],           // 文本，默认为数值文本
            calculable: true
        },
        // 工具箱
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '男生',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                }
            },
            {
                name: '女生',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                }
            }
        ]
    };

    $.ajax({
        url: "${pageContext.request.contextPath}/use/selectByProvince",
        type: "post",
        datatype: "json",
        success: function (data) {
            myChart.setOption({
                series: [
                    {
                        data: data.M
                    },
                    {
                        data: data.W
                    }
                ]
            })
        }
    })
    myChart.setOption(option);

</script>












